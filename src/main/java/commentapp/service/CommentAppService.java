package commentapp.service;

import commentapp.config.DbConfig;
import commentapp.model.Comment;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentAppService {


    private static Connection connection;

    static {
        try {
            connection = DbConfig.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean validateUser(String email, String password) throws SQLException {

        String sql = "SELECT * FROM commentapp.user_table";
        Statement stmt = connection.createStatement();

        ResultSet results = stmt.executeQuery(sql);

        while (results.next()) {
            if (email.equals(results.getString(1)) && password.equals(results.getString(2)))
                return true;
        }
        return false;
    }

    public boolean userIsExists(String email) throws SQLException {

        String sql = "SELECT * FROM commentapp.user_table";
        Statement stmt = connection.createStatement();

        ResultSet results = stmt.executeQuery(sql);

        while (results.next()) {
            if (email.equals(results.getString(1)))
                return true;
        }
        return false;
    }

    public void insertUser(String email, String password, String secret) throws SQLException {

        try {
            String sql = "INSERT INTO commentapp.user_table (email_id, password, secret) VALUES (?,?,?)";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, secret);

            stmt.executeUpdate();
            System.out.println("User Data Inserted in DB...");
        } catch (Exception e) {
            System.out.println("Exception Occurred while Inserting Data to DB " + e);
        }

    }

    public String resetPassword(String email, String secret) throws SQLException {

        String sql = "SELECT * FROM commentapp.user_table;";
        Statement stmt = connection.createStatement();

        ResultSet results = stmt.executeQuery(sql);

        String password = null;

        while (results.next()) {

            if (email.equals(results.getString(1)) && secret.equals(results.getString(3)))
                return results.getString(2);
        }
        return password;
    }

    public boolean isCommentExists(String comment) {

        boolean flag = true;

        try {
            String sql = "SELECT * FROM commentapp.comments_table WHERE comments = ?";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, comment.trim());

            ResultSet data = stmt.executeQuery();

            while (data.next())
                flag = false;

        } catch (Exception e) {
            System.out.println("Exception Occurred Fetching Comment Data from DB " + e);
        }

        return flag;
    }

    public void saveCommentToDb(String comments, String email) throws SQLException {

        try {
            String sql = "INSERT INTO commentapp.comments_table (comments, email_id) VALUES (?,?)";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, comments.trim());
            stmt.setString(2, email.trim());

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Exception Occurred while Inserting Data to DB " + e);
        }
    }

    public List<Comment> getCommentList() throws SQLException {

        List<Comment> commentList = new ArrayList<>();

        String sql = "SELECT * FROM commentapp.comments_table";

        Statement stmt = connection.createStatement();

        ResultSet results = stmt.executeQuery(sql);

        commentList = getCommentList(commentList, results);

        return commentList;
    }


    public List<Comment> filterCommentsByEmailId(String email) throws SQLException {

        List<Comment> filteredCommentList = new ArrayList<>();

        String sql = "SELECT * FROM commentapp.comments_table WHERE email_id = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, email.trim());

        ResultSet results = stmt.executeQuery();

        filteredCommentList = getCommentList(filteredCommentList, results);

        return filteredCommentList;
    }

    private List<Comment> getCommentList(List<Comment> commentList, ResultSet results) throws SQLException {
        while (results.next()) {

            Comment comment = new Comment();
            comment.setComments(results.getString(1));
            comment.setEmail(results.getString(2));

            commentList.add(comment);

        }
        return commentList;
    }
}
