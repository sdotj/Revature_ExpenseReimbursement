import frontcontroller.FrontController;
import io.javalin.Javalin;

public class Main {

    /**
     * Entrance to Client-Server Reimbursement Application.  Sets up a Javalin server that will
     * handle client server requests.  Specifies html, css, and js files that are used to render
     * client view and configures page roots for specific pages.
     * @param args
     */
    public static void main(String[] args) {
        Javalin app = Javalin.create(
                config -> {config.addStaticFiles("/frontend/html");
                    config.addStaticFiles("/frontend/css");
                    config.addStaticFiles("/frontend/js");
                    config.addSinglePageRoot("/employee/dashboard", "/frontend/html/employeeDashboard.html");
                    config.addSinglePageRoot("/employee/newReimbursement", "/frontend/html/createReimbursement.html");
                    config.addSinglePageRoot("/manager/dashboard", "/frontend/html/managerDashboard.html");
                    config.addSinglePageRoot("/signup", "/frontend/html/signup.html");
                }
        ).start(9001);

        FrontController fc = new FrontController(app);
    }

}
