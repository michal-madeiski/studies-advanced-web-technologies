package pl.edu.pwr.ztw.books.service;

import pl.edu.pwr.ztw.books.model.Book;
import java.util.Collection;

public interface IBooksService {
    Collection<Book> getBooks();
    Book getBook(int id);
    Collection<Book> getBooksByAuthor(int authorId);
    Book createBook(String title, int authorId, int pages);
    Book updateBook(int id, String title, int authorId, int pages);
    boolean deleteBook(int id);
}