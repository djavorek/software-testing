package hu.uni.miskolc.iit.swtest.team3.dao;

import hu.uni.miskolc.iit.swtest.team3.model.User;
import java.util.List;

/**
 *
 * @author tdavi
 */
public interface UserDao {
    
    int create(User user);
    
    User read(int userId);

    List<User> read();

    int update(User user);

    int delete(User user);

    int delete(int userId);

}
