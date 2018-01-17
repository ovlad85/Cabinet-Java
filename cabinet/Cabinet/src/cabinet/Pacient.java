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
public class Pacient {
     SimpleStringProperty ora; 
     SimpleStringProperty nume; 
     SimpleDoubleProperty varsta;
     
     public Pacient(String ora, String nume, double varsta) {
        this.ora = new SimpleStringProperty(ora); 
        this.nume = new SimpleStringProperty(nume); 
        this.varsta = new SimpleDoubleProperty(varsta); 
        
    }
     
}
