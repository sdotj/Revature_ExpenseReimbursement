package frontcontroller;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * A class intended to control client server requests.  Handles incoming requests by sending them
 * to the dispatcher or redirecting the user to the login page.
 */
public class FrontController {
    Javalin app;
    Dispatcher dispatcher;

    public FrontController(Javalin app) {
        this.app = app;

        app.before("/employee/*", FrontController::checkAllRequests);
        app.before("/manager/*", FrontController::checkAllRequests);

        //app.before("/", FrontController::checkLoginStatus);

        dispatcher = new Dispatcher(app);
    }

    /**
     * A simple method designed to redirect the user to the login page if they try to
     * hardcode a path into the URL bar.  Uses the session attribute "feedback" to determine.
     * If the value is null, they will be redirected
     * @param context - Javalin context object
     */
    public static void checkAllRequests(Context context) {
        if(context.sessionAttribute("feedback") == null) {
            context.redirect("/");
        }
    }

}
