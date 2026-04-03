package pl.edu.pwr.ztw.books.service;

import pl.edu.pwr.ztw.books.model.Reader;
import java.util.Collection;

public interface IReadersService {
    Collection<Reader> getReaders();
    Reader getReader(int id);
    Reader createReader(String name, String email);
    Reader updateReader(int id, String name, String email);
    boolean deleteReader(int id);
}
