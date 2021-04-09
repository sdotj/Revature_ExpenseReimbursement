package dao;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao{

    public static String url = "jdbc:postgresql://" + System.getenv("MY_DB_ENDPOINT") + "/ExpenseReimbursementDB";
    public static String username = System.getenv("MY_DB_USERNAME");
    public static String password = System.getenv("MY_DB_PASSWORD");

//    public static String url = "jdbc:h2:\\Users\\sam\\Desktop";
//    public static String username = "sa";
//    public static String password = "sa";

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "select * from ERS_USERS";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                allUsers.add(
                        new User(rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getInt(7)));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return allUsers;
    }

    @Override
    public User getUserById(int id) {
        User newUser = null;
        try(Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "select * from ERS_USERS where ers_user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                newUser =
                        new User(rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getInt(7));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return newUser;
    }

    @Override
    public User getUserByUserName(String userName) {
        //System.out.println(username);
        User newUser = null;
        try(Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "select * from ERS_USERS where ers_username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userName);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                newUser =
                        new User(rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getInt(7));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        //System.out.println(newUser);
        return newUser;
    }

    @Override
    public boolean createUser(User u) {
        try(Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "insert into ERS_USERS values(default, ?, ?, ?, ?, ?, 1)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getFirstName());
            ps.setString(4, u.getLastName());
            ps.setString(5, u.getEmail());

            return ps.executeUpdate() != 0;
        } catch(SQLException e){
            //e.printStackTrace();
            System.out.println("user already exists");
        }
        return false;
    }

    @Override
    public boolean deleteUser(int id) {
        try(Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "delete from ERS_USERS where ers_user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            return ps.executeUpdate() != 0;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
