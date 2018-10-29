package hu.uni.miskolc.iit.swtest.team3.controller;

import hu.uni.miskolc.iit.swtest.team3.model.Book;
import hu.uni.miskolc.iit.swtest.team3.model.Borrowing;
import hu.uni.miskolc.iit.swtest.team3.service.ReaderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/reader/")
public class ReaderController {

    private ReaderService readerService;

    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping("books")
    @ResponseBody
    public List<Book> listBooks() {
        return readerService.listBooks();
    }

    @GetMapping("borrowings")
    @ResponseBody
    public List<Borrowing> listBorrowing() {
        return readerService.listBorrowings();
    }
}
