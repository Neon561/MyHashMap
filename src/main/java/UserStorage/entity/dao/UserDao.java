package UserStorage.entity.dao;

import UserStorage.entity.User;

import java.util.List;

public interface UserDao {
    User findById(Long id);
    List<User> findAll();
    void save(User user);
    void update(User user);
    void delete(User user);
}
