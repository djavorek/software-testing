package hu.uni.miskolc.iit.swtest.team3.dao;

import hu.uni.miskolc.iit.swtest.team3.model.Book;
import hu.uni.miskolc.iit.swtest.team3.model.Borrowing;
import hu.uni.miskolc.iit.swtest.team3.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class BorrowingDaoJdbc implements BorrowingDao {

    private static final String SELECT = "SELECT * FROM borrowings";
    private static final String SELECT_BY_ID = "SELECT * FROM borrowings WHERE borrowId = :borrowId";
    private static final String SELECT_BY_USER = "SELECT * FROM borrowings WHERE creatorId = :creatorId";
    private static final String SELECT_BY_BOOK ="SELECT * FROM borrowings WHERE bookIsbn = :bookIsbn";
    private static final String INSERT = "INSERT INTO borrowings(borrowId, status, creatorId, bookIsbn, creationDate) values (:borrowId, :status, :creatorId, :bookIsbn, :creationDate)";
    private static final String UPDATE_BY_ID = "UPDATE borrowings SET borrowId=:borrowId, status=:status, creatorId=:creatorId, bookIsbn=:bookIsbn, creationDate=:creationDate WHERE borrowId=:borrowId";
    private static final String DELETE_BY_ID = "DELETE FROM borrowings WHERE borrowId=:borrowId";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    final RowMapper<Borrowing> rowMapper;

    @Autowired
    public BorrowingDaoJdbc(RowMapper<Borrowing> rowMapper){
        this.rowMapper = rowMapper;
    }

    @Override
    public int create(Borrowing borrowing){
        return namedParameterJdbcTemplate.update(INSERT, getSqlParameterSource(borrowing));
    }

    @Override
    public int[] create(List<Borrowing> borrowings) {
        SqlParameterSource[] params = new SqlParameterSource[borrowings.size()];

        for (int i = 0; i < borrowings.size(); i++) {
            BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(borrowings.get(i));
            parameterSource.registerSqlType("status", Types.VARCHAR);
            params[i] = parameterSource;
        }

        return namedParameterJdbcTemplate.batchUpdate(INSERT, params);
    }

    @Override
    public Borrowing read(int borrowId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("borrowId", borrowId);
        return namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID, namedParameters, rowMapper);
    }

    @Override
    public Borrowing read(Book book){
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("bookIsbn", book.getIsbn());
        return namedParameterJdbcTemplate.queryForObject(SELECT_BY_BOOK,namedParameters,rowMapper);
    }

    @Override
    public List<Borrowing> read() {
        return jdbcTemplate.query(SELECT, rowMapper);
    }

    @Override
    public List<Borrowing> readByUser(User creator) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("creatorId", creator.getUserId());
        return namedParameterJdbcTemplate.query(SELECT_BY_USER, namedParameters, rowMapper);
    }

    @Override
    public int update(Borrowing borrowing) {
        return namedParameterJdbcTemplate.update(UPDATE_BY_ID, getSqlParameterSource(borrowing));
    }

    @Override
    public int[] update(List<Borrowing> borrowings) {
        SqlParameterSource[] params = new SqlParameterSource[borrowings.size()];

        for (int i = 0; i < borrowings.size(); i++) {
            BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(borrowings.get(i));
            parameterSource.registerSqlType("status", Types.VARCHAR);
            params[i] = parameterSource;
        }

        return namedParameterJdbcTemplate.batchUpdate(UPDATE_BY_ID, params);}

    @Override
    public int delete(Borrowing borrowing) {
        return namedParameterJdbcTemplate.update(DELETE_BY_ID, getSqlParameterSource(borrowing));
    }

    @Override
    public int delete(int borrowId) {
        return namedParameterJdbcTemplate.update(DELETE_BY_ID, new MapSqlParameterSource().addValue("borrowId", borrowId));
    }

    @Override
    public int[] delete(List<Borrowing> borrowings) {
        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(borrowings.toArray());
        return namedParameterJdbcTemplate.batchUpdate(DELETE_BY_ID, params);}

    private SqlParameterSource getSqlParameterSource(Borrowing borrowing) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue("borrowId", borrowing.getBorrowId());
        namedParameters.addValue("status", borrowing.getStatus().name());
        namedParameters.addValue("creatorId", borrowing.getCreatorId());
        namedParameters.addValue("bookIsbn", borrowing.getBookIsbn());
        namedParameters.addValue("creationDate", borrowing.getCreationDate());

        return namedParameters;
    }
}
