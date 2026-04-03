package pl.edu.pwr.ztw.books;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import pl.edu.pwr.ztw.books.service.BooksService;

@SpringBootApplication
public class BooksApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BooksApplication.class, args);
        BooksService booksService = context.getBean(BooksService.class);
        booksService.initializeBooks();
    }
}
