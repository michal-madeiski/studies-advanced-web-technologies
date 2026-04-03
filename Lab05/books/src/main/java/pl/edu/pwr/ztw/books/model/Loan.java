package pl.edu.pwr.ztw.books.model;

import java.time.LocalDate;

public class Loan {
    private int id;
    private Book book;
    private Reader reader;
    private LocalDate loanDate;
    private LocalDate returnDate;

    public Loan(int id, Book book, Reader reader, LocalDate loanDate) {
        this.id = id;
        this.book = book;
        this.reader = reader;
        this.loanDate = loanDate;
        this.returnDate = null;
    }

    public int getId() { return id; }
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    public Reader getReader() { return reader; }
    public void setReader(Reader reader) { this.reader = reader; }
    public LocalDate getLoanDate() { return loanDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public boolean isReturned() { return returnDate != null; }
}
