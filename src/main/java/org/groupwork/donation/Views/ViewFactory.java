package org.groupwork.donation.Views;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.groupwork.donation.Controllers.Admin.AdminController;
import org.groupwork.donation.Controllers.Donor.DonorController;

public class ViewFactory {
//    Auth Views
    private final StringProperty authSelectedScenePane;
    private AnchorPane authenticationView;
    private AnchorPane registerChoiceView;
    private AnchorPane registerDonorView;
    private AnchorPane registerRecipientView;

//    Admin Views
    private final StringProperty adminSelectedMenuItem;
    private AnchorPane dashboardView;
    private AnchorPane verifyAccountView;
    private AnchorPane totalDonorsView;
    private AnchorPane totalRecipientsView;
    private AnchorPane totalDonationsView;

    public ViewFactory(){
        this.authSelectedScenePane = new SimpleStringProperty("");
        this.adminSelectedMenuItem = new SimpleStringProperty("");
    }

    public StringProperty getAuthSelectedScenePane(){
        return authSelectedScenePane;
    }

    public StringProperty getAdminSelectedMenuItem(){
        return adminSelectedMenuItem;
    }

        /*
        Client View Sections
     */

    public AnchorPane getAuthenticationView() {
        if(authenticationView == null){
            try {
                authenticationView = new FXMLLoader(getClass().getResource("/fxml/Auth/Authentication.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return authenticationView;
    }

    public AnchorPane getRegisterChoiceView() {
        if(registerChoiceView == null){
            try {
                registerChoiceView = new FXMLLoader(getClass().getResource("/fxml/Auth/RegisterChoice.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return registerChoiceView;
    }

    public AnchorPane getRegisterDonorView() {
        if(registerDonorView == null){
            try {
                registerDonorView = new FXMLLoader(getClass().getResource("/fxml/Auth/RegisterDonor.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return registerDonorView;
    }
    public AnchorPane getRegisterRecipientView() {
        if(registerRecipientView == null){
            try {
                registerRecipientView = new FXMLLoader(getClass().getResource("/fxml/Auth/RegisterRecipient.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return registerRecipientView;
    }

    /*
        Admin View Sections
     */
    public AnchorPane getDashboardView() {
        if(dashboardView == null){
            try {
                dashboardView = new FXMLLoader(getClass().getResource("/fxml/Admin/OverviewDash.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dashboardView;
    }

    public AnchorPane getVerifyAccountsView() {
        if(verifyAccountView == null){
            try {
                verifyAccountView = new FXMLLoader(getClass().getResource("/fxml/Admin/VerifyAccount.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return verifyAccountView;
    }

    public AnchorPane getTotalDonorsView() {
        if(totalDonorsView == null){
            try {
                totalDonorsView = new FXMLLoader(getClass().getResource("/fxml/Admin/TotalDonors.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return totalDonorsView;
    }
    public AnchorPane getTotalRecipientsView() {
        if(totalRecipientsView == null){
            try {
                totalRecipientsView = new FXMLLoader(getClass().getResource("/fxml/Admin/TotalRecipients.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return totalRecipientsView;
    }
    public AnchorPane getTotalDonationsView() {
        if(totalDonationsView == null){
            try {
                totalDonationsView = new FXMLLoader(getClass().getResource("/fxml/Admin/TotalDonations.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return totalDonationsView;
    }

    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Auth/Login.fxml"));
        createStage(loader);
    }

    public void showAdminWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/Admin.fxml"));
        createStage(loader);
    }


    public void showDonorWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Donor/Donor.fxml"));
        createStage(loader);
    }

    public void showRecipientWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Recipient/Recipient.fxml"));
        createStage(loader);
    }

    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e){
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Donation App");
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/donation_app.png"))));
        stage.setResizable(false);
        stage.show();
    }

    public void closeStageWithoutAlert(Stage stage){
        try {
            stage.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private void closeStageWithAlert(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("About to Close the App");
        alert.setHeaderText("Are you sure want to close the App?");

        if(alert.showAndWait().get() == ButtonType.OK){
            try {
                stage.close();
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

}
