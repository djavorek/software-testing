package hu.uni.miskolc.iit.swtest.team3.dao;

import hu.uni.miskolc.iit.swtest.team3.dao.config.SpringDaoTestConfig;
import hu.uni.miskolc.iit.swtest.team3.model.Book;
import hu.uni.miskolc.iit.swtest.team3.model.User;
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
public class UserDaoJdbcTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void oneUserLifecycleTest() {
        int fixedUserId = 33;
        User userToTest = new User("#88dtmfe", "Luke Skywalker", "skywalker@gmail.com", Boolean.FALSE);
        userToTest.setUserId(fixedUserId);

        Assert.assertEquals(1, userDao.create(userToTest));

        Assert.assertThat(userDao.read(), hasItem(userToTest));
        Assert.assertEquals(userToTest, userDao.read(fixedUserId));

        userToTest.setLibrarian(Boolean.TRUE);
        Assert.assertEquals(1, userDao.update(userToTest));
        Assert.assertThat(userDao.read(), hasItem(userToTest));
        Assert.assertEquals(userToTest, userDao.read(fixedUserId));

        Assert.assertEquals(1, userDao.delete(userToTest));
        Assert.assertThat(userDao.read(), not(hasItem(userToTest)));
    }

    @Test
    public void deleteByUserIdTest() {
        User userToTest = new User("#75643sdfd", "Noname Gábor", "smallcatdestroyer@sofunny.eu", Boolean.FALSE);
        userToTest.setUserId(33);
        Assert.assertEquals(1, userDao.create(userToTest));
        Assert.assertThat(userDao.read(), hasItem(userToTest));

        Assert.assertEquals(1, userDao.delete(33));
        Assert.assertThat(userDao.read(), not(hasItem(userToTest)));
    }
}