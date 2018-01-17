/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cabinet;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Vlad
 */
public class Consultatie {
     SimpleStringProperty numele; 
     SimpleStringProperty diagnostic; 
     SimpleStringProperty recomandare;
     
     public Consultatie(String numele, String diagnostic, String recomandare) {
        this.numele = new SimpleStringProperty(numele); 
        this.diagnostic = new SimpleStringProperty(diagnostic); 
        this.recomandare = new SimpleStringProperty(recomandare); 
        
    }
}
