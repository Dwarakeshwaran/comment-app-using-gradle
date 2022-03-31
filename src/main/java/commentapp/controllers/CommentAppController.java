package commentapp.controllers;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import commentapp.model.Comment;
import commentapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import commentapp.service.CommentAppService;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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



    @RequestMapping("/submit-comment")
    public String commentPage(String email, Model model) throws SQLException {
        List<Comment> commentList = service.getCommentList();
        model.addAttribute("commentList", commentList);
        model.addAttribute("email", email);
        model.addAttribute("filterCheck", false);
        return "comment-page";
    }

    @RequestMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/process-login")
    public String processLoginRequest(User user, Model model) throws SQLException {

        if (service.validateUser(user.getEmail(), user.getPassword())){

            return commentPage(user.getEmail(), model);
        }

        else {
            model.addAttribute("invalid", true);
            return "login";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register-user")
    public String processSignUpRequest(User user, Model model) throws SQLException {

        if (!service.userIsExists(user.getEmail()))
            service.insertUser(user.getEmail(), user.getPassword(), user.getSecretCode());
        else {
            model.addAttribute("existsAlready", true);
            return "signup";
        }

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


    @RequestMapping(method = RequestMethod.GET, value = "/submit-comment")
    public String saveComment(Comment comment, Model model) throws SQLException {

        service.saveCommentToDb(comment.getComments(), comment.getEmail());
        model.addAttribute("email", comment.getEmail());

        List<Comment> commentList = service.getCommentList();
        model.addAttribute("commentList", commentList);
        model.addAttribute("filterCheck", false);

        return "comment-page";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/filter-comments")
    public String getComments(@RequestParam("email") String email, Model model) throws SQLException {

        model.addAttribute("email", email);

        System.out.println(email);

        List<Comment> filteredCommentList = service.filterCommentsByEmailId(email);
        model.addAttribute("filteredCommentList", filteredCommentList);
        model.addAttribute("filterCheck", true);

        return "comment-page";
    }

}
