package pl.edu.pwr.ztw.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.model.Loan;
import pl.edu.pwr.ztw.books.service.ILoansService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@Tag(name = "Loans", description = "API for managing book loans")
public class LoansController {

    @Autowired
    ILoansService loansService;

    @RequestMapping(value = "/loans", method = RequestMethod.GET)
    @Operation(summary = "Get all loans with pagination")
    public ResponseEntity<Object> getLoans(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(loansService.getLoansCount()))
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(loansService.getLoans(page, size));
    }

    @RequestMapping(value = "/loans/{id}", method = RequestMethod.GET)
    @Operation(summary = "Get loan details")
    public ResponseEntity<Object> getLoan(@Parameter(description = "Loan ID", example = "1") @PathVariable("id") int id) {
        Loan loan = loansService.getLoan(id);
        if (loan != null) {
            return new ResponseEntity<>(loan, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Loan not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/loans/readers/{readerId}", method = RequestMethod.GET)
    @Operation(summary = "Get loans by reader")
    public ResponseEntity<Object> getLoansByReader(@Parameter(description = "Reader ID", example = "1") @PathVariable("readerId") int readerId) {
        return new ResponseEntity<>(loansService.getLoansByReader(readerId), HttpStatus.OK);
    }

    @RequestMapping(value = "/loans/books/{bookId}", method = RequestMethod.GET)
    @Operation(summary = "Get loans by book")
    public ResponseEntity<Object> getLoansByBook(@Parameter(description = "Book ID", example = "1") @PathVariable("bookId") int bookId) {
        return new ResponseEntity<>(loansService.getLoansByBook(bookId), HttpStatus.OK);
    }

    @RequestMapping(value = "/loans/active", method = RequestMethod.GET)
    @Operation(summary = "Get active loans")
    public ResponseEntity<Object> getActiveLoans() {
        return new ResponseEntity<>(loansService.getActiveLoans(), HttpStatus.OK);
    }

    @RequestMapping(value = "/loans", method = RequestMethod.POST)
    @Operation(summary = "Create new loan")
    public ResponseEntity<Object> createLoan(@Parameter(description = "Loan data") @RequestBody Loan loan) {
        Loan createdLoan = loansService.createLoan(loan.getBook().getId(), loan.getReader().getId());
        if (createdLoan != null) {
            return new ResponseEntity<>(createdLoan, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Book or Reader not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/loans/{loanId}", method = RequestMethod.PUT)
    @Operation(summary = "Return borrowed book")
    public ResponseEntity<Object> returnBook(@Parameter(description = "Loan ID", example = "1") @PathVariable("loanId") int loanId) {
        Loan returnedLoan = loansService.returnBook(loanId);
        if (returnedLoan != null) {
            return new ResponseEntity<>(returnedLoan, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Loan not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/loans/{id}", method = RequestMethod.DELETE)
    @Operation(summary = "Delete loan")
    public ResponseEntity<Object> deleteLoan(@Parameter(description = "Loan ID", example = "1") @PathVariable("id") int id) {
        boolean deleted = loansService.deleteLoan(id);
        if (deleted) {
            return new ResponseEntity<>("Loan deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Loan not found", HttpStatus.NOT_FOUND);
        }
    }
}
