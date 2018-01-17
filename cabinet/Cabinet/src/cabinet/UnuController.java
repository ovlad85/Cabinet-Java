/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cabinet;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * FXML Controller class
 *
 * @author Vlad
 */

public class UnuController {

    Baza baza;

    public void setBaza(Baza baza) {
        this.baza = baza;
    }
    
    
    @FXML
    void iesire(ActionEvent event) {
        Platform.exit();
    }
    
     @FXML
    void intrare(ActionEvent event) {
        baza.incarcDoi();
    }

}
