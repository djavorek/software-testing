package hu.uni.miskolc.iit.swtest.team3.model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

public class Borrowing {

    private int borrowId;
    private BorrowStatus status;
    private int creatorId;
    private String bookIsbn;
    private GregorianCalendar creationDate;

    public Borrowing(){}

    public Borrowing(BorrowStatus status, int creatorId, String bookIsbn, GregorianCalendar creationDate) {
        setStatus(status);
        setCreatorId(creatorId);
        setBookIsbn(bookIsbn);
        setCreationDate(creationDate);
    }

    public Borrowing(int borrowId, BorrowStatus status, int creatorId, String bookIsbn, GregorianCalendar creationDate) {
        setBorrowId(borrowId);
        setStatus(status);
        setCreatorId(creatorId);
        setBookIsbn(bookIsbn);
        setCreationDate(creationDate);
    }

    public int getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }

    public BorrowStatus getStatus() {
        return status;
    }

    public void setStatus(BorrowStatus status) {
        this.status = status;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public GregorianCalendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(GregorianCalendar creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Borrowing borrowing = (Borrowing) o;
        return  getBorrowId() == borrowing.getBorrowId() &&
                getCreatorId() == borrowing.getCreatorId() &&
                getStatus() == borrowing.getStatus() &&
                Objects.equals(getBookIsbn(), borrowing.getBookIsbn()) &&
                getCreationDate().get(Calendar.YEAR) == borrowing.getCreationDate().get(Calendar.YEAR) &&
                getCreationDate().get(Calendar.DAY_OF_YEAR) == borrowing.getCreationDate().get(Calendar.DAY_OF_YEAR);
    }
    @Override
    public int hashCode() {
        return Objects.hash(getBorrowId(), getStatus(), getCreatorId(), getBookIsbn(), getCreationDate());
    }
}
