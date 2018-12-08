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
import org.mockito.Mockito;
import org.springframework.dao.DataAccessException;

import static org.mockito.Mockito.*;

public class LibrarianServiceImplTest {

    LibrarianServiceImpl librarianServiceImpl = null;

    BookDao testBookDao = Mockito.mock(BookDao.class); //Mocked BookDao
    BorrowingDao testBorrowingDao = Mockito.mock(BorrowingDao.class); //Mocked BorrowingDao

    private List<Book> testBookList = new ArrayList<>();
    private Book testBook = new Book();

    private List<Borrowing> testBorrowingList = new ArrayList<>();
    private Borrowing testBorrowing = new Borrowing();
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

        testBorrowingList.add(testBorrowing);

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
    public void addBookInstance () {
        when(testBookDao.read(testBook.getIsbn())).thenReturn(testBook);

        librarianServiceImpl.addBookInstance(testBook);
        verify(testBookDao).read(testBook.getIsbn());
    }
}
