import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String bookNumber;

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?autoReconnect=true&useSSL=false", "root", "root");
                DatabaseProcessor processor = new DatabaseProcessor(connection)){
            for (int i = 28601; i < 50000; i += 100) {
                bookNumber = String.valueOf(i);
                Book book = Parser.process(bookNumber);
                processor.addBookToDatabase(book);
                System.out.println("book number " + i + " was added");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
