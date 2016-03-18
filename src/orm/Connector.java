package orm;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Bartosz on 18.03.2016.
 */
public class Connector {

    private static Connection connection = null;

    static Connection getConnection() {
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

    public static Connection getInstance() throws URISyntaxException, SQLException {
        if (connection == null) {
            connection = getConnection();
            return connection;
        } else
            return connection;
    }
}
