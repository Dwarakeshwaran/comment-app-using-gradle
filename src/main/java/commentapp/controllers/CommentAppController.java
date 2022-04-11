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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CommentAppController {

    @Autowired
    private CommentAppService service;


    @RequestMapping(method = RequestMethod.GET, value = "/comment-page")
    public String commentPage(HttpServletRequest request, HttpServletResponse response, Model model) throws SQLException, IOException {

        response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
        response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
        response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
        response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        if (email == null) {
            response.sendRedirect("login");
            return "login";
        } else {
            List<Comment> commentList = service.getCommentList();
            model.addAttribute("commentList", commentList);
            model.addAttribute("email", email);
            model.addAttribute("filterCheck", false);
            return "comment-page";
        }

    }


    @RequestMapping(method = RequestMethod.GET, value = "/submit-comment")
    public String saveComment(@RequestParam("comments") String comment, HttpServletRequest request, HttpServletResponse response, Model model)
            throws SQLException, IOException {

        response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
        response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
        response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
        response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        System.out.println(email);
        System.out.println(comment);

        if (email == null) {
            response.sendRedirect("login");
            return "login";
        } else {
            if (!comment.isEmpty() && service.isCommentExists(comment)) {
                service.saveCommentToDb(comment, email);
                model.addAttribute("email", email);

                List<Comment> commentList = service.getCommentList();
                model.addAttribute("commentList", commentList);
                model.addAttribute("filterCheck", false);

                return "comment-page";

            } else {
                if(comment.isEmpty())
                    model.addAttribute("emptyComment", true);
                else if(service.isCommentExists(comment) == false)
                    model.addAttribute("duplicateComment", true);

                return "comment-page";
            }
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/filter-comments")
    public String getComments(HttpServletRequest request, HttpServletResponse response, Model model) throws SQLException, IOException {

        response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
        response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
        response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
        response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        if (email == null) {
            response.sendRedirect("login");
            return "login";
        } else {
            model.addAttribute("email", email);

            System.out.println(email);

            List<Comment> filteredCommentList = service.filterCommentsByEmailId(email);
            model.addAttribute("filteredCommentList", filteredCommentList);
            model.addAttribute("filterCheck", true);

            return "comment-page";
        }

    }

}
