package org.groupwork.donation.Controllers.Admin;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.groupwork.donation.Models.Model;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static javafx.geometry.Pos.CENTER;
import static javafx.geometry.Pos.CENTER_LEFT;

public class TotalRecipientController implements Initializable {

    public ScrollPane total_recipient_scrollable;
    public VBox parentVBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InputStream image = getClass().getResourceAsStream("/Images/charity.png");
        assert image != null;
        ImageView loadingImageView = new ImageView(new Image(image));
        Label loadingLabel = new Label("Loading Recipient...");
        loadingImageView.setFitWidth(100);
        loadingImageView.setFitHeight(100);

        VBox loadingIndicator = new VBox(loadingLabel, loadingImageView);
        loadingIndicator.fillWidthProperty();
        loadingIndicator.maxHeight(700);
        loadingIndicator.setAlignment(CENTER);
        parentVBox.getChildren().add(loadingIndicator);
        new Thread(() -> {
            List<Map<String, String>> users = Model.getUsersByUserType("Recipient");
            for (Map<String, String> user : users) {
                String name = user.get("Username");
                String website = user.get("Org_Website");
                String email = user.get("Email");
                String phoneNumber = user.get("PhoneNO");
                String location = user.get("Location");
                String joinedDate = "2024-03-25";

                // Create an HBox using the createVBox method
                HBox hbox = createVBox(name, email, website, phoneNumber, location, joinedDate);

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

    private HBox createVBox(String name, String website, String email, String phoneNumber, String location, String joinedDate) {
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
        Label websiteLabel = new Label(website);
        nameLabel.setFont(new Font("DejaVu Sans Bold", 18));
        vBox1.getChildren().addAll(nameLabel, websiteLabel);
        hbox.getChildren().add(vBox1);

        // Second VBox
        VBox vBox2 = new VBox();
        vBox2.setAlignment(CENTER);
        vBox2.setPrefSize(229, 70);
        vBox2.setSpacing(10);
        Label phoneLabel = new Label(phoneNumber);
        Label emailText = new Label(email);
        vBox2.getChildren().addAll(phoneLabel, emailText);
        vBox2.setPadding(new Insets(10));
        hbox.getChildren().add(vBox2);

        // Third VBox
        VBox vBox3 = new VBox();
        vBox3.setAlignment(CENTER);
        vBox3.setPrefSize(218, 88);
        Label locationLabel = new Label("Location");
        Label locationText = new Label(location);
        vBox3.getChildren().addAll(locationLabel, locationText);
        hbox.getChildren().add(vBox3);

        // Fourth VBox
        VBox vBox4 = new VBox();
        vBox4.setAlignment(CENTER);
        vBox4.setPrefSize(152, 88);
        Label joinedLabel = new Label("Joined on");
        Label joinedText = new Label(joinedDate);
        vBox4.getChildren().addAll(joinedLabel, joinedText);
        hbox.getChildren().add(vBox4);

        return hbox;
    }
}
