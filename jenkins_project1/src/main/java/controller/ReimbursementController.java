package controller;

import io.javalin.http.Context;
import model.Reimbursement;
import service.ReimbursementService;
import service.ReimbursementServiceImpl;
import java.util.List;

/**
 * Handles reimbursement related requests such as getting all reimbursements for an employee
 * or manager.
 */
public class ReimbursementController {
    static ReimbursementService rs = new ReimbursementServiceImpl();

    public ReimbursementController() {

    }

    /**
     * Leverages the UserService class to get all requests for a given user.
     * @param context
     */
    public static void getAllReimbursementsForUser(Context context) {
        int userId = context.sessionAttribute("currentUser");
        //System.out.println(userId);
        List<Reimbursement> allReims = rs.getAllReimbursementsForUser(userId);
        context.json(allReims);
    }

    /**
     * Leverages the ReimbursementService class to get all requests for a manager.
     * @param context
     */
    public static void getAllReimbursementsForManager(Context context) {
        int userId = context.sessionAttribute("currentUser");
        //System.out.println(userId);
        List<Reimbursement> allReims = rs.getAllReimbursements();
        context.json(allReims);
    }

    /**
     * Redirects user to a page where they can submit a new request
     * @param context
     */
    public static void sendToNewTicket(Context context) {
        //System.out.println("To new reimbursement?");
        context.redirect("/employee/newReimbursement");
    }

    /**
     * Leverages the ReimbursementService class to add a new reimbursement to the DB
     * @param context
     */
    public static void newReimbursement(Context context) {
        Reimbursement newReim = null;

        int userId = context.sessionAttribute("currentUser");
        double amount = Double.parseDouble(context.formParam("amount"));
        String description = context.formParam("description");
        int status = 1;
        int type = Integer.parseInt(context.formParam("type"));

        newReim = new Reimbursement(amount, description, userId, status, type);
        rs.addNewReimbursement(newReim, userId);
        context.redirect("/employee/dashboard");
    }

    /**
     * Updates a reimbursement's status to approved upon manager action and sends the manager
     * back to their dashboard page.
     * @param context
     */
    public static void approveReimbursement(Context context) {
        //System.out.println("approve clicked");
        int reimId = Integer.parseInt(context.formParam("ID"));
        int resolverID = Integer.parseInt(context.formParam("resolver"));
        //System.out.println(reimId);
        rs.approveReimbursement(reimId, resolverID);
        context.redirect("/manager/dashboard");
    }

    /**
     * Updates a reimbursement's status to denied upon manager action and sends the manager
     * back to their dashboard page.
     * @param context
     */
    public static void denyReimbursement(Context context) {
        //System.out.println("deny clicked");
        int reimId = Integer.parseInt(context.formParam("ID"));
        int resolverID = Integer.parseInt(context.formParam("resolver"));
        rs.denyReimbursement(reimId, resolverID);
        context.redirect("/manager/dashboard");
    }

}
