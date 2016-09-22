import java.io.Closeable;
import java.io.IOException;
import java.sql.*;

public class DatabaseProcessor implements Closeable {
    private DatabaseTables tables;

    private Connection connection;
    private Statement statement;

    public DatabaseProcessor(Connection connection) throws SQLException {
        this.connection = connection;
        statement = connection.createStatement();
        tables = new DatabaseTables(connection);
    }

    public void addBookToDatabase(Book book) throws SQLException {
        int genreId = tables.getGenre(book.getGenre());
        int authorId = tables.getAuthor(book.getAuthor());
        int publisherId = tables.getPublisher(book.getPublisher());

        if (genreId == -1 || authorId == -1 || publisherId == -1) return;   //if something wrong with DatabaseTables

        statement.executeUpdate("INSERT INTO book (name, page_count, isbn, genre_id, author_id, publish_year, publisher_id, image_number)" +
                "VALUES ('" + book.getName() + "', " + book.getPages() + ", '" + book.getIsbn() + "', " + genreId + ", " + authorId + ", " +
                book.getDate() + ", " + publisherId + ", " + book.getImgNumber() + ")");
    }

    @Override
    public void close() throws IOException {
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tables.close();
    }
}
