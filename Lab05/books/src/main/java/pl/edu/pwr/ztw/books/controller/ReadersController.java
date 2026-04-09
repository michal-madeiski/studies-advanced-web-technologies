package pl.edu.pwr.ztw.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.model.Reader;
import pl.edu.pwr.ztw.books.service.IReadersService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@Tag(name = "Readers", description = "API for managing readers")
public class ReadersController {

    @Autowired
    IReadersService readersService;

    @RequestMapping(value = "/readers", method = RequestMethod.GET)
    @Operation(summary = "Get all readers")
    public ResponseEntity<Object> getReaders() {
        return new ResponseEntity<>(readersService.getReaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/readers/{id}", method = RequestMethod.GET)
    @Operation(summary = "Get reader details")
    public ResponseEntity<Object> getReader(@Parameter(description = "Reader ID", example = "1") @PathVariable("id") int id) {
        Reader reader = readersService.getReader(id);
        if (reader != null) {
            return new ResponseEntity<>(reader, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Reader not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/readers", method = RequestMethod.POST)
    @Operation(summary = "Create new reader")
    public ResponseEntity<Object> createReader(@Parameter(description = "Reader data") @RequestBody Reader reader) {
        Reader createdReader = readersService.createReader(reader.getName(), reader.getEmail());
        return new ResponseEntity<>(createdReader, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/readers/{id}", method = RequestMethod.PUT)
    @Operation(summary = "Update reader")
    public ResponseEntity<Object> updateReader(@Parameter(description = "Reader ID", example = "1") @PathVariable("id") int id,
                                              @Parameter(description = "Updated reader data") @RequestBody Reader reader) {
        Reader updatedReader = readersService.updateReader(id, reader.getName(), reader.getEmail());
        if (updatedReader != null) {
            return new ResponseEntity<>(updatedReader, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Reader not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/readers/{id}", method = RequestMethod.DELETE)
    @Operation(summary = "Delete reader")
    public ResponseEntity<Object> deleteReader(@Parameter(description = "Reader ID", example = "1") @PathVariable("id") int id) {
        boolean deleted = readersService.deleteReader(id);
        if (deleted) {
            return new ResponseEntity<>("Reader deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Reader not found", HttpStatus.NOT_FOUND);
        }
    }
}
