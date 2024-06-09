package org.groupwork.donation.Models;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static org.groupwork.donation.Models.Donor.donorsArray;

public class Admin {

    public static List<Map<String, String>> recipientsRequests() {
        String query = "SELECT * FROM recipient_ud WHERE requestType != ? AND status = 'Active'";
        List<Map<String, String>> recipients = new ArrayList<>();

        try (Connection connection = Model.getInstance().getDatabaseDriver().connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "-");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()){
                    Map<String, String> users = new HashMap<>();
                    users.put("username", resultSet.getString("username"));
                    users.put("requestType", resultSet.getString("requestType"));
                    recipients.add(users);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving recipients" + e.getMessage());
            e.printStackTrace();
        }

        return recipients;
    }


    //The method below takes the donor username and the recipient username and adds them to a db called assigned
    public static void combineData(String donorUsername, String recipientUsername) {
        try (Connection connection = Model.getInstance().getDatabaseDriver().connect()) {
            // Retrieve donor data
            String donorQuery = "SELECT username, email, donationType FROM donor_ud WHERE username = ?";
            PreparedStatement donorStatement = connection.prepareStatement(donorQuery);
            donorStatement.setString(1, donorUsername);
            ResultSet donorResultSet = donorStatement.executeQuery();

            // Retrieve recipient data
            String recipientQuery = "SELECT username, email, requestType FROM recipient_ud WHERE username = ?";
            PreparedStatement recipientStatement = connection.prepareStatement(recipientQuery);
            recipientStatement.setString(1, recipientUsername);
            ResultSet recipientResultSet = recipientStatement.executeQuery();

            // Insert combined data into the combined table
            String combinedInsertQuery = "INSERT INTO `Assigned_Donors&Recipients` (DonorUsername, DonorEmail, Donation, RecipientUsername, RecipientEmail, Request, Status) VALUES (?, ?, ?, ?, ?, ?,?)";
            PreparedStatement combinedStatement = connection.prepareStatement(combinedInsertQuery);

            if (donorResultSet.next() && recipientResultSet.next()) {
                // Extract donor data
                String donorEmail = donorResultSet.getString("email");
                String donationType = donorResultSet.getString("donationType");

                // Extract recipient data
                String recipientEmail = recipientResultSet.getString("email");
                String requestType = recipientResultSet.getString("requestType");

                // Insert combined data into the combined table
                combinedStatement.setString(1, donorUsername);
                combinedStatement.setString(2, donorEmail);
                combinedStatement.setString(3, donationType);
                combinedStatement.setString(4, recipientUsername);
                combinedStatement.setString(5, recipientEmail);
                combinedStatement.setString(6, requestType);
                combinedStatement.setString(7, "Pending Pickup");

                // Execute the insert query
                combinedStatement.executeUpdate();
                System.out.println("Data combined and inserted into the 'combined' table successfully.");
                updateDonorRecipientStatus(donorUsername, recipientUsername);
                Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
                errorAlert.setContentText("Link successful");
                errorAlert.showAndWait();

            } else {
                System.out.println("Donor or recipient not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error combining data: " + e.getMessage());
        }
    }

    // Method to update donor and recipient status based on combined table
    public static void updateDonorRecipientStatus(String donorUsername, String recipientUsername) {
        String getStatusQuery = "SELECT Status FROM `Assigned_Donors&Recipients` WHERE DonorUsername = ? AND RecipientUsername = ?";
        String updateDonorStatusQuery = "UPDATE donor_ud SET status = ? WHERE username = ?";
        String updateRecipientStatusQuery = "UPDATE recipient_ud SET status = ? WHERE username = ?";



        try (Connection connection = Model.getInstance().getDatabaseDriver().connect();
             PreparedStatement getStatusStatement = connection.prepareStatement(getStatusQuery);
             PreparedStatement updateDonorStatusStatement = connection.prepareStatement(updateDonorStatusQuery);
             PreparedStatement updateRecipientStatusStatement = connection.prepareStatement(updateRecipientStatusQuery)) {

            // Retrieve status from combined table
            getStatusStatement.setString(1, donorUsername);
            getStatusStatement.setString(2, recipientUsername);
            ResultSet resultSet = getStatusStatement.executeQuery();
            String status = "";
            if (resultSet.next()) {
                status = resultSet.getString("Status");
                System.out.println(status);
            }

            // Update donor status
            updateDonorStatusStatement.setString(1, status);
            updateDonorStatusStatement.setString(2, donorUsername);
            updateDonorStatusStatement.executeUpdate();

            // Update recipient status
            updateRecipientStatusStatement.setString(1, status);
            updateRecipientStatusStatement.setString(2, recipientUsername);
            updateRecipientStatusStatement.executeUpdate();

            System.out.println("Donor and recipient status updated successfully.");

        } catch (SQLException e) {
            System.out.println("Error updating donor and recipient status: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
