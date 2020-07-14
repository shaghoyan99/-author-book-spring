package am.itspace.controller;

import am.itspace.model.Author;
import am.itspace.model.Book;
import am.itspace.repository.AuthorRepository;
import am.itspace.repository.BookRepository;
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

    private final BookRepository bookRepo;
    private final AuthorRepository authorRepo;



    @GetMapping("/bookPage")
    public String bookPage(ModelMap modelMap) {
        List<Book> allBooks = bookRepo.findAll();
        modelMap.addAttribute("books", allBooks);
        return "bookPage/book";
    }

    @GetMapping("/deleteBook")
    public String deleteBook(@RequestParam("id") int id) {
        bookRepo.deleteById(id);
        return "redirect:/bookPage";
    }

    @PostMapping("/editBook")
    public String add(@ModelAttribute Book book) {
        bookRepo.save(book);
        return "redirect:/";
    }

    @GetMapping("bookPage/editBook")
    public String edit(@RequestParam("id") int id, Model model) {
        Book book = bookRepo.getOne(id);
        List<Author> authors = authorRepo.findAll();
        model.addAttribute("book",book);
        model.addAttribute("authors",authors);
        return "bookPage/editBook";
    }

}
