package dao;

import model.Reimbursement;
import java.util.List;

/**
 * ReimbursementDao Interface implemented by Reimbursement DAO class
 */
public interface ReimbursementDao {

    /**
     * Gets all reimbursements in DB.
     * @return List of all reimbursements
     */
    List<Reimbursement> getAllReimbursements();

    /**
     * @param userId of user to get reimbursements for.
     * @return List of all reimbursements for given user.
     */
    List<Reimbursement> getAllReimbursementsForUser(int userId);
    boolean addNewReimbursement(Reimbursement reim, int userId);
    boolean approveReimbursement(int reimId, int resolverId);
    boolean denyReimbursement(int reimId, int resolverId);
    boolean deleteReimbursement(int reimId);

}
