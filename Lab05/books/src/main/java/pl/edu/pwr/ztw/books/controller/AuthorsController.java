package pl.edu.pwr.ztw.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.model.Author;
import pl.edu.pwr.ztw.books.service.IAuthorsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@Tag(name = "Authors", description = "API for managing authors")
public class AuthorsController {

    @Autowired
    IAuthorsService authorsService;

    @RequestMapping(value = "/get/authors", method = RequestMethod.GET)
    @Operation(summary = "Get all authors")
    public ResponseEntity<Object> getAuthors() {
        return new ResponseEntity<>(authorsService.getAuthors(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get/author/{id}", method = RequestMethod.GET)
    @Operation(summary = "Get author details")
    public ResponseEntity<Object> getAuthor(@Parameter(description = "Author ID", example = "1") @PathVariable("id") int id) {
        Author author = authorsService.getAuthor(id);
        if (author != null) {
            return new ResponseEntity<>(author, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Author not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/create/author", method = RequestMethod.POST)
    @Operation(summary = "Create new author")
    public ResponseEntity<Object> createAuthor(@Parameter(description = "New author data") @RequestBody Author author) {
        Author createdAuthor = authorsService.createAuthor(author.getName());
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update/author/{id}", method = RequestMethod.PUT)
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

    @RequestMapping(value = "/delete/author/{id}", method = RequestMethod.DELETE)
    @Operation(summary = "Delete author")
    public ResponseEntity<Object> deleteAuthor(@Parameter(description = "Author ID", example = "1") @PathVariable("id") int id) {
        boolean deleted = authorsService.deleteAuthor(id);
        if (deleted) {
            return new ResponseEntity<>("Author deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Author not found", HttpStatus.NOT_FOUND);
        }
    }
}
