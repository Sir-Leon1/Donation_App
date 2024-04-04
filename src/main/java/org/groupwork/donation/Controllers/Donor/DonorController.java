package org.groupwork.donation.Controllers.Donor;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.groupwork.donation.Models.Donor;
import org.groupwork.donation.Models.Model;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static javafx.geometry.Pos.CENTER;
import static javafx.geometry.Pos.CENTER_LEFT;

public class DonorController implements Initializable {

    @FXML
    public ComboBox<String> combo_box = new ComboBox<>();

    private final String[] donationTypes = { "Food", "Clothes", "Others" };
    public Label username_label = new Label();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String username = Model.getInstance().getUser().getUsername();
        username_label.setText(username);
        combo_box.setItems(FXCollections.observableArrayList(donationTypes));
    }

    public void handleSendDonation() {
        String selectedDonationType = combo_box.getValue();
        Donor.addDonationTDB(selectedDonationType);

        System.out.println("Sending donation request for: " + selectedDonationType);
    }


    public void handleLogout(ActionEvent actionEvent) {
        Stage stage = (Stage)combo_box.getScene().getWindow();

        Model.getInstance().getViewFactory().closeStageWithoutAlert(stage);
        Model.getInstance().LogOutUser();
    }
}