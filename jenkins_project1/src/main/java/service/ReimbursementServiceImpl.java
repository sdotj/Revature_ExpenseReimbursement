package service;

import dao.ReimbursementDao;
import dao.ReimbursementDaoImpl;
import model.Reimbursement;

import java.util.List;

public class ReimbursementServiceImpl implements ReimbursementService{

    ReimbursementDao rd = new ReimbursementDaoImpl();

    @Override
    public List<Reimbursement> getAllReimbursements() {
        return rd.getAllReimbursements();
    }

    @Override
    public List<Reimbursement> getAllReimbursementsForUser(int userId) {
        return rd.getAllReimbursementsForUser(userId);
    }

    @Override
    public boolean addNewReimbursement(Reimbursement reim, int userId) {
        return rd.addNewReimbursement(reim, userId);
    }

    @Override
    public boolean approveReimbursement(int reimId, int resolverId) {
        return rd.approveReimbursement(reimId, resolverId);
    }

    @Override
    public boolean denyReimbursement(int reimId, int resolverId) {
        return rd.denyReimbursement(reimId, resolverId);
    }

    @Override
    public boolean deleteReimbursement(int reimId) {
        return deleteReimbursement(reimId);
    }
}
