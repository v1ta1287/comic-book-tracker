package db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

public class DBConnection {
    protected Connection connection;

    public DBConnection () throws SQLException {
        this.connection = connectDB();
    }


    protected Connection connectDB() throws SQLException {
        Properties prop = new Properties();
        String filename = "src/main/java/db/db.config";

        try (FileInputStream fis = new FileInputStream(filename)) {
            prop.load(fis);
        } catch (IOException ex) {
            throw new RuntimeException();
        }
        String dbType = prop.getProperty("db.type");

        if (Objects.equals(dbType, "LOCAL")) {
            String username = prop.getProperty("db.localUsername");
            String password = prop.getProperty("db.localPassword");
            String connectionString = prop.getProperty("db.localConnectionString");
            return DriverManager.getConnection(connectionString,username,password);
        } else if (Objects.equals(dbType, "CLOUD")) {
            String username = prop.getProperty("db.azureUsername");
            String password = prop.getProperty("db.azurePassword");
            String connectionString = prop.getProperty("db.azureConnectionString");
            String database = prop.getProperty("db.azureDatabase");
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
