package org.groupwork.donation.Controllers.Admin;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javafx.geometry.Pos.CENTER;

public class AdminControllers {
    public AnchorPane content_area;

    @FXML
    public HBox hbox;
    public String OVERVIEW_DASH_FXML = "/fxml/Admin/OverviewDash.fxml";
    public String TotalDonors_FXML = "/fxml/Admin/TotalDonors.fxml";
    public String TotalDonations_FXML = "/fxml/Admin/TotalDonations.fxml";
    public String TotalRecipients_FXML = "/fxml/Admin/TotalRecipients.fxml";
    public String VerifyAccounts_FXML = "/fxml/Admin/VerifyAccounts.fxml";




    public void handleOverview(ActionEvent actionEvent) {
        navigateTo(OVERVIEW_DASH_FXML);
    }

    public void handleVerifyAccounts(ActionEvent actionEvent) {
        navigateTo(VerifyAccounts_FXML);
    }

    public void handleTotalDonors(ActionEvent actionEvent) {
        navigateTo(TotalDonors_FXML);
    }

    public void handleTotalRecipients(ActionEvent actionEvent) {
        navigateTo(TotalRecipients_FXML);
    }

    public void handleTotalDonations(ActionEvent actionEvent) {
        navigateTo(TotalDonations_FXML);
    }

    @FXML
    public VBox parentVBox = new VBox();

    public void initialize() {
//    // Fetch users asynchronously to prevent blocking the UI thread
//        // Show loading indicator
//        Label loadingLabel = new Label("Loading..");
//        ImageView loadingImageView = new ImageView(new Image(getClass().getResourceAsStream("/Images/charity.png")));
//        loadingImageView.setFitWidth(50); // Adjust size as needed
//        loadingImageView.setFitHeight(50);
//
//        VBox loadingIndicator = new VBox(10, loadingLabel, loadingImageView);
//        loadingIndicator.setAlignment(CENTER);
//        parentVBox.getChildren().add(loadingIndicator);
//
//        new Thread(() -> {
//            List<Map<String, String>> users = UserModel.getUsersByUserType("Donor");
//            for (Map<String, String> user : users) {
//                // Sample data for demonstration
//                String name = user.get("Username");
//                String email = user.get("Email");
//                String phoneNumber = user.get("PhoneNO");
//                String location = user.get("Location");
//                String joinedDate = "2024-03-25";
//
//                // Create an HBox using the createVBox method
//                HBox hbox = createVBox(name, email, phoneNumber, location, joinedDate);
//
//                // Update the UI on the JavaFX Application Thread
//                Platform.runLater(() -> {
//                    // Remove loading indicator
//                    parentVBox.getChildren().remove(loadingIndicator);
//
//                    // Add the created HBox to the parent VBox
//                    parentVBox.getChildren().add(hbox);
//                });
//            }
//        }).start();
    }


    private HBox createVBox(String name, String email, String phoneNumber, String location, String joinedDate) {
        HBox hbox = new HBox();
        hbox.setPrefSize(970, 72);
        hbox.setStyle("-fx-border-color: gray; -fx-border-radius: 10;");

        // First VBox
        VBox vBox1 = new VBox();
        vBox1.setPrefSize(284, 70);
        vBox1.setSpacing(10);
        Label nameLabel = new Label(name);
        nameLabel.setFont(new Font("DejaVu Sans Bold", 18));
        Label phoneLabel = new Label(phoneNumber);
        vBox1.getChildren().addAll(nameLabel, phoneLabel);
        hbox.getChildren().add(vBox1);

        // Second VBox
        VBox vBox2 = new VBox();
        vBox2.setPrefSize(229, 70);
        vBox2.setSpacing(10);
        Label emailLabel = new Label("Email Address");
        Label emailText = new Label(email);
        vBox2.getChildren().addAll(emailLabel, emailText);
        vBox2.setPadding(new Insets(10));
        hbox.getChildren().add(vBox2);

        // Third VBox
        VBox vBox3 = new VBox();
        vBox3.setPrefSize(218, 88);
        Label locationLabel = new Label("Location");
        Label locationText = new Label(location);
        vBox3.getChildren().addAll(locationLabel, locationText);
        hbox.getChildren().add(vBox3);

        // Fourth VBox
        VBox vBox4 = new VBox();
        vBox4.setPrefSize(152, 88);
        Label joinedLabel = new Label("Joined on");
        Label joinedText = new Label(joinedDate);
        vBox4.getChildren().addAll(joinedLabel, joinedText);
        hbox.getChildren().add(vBox4);

        return hbox;
    }

    private void navigateTo(String fxmlFile) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Pane newContentPane = loader.load();

            // Set the loaded content onto the content Pane
            content_area.getChildren().setAll(newContentPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
