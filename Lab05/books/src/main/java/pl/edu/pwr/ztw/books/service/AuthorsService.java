package pl.edu.pwr.ztw.books.service;

import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.model.Author;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AuthorsService implements IAuthorsService {
    private static List<Author> authorsRepo = new ArrayList<>();
    private static int nextId = 4;

    static {
        authorsRepo.add(new Author(1, "Henryk Sienkiewicz"));
        authorsRepo.add(new Author(2, "Stanisław Reymont"));
        authorsRepo.add(new Author(3, "Adam Mickiewicz"));
    }

    @Override
    public Collection<Author> getAuthors() {
        return authorsRepo;
    }

    @Override
    public Author getAuthor(int id) {
        return authorsRepo.stream()
                .filter(a -> a.getId() == id)
                .findAny()
                .orElse(null);
    }

    @Override
    public Author createAuthor(String name) {
        Author newAuthor = new Author(nextId++, name);
        authorsRepo.add(newAuthor);
        return newAuthor;
    }

    @Override
    public Author updateAuthor(int id, String name) {
        Author author = getAuthor(id);
        if (author != null) {
            author.setName(name);
        }
        return author;
    }

    @Override
    public boolean deleteAuthor(int id) {
        return authorsRepo.removeIf(a -> a.getId() == id);
    }
}
