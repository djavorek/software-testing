package hu.uni.miskolc.iit.swtest.team3.model.test;

import hu.uni.miskolc.iit.swtest.team3.model.BorrowStatus;
import hu.uni.miskolc.iit.swtest.team3.model.Borrowing;
import hu.uni.miskolc.iit.swtest.team3.model.User;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Assert;
import org.junit.Test;

import java.util.GregorianCalendar;

public class BorrowingTest {

    private static final int TEST_BORROWID = 1;
    private static final BorrowStatus TEST_BORROWSTATUS = BorrowStatus.RETURNED;
    private static final int TEST_CREATORID = 66;
    private static final String TEST_BOOKISBN = "811326746-8";
    private static final GregorianCalendar TEST_CREATIONDATE = new GregorianCalendar();

    @Test
    public void constructorTest() {
        Borrowing borrowingToTest = new Borrowing(TEST_BORROWID, TEST_BORROWSTATUS, TEST_CREATORID, TEST_BOOKISBN, TEST_CREATIONDATE);

        Assert.assertEquals(TEST_BORROWID, borrowingToTest.getBorrowId());
        Assert.assertEquals(TEST_BORROWSTATUS, borrowingToTest.getStatus());
        Assert.assertEquals(TEST_CREATORID, borrowingToTest.getCreatorId());
        Assert.assertEquals(TEST_BOOKISBN, borrowingToTest.getBookIsbn());
        Assert.assertEquals(TEST_CREATIONDATE, borrowingToTest.getCreationDate());
    }

    @Test
    public void setterTest() {
        Borrowing borrowingToTest = new Borrowing();
        borrowingToTest.setBorrowId(TEST_BORROWID);
        borrowingToTest.setStatus(TEST_BORROWSTATUS);
        borrowingToTest.setCreatorId(TEST_CREATORID);
        borrowingToTest.setBookIsbn(TEST_BOOKISBN);
        borrowingToTest.setCreationDate(TEST_CREATIONDATE);

        Assert.assertEquals(TEST_BORROWID, borrowingToTest.getBorrowId());
        Assert.assertEquals(TEST_BORROWSTATUS, borrowingToTest.getStatus());
        Assert.assertEquals(TEST_CREATORID, borrowingToTest.getCreatorId());
        Assert.assertEquals(TEST_BOOKISBN, borrowingToTest.getBookIsbn());
        Assert.assertEquals(TEST_CREATIONDATE, borrowingToTest.getCreationDate());
    }

    @Test
    public void constructorTestNoId() {
        Borrowing borrowingToTest = new Borrowing(TEST_BORROWSTATUS, TEST_CREATORID, TEST_BOOKISBN, TEST_CREATIONDATE);

        Assert.assertEquals(TEST_BORROWSTATUS, borrowingToTest.getStatus());
        Assert.assertEquals(TEST_CREATORID, borrowingToTest.getCreatorId());
        Assert.assertEquals(TEST_BOOKISBN, borrowingToTest.getBookIsbn());
        Assert.assertEquals(TEST_CREATIONDATE, borrowingToTest.getCreationDate());
    }

    @Test
    public void setterTestNoId() {
        Borrowing borrowingToTest = new Borrowing();
        borrowingToTest.setStatus(TEST_BORROWSTATUS);
        borrowingToTest.setCreatorId(TEST_CREATORID);
        borrowingToTest.setBookIsbn(TEST_BOOKISBN);
        borrowingToTest.setCreationDate(TEST_CREATIONDATE);

        Assert.assertEquals(TEST_BORROWSTATUS, borrowingToTest.getStatus());
        Assert.assertEquals(TEST_CREATORID, borrowingToTest.getCreatorId());
        Assert.assertEquals(TEST_BOOKISBN, borrowingToTest.getBookIsbn());
        Assert.assertEquals(TEST_CREATIONDATE, borrowingToTest.getCreationDate());
    }

    @Test
    public void equalsContractTest() {
        EqualsVerifier.forClass(Borrowing.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}
