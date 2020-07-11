package am.itspace.controller;

import am.itspace.model.Author;
import am.itspace.model.Book;
import am.itspace.repository.AuthorRepository;
import am.itspace.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    AuthorRepository authorRepo;
    @Autowired
    BookRepository bookRepo;


    @GetMapping("/")
    public String homePage(ModelMap modelMap) {
        List<Author> allAuthor = authorRepo.findAll();
        modelMap.addAttribute("authors",allAuthor);
        return "home";
    }


    @PostMapping("/addAuthor")
    public String addAuthor (@ModelAttribute Author author) {
        authorRepo.save(author);
        return "redirect:/";
    }


    @GetMapping("/authorPage")
    public String authorPage(ModelMap modelMap) {
        List<Author> allAuthor = authorRepo.findAll();
        modelMap.addAttribute("authors",allAuthor);
        return "authorPage/author";
    }


    @GetMapping("/bookPage")
    public String bookPage(ModelMap modelMap) {
        List<Book> allBooks = bookRepo.findAll();
        modelMap.addAttribute("books",allBooks);
        return "bookPage/book";
    }

    @PostMapping("/addBook")
    public String addBook (@ModelAttribute Book book) {
        bookRepo.save(book);
        return "redirect:/";
    }

    //Delete

    @GetMapping("/deleteAuthor")
    public String deleteAuthor (@RequestParam("id") int id){
        authorRepo.deleteById(id);
        return "redirect:/authorPage";
    }

    @GetMapping("/deleteBook")
    public String deleteBook (@RequestParam("id") int id){
        bookRepo.deleteById(id);
        return "redirect:/bookPage";
    }

}
