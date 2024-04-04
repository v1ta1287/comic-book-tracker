package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Establishes connection to database and performs an SQL INSERT query for a character
 */
public class DBInsertCharacter extends DBConnection{
    public DBInsertCharacter() throws SQLException {

    }

    public void insertCharacter(String name, String description, int age, String status, int comicId) throws SQLException {
        PreparedStatement insertStatement =
                connection.prepareStatement(
                        STR."INSERT INTO Characters (Name, Description, Age, Status, Comicid) VALUES ('\{name}','\{description}','\{age}','\{status}','\{comicId}');");
        insertStatement.executeUpdate();
        connection.close();
    }
}
