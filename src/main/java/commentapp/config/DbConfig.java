package commentapp.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfig {

    public static Connection getConnection() throws SQLException {

        Connection connection = null;

        String userName = "root";
        String password = "root";
        String url = "jdbc:mysql://localhost:3306/commentapp";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, userName, password);
            System.out.println("DB Connected");
            return connection;
        } catch (Exception e) {
            System.out.println("Exception Occurred while Connecting to DB " + e);
            return null;
        }


    }
}
