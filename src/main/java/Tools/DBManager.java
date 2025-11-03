package Tools;

import java.sql.*;

public class DBManager {
    private static final String DB_URL = "jdbc:sqlite:refunds.db";
    private static Connection connection;

    private static void initDB() throws SQLException {
        String initialization = """
                CREATE TABLE IF NOT EXISTS refund_cases (
                    case_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    customer_email TEXT NOT NULL,
                    order_id TEXT NOT NULL,
                    reason TEXT,
                    status TEXT DEFAULT 'Pending',
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    )
                """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(initialization);
        }
    }
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
            initDB();
        }
        return connection;
    }
    public static Connection closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        return connection;
    }
}
