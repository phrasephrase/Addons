package phrase.addons.sql;

import phrase.addons.Plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    public static void registerDriver() {
        try {
            Class.forName("org.postgresql.Driver");
            Plugin.getInstance().getLogger().info("Драйвер JDBC зарегистрирован");
        } catch (ClassNotFoundException e) {
            Plugin.getInstance().getLogger().info("Не удалось зарегистрировать драйвер JDBC " + e);
        }
    }

    public static Connection getServerConnection() throws SQLException {
       return DriverManager.getConnection(Plugin.URL, Plugin.USERNAME, Plugin.PASSWORD);
    }
}

