package am.itspace.controller;


import am.itspace.model.Book;
import am.itspace.model.User;
import am.itspace.pagination.PageWrapper;
import am.itspace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/userPage")
    public String authorPage(ModelMap modelMap,
                             @RequestParam(name = "msg", required = false) String msg,
                             @RequestParam(value = "page", defaultValue = "1") int page,
                             @RequestParam(value = "size", defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Order.asc("name")));
        Page<User> users = userService.findAllPageUser(pageRequest);
        PageWrapper<User> pageWrapper = new PageWrapper<>(users, "/userPage");
        int totalPages = users.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        modelMap.addAttribute("users", users);
        modelMap.addAttribute("msg", msg);
        return "userPage/users";
    }

    @GetMapping("/deleteUser")
    public String deleteAuthor(@RequestParam("id") int id, ModelMap map) {
        String msg = "User was delete";
        userService.deleteById(id);
        map.addAttribute("msg", msg);
        return "redirect:/userPage/?msg=" + msg;

    }

    @GetMapping("userPage/editUser")
    public String authorByIde(@RequestParam("id") int id, ModelMap map) {
        User user = userService.getOne(id);
        map.addAttribute("user", user);
        return "userPage/editUser";
    }

    @PostMapping("/editUser")
    public String changeUser(@ModelAttribute User user, ModelMap map) {
        String msg = "User was updated";
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        map.addAttribute("msg", msg);
        return "redirect:/?msg=" + msg;
    }


}
