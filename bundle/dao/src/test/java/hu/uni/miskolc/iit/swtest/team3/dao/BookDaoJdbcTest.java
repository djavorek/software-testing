package hu.uni.miskolc.iit.swtest.team3.dao;

import hu.uni.miskolc.iit.swtest.team3.dao.config.SpringDaoTestConfig;
import hu.uni.miskolc.iit.swtest.team3.model.Book;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={SpringDaoTestConfig.class})
public class BookDaoJdbcTest {

    @Autowired
    private BookDao bookDao;

    @Test
    public void oneBookLifecycleTest() {
        Book bookToTest = new Book(
                "978-0-7653-1985-2",
                "Cory Doctorow",
                "Little Brother",
                "Marcus Yallow is a 17-year-old hacker/techno whiz from San Francisco...",
                "English",
                16);

        Assert.assertEquals(1, bookDao.create(bookToTest));

        Assert.assertThat(bookDao.read(), hasItem(bookToTest));
        Assert.assertEquals(bookToTest, bookDao.read("978-0-7653-1985-2"));

        bookToTest.setAvailableCopies(1);
        Assert.assertEquals(1, bookDao.update(bookToTest));
        Assert.assertThat(bookDao.read(), hasItem(bookToTest));
        Assert.assertEquals(bookToTest, bookDao.read("978-0-7653-1985-2"));

        Assert.assertEquals(1, bookDao.delete(bookToTest));
        Assert.assertThat(bookDao.read(), not(hasItem(bookToTest)));
    }

    @Test
    public void multipleBookLifeCycleTest() {
        Book creepyBook = new Book("9786155676390", "Rachel Abbott", "Aludj jól", "Meddig mennél el a szeretteidért?", "Magyar", 20);
        Book adventureBook = new Book("9789632663630", "James Dashner", "Útvesztő", "Thomas egy hideg, sötét liftben tér magához..", "Magyar", 6);

        List<Book> bookListToTest = new ArrayList<>();
        bookListToTest.add(creepyBook);
        bookListToTest.add(adventureBook);

        bookDao.create(bookListToTest);
        Assert.assertThat(bookDao.read(), hasItems(creepyBook, adventureBook));
        bookListToTest.clear();

        creepyBook.setAvailableCopies(150);
        bookListToTest.add(creepyBook);
        bookListToTest.add(adventureBook);
        bookDao.update(bookListToTest);
        Assert.assertThat(bookDao.read(), hasItems(creepyBook, adventureBook));

        bookDao.delete(bookListToTest);
        Assert.assertThat(bookDao.read(), not(hasItems(creepyBook, adventureBook)));
    }

    @Test
    public void deleteByIsbnTest() {
        Book bookToTest = new Book("963-11-7380-1", "Antoine de Saint-Exupéry", "A kis herceg", "A könyv írója repülőgép-balesetet szenved, és lázálmot lát.", "Magyar", 20);
        Assert.assertEquals(1, bookDao.create(bookToTest));

        bookDao.delete("963-11-7380-1");
        Assert.assertThat(bookDao.read(), not(hasItem(bookToTest)));
    }

    @Test
    public void modifyingNotExistingBooksTest() {
        Assert.assertEquals(0, bookDao.update(new Book("111-11-1111-1","Foo", "Bar", "Baz", "Qux", 0)));
        Assert.assertEquals(0, bookDao.delete("111-11-1111-1"));
    }
}