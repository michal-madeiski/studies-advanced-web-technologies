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
    private static int nextId = 1;

    @Autowired
    private IBooksService booksService;

    @Autowired
    private IReadersService readersService;

    public void initializeLoans() {
        Book book1 = booksService.getBook(1);
        Book book2 = booksService.getBook(2);
        Reader reader1 = readersService.getReader(1);
        Reader reader2 = readersService.getReader(2);
        Reader reader3 = readersService.getReader(3);

        if (book1 != null && book2 != null && reader1 != null && reader2 != null && reader3 != null) {
            loansRepo.add(new Loan(1, book1, reader1, LocalDate.of(2024, 1, 15)));
            loansRepo.add(new Loan(2, book2, reader2, LocalDate.of(2024, 2, 10)));
            loansRepo.add(new Loan(3, book1, reader3, LocalDate.of(2024, 3, 5), LocalDate.of(2024, 3, 20)));
        }
    }

    @Override
    public Collection<Loan> getLoans() {
        return loansRepo;
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
