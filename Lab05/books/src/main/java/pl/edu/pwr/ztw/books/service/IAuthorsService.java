package pl.edu.pwr.ztw.books.service;

import pl.edu.pwr.ztw.books.model.Author;
import java.util.Collection;

public interface IAuthorsService {
    Collection<Author> getAuthors();
    Author getAuthor(int id);
    Author createAuthor(String name);
    Author updateAuthor(int id, String name);
    boolean deleteAuthor(int id);
}
