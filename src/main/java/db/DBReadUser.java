package db;

import schema.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        int userid = resultSet.getInt("Userid");
        connection.close();
        return new User(userid, username);
    }
}
