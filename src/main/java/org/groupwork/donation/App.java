package org.groupwork.donation;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.groupwork.donation.Models.Model;

public class App extends Application {
    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage stage) {

        Model.getInstance().getViewFactory().showLoginWindow();

        stage.setOnCloseRequest(e -> {
            e.consume();
            exitApp(stage);
        });
    }


    public void exitApp(Stage stage){
        // Close Alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close App");
        alert.setHeaderText("Are You Sure You Want to Exit?");
        if(alert.showAndWait().get() == ButtonType.OK){
            try {
                Platform.exit();
                stage.close();
            }catch (Exception e){
                // System.out.println(e);
                e.printStackTrace();
            }
        }
    }
}
