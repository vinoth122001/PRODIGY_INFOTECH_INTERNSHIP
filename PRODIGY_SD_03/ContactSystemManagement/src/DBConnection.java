import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {             //This Class helps to Secure the Login Credentials while Connecting with DataBase
    
    private static final String url = "jdbc:mysql://localhost:3306/internworks";
    private static final String userName = "root";
    private static final String password = "password";

    
    public static Connection getConnection() throws SQLException{
        
        Connection con=null;
        con = DriverManager.getConnection(url,userName ,password);

        return con;
    }

//Method to Close Connections
    public static void closeConnection (Connection con) { 
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println("While Closing Connection" + e);
        }
    }

    
}
