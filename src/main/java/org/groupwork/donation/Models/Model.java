package org.groupwork.donation.Models;

import javafx.scene.control.Alert;
import org.groupwork.donation.Controllers.Auth.AuthenticationController;
import org.groupwork.donation.Views.ViewFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {
    private static Model model;
    private static DatabaseDriver databaseDriver;
    private final ViewFactory viewFactory;
    // user
    private User user;
    private String isUserLoggedIn;


    public Model() {
        this.viewFactory = new ViewFactory();
        databaseDriver = new DatabaseDriver();

        this.isUserLoggedIn = null;

        this.user = new User("", "", "", "", "", "");
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public DatabaseDriver getDatabaseDriver() {
        return databaseDriver;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String isUserLoggedIn() {
        return isUserLoggedIn;
    }

    public void setUserLoggedIn(String userLoggedIn) {
        isUserLoggedIn = userLoggedIn;
    }

    public void validateUserCredentials(String email, String password) throws SQLException {
        String query = "SELECT * FROM Donation_App_UD WHERE Email = ? AND Password = ?";
        AuthenticationController auth_controller = new AuthenticationController();
        try (Connection connection = databaseDriver.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String email_address = resultSet.getString("Email");
                    String username = resultSet.getString("Username");
                    String location = resultSet.getString("Location");
                    String userType = resultSet.getString("UserType");
                    String phoneNo = resultSet.getString("PhoneNo");
                    String website = resultSet.getString("Org_Website");
                    User user = new User(email_address, username, location, userType, phoneNo, website);
                    setUser(user);
                    setUserLoggedIn(userType);
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setContentText("Wrong Credentials. Try Again.");
                    errorAlert.showAndWait();
                }
            }
        } catch (SQLException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Connection Failed");
            errorAlert.setContentText("Contact service provider. Error occurred on application");
            errorAlert.showAndWait();
            System.out.println("Error occurred in validateUserCredentials: " + e);
            throw e; // Rethrow the exception to propagate it to the caller
        }
    }

    public void registerNewUser(String email, String username, String password, String location, String userType, String phoneNo, String website) throws SQLException {
        String insertSQL = "INSERT INTO Donation_App_UD(Email, Username, Password, Location, UserType, PhoneNo, Org_Website) VALUES (?,?,?,?,?,?,?)";

        try (Connection connection = databaseDriver.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, location);
            preparedStatement.setString(5, userType);
            preparedStatement.setString(6, phoneNo);
            preparedStatement.setString(7, website);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                Model.getInstance().getViewFactory().showLoginWindow();
                System.out.println("User registered successfully");
            }
        } catch (SQLException e) {
            System.out.println("Error registering user" + e);
            throw e; // Rethrow the exception to propagate it to the caller
        }
    }


    public static Map<String, String> getUserDetails(String email) {
        String query = "SELECT * FROM Donation_App_UD WHERE Email = ?";
        Map<String, String> userDetails = new HashMap<>();

        try (Connection connection = databaseDriver.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    userDetails.put("Email", resultSet.getString("Email"));
                    userDetails.put("Username", resultSet.getString("Username"));
                    userDetails.put("Location", resultSet.getString("Location"));
                    userDetails.put("UserType", resultSet.getString("UserType"));
                    userDetails.put("PhoneNo", resultSet.getString("PhoneNo"));
                    userDetails.put("Org_Website", resultSet.getString("Org_Website"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user details");
            e.printStackTrace();
        }

        return userDetails;
    }

    public static List<Map<String, String>> getUsersByUserType(String userType) {
        String query = "SELECT * FROM Donation_App_UD WHERE UserType = ?";
        List<Map<String, String>> users = new ArrayList<>();

        try (Connection connection = databaseDriver.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userType);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                ResponseArray(users, resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving users by userType: " + e.getMessage());
            e.printStackTrace();
        }

        return users;
    }

    public static List<Map<String, String>> getInactiveVerifiedRecipients() {
        String query = "SELECT * FROM Donation_App_UD WHERE UserType = 'Recipient' AND Verified = false";
        List<Map<String, String>> recipients = new ArrayList<>();

        try (Connection connection = databaseDriver.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            ResponseArray(recipients, resultSet);
        } catch (SQLException e) {
            System.out.println("Error retrieving inactive verified recipients: " + e.getMessage());
            e.printStackTrace();
        }

        return recipients;
    }

    public void LogOutUser (){
        this.user = new User("", "", "", "", "", "");
        getViewFactory().showLoginWindow();
    }

    private static void ResponseArray(List<Map<String, String>> member, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Map<String, String> users = new HashMap<>();
            users.put("Email", resultSet.getString("Email"));
            users.put("Username", resultSet.getString("Username"));
            users.put("Location", resultSet.getString("Location"));
            users.put("PhoneNo", resultSet.getString("PhoneNo"));
            users.put("UserType", resultSet.getString("UserType"));
            users.put("Org_Website", resultSet.getString("Org_Website"));
            member.add(users);
        }
    }

}
