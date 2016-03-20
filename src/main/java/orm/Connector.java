package orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Bartosz on 18.03.2016.
 */
class Connector {

    private static Connection connection = null;

    private static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            String connectionString = "jdbc:postgresql://localhost:5432/transport_claims";
            String connectionUser = "traclaim";
            String connectionPassword = "traclaim";
            return DriverManager.getConnection(connectionString, connectionUser, connectionPassword);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    static Connection getInstance() throws SQLException {
        if (connection == null) {
            connection = getConnection();
            return connection;
        } else
            return connection;
    }
}
