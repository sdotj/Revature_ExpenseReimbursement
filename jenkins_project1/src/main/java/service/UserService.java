package service;

import model.User;

import java.util.List;

public interface UserService {

    public List<User> getAllUsers();

    public User getUserById(int id);

    public User getUserByUserName(String username);

    public boolean createUser(User u);

    public boolean deleteUser(int id);

}
