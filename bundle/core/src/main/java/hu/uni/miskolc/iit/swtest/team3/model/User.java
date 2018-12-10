package hu.uni.miskolc.iit.swtest.team3.model;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;

public class User {

    private static final EmailValidator EMAIL_VALIDATOR = EmailValidator.getInstance();

    private int userId;
    private String passwordHash;
    private String name;
    private String email;
    private Boolean librarian;

    public User(){}

    public User(String passwordHash, String name, String email, Boolean librarian) {
        setPasswordHash(passwordHash);
        setName(name);
        setEmail(email);
        setLibrarian(librarian);
    }

    public User(int userId, String passwordHash, String name, String email, Boolean librarian) {
        setUserId(userId);
        setPasswordHash(passwordHash);
        setName(name);
        setEmail(email);
        setLibrarian(librarian);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(EMAIL_VALIDATOR.isValid(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email address, please check its format!");
        }
    }

    public Boolean isLibrarian() {
        return librarian;
    }

    public void setLibrarian(Boolean librarian) {
        this.librarian = librarian;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUserId() == user.getUserId() &&
                Objects.equals(getPasswordHash(), user.getPasswordHash()) &&
                Objects.equals(getName(), user.getName()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(librarian, user.librarian);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getUserId(), getPasswordHash(), getName(), getEmail(), librarian);
    }
}
