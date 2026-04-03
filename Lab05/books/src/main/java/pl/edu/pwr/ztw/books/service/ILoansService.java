package pl.edu.pwr.ztw.books.service;

import pl.edu.pwr.ztw.books.model.Loan;
import java.util.Collection;

public interface ILoansService {
    Collection<Loan> getLoans();
    Loan getLoan(int id);
    Collection<Loan> getLoansByReader(int readerId);
    Collection<Loan> getLoansByBook(int bookId);
    Collection<Loan> getActiveLoans();
    Loan createLoan(int bookId, int readerId);
    Loan returnBook(int loanId);
    boolean deleteLoan(int id);
}
