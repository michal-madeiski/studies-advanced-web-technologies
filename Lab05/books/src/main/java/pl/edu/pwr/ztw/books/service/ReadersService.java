package pl.edu.pwr.ztw.books.service;

import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.model.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ReadersService implements IReadersService {
    private static List<Reader> readersRepo = new ArrayList<>();
    private static int nextId = 16;

    static {
        readersRepo.add(new Reader(1, "Jan Kowalski", "jan.kowalski@mail.com"));
        readersRepo.add(new Reader(2, "Maria Nowak", "maria.nowak@mail.com"));
        readersRepo.add(new Reader(3, "Piotr Lewandowski", "piotr.lew@mail.com"));
        readersRepo.add(new Reader(4, "Anna Wiśniewska", "anna.wisn@mail.com"));
        readersRepo.add(new Reader(5, "Tomasz Wójcik", "t.wojcik@mail.com"));
        readersRepo.add(new Reader(6, "Katarzyna Kamińska", "k.kaminska@mail.com"));
        readersRepo.add(new Reader(7, "Michał Kowalczyk", "m.kowalczyk@mail.com"));
        readersRepo.add(new Reader(8, "Agnieszka Zielińska", "a.zielinska@mail.com"));
        readersRepo.add(new Reader(9, "Paweł Szymański", "p.szymanski@mail.com"));
        readersRepo.add(new Reader(10, "Magdalena Woźniak", "m.wozniak@mail.com"));
        readersRepo.add(new Reader(11, "Krzysztof Dąbrowski", "k.dabrowski@mail.com"));
        readersRepo.add(new Reader(12, "Barbara Kozłowska", "b.kozlowska@mail.com"));
        readersRepo.add(new Reader(13, "Marek Jankowski", "m.jankowski@mail.com"));
        readersRepo.add(new Reader(14, "Joanna Mazur", "j.mazur@mail.com"));
        readersRepo.add(new Reader(15, "Robert Krawczyk", "r.krawczyk@mail.com"));
    }

    @Override
    public Collection<Reader> getReaders(int page, int size) {
        int fromIndex = page * size;
        if (fromIndex >= readersRepo.size()) return new ArrayList<>();
        int toIndex = Math.min(fromIndex + size, readersRepo.size());
        return readersRepo.subList(fromIndex, toIndex);
    }

    @Override
    public int getReadersCount() {
        return readersRepo.size();
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
