package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
