package hu.uni.miskolc.iit.swtest.team3.service;

import hu.uni.miskolc.iit.swtest.team3.dao.BookDao;
import hu.uni.miskolc.iit.swtest.team3.dao.BorrowingDao;
import hu.uni.miskolc.iit.swtest.team3.model.Book;
import hu.uni.miskolc.iit.swtest.team3.model.BorrowStatus;
import hu.uni.miskolc.iit.swtest.team3.model.Borrowing;
import hu.uni.miskolc.iit.swtest.team3.model.User;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import hu.uni.miskolc.iit.swtest.team3.model.exception.NoAvailableCopiesException;
import hu.uni.miskolc.iit.swtest.team3.model.exception.UnsuccessfulOperationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.dao.DataAccessException;

import javax.naming.InitialContext;
import javax.xml.crypto.Data;

import static org.mockito.Mockito.*;

public class LibrarianServiceImplTest {

    LibrarianServiceImpl librarianServiceImpl = null;

    BookDao testBookDao = Mockito.mock(BookDao.class); //Mocked BookDao
    BorrowingDao testBorrowingDao = Mockito.mock(BorrowingDao.class); //Mocked BorrowingDao

    private List<Book> testBookList = new ArrayList<>();
    private Book testBook = new Book();

    private List<Borrowing> testBorrowingList = new ArrayList<>();
    private Borrowing testBorrowing = new Borrowing();
    private Borrowing testBorrowing2 = new Borrowing();
    private static final GregorianCalendar TEST_CREATIONDATE = new GregorianCalendar();
    private User testUser = new User();
    private Book newBook = new Book();

    private Borrowing testBorrowingCreate = new Borrowing();

    @Before
    public void init() {
        librarianServiceImpl = new LibrarianServiceImpl(testBookDao, testBorrowingDao);

        testBook.setIsbn("0-684-84328-5");
        testBook.setAuthor("Author Arthur");
        testBook.setTitle("The test");
        testBook.setDescription("A real good one for testing");
        testBook.setLanguage("English");
        testBook.setAvailableCopies(1);

        testBookList.add(testBook);

        testBorrowing.setBorrowId(1);
        testBorrowing.setStatus(BorrowStatus.RETURNED);
        testBorrowing.setCreatorId(66);
        testBorrowing.setBookIsbn("811326746-8");
        testBorrowing.setCreationDate(TEST_CREATIONDATE);

        testBorrowing2.setBorrowId(2);
        testBorrowing2.setStatus(BorrowStatus.REQUESTED);
        testBorrowing2.setCreatorId(66);
        testBorrowing2.setBookIsbn("811326746-8");
        testBorrowing2.setCreationDate(TEST_CREATIONDATE);

        testBorrowingList.add(testBorrowing);
        testBorrowingList.add(testBorrowing2);

        testUser.setUserId(66);
        testUser.setPasswordHash("$2a$frtF_$dasgew");
        testUser.setName("Test Thomas");
        testUser.setEmail("test.thomas88@unittest.org");
        testUser.setLibrarian(Boolean.TRUE);

        newBook.setIsbn("9-321-31672-8");
        newBook.setAuthor("Alfred von Bluckelen");
        newBook.setTitle("Das Leben der Elefanten");
        newBook.setDescription("Sehr langweiliges");
        newBook.setLanguage("German");
        newBook.setAvailableCopies(1);

        testBookList.add(newBook);
    }

    @Test
    public void testListBooks() {
        when(testBookDao.read()).thenReturn(testBookList);

        Assert.assertEquals(testBookList, librarianServiceImpl.listBooks());
        verify(testBookDao).read();
    }

    @Test(expected = UnsuccessfulOperationException.class)
    public void testListBooksException() {
        Mockito.when(testBookDao.read()).thenThrow(Mockito.mock(DataAccessException.class));

        Assert.assertEquals(testBookList, librarianServiceImpl.listBooks());
        verify(testBookDao).create(newBook);
    }

    @Test
    public void testListBorrowings() {
        when(testBorrowingDao.read()).thenReturn(testBorrowingList);

        Assert.assertEquals(testBorrowingList, librarianServiceImpl.listBorrowings());
        verify(testBorrowingDao).read();
    }

    @Test(expected = UnsuccessfulOperationException.class)
    public void testListBorrowingsException() {
        Mockito.when(testBorrowingDao.read()).thenThrow(Mockito.mock(DataAccessException.class));

        Assert.assertEquals(testBorrowingList, librarianServiceImpl.listBorrowings());
        verify(testBorrowingDao).read();
    }

    @Test
    public void testAddBook() {
        doReturn(testBookList.add(newBook)).when(testBookDao).create(newBook);

        librarianServiceImpl.addBook(newBook);
        verify(testBookDao).create(newBook);
    }

    @Test(expected = UnsuccessfulOperationException.class)
    public void testAddBookException() {
        Mockito.when(testBookDao.create(newBook)).thenThrow(Mockito.mock(DataAccessException.class));

        librarianServiceImpl.addBook(newBook);
        verify(testBookDao).create(newBook);

    }

