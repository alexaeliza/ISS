package data;

import models.Book;
import models.Edition;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            return new User(cnp, resultSet.getString("name"), resultSet.getString("password"), resultSet.getString("address"));
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
            return new Book(resultSet.getString("title"), resultSet.getString("author"));
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
            while (resultSet.next())
            {
                int bookId = resultSet.getInt("bookId");
                Edition edition = new Edition(getBookById(bookId));
                editions.add(edition);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.exit(1);
        }
        return editions;
    }
}
