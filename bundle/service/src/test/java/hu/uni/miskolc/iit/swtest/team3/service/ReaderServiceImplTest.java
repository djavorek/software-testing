/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author tdavi
 *exceptions tested by Áron Nagy
 */
public class ReaderServiceImplTest {

    ReaderServiceImpl readerServiceImpl = null;

    BookDao testBookDao = Mockito.mock(BookDao.class); //Mocked BookDao
    BorrowingDao testBorrowingDao = Mockito.mock(BorrowingDao.class); //Mocked BorrowingDao

    private List<Book> testBookList = new ArrayList<>();
    private Book testBook = new Book();

    private List<Borrowing> testBorrowingList = new ArrayList<>();
    private Borrowing testBorrowing = new Borrowing();
    private static final GregorianCalendar TEST_CREATIONDATE = new GregorianCalendar();
    private User testUser = new User();

    private Borrowing testBorrowingCreate = new Borrowing();

    @Before
    public void init() {
        readerServiceImpl = new ReaderServiceImpl(testBookDao, testBorrowingDao);

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
    }

    @Test
    public void testListBooks() {
        when(testBookDao.read()).thenReturn(testBookList);

        Assert.assertEquals(testBookList, readerServiceImpl.listBooks());
        verify(testBookDao).read();
    }

    @Test(expected = UnsuccessfulOperationException.class)
    public void testListBooksException() {
        Mockito.when(testBookDao.read()).thenThrow(Mockito.mock(DataAccessException.class));

        Assert.assertEquals(testBookList, readerServiceImpl.listBooks());
        verify(testBookDao).read();
    }

    @Test
    public void testListBorrowings() {
        when(testBorrowingDao.readByUser(testUser)).thenReturn(testBorrowingList);

        Assert.assertEquals(testBorrowingList, readerServiceImpl.listBorrowings(testUser));
        verify(testBorrowingDao).readByUser(testUser);
    }

    @Test(expected = UnsuccessfulOperationException.class)
    public void testListBorrowingsException() {
        Mockito.when(testBorrowingDao.readByUser(testUser)).thenThrow(Mockito.mock(DataAccessException.class));

        Assert.assertEquals(testBorrowingList, readerServiceImpl.listBorrowings(testUser));
        verify(testBorrowingDao).readByUser(testUser);
    }

    @Test
    public void testRequestBook() {
        when(testBookDao.read(testBook.getIsbn())).thenReturn(testBook);
        readerServiceImpl.requestBook(testBook, testUser);
    }

    @Test(expected = UnsuccessfulOperationException.class)
    public void testRequestBookException() {
        Mockito.when(testBookDao.read(testBook.getIsbn())).thenThrow(Mockito.mock(DataAccessException.class));

        readerServiceImpl.requestBook(testBook, testUser);
    }

    @Test(expected = NoAvailableCopiesException.class)
    public void TestRequestBookZeroCopies() {
        testBook.setAvailableCopies(0);
        when(testBookDao.read(testBook.getIsbn())).thenReturn(testBook);
        readerServiceImpl.requestBook(testBook, testUser);
    }

    @Test
    public void testCheckAvailability(){
        when(testBookDao.read(testBook.getIsbn())).thenReturn(testBook);
        
        if(testBook.getAvailableCopies() > 0){
            Assert.assertTrue(readerServiceImpl.checkAvailability(testBook));
        }
        else {
            Assert.assertFalse(readerServiceImpl.checkAvailability(testBook));
        }
    }

    @Test(expected = UnsuccessfulOperationException.class)
    public void testCheckAvailabilityException() {
        Mockito.when(testBookDao.read(testBook.getIsbn())).thenThrow(Mockito.mock(DataAccessException.class));

        if(testBook.getAvailableCopies() > 0){
            Assert.assertTrue(readerServiceImpl.checkAvailability(testBook));
        }
        else {
            Assert.assertFalse(readerServiceImpl.checkAvailability(testBook));
        }
    }

    @Test
    public void testNoAvailability() {
        testBook.setAvailableCopies(0);
        when(testBookDao.read(testBook.getIsbn())).thenReturn(testBook);
        Assert.assertEquals(false,readerServiceImpl.checkAvailability(testBook));
    }
}