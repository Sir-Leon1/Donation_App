package org.groupwork.donation.Models;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Recipient {
    //The method below adds a recipient to the DB on registration. (TDB) To DataBase
    public static void registerRecipient(String email, String username, String password, String location, String usertype, String phoneno, String orgWeb){

        String inAuth = "INSERT INTO Donation_App_UD (Email, Username, Password, Location, UserType, PhoneNo, Org_Website) VALUES (?,?,?,?,?,?,?)";
        String inRecipient = "INSERT INTO Recipient_UD (email, username, location, usertype, phone, status, requestType) VALUES (?,?,?,?,?,?,?)";

        try (Connection connection = Model.getInstance().getDatabaseDriver().connect();
             PreparedStatement preparedStatement = connection.prepareStatement(inAuth)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, location);
            preparedStatement.setString(5, usertype);
            preparedStatement.setString(6, phoneno);
            preparedStatement.setString(7, orgWeb);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Success");
            }
        } catch (SQLException e){
            System.out.println("Exception to Auth DB caught");
            e.printStackTrace();
        }

        try (Connection connection = Model.getInstance().getDatabaseDriver().connect();
             PreparedStatement prepStmtRUD = connection.prepareStatement(inRecipient)){
            prepStmtRUD.setString(1, email);
            prepStmtRUD.setString(2, username);
            prepStmtRUD.setString(3, location);
            prepStmtRUD.setString(4, usertype);
            prepStmtRUD.setString(5, phoneno);
            prepStmtRUD.setString(6, "-");
            prepStmtRUD.setString(7, "-");

            int rowsAffected2 = prepStmtRUD.executeUpdate();
            if (rowsAffected2 > 0){
                Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
                errorAlert.setContentText(email+" Registration Successful. Kindly login");
                errorAlert.showAndWait();
                System.out.println("Added to the recipient DB");
            }
        }
        catch (SQLException e){
            System.out.println("Exception to recipient DB Caught");
            e.printStackTrace();
        }

    }

    public static void addRequestTDB(String request) {
        String sql = "UPDATE Recipient_UD SET requestType = ?, status = 'Active' WHERE email = ?";

        String email = Model.getInstance().getUser().getEmail();

        System.out.println(email);

        try (Connection connection = Model.getInstance().getDatabaseDriver().connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, request);
            preparedStatement.setString(2, email);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setContentText("Request was sent successfully.");
                errorAlert.showAndWait();
                System. out.println("Request was sent successfully.");
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("No request updated. Make sure the email exists");
                errorAlert.showAndWait();
                System.out.println("No request updated. Make suze the email exists");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> recipientsRequests() {
        String query = "SELECT * FROM Recipient_UD WHERE requestType != ? AND  status = 'Active'";
        Map<String, String> recipients = new LinkedHashMap<>();

        try (Connection connection = Model.getInstance().getDatabaseDriver().connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "-");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()){
                    recipients.put(resultSet.getString("username"), resultSet.getString("requestType"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving recipients" + e.getMessage());
            e.printStackTrace();
        }

        return recipients;
    }

    // Method to update status in combined table when recipient clicks complete
    public static void markCompleteDonation(String recipientEmail) {
        String recipient = Model.getInstance().getUser().getUsername();
        String updateStatusQuery = "UPDATE `Assigned_Donors&Recipients` SET Status = ? WHERE RecipientEmail = ?";
        String DonorUsernameQuery = "SELECT DonorUsername FROM `Assigned_Donors&Recipients` WHERE RecipientEmail = ?";

        try (Connection connection = Model.getInstance().getDatabaseDriver().connect();
             PreparedStatement updateStatusStatement = connection.prepareStatement(updateStatusQuery);
             PreparedStatement donorUsernameStmnt = connection.prepareStatement(DonorUsernameQuery)) {

            updateStatusStatement.setString(1, "Complete");
            updateStatusStatement.setString(2, recipientEmail);
            updateStatusStatement.executeUpdate();

            String donorUsername = "";
            donorUsernameStmnt.setString(1, recipientEmail);
            ResultSet resultSet = donorUsernameStmnt.executeQuery();
            if (resultSet.next()){
                donorUsername = resultSet.getString("DonorUsername");
            }

            Admin.updateDonorRecipientStatus(donorUsername, recipient);

            Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
            errorAlert.setContentText("Donation is complete.");
            errorAlert.showAndWait();
            System.out.println("Added to the recipient DB");
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //A list of requests made by a specific recipient and their status
    public static List<Map<String, String>> requestsMade(String recipientEmail) {
        String query = "SELECT * FROM `Assigned_Donors&Recipients` WHERE RecipientEmail = ?";
        List<Map<String, String>>  requests = new ArrayList<>();

        try (Connection connection = Model.getInstance().getDatabaseDriver().connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, recipientEmail);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Map<String, String> donor = new HashMap<>();
                while (resultSet.next()){
                    donor.put("DonorUsername", resultSet.getString("DonorUsername"));
                    donor.put("Status", resultSet.getString("Status"));
                    donor.put("Request", resultSet.getString("Request"));
                    requests.add(donor);
//               requests.put(resultSet.getString("status"), resultSet.getString("requestType"));
                }
                System.out.println(recipientEmail);
                System.out.println(requests);
                return requests;
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving requests" + e.getMessage());
            e.printStackTrace();
        }

        return requests;
    }

}
