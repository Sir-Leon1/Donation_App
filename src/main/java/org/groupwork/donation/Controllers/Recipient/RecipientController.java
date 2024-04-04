package org.groupwork.donation.Controllers.Recipient;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.groupwork.donation.Models.Admin;
import org.groupwork.donation.Models.Model;
import org.groupwork.donation.Models.Recipient;

import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static javafx.geometry.Pos.CENTER;
import static javafx.geometry.Pos.CENTER_LEFT;

public class RecipientController  implements Initializable {
    @FXML
    public ScrollPane total_donor_scrollable;
    @FXML
    public VBox parentVBox = new VBox();
    @FXML
    public ComboBox<String> combo_box = new ComboBox<>();

    private final String[] donationTypes = { "Food", "Clothes", "Others" };
    public Label username_label = new Label();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String username = Model.getInstance().getUser().getUsername();
        username_label.setText(username);
        combo_box.setItems(FXCollections.observableArrayList(donationTypes));

        InputStream image = getClass().getResourceAsStream("/Images/charity.png");
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
            String user_email = Model.getInstance().getUser().getEmail();
            List<Map<String, String>> donations = Recipient.requestsMade(user_email);

            if(donations.isEmpty()){
                HBox hbox = createVBox("", "", "");

                Platform.runLater(() -> {
                    // Remove loading indicator
                    parentVBox.getChildren().remove(loadingIndicator);

                    // Add the created HBox to the parent VBox
                    parentVBox.getChildren().add(new VBox());
                });
            }

            for (Map<String, String> donation : donations) {

                // Sample data for demonstration
                //DonorUsername
                String name = donation.get("DonorUsername");
                String status = donation.get("Status");
                String requestType = donation.get("Request");

                // Create an HBox using the createVBox method
                HBox hbox = createVBox(name, status, requestType);

                // Update the UI on the JavaFX Application Thread
                Platform.runLater(() -> {
                    // Remove loading indicator
                    parentVBox.getChildren().remove(loadingIndicator);

                    // Add the created HBox to the parent VBox
                    parentVBox.getChildren().add(hbox);
                });
            }

        }).start();
    }



    public void handleMakeRequest() {
        String selectedDonationType = combo_box.getValue();
        Recipient.addRequestTDB(selectedDonationType);

        System.out.println("Sending donation request for: " + selectedDonationType);
    }

    public void handleLogout(ActionEvent actionEvent) {
        Stage stage = (Stage)combo_box.getScene().getWindow();

        Model.getInstance().getViewFactory().closeStageWithoutAlert(stage);
        Model.getInstance().LogOutUser();
    }

    private void handleComplete(String donorUsername){
        String email = Model.getInstance().getUser().getEmail();

        Recipient.markCompleteDonation(email);

        System.out.println("markCompleteDonation: " + email);

    }

    private HBox createVBox(String name, String status, String requestType) {
        HBox hbox = new HBox();
        hbox.setSpacing(10);

        hbox.setPrefSize(970, 70);
        hbox.setStyle("-fx-border-color: gray; -fx-border-radius: 10; -fx-alignment: center; -fx-padding: 10; -fx-background-color: white");

        // First VBox
        VBox vBox1 = new VBox();
        vBox1.setPrefSize(284, 70);
        vBox1.setSpacing(10);
        vBox1.setAlignment(CENTER_LEFT);
        Label nameLabel = new Label(name);
        nameLabel.setFont(new Font("DejaVu Sans Bold", 18));
        Label statusLabel = new Label(status);
        vBox1.getChildren().addAll(nameLabel, statusLabel);
        hbox.getChildren().add(vBox1);

        // Second VBox
        VBox vBox2 = new VBox();
        vBox2.setAlignment(CENTER);
        vBox2.setPrefSize(229, 70);
        vBox2.setSpacing(10);
        Label emailLabel = new Label("Request Type");
        Label emailText = new Label(requestType);
        vBox2.getChildren().addAll(emailLabel, emailText);
        vBox2.setPadding(new Insets(10));
        hbox.getChildren().add(vBox2);
//
//        // Third VBox
//        VBox vBox3 = new VBox();
//        vBox3.setAlignment(CENTER);
//        vBox3.setPrefSize(218, 88);
//        Label locationLabel = new Label("Location");
//        Label locationText = new Label(location);
//        vBox3.getChildren().addAll(locationLabel, locationText);
//        hbox.getChildren().add(vBox3);

        // Fourth VBox
        VBox vBox4 = new VBox();
        vBox4.setAlignment(CENTER);
        vBox4.setPrefSize(152, 88);
        Button confirmButton = new Button("Confirm pickup");
        confirmButton.setOnAction(actionEvent -> this.handleComplete(name));
        vBox4.getChildren().addAll(confirmButton);
        hbox.getChildren().add(vBox4);

        return hbox;
    }
}
