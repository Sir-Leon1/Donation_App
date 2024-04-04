package org.groupwork.donation.Controllers.Admin;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import org.groupwork.donation.Models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    public BorderPane admin_parent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener(((observableValue, oldValue, newValue) -> {
            switch (newValue){
                case "VerifyAccounts" -> admin_parent.setCenter(Model.getInstance().getViewFactory().getVerifyAccountsView());
                case "TotalDonors" -> admin_parent.setCenter(Model.getInstance().getViewFactory().getTotalDonorsView());
                case "TotalRecipients" -> admin_parent.setCenter(Model.getInstance().getViewFactory().getTotalRecipientsView());
                case "TotalDonations" -> admin_parent.setCenter(Model.getInstance().getViewFactory().getTotalDonationsView());
                default -> admin_parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
            }
        }));
    }
}
