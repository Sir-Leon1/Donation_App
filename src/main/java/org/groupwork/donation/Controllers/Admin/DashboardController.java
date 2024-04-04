package org.groupwork.donation.Controllers.Admin;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.groupwork.donation.Models.Admin;
import org.groupwork.donation.Models.Donor;
import org.groupwork.donation.Models.Model;

import java.io.InputStream;
import java.net.URL;
import java.util.*;

import static javafx.geometry.Pos.CENTER;
import static javafx.geometry.Pos.CENTER_LEFT;

public class DashboardController implements Initializable {
    public ScrollPane pending_link;
    public VBox parentVBox;
    public Text username_label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String username = Model.getInstance().getUser().getUsername();
        username_label.setText("Hello");
        InputStream image = getClass().getResourceAsStream("/Images/donation_app.png");
        assert image != null;
        ImageView loadingImageView = new ImageView(new Image(image));
        Label loadingLabel = new Label("Loading Donors...");
        loadingImageView.setFitWidth(100);
        loadingImageView.setFitHeight(100);

        VBox loadingIndicator = new VBox(loadingLabel, loadingImageView);
        loadingIndicator.fillWidthProperty();
        loadingIndicator.maxHeight(700);
        loadingIndicator.setAlignment(CENTER);
        parentVBox.getChildren().add(loadingIndicator);

        new Thread(() -> {
            // Retrieve recipients
            List<Map<String, String>> recipients = Admin.recipientsRequests();
            List<String> recipientNamesAndDonationTypes = new ArrayList<>();
            for (Map<String, String> recipient : recipients) {
                System.out.println("recipients" + recipient);
                String name = recipient.get("username");
                String donationType = recipient.get("requestType");
                String recipientInfo = name + " - " + donationType;
                recipientNamesAndDonationTypes.add(recipientInfo);
            }

            ComboBox<String> combo_box = new ComboBox<>(FXCollections.observableArrayList(recipientNamesAndDonationTypes));

            List<Map<String, String>> donations = Donor.donationsMadeByDonor();
            for (Map<String, String> donation : donations) {
                System.out.println("Donations" + donation);
                // Sample data for demonstration
                String name = donation.get("Username");
                String email = donation.get("Email");
                String phoneNumber = donation.get("PhoneNO");
                String location = donation.get("Location");

                // Create an HBox using the createVBox method
                HBox hbox = createVBox(name, email, phoneNumber, location, combo_box);

                // Update the UI on the JavaFX Application Thread
                Platform.runLater(() -> {
                    // Remove loading indicator
                    parentVBox.getChildren().remove(loadingIndicator);

                    hbox.setSpacing(10);

                    // Add the created HBox to the parent VBox
                    parentVBox.getChildren().add(hbox);
                });
            }


        }).start();


    }

    public void handleLinkDonation(String selectedDonationType, String donorName) {

        if (!selectedDonationType.isEmpty()) {
            String[] splitDonationType = selectedDonationType.split("-");
            String recipientName = splitDonationType[0];

            Admin.combineData(donorName, recipientName);
            System.out.println("Sending donation request for: " + recipientName + donorName);
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Recipient dropdown missing ");
            errorAlert.showAndWait();
        }
    }


    private HBox createVBox(String name, String email, String phoneNumber, String location, ComboBox<String> combo_box) {
        System.out.println(combo_box.getItems());
        HBox hbox = new HBox();
        hbox.setSpacing(10);

        hbox.setPrefSize(970, 70);
        hbox.setStyle("-fx-border-color: gray; -fx-border-radius: 10; -fx-alignment: center;");

        // First VBox
        VBox vBox1 = new VBox();
        vBox1.setPrefSize(284, 70);
        vBox1.setSpacing(10);
        vBox1.setAlignment(CENTER_LEFT);
        Label nameLabel = new Label(name);
        nameLabel.setFont(new Font("DejaVu Sans Bold", 18));
        Label phoneLabel = new Label(phoneNumber);
        vBox1.getChildren().addAll(nameLabel, phoneLabel);
        hbox.getChildren().add(vBox1);

        // Second VBox
        VBox vBox2 = new VBox();
        vBox2.setAlignment(CENTER);
        vBox2.setPrefSize(229, 70);
        vBox2.setSpacing(10);
        Label emailLabel = new Label("Email Address");
        Label emailText = new Label(email);
        vBox2.getChildren().addAll(emailLabel, emailText);
        vBox2.setPadding(new Insets(10));
        hbox.getChildren().add(vBox2);

        // Third VBox
        VBox vBox3 = new VBox();
        vBox3.setAlignment(CENTER);
        vBox3.setPrefSize(218, 88);
        ComboBox combo = new ComboBox<>((ObservableList<? extends Object>) combo_box.getItems());
        vBox3.getChildren().addAll(combo);
        hbox.getChildren().add(vBox3);

        // Fourth VBox
        VBox vBox4 = new VBox();
        vBox4.setAlignment(CENTER);
        vBox4.setPrefSize(152, 88);
        Button linkingButton = new Button("Link Donation");
        linkingButton.setOnAction((actionEvent -> this.handleLinkDonation((String) combo.getValue(), name)));
        vBox4.getChildren().addAll(linkingButton);
        hbox.getChildren().add(vBox4);

        return hbox;
    }
}
