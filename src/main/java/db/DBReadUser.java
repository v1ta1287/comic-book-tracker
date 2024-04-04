package db;

import schema.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Establishes connection to database and performs an SQL SELECT query for a user
 * matching a specific username and password
 */

public class DBReadUser extends DBConnection {
    public DBReadUser() throws SQLException {

    }

    public User selectUser(String username, String password) throws SQLException {
        PreparedStatement readStatement =
                connection.prepareStatement(
                        STR."SELECT * FROM Users WHERE Username = '\{username}' AND Password = '\{password}';");
        ResultSet resultSet = readStatement.executeQuery();
        if (!resultSet.next()) {
            return null;
        }
        // if data is retrieved successfully, create and return a User object
        int userid = resultSet.getInt("Userid");
        connection.close();
        return new User(userid, username);
    }
}
