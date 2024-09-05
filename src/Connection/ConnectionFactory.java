package Connection;

import java.sql.Connection;
import java.sql.DriverManager;


public class ConnectionFactory {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Moh@9430566126";
    private static final String URL = "jdbc:mysql://localhost:3306/project";

    public static Connection getConnection() {
        Connection con = null;
        try {
            // Class.forName("com.mysql.jdbc.Driver");
  
             con = DriverManager.getConnection(URL, USERNAME,PASSWORD);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;

    }
}