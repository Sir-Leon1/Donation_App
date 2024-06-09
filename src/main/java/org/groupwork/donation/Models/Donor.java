package org.groupwork.donation.Models;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Donor {

    public static int rowCount;

    //The method below adds a donor to the DB on registration. (TDB) To DataBase
    public static void registerDonor(String email, String username, String password, String location, String usertype, String phoneno){

        String inAuth = "INSERT INTO donation_app_ud (Email, Username, Password, Location, UserType, PhoneNo, Org_Website) VALUES (?,?,?,?,?,?,?)";
        String inDonor = "INSERT INTO donor_ud (email, username, location, userType, phoneno, status, donationType) VALUES (?,?,?,?,?,?,?)";

        try (Connection connection = Model.getInstance().getDatabaseDriver().connect();
             PreparedStatement preparedStatement = connection.prepareStatement(inAuth)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, location);
            preparedStatement.setString(5, usertype);
            preparedStatement.setString(6, phoneno);
            preparedStatement.setString(7, "NA");

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {

                System.out.println("Success");
            }
        } catch (SQLException e){
            System.out.println("Exception to Auth DB caught");
            e.printStackTrace();
        }

        try (Connection connection = Model.getInstance().getDatabaseDriver().connect();
             PreparedStatement prepStmtDUD = connection.prepareStatement(inDonor)){
            prepStmtDUD.setString(1, email);
            prepStmtDUD.setString(2, username);
            prepStmtDUD.setString(3, location);
            prepStmtDUD.setString(4, usertype);
            prepStmtDUD.setString(5, phoneno);
            prepStmtDUD.setString(6, "-");
            prepStmtDUD.setString(7, "-");

            int rowsAffected2 = prepStmtDUD.executeUpdate();
            if (rowsAffected2 > 0){
                Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
                errorAlert.setContentText(email+" Registration Successful. Kindly login");
                errorAlert.showAndWait();
                System.out.println("Added to the donor DB");
            }
        }
        catch (SQLException e){
            System.out.println("Exception to donor DB Caught");
            e.printStackTrace();
        }
    }

    public static void addDonationTDB(String donation)
    {
        String sql = "UPDATE donor_ud SET donationType = ?, status = 'Active' WHERE email = ?";

        String email = Model.getInstance().getUser().getEmail();

        try (Connection connection = Model.getInstance().getDatabaseDriver().connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, donation);
            preparedStatement.setString(2, email);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setContentText("Donation type updated successfully.");
                errorAlert.showAndWait();
                System.out.println("Donation type updated successfully.");
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("No donation type updated. Make suze the email exists.");
                errorAlert.showAndWait();
                System.out.println("No donation type updated. Make suze the email exists");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Gets donors who have made donations from DB
    public static List<Map<String, String>> donationsMadeByDonor() {
        String query = "SELECT * FROM donor_ud WHERE donationType != ? AND status = 'Active'";
        List<Map<String, String>> donors = new ArrayList<>();

        try (Connection connection = Model.getInstance().getDatabaseDriver().connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "-");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                rowCount = donorsArray(donors, resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving donations made: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println(donors);
        return donors;
    }

    //Creates a hashmap and adds the donor details from the result set
    public static int donorsArray(List<Map<String, String>> donors, ResultSet resultSet) throws SQLException{
        int count = 0;
        while (resultSet.next()) {
            count++;
            Map<String, String> donor = new HashMap<>();
            donor.put("Email", resultSet.getString("Email"));
            donor.put("Username", resultSet.getString("Username"));
            donor.put("Location", resultSet.getString("Location"));
            donor.put("PhoneNo", resultSet.getString("PhoneNo"));
            donors.add(donor);
        }
        return count;
    }



}
