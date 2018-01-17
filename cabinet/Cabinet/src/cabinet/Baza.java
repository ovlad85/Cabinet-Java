/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cabinet;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Vlad
 */
public class Baza extends Application {
    
   Stage fereastra;
    
     @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            fereastra = primaryStage;
            fereastra.setTitle("Cabinet");
            incarcUnu();
            primaryStage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    

    UnuController ctrUnu;

    public void incarcUnu() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("unu.fxml"));
        AnchorPane container;
        try {
            container = (AnchorPane) loader.load();
            Scene scene = new Scene(container);
            fereastra.setScene(scene);
            fereastra.sizeToScene();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        ctrUnu = loader.getController();
        ctrUnu.setBaza(this);
    }
    
   DoiController ctrDoi;
    public void incarcDoi() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("doi.fxml"));
        AnchorPane container;
        try {
            container = (AnchorPane) loader.load();
            Scene scene = new Scene(container);
            fereastra.setScene(scene);
            fereastra.sizeToScene();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        ctrDoi = loader.getController();
        ctrDoi.setBaza(this);
    }

    TreiController ctrTrei;
    public void incarcTrei() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("trei.fxml"));
        AnchorPane container;
        try {
            container = (AnchorPane) loader.load();
            Scene scene = new Scene(container);
            fereastra.setScene(scene);
            fereastra.sizeToScene();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        ctrTrei = loader.getController();
        ctrTrei.setBaza(this);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
