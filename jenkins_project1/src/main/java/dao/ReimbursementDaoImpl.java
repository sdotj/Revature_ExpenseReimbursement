package dao;

import model.Reimbursement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDaoImpl implements ReimbursementDao{

    public static String url = "jdbc:postgresql://" + System.getenv("MY_DB_ENDPOINT") + "/ExpenseReimbursementDB";
    public static String username = System.getenv("MY_DB_USERNAME");
    public static String password = System.getenv("MY_DB_PASSWORD");

    @Override
    public List<Reimbursement> getAllReimbursements() {
        List<Reimbursement> allReims = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "select * from ERS_REIMBURSEMENT";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                allReims.add(
                        new Reimbursement(rs.getInt(1),
                                rs.getDouble(2),
                                rs.getDate(3),
                                rs.getDate(4),
                                rs.getString(5),
                                rs.getBlob(6),
                                rs.getInt(7),
                                rs.getInt(8),
                                rs.getInt(9),
                                rs.getInt(10)));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return allReims;
    }

    @Override
    public List<Reimbursement> getAllReimbursementsForUser(int userId) {
        List<Reimbursement> allReimsForUser = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "select * from ERS_REIMBURSEMENT where reimb_author = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                allReimsForUser.add(
                        new Reimbursement(rs.getInt(1),
                                rs.getDouble(2),
                                rs.getDate(3),
                                rs.getDate(4),
                                rs.getString(5),
                                rs.getBlob(6),
                                rs.getInt(7),
                                (rs.getInt(8)),
                                rs.getInt(9),
                                rs.getInt(10)));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return allReimsForUser;
    }

    @Override
    public boolean addNewReimbursement(Reimbursement reim, int userID) {
        try(Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "insert into ers_reimbursement " +
                    "(reimb_id, reimb_amount, " +
                    "reimb_submitted, " +
                    "reimb_resolved," +
                    "reimb_description, " +
                    "reimb_author, " +
                    "reimb_status_id, " +
                    "reimb_type_id)" +
                    "values (default, ?, default, null, ?, ?, ?, ?);";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, reim.getAmount());
            ps.setString(2, reim.getDescription());
            ps.setInt(3, userID);
            ps.setInt(4, reim.getStatus());
            ps.setInt(5, reim.getType());

            return ps.executeUpdate() != 0;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean approveReimbursement(int reimId, int resolverId) {
        try(Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "update ers_reimbursement set reimb_status_id = 2 where reimb_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, reimId);
            ps.executeUpdate();

            sql = "update ers_reimbursement set reimb_resolver = ? where reimb_id = ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, resolverId);
            ps.setInt(2, reimId);
            ps.executeUpdate();

            sql = "update ers_reimbursement set reimb_resolved = default where reimb_id = ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, reimId);
            return ps.executeUpdate() != 0;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean denyReimbursement(int reimId, int resolverId) {
        try(Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "update ers_reimbursement set reimb_status_id = 3 where reimb_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, reimId);
            ps.executeUpdate();

            sql = "update ers_reimbursement set reimb_resolver = ? where reimb_id = ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, resolverId);
            ps.setInt(2, reimId);
            ps.executeUpdate();

            sql = "update ers_reimbursement set reimb_resolved = default where reimb_id = ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, reimId);
            return ps.executeUpdate() != 0;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteReimbursement(int reimId) {
        try(Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "delete from ERS_REIMBURSEMENT where reimb_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, reimId);
            return ps.executeUpdate() != 0;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
