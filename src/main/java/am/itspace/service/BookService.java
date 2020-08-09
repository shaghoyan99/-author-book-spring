package am.itspace.service;

import am.itspace.model.Book;
import am.itspace.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepo;

    public void save(Book book) {
     bookRepo.save(book);
    }

    public Book getOne(int id) {
        return bookRepo.getOne(id);
    }

    public Page<Book> findAll(PageRequest pageRequest) {
        return bookRepo.findAll(pageRequest);
    }

    public void deleteById(int id) {
        bookRepo.deleteById(id);
    }
}
