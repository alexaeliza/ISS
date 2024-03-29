package data;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import models.Book;
import models.Borrowing;
import models.Edition;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Repository {
    private final String databaseURL;
    private final String databaseUsername;
    private final String databasePassword;

    public Repository(String databaseURL, String databaseUsername, String databasePassword) {
        this.databaseURL = databaseURL;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
    }

    public void addUser(User user) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO public.\"Users\"(name, password, address, cnp) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getAddress());
            preparedStatement.setString(4, user.getCnp());
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
    }

    public User getUserByCnp(String cnp) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Users\" WHERE cnp = ?");
            preparedStatement.setString(1, cnp);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                return null;
            User user = new User(cnp, resultSet.getString("name"), resultSet.getString("password"), resultSet.getString("address"));
            user.setId(resultSet.getInt("id"));
            return user;
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return null;
    }

    public Book getBookById(int id) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Books\" WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                return null;
            Book book = new Book(resultSet.getString("title"), resultSet.getString("author"));
            book.setId(resultSet.getInt("id"));
            return book;
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return null;
    }

    public Edition getEditionById(int editionId) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Editions\" WHERE id = ?");
            preparedStatement.setInt(1, editionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int bookId = resultSet.getInt("bookId");
                Edition edition = new Edition(getBookById(bookId));
                edition.setId(resultSet.getInt("id"));
                return edition;
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return null;
    }

    public List<Edition> getEditions() {
        List<Edition> editions = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Editions\" WHERE rent IS NULL");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int bookId = resultSet.getInt("bookId");
                Edition edition = new Edition(getBookById(bookId));
                edition.setId(resultSet.getInt("id"));
                editions.add(edition);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return editions;
    }

    public void borrowEditionForUser(Edition edition, User user) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO public.\"Borrowings\"(\"userId\", \"editionId\") VALUES (?, ?)");
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, edition.getId());
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement("UPDATE public.\"Editions\" SET rent = 1 WHERE id = ?");
            preparedStatement.setInt(1, edition.getId());
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
    }

    public List<Borrowing> getBorrowingsOfUser(User user) {
        List<Borrowing> borrowings = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.\"Borrowings\" WHERE \"userId\" = ?");
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                int editionId = resultSet.getInt("editionId");
                Borrowing borrowing = new Borrowing(user, getEditionById(editionId));
                borrowings.add(borrowing);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return borrowings;
    }

    public void returnEdition(Edition edition, User user) {
        try (Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM public.\"Borrowings\" WHERE \"userId\" = ? AND \"editionId\" = ?");
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, edition.getId());
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement("UPDATE public.\"Editions\" SET rent = null WHERE id = ?");
            preparedStatement.setInt(1, edition.getId());
            preparedStatement.execute();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
    }
}
