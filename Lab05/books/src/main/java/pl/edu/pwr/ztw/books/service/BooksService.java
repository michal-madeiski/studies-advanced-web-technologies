package pl.edu.pwr.ztw.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.model.Book;
import pl.edu.pwr.ztw.books.model.Author;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BooksService implements IBooksService {
    private static List<Book> booksRepo = new ArrayList<>();
    private static int nextId = 4;

    @Autowired
    private IAuthorsService authorsService;

    static { 
        // Initialized after authors are loaded
    }

    @Override
    public Collection<Book> getBooks() { 
        return booksRepo; 
    }

    @Override
    public Book getBook(int id) { 
        return booksRepo.stream() 
                .filter(b -> b.getId() == id) 
                .findAny() 
                .orElse(null); 
    }

    @Override
    public Collection<Book> getBooksByAuthor(int authorId) {
        return booksRepo.stream()
                .filter(b -> b.getAuthor().getId() == authorId)
                .collect(Collectors.toList());
    }

    @Override
    public Book createBook(String title, int authorId, int pages) {
        Author author = authorsService.getAuthor(authorId);
        if (author == null) {
            return null;
        }
        Book newBook = new Book(nextId++, title, author, pages);
        booksRepo.add(newBook);
        return newBook;
    }

    @Override
    public Book updateBook(int id, String title, int authorId, int pages) {
        Book book = getBook(id);
        if (book != null) {
            Author author = authorsService.getAuthor(authorId);
            if (author != null) {
                book.setTitle(title);
                book.setAuthor(author);
                book.setPages(pages);
            }
        }
        return book;
    }

    @Override
    public boolean deleteBook(int id) {
        return booksRepo.removeIf(b -> b.getId() == id);
    }

    // Helper method to initialize books with authors
    public void initializeBooks() {
        Author author1 = authorsService.getAuthor(1);
        Author author2 = authorsService.getAuthor(2);
        Author author3 = authorsService.getAuthor(3);
        
        if (author1 != null && author2 != null && author3 != null) {
            booksRepo.add(new Book(1, "Potop", author1, 936));
            booksRepo.add(new Book(2, "Wesele", author2, 150));
            booksRepo.add(new Book(3, "Dziady", author3, 292));
        }
    }
}
