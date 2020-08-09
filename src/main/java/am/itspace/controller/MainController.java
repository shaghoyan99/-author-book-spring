package am.itspace.controller;

import am.itspace.model.Book;
import am.itspace.model.Role;
import am.itspace.model.User;
import am.itspace.repository.UserRepository;
import am.itspace.security.CurrentUser;
import am.itspace.service.BookService;
import am.itspace.service.EmailService;
import am.itspace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MainController {

    @Value("${file.upload.dir}")
    private String uploadDir;
    private final BookService bookService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @GetMapping("/")
    public String homePage(Model modelMap, @RequestParam(name = "msg", required = false) String msg) {
        List<User> allUser = userService.findAll();
        modelMap.addAttribute("users", allUser);
        modelMap.addAttribute("msg", msg);
        return "home";
    }

    @PostMapping("/addBook")
    public String addBook(@ModelAttribute Book book) {
        String msg = "Book was added";
        bookService.save(book);
        return "redirect:/?msg=" + msg;
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user, @RequestParam("image") MultipartFile file) throws IOException {
        String msg;
        if (!user.getPassword().equals(user.getConfimPassword())) {
            msg = "Password and Confirm Password does not match!";
            return "redirect:/?msg=" + msg;
        }
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail.isPresent()) {
            msg = "User already exists";
            return "redirect:/?msg=" + msg;
        }
        String name = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File image = new File(uploadDir, name);
        file.transferTo(image);
        user.setProfilePic(name);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(false);
        user.setToken(UUID.randomUUID().toString());
        userService.save(user);
        String link = "http://localhost:8080/activate?email=" + user.getEmail() + "&token=" + user.getToken();
        msg = "User was added";
        emailService.setMailSender(user.getEmail(), "Welcome", " Dear " + " " + user.getName() + " You have successfully registered. Please activate yor" +
                " account by cliking on: " + link);
        return "redirect:/?msg=" + msg;
    }


    @GetMapping(
            value = "/image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody
    byte[] getImage(@RequestParam("name") String imageName) throws IOException {
        InputStream in = new FileInputStream(uploadDir + File.separator + imageName);
        return IOUtils.toByteArray(in);
    }


    @GetMapping("/loginPage")
    public String loginPage() {
        return "loginPage";
    }

    @GetMapping("/successLogin")
    public String successLogin(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser == null) {
            return "redirect:/";
        }
        User user = currentUser.getUser();
        if (user.getRole() == Role.ADMIN) {
            return "redirect:/userPage";
        } else {
            return "redirect:/bookPage";
        }
    }

    @GetMapping("/activate")
    public String activateUser(@RequestParam("email") String email, @RequestParam("token") String token) {
        String msg;
        Optional<User> byEmail = userService.findByEmail(email);
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            if (user.getToken().equals(token)) {
                user.setActive(true);
                user.setToken("");
                userService.save(user);
                msg = "User was activate";
                return "redirect:/?msg=" + msg;
            }
        }
        msg = "Something went wrong. Please try again";
        return "redirect:/?msg=" + msg;
    }

    @RequestMapping("/403")
    public String accessDenied() {
        return "forbiddenPage";
    }
}
