package hu.uni.miskolc.iit.swtest.team3.dao;

import hu.uni.miskolc.iit.swtest.team3.dao.config.SpringDaoConfig;
import hu.uni.miskolc.iit.swtest.team3.dao.config.SpringDaoTestConfig;
import hu.uni.miskolc.iit.swtest.team3.model.Book;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={SpringDaoConfig.class, SpringDaoTestConfig.class})

public class BookDaoJdbcTest {

    @Autowired
    BookDao bookDao;

    @Test
    public void readTest() {
        bookDao.read();
    }

    @Test
    public void readByIsbnTest() {
        final String isbn = "0345391802";
        Book book = bookDao.read(isbn);
        Assert.assertEquals(isbn, book.getIsbn());
    }

}
