package hu.uni.miskolc.iit.swtest.team3.dao;

import hu.uni.miskolc.iit.swtest.team3.model.BorrowStatus;
import hu.uni.miskolc.iit.swtest.team3.model.Borrowing;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;

@Repository
public class BorrowingRowMapper implements RowMapper<Borrowing> {

    @Override
    public Borrowing mapRow(ResultSet resultSet, int i) throws SQLException {
        Borrowing borrowing = new Borrowing();

        borrowing.setBorrowId(resultSet.getInt("borrowId"));
        borrowing.setStatus(BorrowStatus.valueOf(resultSet.getString("status").toUpperCase()));
        borrowing.setCreatorId(resultSet.getInt("creatorId"));
        borrowing.setBookIsbn(resultSet.getString("bookIsbn"));

        GregorianCalendar creationDate = new GregorianCalendar();
        creationDate.setTime(resultSet.getDate("creationDate"));
        borrowing.setCreationDate(creationDate);

        return borrowing;
    }
}
