package hu.uni.miskolc.iit.swtest.team3.model;

import org.apache.commons.validator.routines.ISBNValidator;

import java.util.Objects;

public class Book {

    private static final ISBNValidator ISBN_VALIDATOR = ISBNValidator.getInstance();

    private String isbn;
    private String author;
    private String title;
    private String description;
    private String language;
    private int availableCopies;

    public Book(){}

    public Book(String isbn, String author, String title, String description, String language, int availableCopies) {
        setIsbn(isbn);
        setAuthor(author);
        setTitle(title);
        setDescription(description);
        setLanguage(language);
        setAvailableCopies(availableCopies);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        if(ISBN_VALIDATOR.isValid(isbn)) {
            this.isbn = isbn;
        } else {
            throw new IllegalArgumentException("The given ISBN is invalid, check its format!");
        }
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        if(availableCopies >= 0) {
            this.availableCopies = availableCopies;
        } else {
            throw new IllegalArgumentException("The number of available copies should be a positive number!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(getIsbn(), book.getIsbn()) &&
                Objects.equals(getAuthor(), book.getAuthor()) &&
                Objects.equals(getTitle(), book.getTitle()) &&
                Objects.equals(getDescription(), book.getDescription()) &&
                Objects.equals(getLanguage(), book.getLanguage()) &&
                getAvailableCopies() == book.getAvailableCopies();
    }
    @Override
    public int hashCode() {
        return Objects.hash(getIsbn(), getAuthor(), getTitle(), getDescription(), getLanguage(), getAvailableCopies());
    }
}
