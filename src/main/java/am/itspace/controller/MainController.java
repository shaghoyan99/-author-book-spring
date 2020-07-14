package am.itspace.controller;

import am.itspace.model.Author;
import am.itspace.model.Book;
import am.itspace.repository.AuthorRepository;

import am.itspace.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    @Value("${file.upload.dir}")
    private String uploadDir;
    private final AuthorRepository authorRepo;
    private final BookRepository bookRepo;


    @GetMapping("/")
    public String homePage(Model modelMap, @RequestParam(name = "msg", required = false) String msg) {
        List<Author> allAuthor = authorRepo.findAll();
        modelMap.addAttribute("authors", allAuthor);
        modelMap.addAttribute("msg", msg);
        return "home";
    }

    @PostMapping("/addBook")
    public String addBook(@ModelAttribute Book book) {
        String msg = book.getId() > 0 ? "Book was updated": "Book was added";
        bookRepo.save(book);
        return "redirect:/";
    }


    @PostMapping("/addAuthor")
    public String addAuthor(@ModelAttribute Author author, @RequestParam("image") MultipartFile file) throws IOException {
        String name = System.currentTimeMillis()+ "_" + file.getOriginalFilename();
        File image = new File(uploadDir,name);
        file.transferTo(image);
        author.setProfilePic(name);
        authorRepo.save(author);
        return "redirect:/";
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
}
