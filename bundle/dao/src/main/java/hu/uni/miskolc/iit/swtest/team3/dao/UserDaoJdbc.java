package hu.uni.miskolc.iit.swtest.team3.dao;

import hu.uni.miskolc.iit.swtest.team3.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoJdbc implements UserDao {

    private static final String SELECT = "SELECT * FROM user";
    private static final String SELECT_BY_ID = "SELECT * FROM user WHERE userId = :userId";
    private static final String INSERT = "INSERT INTO user (userId, name, email, passwordHash, librarian) values (:userId, :name, :email, :passwordHash, :librarian)";
    private static final String UPDATE_BY_ID = "UPDATE user SET userId=:userId, name=:name, email=:email, passwordHash=:passwordHash, librarian=:librarian WHERE userId=:userId";
    private static final String DELETE_BY_ID = "DELETE FROM user WHERE userId=:userId";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    final
    RowMapper<User> rowMapper;

    @Autowired
    public UserDaoJdbc(RowMapper<User> rowMapper) {
        this.rowMapper = rowMapper;
    }

    @Override
    public int create(User user) {
        return namedParameterJdbcTemplate.update(INSERT, getSqlParameterSource(user));
    }

    @Override
    public User read(int userId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("userId", userId);
        return namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID, namedParameters, rowMapper);
    }

    @Override
    public List<User> read() {
        return jdbcTemplate.query(SELECT, rowMapper);
    }

    @Override
    public int update(User user) {
        return namedParameterJdbcTemplate.update(UPDATE_BY_ID, getSqlParameterSource(user));
    }

    @Override
    public int delete(User user) {
        return namedParameterJdbcTemplate.update(DELETE_BY_ID, getSqlParameterSource(user));
    }

    @Override
    public int delete(int userId) {
        return namedParameterJdbcTemplate.update(DELETE_BY_ID, new MapSqlParameterSource().addValue("userId", userId));
    }

    private SqlParameterSource getSqlParameterSource(User user) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue("userId", user.getUserId());
        namedParameters.addValue("name", user.getName());
        namedParameters.addValue("email", user.getEmail());
        namedParameters.addValue("passwordHash", user.getPasswordHash());
        namedParameters.addValue("librarian", user.isLibrarian());

        return namedParameters;
    }
}
