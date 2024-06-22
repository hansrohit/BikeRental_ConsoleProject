import java.sql.*;

public class Jdbc {
    private static final String url = "jdbc:mysql://localhost:3306/bikerental";
    private static final String userName = "root";
    private static final String passWord = "";

    public static Connection getConnections() throws SQLException {
        return DriverManager.getConnection(url, userName, passWord);
    }
}
