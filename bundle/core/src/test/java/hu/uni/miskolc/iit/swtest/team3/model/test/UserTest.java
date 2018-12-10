package hu.uni.miskolc.iit.swtest.team3.model.test;

import hu.uni.miskolc.iit.swtest.team3.model.User;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Assert;
import org.junit.Test;

public class UserTest {

    private static final int TEST_USERID = 1;
    private static final String TEST_PASSWORDHASH = "$2a$frtF_$dasgew";
    private static final String TEST_NAME = "Test Thomas";
    private static final String TEST_EMAIL = "test.thomas88@unittest.org";
    private static final Boolean TEST_LIBRARIAN = Boolean.TRUE;

    @Test
    public void constructorTest() {
        User userToTest = new User(TEST_USERID, TEST_PASSWORDHASH, TEST_NAME, TEST_EMAIL, TEST_LIBRARIAN);

        Assert.assertEquals(TEST_USERID, userToTest.getUserId());
        Assert.assertEquals(TEST_PASSWORDHASH, userToTest.getPasswordHash());
        Assert.assertEquals(TEST_NAME, userToTest.getName());
        Assert.assertEquals(TEST_EMAIL, userToTest.getEmail());
        Assert.assertEquals(TEST_LIBRARIAN, userToTest.isLibrarian());
    }

    @Test
    public void setterTest() {
        User userToTest = new User();

        userToTest.setUserId(TEST_USERID);
        userToTest.setPasswordHash(TEST_PASSWORDHASH);
        userToTest.setName(TEST_NAME);
        userToTest.setEmail(TEST_EMAIL);
        userToTest.setLibrarian(TEST_LIBRARIAN);

        Assert.assertEquals(TEST_USERID, userToTest.getUserId());
        Assert.assertEquals(TEST_PASSWORDHASH, userToTest.getPasswordHash());
        Assert.assertEquals(TEST_NAME, userToTest.getName());
        Assert.assertEquals(TEST_EMAIL, userToTest.getEmail());
        Assert.assertEquals(TEST_LIBRARIAN, userToTest.isLibrarian());
    }

    @Test
    public void constructorTestNoId() {
        User userToTest = new User(TEST_PASSWORDHASH, TEST_NAME, TEST_EMAIL, TEST_LIBRARIAN);

        Assert.assertEquals(TEST_PASSWORDHASH, userToTest.getPasswordHash());
        Assert.assertEquals(TEST_NAME, userToTest.getName());
        Assert.assertEquals(TEST_EMAIL, userToTest.getEmail());
        Assert.assertEquals(TEST_LIBRARIAN, userToTest.isLibrarian());
    }

    @Test
    public void setterTestNoId() {
        User userToTest = new User();

        userToTest.setPasswordHash(TEST_PASSWORDHASH);
        userToTest.setName(TEST_NAME);
        userToTest.setEmail(TEST_EMAIL);
        userToTest.setLibrarian(TEST_LIBRARIAN);

        Assert.assertEquals(TEST_PASSWORDHASH, userToTest.getPasswordHash());
        Assert.assertEquals(TEST_NAME, userToTest.getName());
        Assert.assertEquals(TEST_EMAIL, userToTest.getEmail());
        Assert.assertEquals(TEST_LIBRARIAN, userToTest.isLibrarian());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setEmailTest(){
        User user = new User();
        user.setEmail(null);
    }

    @Test
    public void equalsContractTest() {
        EqualsVerifier.forClass(User.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}