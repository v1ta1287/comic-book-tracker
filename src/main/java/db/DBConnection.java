package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;


/**
 * This class establishes the database connection, which can either be local or cloud
 * Database configurations are defined by db.config
 * Other DB classes will inherit this connection
 */
public class DBConnection {
    protected Connection connection;

    public DBConnection () throws SQLException {
        this.connection = connectDB();
    }

    protected Connection connectDB() throws SQLException {
        // Extract db.config configurations into Properties object
        Properties configuration = new Properties();

        // Ensure that the db.config file is in the db package
        String filename = "src/main/java/db/db.config";

        // Attempt to load db.config information into the Properties object
        try (FileInputStream fis = new FileInputStream(filename)) {
            configuration.load(fis);
        } catch (IOException ex) {
            throw new RuntimeException();
        }

        // determine if the db.config specifies LOCAL or CLOUD
        // perform the appropriate DB connection for LOCAL or CLOUD
        String dbType = configuration.getProperty("db.type");
        if (Objects.equals(dbType, "LOCAL")) {
            String username = configuration.getProperty("db.localUsername");
            String password = configuration.getProperty("db.localPassword");
            String connectionString = configuration.getProperty("db.localConnectionString");
            return DriverManager.getConnection(connectionString,username,password);
        } else if (Objects.equals(dbType, "CLOUD")) {
            String username = configuration.getProperty("db.azureUsername");
            String password = configuration.getProperty("db.azurePassword");
            String connectionString = configuration.getProperty("db.azureConnectionString");
            String database = configuration.getProperty("db.azureDatabase");
            @SuppressWarnings("StringTemplateMigration") String connection =
                            connectionString + ";" +
                            "database=" + database + ";" +
                            "user=" + username + ";" +
                            "password=" + password + ";" +
                            "encrypt=true;" +
                            "trustServerCertificate=false;" +
                            "hostNameInCertificate=*.database.windows.net;" +
                            "loginTimeout=30;";
            return DriverManager.getConnection(connection);
        } else {
            throw new RuntimeException("Invalid config properties");
        }
    }

}
