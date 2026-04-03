package pl.edu.pwr.ztw.books.service;

import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.model.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ReadersService implements IReadersService {
    private static List<Reader> readersRepo = new ArrayList<>();
    private static int nextId = 4;

    static {
        readersRepo.add(new Reader(1, "Jan Kowalski", "jan.kowalski@mail.com"));
        readersRepo.add(new Reader(2, "Maria Nowak", "maria.nowak@mail.com"));
        readersRepo.add(new Reader(3, "Piotr Lewandowski", "piotr.lew@mail.com"));
    }

    @Override
    public Collection<Reader> getReaders() {
        return readersRepo;
    }

    @Override
    public Reader getReader(int id) {
        return readersRepo.stream()
                .filter(r -> r.getId() == id)
                .findAny()
                .orElse(null);
    }

    @Override
    public Reader createReader(String name, String email) {
        Reader newReader = new Reader(nextId++, name, email);
        readersRepo.add(newReader);
        return newReader;
    }

    @Override
    public Reader updateReader(int id, String name, String email) {
        Reader reader = getReader(id);
        if (reader != null) {
            reader.setName(name);
            reader.setEmail(email);
        }
        return reader;
    }

    @Override
    public boolean deleteReader(int id) {
        return readersRepo.removeIf(r -> r.getId() == id);
    }
}