    @Test
    public void testAddBookInstance() {
        when(testBookDao.read(testBook.getIsbn())).thenReturn(testBook);

        librarianServiceImpl.addBookInstance(testBook);
        verify(testBookDao).read(testBook.getIsbn());
    }

    @Test(expected = UnsuccessfulOperationException.class)
    public void testAddBookInstanceException() {
        Mockito.when(testBookDao.read(testBook.getIsbn())).thenThrow(Mockito.mock(DataAccessException.class));

        librarianServiceImpl.addBookInstance(testBook);
        verify(testBookDao).read(testBook.getIsbn());
    }

    @Test
    public void testListRequests() {
        List<Borrowing> requestedBorrowings = new ArrayList<>();

        when(testBorrowingDao.read()).thenReturn(testBorrowingList);

        for (Borrowing borrowing : testBorrowingList) {
            if (borrowing.getStatus() == BorrowStatus.REQUESTED) {
                requestedBorrowings.add(borrowing);
            }
        }

        Assert.assertEquals(requestedBorrowings, librarianServiceImpl.listRequests());
        verify(testBorrowingDao).read();
    }

    @Test(expected = UnsuccessfulOperationException.class)
    public void testListRequestException() {
        Mockito.when(testBorrowingDao.read()).thenThrow(Mockito.mock(DataAccessException.class));

        librarianServiceImpl.listRequests();
    }

    @Test
    public void testManageRequestsElseCase() {
        //We start from a Borrowing with REQUESTED status
        Borrowing borrowingToUpdate = testBorrowing2;
        Assert.assertEquals(borrowingToUpdate.getStatus(), BorrowStatus.REQUESTED);

        //We need a Borrowing with BORROWED status
        Borrowing expected = testBorrowing;
        expected.setStatus(BorrowStatus.BORROWED);

        //
        testBook.setAvailableCopies(5);


        when(testBorrowingDao.read(testBorrowing.getBorrowId())).thenReturn(borrowingToUpdate);
        when(testBookDao.read(borrowingToUpdate.getBookIsbn())).thenReturn(testBook);

        librarianServiceImpl.manageRequest(expected);

        //borrowingToUpdate.setStatus(expected.getStatus());
        //requestedBook.setAvailableCopies(requestedBook.getAvailableCopies() - 1);

        ArgumentCaptor<Borrowing> borrowingArgumentCaptor = ArgumentCaptor.forClass(Borrowing.class);
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);

        verify(testBorrowingDao).update(borrowingArgumentCaptor.capture());
        verify(testBookDao).update(bookArgumentCaptor.capture());

        // Assert that every required action has been done
        Assert.assertEquals(BorrowStatus.BORROWED, borrowingArgumentCaptor.getValue().getStatus());
        Assert.assertEquals(4, bookArgumentCaptor.getValue().getAvailableCopies());
    }

    @Test(expected = IllegalStateException.class)
    public void testManageRequestsIllegalException() {
        when(testBorrowingDao.read(testBorrowing.getBorrowId())).thenReturn(testBorrowing);

        librarianServiceImpl.manageRequest(testBorrowing);
    }

    @Test(expected = UnsuccessfulOperationException.class)
    public void testManageRequestsException() {
        Mockito.when(testBorrowingDao.read(testBorrowing.getBorrowId())).thenThrow(Mockito.mock(DataAccessException.class));

        librarianServiceImpl.manageRequest(testBorrowing);
    }

    @Test
    public void testIsValidStatusChange() {
        BorrowStatus requested = BorrowStatus.REQUESTED;
        BorrowStatus borrowed = BorrowStatus.BORROWED;
        BorrowStatus returned = BorrowStatus.RETURNED;
        BorrowStatus nullStatus = null;

        Assert.assertFalse(librarianServiceImpl.isValidStatusChange(requested, requested));
        Assert.assertFalse(librarianServiceImpl.isValidStatusChange(borrowed, borrowed));
        Assert.assertFalse(librarianServiceImpl.isValidStatusChange(returned, returned));
        Assert.assertFalse(librarianServiceImpl.isValidStatusChange(nullStatus, returned));

        Assert.assertTrue(librarianServiceImpl.isValidStatusChange(requested, borrowed));
        Assert.assertTrue(librarianServiceImpl.isValidStatusChange(borrowed, returned));
    }

    @Test
    public void testUpdateAvailableCopies() {
        BorrowStatus statusBorrowed = BorrowStatus.BORROWED;
        BorrowStatus statusReturned = BorrowStatus.RETURNED;

        int availableCopiesBeforeUpdate = testBook.getAvailableCopies();
        librarianServiceImpl.updateAvailableCopies(statusBorrowed, testBook);
        Assert.assertEquals(availableCopiesBeforeUpdate - 1, testBook.getAvailableCopies());

        //Reset available copies to the original value
        testBook.setAvailableCopies(availableCopiesBeforeUpdate);

        librarianServiceImpl.updateAvailableCopies(statusReturned, testBook);
        Assert.assertEquals(availableCopiesBeforeUpdate + 1, testBook.getAvailableCopies());
    }
}