package phrase.addons.sql;

import phrase.addons.Addons;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    public static void registerDriver() {
        try {
            Class.forName("org.postgresql.Driver");
            Addons.getInstance().getLogger().info("Драйвер JDBC зарегистрирован");
        } catch (ClassNotFoundException e) {
            Addons.getInstance().getLogger().info("Не удалось зарегистрировать драйвер JDBC " + e);
        }
    }

    public static Connection getServerConnection() throws SQLException {
       return DriverManager.getConnection(Addons.URL, Addons.USERNAME, Addons.PASSWORD);
    }
}

