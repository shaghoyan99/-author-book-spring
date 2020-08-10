package am.itspace.controller;

import am.itspace.model.User;
import am.itspace.model.Book;
import am.itspace.pagination.PageWrapper;
import am.itspace.service.UserService;
import am.itspace.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class BookController {

    private final BookService bookService;
    private final UserService userService;


    @GetMapping("/bookPage")
    public String bookPage(ModelMap modelMap,
                           @RequestParam(name = "msg", required = false) String msg,
                            @RequestParam(value = "page",defaultValue = "1" ) int page,
                            @RequestParam(value = "size" ,defaultValue = "10" ) int size,
                            @RequestParam(value = "orderBy" , defaultValue = "title") String orderBy,
                           @RequestParam(value = "order",defaultValue = "ASC") String order) {

        Sort sort = order.equals("ASC") ? Sort.by(Sort.Order.asc(orderBy)) : Sort.by(Sort.Order.desc(orderBy));
        PageRequest pageRequest = PageRequest.of(page - 1, size,sort);
        Page<Book> books = bookService.findAll(pageRequest);
        PageWrapper<Book> pageWrapper = new PageWrapper<>(books,"/bookPage");
        int totalPages = books.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        modelMap.addAttribute("books", books);
        modelMap.addAttribute("msg",msg);
        return "bookPage/books";
    }

    @GetMapping("/deleteBook")
    public String deleteBook(@RequestParam("id") int id, ModelMap map) {
        String msg = "Book was delete";
        bookService.deleteById(id);
        map.addAttribute("msg",msg);
        return "redirect:/bookPage/?msg=" + msg;
    }

    @PostMapping("/editBook")
    public String add(@ModelAttribute Book book, ModelMap map) {
        String msg = "Book was updated";
        bookService.save(book);
        map.addAttribute("msg",msg);
        return "redirect:/?msg=" + msg;
    }

    @GetMapping("bookPage/editBook")
    public String edit(@RequestParam("id") int id, Model model) {
        Book book = bookService.getOne(id);
        List<User> users = userService.findAll();
        model.addAttribute("book", book);
        model.addAttribute("users", users);
        return "bookPage/editBook";
    }

}
