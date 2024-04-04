package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBInsertComic extends DBConnection{
    public DBInsertComic() throws SQLException {

    }

    public void insertComic(String name, String description, int rating, String category, int recommended, int userId) throws SQLException {
        PreparedStatement insertStatement =
                connection.prepareStatement(
                        STR."INSERT INTO Comics (Name, Description, Rating, Category, Recommended, Userid) VALUES ('\{name}','\{description}','\{rating}','\{category}','\{recommended}', '\{userId}');");
        insertStatement.executeUpdate();
        connection.close();
    }
}
