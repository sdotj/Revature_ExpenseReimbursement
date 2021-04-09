package service;

import model.Reimbursement;

import java.util.List;

public interface ReimbursementService {

    public List<Reimbursement> getAllReimbursements();
    public List<Reimbursement> getAllReimbursementsForUser(int userId);
    public boolean addNewReimbursement(Reimbursement reim, int userId);
    public boolean approveReimbursement(int reimId, int resolverId);
    public boolean denyReimbursement(int reimId, int resolverId);
    public boolean deleteReimbursement(int reimId);

}
