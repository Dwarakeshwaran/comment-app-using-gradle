package commentapp.controllers;

import commentapp.model.User;
import commentapp.service.CommentAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.sql.SQLException;

@Controller
public class LoginPageController {

    @Autowired
    private CommentAppService service;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/signup")
    public String signup() {
        return "signup";
    }

    @RequestMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/process-login")
    public void processLoginRequest(User user, HttpServletRequest request, HttpServletResponse response, Model model) throws SQLException, IOException {

        response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
        response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
        response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
        response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility

        HttpSession session = request.getSession();

        if (service.validateUser(user.getEmail(), user.getPassword())) {

            session.setAttribute("email", user.getEmail());
            response.sendRedirect("comment-page");
        } else {
            session.setAttribute("invalid", "true");
            response.sendRedirect("login");

        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register-user")
    public String processSignUpRequest(User user, Model model, HttpServletResponse response) throws SQLException, IOException {

        if (!service.userIsExists(user.getEmail()) && !user.getEmail().isEmpty() && !user.getPassword().isEmpty() && !user.getSecretCode().isEmpty())
            service.insertUser(user.getEmail(), user.getPassword(), user.getSecretCode());
        else {
            if (user.getEmail().isEmpty() || user.getPassword().isEmpty() || user.getSecretCode().isEmpty())
                model.addAttribute("invalid", true);
            else if(service.userIsExists(user.getEmail()) == false)
                model.addAttribute("existsAlready", true);

            return "signup";
        }

        response.sendRedirect("login");

        return "login";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/reset-password")
    public String processForgotPasswordRequest(User user, Model model) throws SQLException {

        String password = service.resetPassword(user.getEmail(), user.getSecretCode());

        System.out.println("Password: " + password);

        if (password != null) {
            model.addAttribute("passwordAvailability", true);
            model.addAttribute("password", password);
            return "forgot-password";
        } else {
            model.addAttribute("passwordAvailability", false);
            return "forgot-password";
        }

    }

    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
        response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
        response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
        response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
        response.sendRedirect("/");

    }
}
