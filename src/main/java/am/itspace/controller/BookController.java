package am.itspace.controller;

import am.itspace.model.Author;
import am.itspace.model.Book;
import am.itspace.service.AuthorService;
import am.itspace.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;


    @GetMapping("/bookPage")
    public String bookPage(ModelMap modelMap) {
        List<Book> allBooks = bookService.findAll();
        modelMap.addAttribute("books", allBooks);
        return "bookPage/book";
    }

    @GetMapping("/deleteBook")
    public String deleteBook(@RequestParam("id") int id) {
        String msg = "Book was delete";
        bookService.deleteById(id);
        return "redirect:/bookPage/?msg=" + msg;
    }

    @PostMapping("/editBook")
    public String add(@ModelAttribute Book book) {
        String msg = "Book was updated";
        bookService.save(book);
        return "redirect:/?msg=" + msg;
    }

    @GetMapping("bookPage/editBook")
    public String edit(@RequestParam("id") int id, Model model) {
        Book book = bookService.getOne(id);
        List<Author> authors = authorService.findAll();
        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
        return "bookPage/editBook";
    }

}
