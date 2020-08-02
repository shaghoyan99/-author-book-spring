package am.itspace.controller;


import am.itspace.model.Author;
import am.itspace.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorController {


    private final AuthorService authorService;


    @GetMapping("/authorPage")
    public String authorPage(ModelMap modelMap) {
        List<Author> allAuthor = authorService.findAll();
        modelMap.addAttribute("authors", allAuthor);
        return "authorPage/author";
    }

    @GetMapping("/deleteAuthor")
    public String deleteAuthor(@RequestParam("id") int id) {
        String msg = "Author was delete";
        authorService.deleteById(id);
        return "redirect:/authorPage/?msg=" + msg;

    }

    @GetMapping("authorPage/editAuthor")
    public String authorByIde(@RequestParam("id") int id, ModelMap map) {
        Author author = authorService.getOne(id);
        map.addAttribute("author", author);
        return "authorPage/editAuthor";
    }

    @PostMapping("/editAuthor")
    public String changeAuthor(@ModelAttribute Author author) {
        String msg = "Author was updated";
        authorService.save(author);
        return "redirect:/?msg=" + msg;
    }


}
