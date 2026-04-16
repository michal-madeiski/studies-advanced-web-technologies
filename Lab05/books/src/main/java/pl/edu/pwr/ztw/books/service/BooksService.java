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
    private static int nextId = 16;

    @Autowired
    private IAuthorsService authorsService;

    @Autowired
    private ILoansService loansService;

    static { 
        
    }

    @Override
    public Collection<Book> getBooks(int page, int size) {
        int fromIndex = page * size;
        if (fromIndex >= booksRepo.size()) return new ArrayList<>();
        int toIndex = Math.min(fromIndex + size, booksRepo.size());
        return booksRepo.subList(fromIndex, toIndex);
    }

    @Override
    public int getBooksCount() {
        return booksRepo.size();
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
        if (pages <= 0) {
            return null;
        }
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
        if (pages <= 0) {
            return null;
        }
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
        boolean hasActiveLoans = loansService.getLoansByBook(id).stream()
                .anyMatch(loan -> !loan.isReturned());
        
        if (hasActiveLoans) {
            return false;
        }
        
        return booksRepo.removeIf(b -> b.getId() == id);
    }

    public void initializeBooks() {
        Author a1  = authorsService.getAuthor(1);
        Author a2  = authorsService.getAuthor(2);
        Author a3  = authorsService.getAuthor(3);
        Author a4  = authorsService.getAuthor(4);
        Author a5  = authorsService.getAuthor(5);
        Author a6  = authorsService.getAuthor(6);
        Author a7  = authorsService.getAuthor(7);
        Author a8  = authorsService.getAuthor(8);
        Author a10 = authorsService.getAuthor(10);
        Author a13 = authorsService.getAuthor(13);
        Author a14 = authorsService.getAuthor(14);
        Author a15 = authorsService.getAuthor(15);

        booksRepo.add(new Book(1,  "Potop",                    a1,  936));
        booksRepo.add(new Book(2,  "Wesele",                   a2,  150));
        booksRepo.add(new Book(3,  "Dziady",                   a3,  292));
        booksRepo.add(new Book(4,  "Lalka",                    a4,  823));
        booksRepo.add(new Book(5,  "Kordian",                  a5,  180));
        booksRepo.add(new Book(6,  "Nad Niemnem",              a6,  542));
        booksRepo.add(new Book(7,  "Ludzie bezdomni",          a7,  450));
        booksRepo.add(new Book(8,  "Stara baśń",               a8,  380));
        booksRepo.add(new Book(9,  "Pan Tadeusz",              a3,  430));
        booksRepo.add(new Book(10, "Krzyżacy",                 a1,  654));
        booksRepo.add(new Book(11, "Chłopi",                   a2,  680));
        booksRepo.add(new Book(12, "Solaris",                  a14, 204));
        booksRepo.add(new Book(13, "Bieguni",                  a15, 430));
        booksRepo.add(new Book(14, "Mąż i żona",               a10, 120));
        booksRepo.add(new Book(15, "Tango",                    a13,  95));
    }
}
