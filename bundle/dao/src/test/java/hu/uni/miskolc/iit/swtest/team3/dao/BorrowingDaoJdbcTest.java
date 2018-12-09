package hu.uni.miskolc.iit.swtest.team3.dao;

import hu.uni.miskolc.iit.swtest.team3.dao.config.SpringDaoTestConfig;

import hu.uni.miskolc.iit.swtest.team3.model.Book;
import hu.uni.miskolc.iit.swtest.team3.model.BorrowStatus;
import hu.uni.miskolc.iit.swtest.team3.model.Borrowing;
import hu.uni.miskolc.iit.swtest.team3.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={SpringDaoTestConfig.class})
public class BorrowingDaoJdbcTest {

    @Autowired
    private BorrowingDao borrowingDao;

    @Test
    public void oneBorrowingLifecycleTest() {
        Borrowing borrowingToTest = new Borrowing(BorrowStatus.REQUESTED, 1, "9780345391803", new GregorianCalendar(2018,1,1));
        borrowingToTest.setBorrowId(66);

        Assert.assertEquals(1, borrowingDao.create(borrowingToTest));
        Assert.assertThat(borrowingDao.read(), hasItem(borrowingToTest));

        Book borrowedBook = new Book();
        borrowedBook.setIsbn("9780345391803");

        Assert.assertEquals(borrowingToTest, borrowingDao.read(borrowedBook));

        borrowingToTest.setStatus(BorrowStatus.BORROWED);
        Assert.assertEquals(1, borrowingDao.update(borrowingToTest));
        Assert.assertThat(borrowingDao.read(), hasItem(borrowingToTest));
        Assert.assertEquals(borrowingToTest, borrowingDao.read(borrowedBook));

        Assert.assertEquals(1, borrowingDao.delete(borrowingToTest));
        Assert.assertThat(borrowingDao.read(), not(hasItem(borrowingToTest)));
    }

    @Test
    public void multipleBorrowingLifeCycleTest() {
        Borrowing inProgressBorrowing = new Borrowing(BorrowStatus.BORROWED, 2, "0345391802", new GregorianCalendar(2018,9,13));
        inProgressBorrowing.setBorrowId(4);
        Borrowing closedBorrowing = new Borrowing(BorrowStatus.RETURNED, 1, "9780345391803", new GregorianCalendar(2017,6,28));
        inProgressBorrowing.setBorrowId(5);

        List<Borrowing> borrowingListToTest = new ArrayList<>();
        borrowingListToTest.add(inProgressBorrowing);
        borrowingListToTest.add(closedBorrowing);
        borrowingDao.create(borrowingListToTest);

        List<Borrowing> borrowingsInDB = borrowingDao.read();
        Assert.assertThat(borrowingsInDB, hasItems(inProgressBorrowing, closedBorrowing));

        inProgressBorrowing.setStatus(BorrowStatus.RETURNED);
        borrowingDao.update(borrowingListToTest);

        Assert.assertThat(borrowingDao.read(), hasItems(inProgressBorrowing, closedBorrowing));

        borrowingDao.delete(borrowingListToTest);
        Assert.assertThat(borrowingDao.read(), not(hasItems(inProgressBorrowing, closedBorrowing)));
    }

    @Test
    public void readByIDsTest() {
        int fixedBorrowId = 80;
        Borrowing testBorrowing = new Borrowing(BorrowStatus.REQUESTED, 2, "0345391802", new GregorianCalendar(2018,11,23));
        testBorrowing.setBorrowId(fixedBorrowId);
        borrowingDao.create(testBorrowing);

        Assert.assertEquals(testBorrowing, borrowingDao.read(fixedBorrowId));

        User creator = new User();
        creator.setUserId(2);
        Assert.assertThat(borrowingDao.readByUser(creator), hasItems(testBorrowing));

        borrowingDao.delete(fixedBorrowId);
    }

    @Test
    public void notSetBorrowIdTest() {
        Borrowing testBorrowing = new Borrowing(BorrowStatus.REQUESTED, 2, "0345391802", new GregorianCalendar());
        borrowingDao.create(testBorrowing);

        List<Borrowing> borrowingsInDB = borrowingDao.read();
        Assert.assertEquals(testBorrowing, borrowingsInDB.get(borrowingsInDB.size() - 1));
    }
}