package fr.univamu.iut.book;

import javax.xml.transform.Result;
import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

public class BookRepositoryMariadb implements BookRepositoryInterface, Closeable {

    protected Connection dbConnection;

    public BookRepositoryMariadb(String infoConnection, String user, String pwd) throws java.sql.SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection(infoConnection, user, pwd);
    }

    @Override
    public void close() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

    @Override
    public Book getBook(String reference) {
        Book selectedBook = null;

        String query = "SELECT * FROM Book WHERE reference=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, reference);

            ResultSet result = ps.executeQuery();

            if (result.next()) {
                String title = result.getString("title");
                String author = result.getString("authors");
                char status = result.getString("status").charAt(0);

                // Création et initialisation de l'objet Book
                selectedBook = new Book(reference, title, author);
                selectedBook.setStatus(status);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedBook;
    }

    @Override
    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> listBooks;

        String query = "SELECT * FROM Book";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ResultSet result = ps.executeQuery();

            listBooks = new ArrayList<>();

            while (result.next()) {
                String reference = result.getString("reference");
                String title = result.getString("title");
                String author = result.getString("authors");
                char status = result.getString("status").charAt(0);

                Book currentBook = new Book(reference, title, author);
                currentBook.setStatus(status);

                listBooks.add(currentBook);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listBooks;
    }

    @Override
    public boolean updateBook(String reference, String title, String authors, char status) {
        String query = "UPDATE Book SET title=?, authors=?, status=? WHERE reference=?";
        int nbRowModified = 0;

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, title);
            ps.setString(2, authors);
            ps.setString(3, String.valueOf(status));
            ps.setString(4, reference);

            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (nbRowModified != 0);
    }
}
