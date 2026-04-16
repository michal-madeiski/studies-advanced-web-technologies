package pl.edu.pwr.ztw.books.service;

import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.model.Author;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AuthorsService implements IAuthorsService {
    private static List<Author> authorsRepo = new ArrayList<>();
    private static int nextId = 16;

    static {
        authorsRepo.add(new Author(1, "Henryk Sienkiewicz"));
        authorsRepo.add(new Author(2, "Stanisław Reymont"));
        authorsRepo.add(new Author(3, "Adam Mickiewicz"));
        authorsRepo.add(new Author(4, "Bolesław Prus"));
        authorsRepo.add(new Author(5, "Juliusz Słowacki"));
        authorsRepo.add(new Author(6, "Eliza Orzeszkowa"));
        authorsRepo.add(new Author(7, "Stefan Żeromski"));
        authorsRepo.add(new Author(8, "Józef Ignacy Kraszewski"));
        authorsRepo.add(new Author(9, "Maria Konopnicka"));
        authorsRepo.add(new Author(10, "Aleksander Fredro"));
        authorsRepo.add(new Author(11, "Cyprian Kamil Norwid"));
        authorsRepo.add(new Author(12, "Wisława Szymborska"));
        authorsRepo.add(new Author(13, "Sławomir Mrożek"));
        authorsRepo.add(new Author(14, "Stanisław Lem"));
        authorsRepo.add(new Author(15, "Olga Tokarczuk"));
    }

    @Override
    public Collection<Author> getAuthors(int page, int size) {
        int fromIndex = page * size;
        if (fromIndex >= authorsRepo.size()) return new ArrayList<>();
        int toIndex = Math.min(fromIndex + size, authorsRepo.size());
        return authorsRepo.subList(fromIndex, toIndex);
    }

    @Override
    public int getAuthorsCount() {
        return authorsRepo.size();
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
