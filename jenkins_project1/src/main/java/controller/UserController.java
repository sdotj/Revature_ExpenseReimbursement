package controller;

import io.javalin.http.Context;
import model.User;
import service.UserService;
import service.UserServiceImpl;

/**
 * Handles user related requests such as logging in or out, and checking login status.
 */
public class UserController {
    static UserService us = new UserServiceImpl();

    public UserController() {

    }

    /**
     * Handles user login.  Provides appropriate action for invalid usernames, passwords,
     * and successful logins based on session attributes.  Triggered by submitting the login
     * form.
     * @param context
     */
    public static void loginUser(Context context){
        String username = context.formParam("username");
        String password = context.formParam("password");

        User checkUser = us.getUserByUserName(username);

        if(checkUser != null) {
            if(checkUser.getPassword().equals(password)){
                if (checkUser.getUserRole() == 1) {
                    context.sessionAttribute("feedback", "loggedIn");
                    context.sessionAttribute("currentUser", checkUser.getUserId());
                    context.redirect("/employee/dashboard");
                }
                else {
                    context.sessionAttribute("feedback", "loggedIn");
                    context.sessionAttribute("currentUser", checkUser.getUserId());
                    context.redirect("/manager/dashboard");
                }
            }
            else {
                context.sessionAttribute("feedback", "invalidPassword");
                context.redirect("/");
            }
        }
        else {
            context.sessionAttribute("feedback", "invalidUser");
            context.redirect("/");
        }

    }

    /**
     * A method to check if a user is logged in.  Uses the session attribute "feedback" to determine.
     * @param context
     */
    public static void checkLoginStatus(Context context) {
        String feedbackVal = context.sessionAttribute("feedback");
        if(feedbackVal != null) {
            context.json(feedbackVal);
        }
        else {
            context.json("");
        }
    }

    /**
     * Provides the client with the Object associated with the current user.  Used for DOM
     * manipulation and reimbursement related actions.
     * @param context
     */
    public static void giveCurrentUser(Context context) {
        int userId = context.sessionAttribute("currentUser");
        User u = us.getUserById(userId);
        context.json(u);
    }

    /**
     * Handles user logouts.  Sets appropriate session attributes to null in order to redirect user
     * and keep them logged out.
     * @param context
     */
    public static void logOut(Context context) {
        context.sessionAttribute("currentUser", null);
        context.sessionAttribute("feedback", null);
        context.redirect("/");
    }

    public static void signUpRedirect(Context context) {
        context.redirect("/signup");
    }

    public static void signUpUser(Context context) {
        String firstName = context.formParam("first");
        String lastName = context.formParam("last");
        String userName = context.formParam("username");
        String password = context.formParam("password");
        String confirmPassword = context.formParam("confirmPassword");
        String email = context.formParam("email");

        if(!(password.equals(confirmPassword))) {
            context.sessionAttribute("feedback","passwordMismatch");
            context.redirect("/signup");
        }
        else {
            User newUser = new User(userName, password, firstName, lastName, email);
            boolean created = us.createUser(newUser);
            if(!created) {
                context.sessionAttribute("feedback", "userExists");
                context.redirect("/signup");
            }
            else {
                context.redirect("/");
            }
        }

    }

}
