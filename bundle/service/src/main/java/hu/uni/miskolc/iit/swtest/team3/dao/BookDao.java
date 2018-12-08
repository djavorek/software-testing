package hu.uni.miskolc.iit.swtest.team3.dao;

import hu.uni.miskolc.iit.swtest.team3.model.Book;

import java.util.List;

public interface BookDao {

    boolean create(Book book);

    int[] create(List<Book> books);

    Book read(String isbn);

    List<Book> read();

    int update(Book book);

    int[] update(List<Book> books);

    int delete(Book book);

    int delete(String isbn);

    int[] delete(List<Book> books);

}
