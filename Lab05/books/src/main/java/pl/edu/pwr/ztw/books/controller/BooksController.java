package pl.edu.pwr.ztw.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.model.Book;
import pl.edu.pwr.ztw.books.service.IBooksService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@Tag(name = "Books", description = "API for managing books")
public class BooksController {

    @Autowired
    IBooksService booksService;

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    @Operation(summary = "Get all books")
    public ResponseEntity<Object> getBooks() {
        return new ResponseEntity<>(booksService.getBooks(), HttpStatus.OK);
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.GET) 
    @Operation(summary = "Get book details")
    public ResponseEntity<Object> getBook(@Parameter(description = "Book ID", example = "1") @PathVariable("id") int id) {
        Book book = booksService.getBook(id);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/books/authors/{authorId}", method = RequestMethod.GET)
    @Operation(summary = "Get books by author")
    public ResponseEntity<Object> getBooksByAuthor(@Parameter(description = "Author ID", example = "1") @PathVariable("authorId") int authorId) {
        return new ResponseEntity<>(booksService.getBooksByAuthor(authorId), HttpStatus.OK);
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    @Operation(summary = "Create new book")
    public ResponseEntity<Object> createBook(@Parameter(description = "Book data") @RequestBody Book book) {
        Book createdBook = booksService.createBook(book.getTitle(), book.getAuthor().getId(), book.getPages());
        if (createdBook != null) {
            return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Author not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.PUT)
    @Operation(summary = "Update book")
    public ResponseEntity<Object> updateBook(@Parameter(description = "Book ID", example = "1") @PathVariable("id") int id,
                                            @Parameter(description = "Updated book data") @RequestBody Book book) {
        Book updatedBook = booksService.updateBook(id, book.getTitle(), book.getAuthor().getId(), book.getPages());
        if (updatedBook != null) {
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book or Author not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    @Operation(summary = "Delete book")
    public ResponseEntity<Object> deleteBook(@Parameter(description = "Book ID", example = "1") @PathVariable("id") int id) {
        boolean deleted = booksService.deleteBook(id);
        if (deleted) {
            return new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
        }
    }
}