package hu.uni.miskolc.iit.swtest.team3.web.controller;

import hu.uni.miskolc.iit.swtest.team3.model.Book;
import hu.uni.miskolc.iit.swtest.team3.model.Borrowing;
import hu.uni.miskolc.iit.swtest.team3.model.exception.UnsuccessfulOperationException;
import hu.uni.miskolc.iit.swtest.team3.service.LibrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/librarian/")
public class LibrarianController {

    private final LibrarianService librarianService;

    @Autowired
    public LibrarianController(LibrarianService librarianService) {
        this.librarianService = librarianService;
    }

    @GetMapping("books")
    public List<Book> listBooks() {
        return librarianService.listBooks();
    }

    @PostMapping("addBook")
    public void addBook(@RequestBody Book book) {
        librarianService.addBook(book);
    }

    @PostMapping("manageBookInstances")
    public void manageBookInstances(@RequestBody Book book) {
        librarianService.manageBookInstances(book);
    }

    @PostMapping("acquireBookInstance")
    public void addBookInstance(@RequestBody Book book) {
        librarianService.addBookInstance(book);
    }

    @GetMapping("requests")
    public List<Borrowing> listRequests() {
        return librarianService.listRequests();
    }

    @GetMapping("borrowings")
    public List<Borrowing> listBorrowings() {
        return librarianService.listBorrowings();
    }

    @PostMapping("manageRequest")
    public void manageRequest(@RequestBody Borrowing borrowing) {
        librarianService.manageRequest(borrowing);
    }

    @ExceptionHandler(UnsuccessfulOperationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void unsuccessfulOperation() {}
}
