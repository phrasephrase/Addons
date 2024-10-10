package phrase.addons.sql;

import phrase.addons.Addons;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static Connection connection;

    public static void registerDriver() {
        try {
            Class.forName("org.postgresql.Driver");
            Addons.getInstance().getLogger().info("Драйвер JDBC зарегистрирован");
        } catch (ClassNotFoundException e) {
            Addons.getInstance().getLogger().info("Не удалось зарегистрировать драйвер JDBC " + e);
        }
    }

    public static Connection getServerConnection(String url, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
        return connection;
    }
}

