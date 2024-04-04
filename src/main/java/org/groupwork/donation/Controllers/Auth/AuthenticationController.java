package org.groupwork.donation.Controllers.Auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.groupwork.donation.Models.Donor;
import org.groupwork.donation.Models.Model;
import org.groupwork.donation.Models.Recipient;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AuthenticationController implements Initializable {

    @FXML
    public VBox register_recipient_button = new VBox();
    @FXML
    public VBox register_donor_button = new VBox();
    public FontIcon auth_button = new FontIcon();
    @FXML
    public Button register_choice_button = new Button();
    public FontIcon font_register_choice_button = new FontIcon();
    public TextField username_field;
    public TextField phone_number_field;
    public TextField location_field;
    public TextField website_field;

    @FXML
    private TextField email_field;

    @FXML
    private PasswordField password_field;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        auth_button.setOnMouseClicked(event -> onAuthentication());
        register_choice_button.setOnAction(event -> onRegisterChoice());
        font_register_choice_button.setOnMouseClicked(event -> onRegisterChoice());
        register_donor_button.setOnMouseClicked(event -> onRegisterDonor());
        register_recipient_button.setOnMouseClicked(event -> onRegisterRecipient());
    }

    private void onAuthentication() {
        Model.getInstance().getViewFactory().getAuthSelectedScenePane().set("Authentication");
    }

    private void onRegisterChoice() {
        Model.getInstance().getViewFactory().getAuthSelectedScenePane().set("RegisterChoice");
    }

    private void onRegisterDonor() {
        Model.getInstance().getViewFactory().getAuthSelectedScenePane().set("RegisterDonor");
    }

    private void onRegisterRecipient() {
        Model.getInstance().getViewFactory().getAuthSelectedScenePane().set("RegisterRecipient");
    }

    public void handleLogin(ActionEvent actionEvent) throws SQLException {
        Stage stage = (Stage)register_choice_button.getScene().getWindow();
        if (!email_field.getText().isBlank() && !password_field.getText().isBlank()) {
            Model.getInstance().validateUserCredentials(email_field.getText(), password_field.getText());
            String type = Model.getInstance().isUserLoggedIn();
            switch (type) {
                case "Admin" -> {
                    Model.getInstance().getViewFactory().closeStageWithoutAlert(stage);
                    Model.getInstance().getViewFactory().showAdminWindow();
                }
                case "Donor" -> {
                    Model.getInstance().getViewFactory().closeStageWithoutAlert(stage);
                    Model.getInstance().getViewFactory().showDonorWindow();
                }
                case "Recipient" -> {
                    Model.getInstance().getViewFactory().closeStageWithoutAlert(stage);
                    Model.getInstance().getViewFactory().showRecipientWindow();
                }
                default -> {}
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Enter missing fields to login.");
            errorAlert.showAndWait();
        }
    }

    public void registerDonor(ActionEvent event){
        String email = email_field.getText();
        String password = password_field.getText();
        String username = username_field.getText();
        String phone_number = phone_number_field.getText();
        String location = location_field.getText();

        if(!email.isBlank() && !password.isBlank() && !username.isBlank() && !phone_number.isBlank() && !location.isBlank()) {
            Donor.registerDonor(email, username, password, location, "Donor", phone_number);
            Model.getInstance().getViewFactory().getAuthSelectedScenePane().set("Authentication");
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Enter missing fields.");
            errorAlert.showAndWait();
        }
    }

    public void registerRecipient(ActionEvent event) {
        String email = email_field.getText();
        String password = password_field.getText();
        String username = username_field.getText();
        String phone_number = phone_number_field.getText();
        String location = location_field.getText();
        String website = website_field.getText();

        if(!email.isBlank() && !password.isBlank() && !username.isBlank() && !phone_number.isBlank() && !location.isBlank()) {
            Recipient.registerRecipient(email, username, password, location, "Recipient", phone_number, website);
            Model.getInstance().getViewFactory().getAuthSelectedScenePane().set("Authentication");
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Enter missing fields.");
            errorAlert.showAndWait();
        }
    }


}
