package hu.uni.miskolc.iit.swtest.team3.service;

import hu.uni.miskolc.iit.swtest.team3.model.Book;
import hu.uni.miskolc.iit.swtest.team3.model.Borrowing;
import java.util.List;

/**
 *
 * @author tdavi
 */
public interface LibrarianService {
    
    List<Book> listBooks();
    List<Borrowing> listBorrowings();
    void addBook(Book book);
    void manageBookInstances(Book book);
    void addBookInstance(Book book);
    List<Borrowing> listRequests();
    void manageRequest(Borrowing borrowing);
    
}
