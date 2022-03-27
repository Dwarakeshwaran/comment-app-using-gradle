package commentapp.service;

import commentapp.config.DbConfig;
import org.springframework.stereotype.Service;

import java.sql.*;

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
}
