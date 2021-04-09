package frontcontroller;

import controller.ReimbursementController;
import controller.UserController;
import io.javalin.Javalin;

import java.net.UnknownServiceException;

import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * Class intended to manage server requests.  The dispatcher controller specifies a path for
 * any request sent to the server and leverages the UserController and Reimbursement Controller
 * to handle them.
 */
public class Dispatcher {
    UserController uc = new UserController();
    ReimbursementController rc = new ReimbursementController();

    public Dispatcher(Javalin app) {
        app.routes(() -> {
            path("/api", () -> {
                path("post", () -> {
                    path("users", () -> {
                        path("login", () -> {
                            post(UserController::loginUser);
                        });
                        path("newReimbursement", () -> {
                            post(ReimbursementController::sendToNewTicket);
                        });
                        path("redirectToSignUp", () -> {
                            post(UserController::signUpRedirect);
                        });
                        path("signup", () -> {
                            post(UserController::signUpUser);
                        });
                    });
                    path("reimbursements", () -> {
                        path("new", () -> {
                            post(ReimbursementController::newReimbursement);
                        });
                        path("approve", () -> {
                            post(ReimbursementController::approveReimbursement);
                        });
                        path("deny", () -> {
                            post(ReimbursementController::denyReimbursement);
                        });
                    });
                });
                path("get", () -> {
                    path("users", () -> {
                        path("feedback", () -> {
                            get(UserController::checkLoginStatus);
                        });
                        path("currentUser", () -> {
                            get(UserController::giveCurrentUser);
                        });
                        path("logout", () -> {
                            get(UserController::logOut);
                        });
                    });
                    path("reimbursements", () -> {
                        path("getAll", () -> {
                            get(ReimbursementController::getAllReimbursementsForUser);
                        });
                    });
                    path("reimbursements", () -> {
                        path("getAllManager", () -> {
                            get(ReimbursementController::getAllReimbursementsForManager);
                        });
                    });
                });
            });
        });
    }
}
