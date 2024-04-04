package org.groupwork.donation.Controllers.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.groupwork.donation.Models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {

    public Button dashboard_button;
    public Button verify_accounts_button;
    public Button total_donors_button;
    public Button total_recipients_button;
    public Button total_donations_button;
    public Button log_out_button;
    public Label username_label = new Label();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String username = Model.getInstance().getUser().getUsername();
        username_label.setText(username);
        addListeners();
    }

    private void addListeners(){
        dashboard_button.setOnAction(event -> onDashboard());
//        verify_accounts_button.setOnAction(event -> onVerifyAccounts());
        total_donors_button.setOnAction(event -> onTotalDonors());
        total_donations_button.setOnAction(event -> onTotalDonations());
        total_recipients_button.setOnAction(event -> onTotalRecipients());
    }

    private void onDashboard(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set("Dashboard");
    }

    private void onVerifyAccounts(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set("VerifyAccounts");
    }

    private void onTotalDonors(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set("TotalDonors");
    }

    private void onTotalRecipients(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set("TotalRecipients");
    }

    private void onTotalDonations(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set("TotalDonations");
    }

    public void handleLogOutUser(ActionEvent actionEvent) {
        Stage stage = (Stage)dashboard_button.getScene().getWindow();

        Model.getInstance().getViewFactory().closeStageWithoutAlert(stage);
        Model.getInstance().LogOutUser();
    }
}
