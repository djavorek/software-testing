package hu.uni.miskolc.iit.swtest.team3.service;

import hu.uni.miskolc.iit.swtest.team3.dao.*;
import hu.uni.miskolc.iit.swtest.team3.model.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import org.junit.*;
import org.mockito.Mockito;
import org.springframework.dao.DataAccessException;
import hu.uni.miskolc.iit.swtest.team3.model.exception.NoAvailableCopiesException;
import hu.uni.miskolc.iit.swtest.team3.model.exception.UnsuccessfulOperationException;
import hu.uni.miskolc.iit.swtest.team3.model.exception.IllegalStateException;

import static org.mockito.Mockito.*;

public class LibrarianServiceImplTest{
    private List<Book> testBookList = new ArrayList<>();
    private List<Borrowing> testBorrowingList = new ArrayList<>();
    private Book testBook = new Book();
    private User testUser = new User();
    private Borrowing testBorrowing= new Borrowing();

    LibrarianServiceImpl librarianServiceImpl = null;

    BookDao testBookDao = Mockito.mock(BookDao.class);
    BorrowingDao testBorrowingDao = Mockito.mock(BorrowingDao.class);
    private Borrowing testBorrowing1;

    @BeforeClass
    public void init() {
        librarianServiceImpl = new LibrarianServiceImpl(testBookDao, testBorrowingDao);

        testBook.setIsbn("12-114-01010-0");
        testBook.setAuthor("some one");
        testBook.setTitle("The beauty and the beast");
        testBook.setDescription("Adventures, Drama");
        testBook.setLanguage("English");
        testBook.setAvailableCopies(5);
        testBookList.add(testBook);

        testBorrowing.setBorrowId(1);
        testBorrowing.setStatus(BorrowStatus.RETURNED);
        testBorrowing.setCreatorId(6);
        testBorrowing.setBookIsbn("123456789-8");
        testBorrowing.setCreationDate(TEST_CREATIONDATE);

        testBorrowingList.add(testBorrowing);

        testUser.setUserId(6);
        testUser.setPasswordHash("killthemall");
        testUser.setName("some body");
        testUser.setEmail("some.body@miskolc-uni.hu");
        testUser.setLibrarian(Boolean.TRUE);
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
        verify(testBookDao).read();
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
    }
    @Test
    public void testAddBookInstance(){
        when(testBookDao.create(any(Book.class))).thenReturn(testBook);
        librarianServiceImpl.addBookInstance(testBook);
    }

    @Test(expected = UnsuccessfulOperationException.class)
    public void testAddBookInstanceException() {
        Mockito.when(testBookDao.read()).thenThrow(Mockito.mock(DataAccessException.class));

        Assert.assertEquals(TestAddBookInstance, librarianServiceImpl.addBookInstance(testBook));
    }

    @Test
    public void testAddBook(){
        when(testBookDao.create(any(Book.class))).thenReturn(testBook);
        librarianServiceImpl.addBook(testBook);
    }

    @Test(expected = UnsuccessfulOperationException.class)
    public void testAddBookException() {
        Mockito.when(testBookDao.read()).thenThrow(Mockito.mock(DataAccessException.class));

        Assert.assertEquals(TestAddBook, librarianServiceImpl.addBook(testBook));
    }
    @Test
    public void testListRequests() {
        when(testBookDao.read()).thenReturn(testListRequests);
        Assert.assertEquals(testListRequests, librarianServiceImpl.ListRequests());
        verify(testBookDao).read();
    }

    @Test(expected = UnsuccessfulOperationException.class)
    public void testListBooksException() {
        Mockito.when(testBookDao.read()).thenThrow(Mockito.mock(DataAccessException.class));
        Assert.assertEquals(testBookList, librarianServiceImpl.listBooks());
        verify(testBookDao).read();
    }

    @Test
    public void testManageRequest(){
        when(testBorrowingDao.read()).thenReturn(testBorrowing);
        librarianServiceImpl.manageRequest(testBorrowing);
    }
    @Test(expected = Exception.class)
    public void testManageRequestException() {
        Mockito.when(testBorrowingDao.read()).thenThrow(Mockito.mock(DataAccessException.class));
        Assert.assertEquals(testManageRequest, librarianServiceImpl.manageRequest(testBorrowing));
        verify(testBorrowingDao).read();
    }


    @Test
    public void testIsValidStatusChange(BorrowStatus oldStatus, BorrowStatus newStatus){
        when(testBookDao.read(testBook.getIsbn())).thenReturn(testBook);

       if (oldStatus== BorrowStatus.REQUESTED){
             if (newStatus== BorrowStatus.BORROWED){
                 Assert.assertTrue(librarianServiceImpl.isValidStatusChange(oldStatus, newStatus));
             }
             else{
                 Assert.assertFalse(librarianServiceImpl.isValidStatusChange(oldStatus, newStatus));
             }
       }
       if (oldStatus== BorrowStatus.BORROWED){
             if(newStatus == BorrowStatus.RETURNED){
                 Assert.assertTrue(librarianServiceImpl.isValidStatusChange(oldStatus, newStatus));
             }
             else{
                 Assert.assertFalse(librarianServiceImpl.isValidStatusChange(oldStatus, newStatus));
             }

       }
        if (oldStatus== BorrowStatus.RETURNED){
                 Assert.assertFalse(librarianServiceImpl.isValidStatusChange(oldStatus, newStatus));
        }

    }

    @Test
    public void testUpdateAvailableCopies(){
        when(testBookDao.read(testBook.getIsbn())).thenReturn(testBook);
        librarianServiceImpl.updateAvailableCopies(testBook);

    }


}
