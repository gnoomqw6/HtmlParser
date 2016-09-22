import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DatabaseTables implements Closeable {
    private Map<String, Integer> authors = new HashMap<>();
    private Map<String, Integer> genres = new HashMap<>();
    private Map<String, Integer> publishers = new HashMap<>();

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public DatabaseTables(Connection connection) throws SQLException {
        this.connection = connection;
        statement = connection.createStatement();
        //find authors
        resultSet = statement.executeQuery("SELECT id, fio FROM author");
        while (resultSet.next()) {
            authors.put(resultSet.getString("fio"), resultSet.getInt("id"));
        }

        //find genres
        resultSet = statement.executeQuery("SELECT * FROM genre");
        while (resultSet.next()) {
            genres.put(resultSet.getString("name"), resultSet.getInt("id"));
        }

        resultSet = statement.executeQuery("SELECT * FROM publisher");
        while (resultSet.next()) {
            publishers.put(resultSet.getString("name"), resultSet.getInt("id"));
        }
    }

    public int getAuthor(String name) throws SQLException {
        int id = -1;
        if (authors.containsKey(name)) id = authors.get(name);
        else {
            statement.executeUpdate("INSERT INTO author (fio) VALUE ('" + name + "')");
            resultSet = statement.executeQuery("SELECT id FROM author WHERE fio = '" + name + "'");
            if (resultSet.next()) {
                id = resultSet.getInt("id");
                authors.put(name, id);
            }
        }
        return id;
    }

    public int getGenre(String genre) throws SQLException {
        int id = -1;
        if (genres.containsKey(genre)) id = genres.get(genre);
        else {
            statement.executeUpdate("INSERT INTO genre (name) VALUE ('" + genre + "')");
            resultSet = statement.executeQuery("SELECT id FROM genre WHERE name = '" + genre + "'");
            if (resultSet.next()) {
                id = resultSet.getInt("id");
                genres.put(genre, id);
            }
        }
        return id;
    }

    public int getPublisher(String publisher) throws SQLException {
        int id = -1;
        if (publishers.containsKey(publisher)) id = publishers.get(publisher);
        else {
            statement.executeUpdate("INSERT INTO publisher (name) VALUE ('" + publisher + "')");
            resultSet = statement.executeQuery("SELECT id FROM publisher WHERE name = '" + publisher + "'");
            if (resultSet.next()) {
                id = resultSet.getInt("id");
                publishers.put(publisher, id);
            }
        }
        return id;
    }

    @Override
    public void close() throws IOException {
        try {
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
