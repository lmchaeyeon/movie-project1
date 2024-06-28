package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB1 {
    private static final String URL = "jdbc:mysql://localhost:3306/java_b";
    private static final String USER = "root"; // MySQL 사용자명
    private static final String PASSWORD = "202345038"; // MySQL 비밀번호

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

