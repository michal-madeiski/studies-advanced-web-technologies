package pl.edu.pwr.ztw.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.model.Book;
import pl.edu.pwr.ztw.books.model.Loan;
import pl.edu.pwr.ztw.books.model.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoansService implements ILoansService {
    private static List<Loan> loansRepo = new ArrayList<>();
    private static int nextId = 16;

    @Autowired
    private IBooksService booksService;

    @Autowired
    private IReadersService readersService;

    public void initializeLoans() {
        Book b1  = booksService.getBook(1);
        Book b2  = booksService.getBook(2);
        Book b3  = booksService.getBook(3);
        Book b4  = booksService.getBook(4);
        Book b5  = booksService.getBook(5);
        Book b6  = booksService.getBook(6);
        Book b7  = booksService.getBook(7);
        Book b8  = booksService.getBook(8);
        Book b9  = booksService.getBook(9);
        Book b10 = booksService.getBook(10);
        Book b11 = booksService.getBook(11);
        Book b12 = booksService.getBook(12);
        Book b13 = booksService.getBook(13);
        Book b14 = booksService.getBook(14);
        Reader r1  = readersService.getReader(1);
        Reader r2  = readersService.getReader(2);
        Reader r3  = readersService.getReader(3);
        Reader r4  = readersService.getReader(4);
        Reader r5  = readersService.getReader(5);
        Reader r6  = readersService.getReader(6);
        Reader r7  = readersService.getReader(7);
        Reader r8  = readersService.getReader(8);
        Reader r9  = readersService.getReader(9);
        Reader r10 = readersService.getReader(10);
        Reader r11 = readersService.getReader(11);
        Reader r12 = readersService.getReader(12);
        Reader r13 = readersService.getReader(13);
        Reader r14 = readersService.getReader(14);
        Reader r15 = readersService.getReader(15);

        loansRepo.add(new Loan(1,  b1,  r1,  LocalDate.of(2024, 1, 15)));
        loansRepo.add(new Loan(2,  b2,  r2,  LocalDate.of(2024, 2, 10)));
        loansRepo.add(new Loan(3,  b1,  r3,  LocalDate.of(2024, 3,  5), LocalDate.of(2024, 3, 20)));
        loansRepo.add(new Loan(4,  b3,  r4,  LocalDate.of(2024, 4,  1)));
        loansRepo.add(new Loan(5,  b4,  r5,  LocalDate.of(2024, 4, 15), LocalDate.of(2024, 5,  1)));
        loansRepo.add(new Loan(6,  b5,  r6,  LocalDate.of(2024, 5, 10)));
        loansRepo.add(new Loan(7,  b6,  r7,  LocalDate.of(2024, 6,  1), LocalDate.of(2024, 6, 20)));
        loansRepo.add(new Loan(8,  b7,  r8,  LocalDate.of(2024, 7,  5)));
        loansRepo.add(new Loan(9,  b8,  r9,  LocalDate.of(2024, 8, 10), LocalDate.of(2024, 8, 25)));
        loansRepo.add(new Loan(10, b9,  r10, LocalDate.of(2024, 9,  1)));
        loansRepo.add(new Loan(11, b10, r11, LocalDate.of(2024, 10, 15)));
        loansRepo.add(new Loan(12, b11, r12, LocalDate.of(2024, 11,  1), LocalDate.of(2024, 11, 20)));
        loansRepo.add(new Loan(13, b12, r13, LocalDate.of(2024, 12,  5)));
        loansRepo.add(new Loan(14, b13, r14, LocalDate.of(2025, 1, 10), LocalDate.of(2025, 1, 25)));
        loansRepo.add(new Loan(15, b14, r15, LocalDate.of(2025, 2,  1)));
    }

    @Override
    public Collection<Loan> getLoans(int page, int size) {
        int fromIndex = page * size;
        if (fromIndex >= loansRepo.size()) return new ArrayList<>();
        int toIndex = Math.min(fromIndex + size, loansRepo.size());
        return loansRepo.subList(fromIndex, toIndex);
    }

    @Override
    public int getLoansCount() {
        return loansRepo.size();
    }

    @Override
    public Loan getLoan(int id) {
        return loansRepo.stream()
                .filter(l -> l.getId() == id)
                .findAny()
                .orElse(null);
    }

    @Override
    public Collection<Loan> getLoansByReader(int readerId) {
        return loansRepo.stream()
                .filter(l -> l.getReader().getId() == readerId)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Loan> getLoansByBook(int bookId) {
        return loansRepo.stream()
                .filter(l -> l.getBook().getId() == bookId)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Loan> getActiveLoans() {
        return loansRepo.stream()
                .filter(l -> !l.isReturned())
                .collect(Collectors.toList());
    }

    @Override
    public Loan createLoan(int bookId, int readerId) {
        Book book = booksService.getBook(bookId);
        Reader reader = readersService.getReader(readerId);

        if (book == null || reader == null) {
            return null;
        }

        Loan newLoan = new Loan(nextId++, book, reader, LocalDate.now());
        loansRepo.add(newLoan);
        return newLoan;
    }

    @Override
    public Loan returnBook(int loanId) {
        Loan loan = getLoan(loanId);
        if (loan != null && !loan.isReturned()) {
            loan.setReturnDate(LocalDate.now());
        }
        return loan;
    }

    @Override
    public boolean deleteLoan(int id) {
        return loansRepo.removeIf(l -> l.getId() == id);
    }
}
