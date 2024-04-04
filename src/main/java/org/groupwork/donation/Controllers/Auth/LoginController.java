package org.groupwork.donation.Controllers.Auth;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import org.groupwork.donation.Models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public BorderPane auth_parent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getAuthSelectedScenePane().addListener(((observableValue, oldValue, newValue) -> {
            switch (newValue){
                case "RegisterChoice" -> auth_parent.setCenter(Model.getInstance().getViewFactory().getRegisterChoiceView());
                case "RegisterDonor" -> auth_parent.setCenter(Model.getInstance().getViewFactory().getRegisterDonorView());
                case "RegisterRecipient" -> auth_parent.setCenter(Model.getInstance().getViewFactory().getRegisterRecipientView());
                default -> auth_parent.setCenter(Model.getInstance().getViewFactory().getAuthenticationView());
            }
        }));
    }
}
