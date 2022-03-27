package commentapp.controllers;

import commentapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import commentapp.service.CommentAppService;

import java.sql.SQLException;

@Controller
public class CommentAppController {

    @Autowired
    private CommentAppService service;


    @RequestMapping("/test")
    public String home() {
        return "index";
    }

    @RequestMapping("/")
    public String check() {
        return "test";
    }

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
    public String processLoginRequest(@ModelAttribute User user, Model model) throws SQLException {

        if (service.validateUser(user.getEmail(), user.getPassword()))
            return "home";
        else {
            model.addAttribute("invalid", true);
            return "login";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register-user")
    public String processSignUpRequest(@ModelAttribute User user, Model model) throws SQLException {

        if (!service.userIsExists(user.getEmail()))
            service.insertUser(user.getEmail(), user.getPassword(), user.getSecretCode());
        else {
            model.addAttribute("existsAlready", true);
            return "signup";
        }

        return "login";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/reset-password")
    public String processForgotPasswordRequest(@ModelAttribute User user, Model model) throws SQLException {

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

}
