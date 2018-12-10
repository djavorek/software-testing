package hu.uni.miskolc.iit.swtest.team3.model.test;

import hu.uni.miskolc.iit.swtest.team3.model.Book;
import hu.uni.miskolc.iit.swtest.team3.model.Borrowing;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Assert;
import org.junit.Test;

public class BookTest {

    private static final String TEST_ISBN = "0-684-84328-5";
    private static final String TEST_AUTHOR = "Author Arthur";
    private static final String TEST_TITLE = "The test";
    private static final String TEST_DESCRIPTION = "A real good one for testing";
    private static final String TEST_LANGUAGE = "english";
    private static final int TEST_COPIES = 1;

    @Test
    public void constructorTest() {
        Book bookToTest = new Book(TEST_ISBN, TEST_AUTHOR, TEST_TITLE, TEST_DESCRIPTION, TEST_LANGUAGE, TEST_COPIES);

        Assert.assertEquals(TEST_ISBN, bookToTest.getIsbn());
        Assert.assertEquals(TEST_AUTHOR, bookToTest.getAuthor());
        Assert.assertEquals(TEST_TITLE, bookToTest.getTitle());
        Assert.assertEquals(TEST_DESCRIPTION, bookToTest.getDescription());
        Assert.assertEquals(TEST_LANGUAGE, bookToTest.getLanguage());
        Assert.assertEquals(TEST_COPIES, bookToTest.getAvailableCopies());
    }

    @Test
    public void setterTest() {
        Book bookToTest = new Book();
        bookToTest.setIsbn(TEST_ISBN);
        bookToTest.setAuthor(TEST_AUTHOR);
        bookToTest.setTitle(TEST_TITLE);
        bookToTest.setAuthor(TEST_AUTHOR);
        bookToTest.setDescription(TEST_DESCRIPTION);
        bookToTest.setLanguage(TEST_LANGUAGE);
        bookToTest.setAvailableCopies(TEST_COPIES);

        Assert.assertEquals(bookToTest.getIsbn(), TEST_ISBN);
        Assert.assertEquals(bookToTest.getAuthor(), TEST_AUTHOR);
        Assert.assertEquals(bookToTest.getTitle(), TEST_TITLE);
        Assert.assertEquals(bookToTest.getDescription(), TEST_DESCRIPTION);
        Assert.assertEquals(bookToTest.getLanguage(), TEST_LANGUAGE);
        Assert.assertEquals(bookToTest.getAvailableCopies(), TEST_COPIES);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setAvailableCopiesTest(){
        Book book = new Book();
        book.setAvailableCopies(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setIsbnTest(){
        Book book = new Book();
        book.setIsbn(null);
    }

    @Test
    public void equalsContractTest() {
        EqualsVerifier.forClass(Book.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}
