package hu.uni.miskolc.iit.swtest.team3.service;

import hu.uni.miskolc.iit.swtest.team3.dao.BookDao;
import hu.uni.miskolc.iit.swtest.team3.dao.BorrowingDao;
import hu.uni.miskolc.iit.swtest.team3.model.*;
import hu.uni.miskolc.iit.swtest.team3.model.exception.NoAvailableCopiesException;
import hu.uni.miskolc.iit.swtest.team3.model.exception.UnsuccessfulOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.GregorianCalendar;
import java.util.List;

@Service
public class ReaderServiceImpl implements ReaderService {

    private BookDao bookDao;
    private BorrowingDao borrowingDao;

    @Autowired
    public ReaderServiceImpl(BookDao bookDao, BorrowingDao borrowingDao){
        this.bookDao=bookDao;
        this.borrowingDao=borrowingDao;
    }

    @Override
    public List<Book> listBooks() {
        try {
            return bookDao.read();
        } catch (DataAccessException exception) {
            throw new UnsuccessfulOperationException("Could not get the list of books!", exception);
        }
    }

    @Override
    public List<Borrowing> listBorrowings(User user) {
        try {
            return borrowingDao.readByUser(user);
        } catch (DataAccessException exception) {
            throw new UnsuccessfulOperationException("Could not get the list of borrowings!", exception);
        }
    }

    @Override
    public void requestBook(Book book, User reader) {
        try {
            Book requestedBook = bookDao.read(book.getIsbn());

            if(requestedBook.getAvailableCopies() < 1) {
                throw new NoAvailableCopiesException("There is no available copies from the requested book currently!");
            }
            else {
                Borrowing borrowingToCreate = new Borrowing(BorrowStatus.REQUESTED,reader.getUserId(), book.getIsbn(), new GregorianCalendar());
                borrowingDao.create(borrowingToCreate);
            }
        } catch(DataAccessException exception) {
            throw new UnsuccessfulOperationException("Book request cannot be created!", exception);
        }
    }

    @Override
    public Boolean checkAvailability(Book book){
        try{
            Book bookToCheck = bookDao.read(book.getIsbn());
            if(bookToCheck.getAvailableCopies() > 0){
                return Boolean.TRUE;
            }
            else return Boolean.FALSE;
        }
        catch (DataAccessException exception){
            throw new UnsuccessfulOperationException("Could not check the availability of the book!", exception);
        }
    }
}