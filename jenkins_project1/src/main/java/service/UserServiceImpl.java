package service;

import dao.UserDao;
import dao.UserDaoImpl;
import model.User;

import java.util.List;

public class UserServiceImpl implements UserService{

    UserDao ud = new UserDaoImpl();

    @Override
    public List<User> getAllUsers() {
        return ud.getAllUsers();
    }

    @Override
    public User getUserById(int id) {
        return ud.getUserById(id);
    }

    @Override
    public User getUserByUserName(String username) {
        return ud.getUserByUserName(username);
    }

    @Override
    public boolean createUser(User u) {
        return ud.createUser(u);
    }

    @Override
    public boolean deleteUser(int id) {
        return ud.deleteUser(id);
    }
}
