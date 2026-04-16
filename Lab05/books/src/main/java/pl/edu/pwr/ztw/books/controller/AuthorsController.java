package pl.edu.pwr.ztw.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.model.Author;
import pl.edu.pwr.ztw.books.service.IAuthorsService;
import pl.edu.pwr.ztw.books.service.IBooksService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@Tag(name = "Authors", description = "API for managing authors")
public class AuthorsController {

    @Autowired
    IAuthorsService authorsService;

    @Autowired
    IBooksService booksService;

    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    @Operation(summary = "Get all authors with pagination")
    public ResponseEntity<Object> getAuthors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(authorsService.getAuthorsCount()))
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(authorsService.getAuthors(page, size));
    }

    @RequestMapping(value = "/authors/{id}", method = RequestMethod.GET)
    @Operation(summary = "Get author details")
    public ResponseEntity<Object> getAuthor(@Parameter(description = "Author ID", example = "1") @PathVariable("id") int id) {
        Author author = authorsService.getAuthor(id);
        if (author != null) {
            return new ResponseEntity<>(author, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Author not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/authors", method = RequestMethod.POST)
    @Operation(summary = "Create new author")
    public ResponseEntity<Object> createAuthor(@Parameter(description = "New author data") @RequestBody Author author) {
        Author createdAuthor = authorsService.createAuthor(author.getName());
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/authors/{id}", method = RequestMethod.PUT)
    @Operation(summary = "Update author")
    public ResponseEntity<Object> updateAuthor(@Parameter(description = "Author ID", example = "1") @PathVariable("id") int id, 
                                              @Parameter(description = "Updated author data") @RequestBody Author author) {
        Author updatedAuthor = authorsService.updateAuthor(id, author.getName());
        if (updatedAuthor != null) {
            return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Author not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/authors/{id}", method = RequestMethod.DELETE)
    @Operation(summary = "Delete author")
    public ResponseEntity<Object> deleteAuthor(@Parameter(description = "Author ID", example = "1") @PathVariable("id") int id) {
        if (!booksService.getBooksByAuthor(id).isEmpty()) {
            return new ResponseEntity<>("Nie można usunąć autora z przypisanymi książkami", HttpStatus.CONFLICT);
        }
        boolean deleted = authorsService.deleteAuthor(id);
        if (deleted) {
            return new ResponseEntity<>("Author deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Author not found", HttpStatus.NOT_FOUND);
        }
    }
}
