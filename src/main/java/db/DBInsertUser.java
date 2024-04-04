package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBInsertUser extends DBConnection{
    public DBInsertUser() throws SQLException {

    }

    public void insertUser(String username, String password) throws SQLException {
        PreparedStatement insertStatement =
                connection.prepareStatement(
                        STR."INSERT INTO Users (Username, Password) VALUES ('\{username}','\{password}');");
        insertStatement.executeUpdate();
        connection.close();
    }
}
