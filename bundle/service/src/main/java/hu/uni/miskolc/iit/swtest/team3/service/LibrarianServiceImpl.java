package hu.uni.miskolc.iit.swtest.team3.service;

import hu.uni.miskolc.iit.swtest.team3.dao.BookDao;
import hu.uni.miskolc.iit.swtest.team3.dao.BorrowingDao;
import hu.uni.miskolc.iit.swtest.team3.model.Book;

import hu.uni.miskolc.iit.swtest.team3.model.BorrowStatus;
import hu.uni.miskolc.iit.swtest.team3.model.Borrowing;
import java.util.ArrayList;
import java.util.List;

import hu.uni.miskolc.iit.swtest.team3.model.exception.UnsuccessfulOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author tdavi
 */
@Service
public class LibrarianServiceImpl implements LibrarianService {

    private BookDao bookDao;
    private BorrowingDao borrowingDao;

    @Autowired
    public LibrarianServiceImpl(BookDao bookDao, BorrowingDao borrowingDao) {
        this.bookDao = bookDao;
        this.borrowingDao = borrowingDao;
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
    public List<Borrowing> listBorrowings() {
        try {
            return borrowingDao.read();
        } catch (DataAccessException exception) {
            throw new UnsuccessfulOperationException("Could not get the list of borrowings!", exception);
        }
    }

    @Override
    public void addBook(Book book) {
        try {
            bookDao.create(book);
        } catch (DataAccessException exception) {
            throw new UnsuccessfulOperationException("Could not add book!", exception);
        }
    }

    @Override
    public void manageBookInstances(Book book) {
        String isbn = book.getIsbn();
        try {
            Book bookToUpdate = bookDao.read(isbn);
            bookToUpdate.setAvailableCopies(book.getAvailableCopies());
            bookDao.update(bookToUpdate);
        } catch (DataAccessException | IllegalArgumentException exception) {
            throw new UnsuccessfulOperationException("Could not modify the number of book instances!", exception);
        }
    }

    @Override
    public void addBookInstance(Book book) {
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        manageBookInstances(book);
    }

    @Override
    public List<Borrowing> listRequests() {
        List<Borrowing> borrowings;
        List<Borrowing> requestedBorrowings = new ArrayList<>();

        try {
            borrowings = borrowingDao.read();
        } catch (DataAccessException exception) {
            throw new UnsuccessfulOperationException("Could not get the list of borrowings!", exception);
        }

        for (Borrowing borrowing : borrowings) {
            if (borrowing.getStatus() == BorrowStatus.REQUESTED) {
                requestedBorrowings.add(borrowing);
            }
        }

        return requestedBorrowings;
    }

    @Override
    public void manageRequest(Borrowing borrowing) {
        int borrowId = borrowing.getBorrowId();
        BorrowStatus newStatus = borrowing.getStatus();

        try {
            Borrowing borrowingToUpdate = borrowingDao.read(borrowId);
            BorrowStatus oldStatus = borrowingToUpdate.getStatus();

            if (!isValidStatusChange(oldStatus, newStatus)) {
                throw new IllegalStateException("The requested status is invalid or already persists");
            } else {
                borrowingToUpdate.setStatus(newStatus);

                Book managedBook = bookDao.read(borrowingToUpdate.getBookIsbn());
                updateAvailableCopies(newStatus, managedBook);

                borrowingDao.update(borrowingToUpdate);
                bookDao.update(managedBook);
            }
        } catch (DataAccessException exception) {
            throw new UnsuccessfulOperationException("Could not change the status of the request!", exception);
        }
    }

    protected boolean isValidStatusChange(BorrowStatus oldStatus, BorrowStatus newStatus) {
        if (oldStatus != null && newStatus != null) {
            switch (oldStatus) {
                case REQUESTED: {
                    if (newStatus == BorrowStatus.BORROWED) return true;
                    else return false;
                }
                case BORROWED: {
                    if (newStatus == BorrowStatus.RETURNED) return true;
                    else return false;
                }
                case RETURNED: {
                    return false;
                }
            }
        }
        return false;
    }

    protected void updateAvailableCopies(BorrowStatus newStatus, Book bookToManage) {
        int availableCopies = bookToManage.getAvailableCopies();

        switch (newStatus) {
            case BORROWED: {
                bookToManage.setAvailableCopies(--availableCopies);
                break;
            }
            case RETURNED: {
                bookToManage.setAvailableCopies(++availableCopies);
                break;
            }
        }
    }
}
