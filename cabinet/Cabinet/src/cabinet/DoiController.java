/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cabinet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 * FXML Controller class
 *
 * @author Vlad
 */
public class DoiController  {

    Baza baza;

    public void setBaza(Baza baza) {
        this.baza = baza;
    }
    
    @FXML
    private ListView<String> zile;
    
    @FXML
    private TableView<Pacient> tabel;
    
    @FXML
    private TableColumn<Pacient, String> cOra;

    @FXML
    private TableColumn<Pacient, String> cNume;

    @FXML
    private TableColumn<Pacient, Double> cVarsta;

    @FXML
    private TextField ora;

    @FXML
    private TextField nume;

    @FXML
    private TextField varsta;

    @FXML
    void Iesire(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void adauga(ActionEvent event) {
           try {
            File fXmlFile = new File("planificari.xml");
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            Element radacina = doc.getDocumentElement();
            
            Text valoare;  
            Element pacNou = doc.createElement("pacient");
            String zn
                    = (String) zile.getSelectionModel().getSelectedItem();
            pacNou.setAttribute("zi", zn);

            Element orNou = doc.createElement("ora");
            pacNou.appendChild(orNou);
            valoare = doc.createTextNode(ora.getText());
            orNou.appendChild(valoare);

            Element numNou = doc.createElement("nume");
            pacNou.appendChild(numNou);
            valoare = doc.createTextNode(nume.getText());
            numNou.appendChild(valoare);

            Element varstaNou = doc.createElement("varsta");
            pacNou.appendChild(varstaNou);
            valoare = doc.createTextNode(varsta.getText());
            varstaNou.appendChild(valoare);

           

            radacina.appendChild(pacNou);
            //  Salvez fisierul .xml
            salvez(doc);
            incarc(zn);
            
            ora.setText("");
            nume.setText("");
            varsta.setText("");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @FXML
    void schimba(ActionEvent event) {
            Pacient p = (Pacient) tabel.getSelectionModel().getSelectedItem();
        String nInitial = p.nume.get();
        try {
            File fXmlFile = new File("planificari.xml");
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            Element radacina = doc.getDocumentElement();

            NodeList lista = doc.getElementsByTagName("pacient");

            for (int contor = 0; contor < lista.getLength(); contor++) {
                Element emp = (Element) lista.item(contor);
                String t
                        = emp.getElementsByTagName("nume").item(0).getTextContent();

                if (t.equals(nInitial)) {
                    emp.getElementsByTagName("ora").item(0).
                            setTextContent(ora.getText());
                    emp.getElementsByTagName("nume").item(0).
                            setTextContent(nume.getText());
                    emp.getElementsByTagName("varsta").item(0).
                            setTextContent(varsta.getText());
                    
                    salvez(doc);
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String team
                = (String) zile.getSelectionModel().getSelectedItem();
        incarc(team);
    }

    @FXML
    void sterge(ActionEvent event) {
           Pacient p
                = (Pacient) tabel.getSelectionModel().getSelectedItem();
        String nInitial = p.nume.get();
        try {
            File fXmlFile = new File("planificari.xml");
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            
            NodeList lista = doc.getElementsByTagName("pacient");
            
            for (int contor = 0; contor < lista.getLength(); contor++) {
                Element emp = (Element) lista.item(contor);
                String t
                        = emp.getElementsByTagName("nume").item(0).getTextContent();
             
                if (t.equals(nInitial)) {
                    
                    emp.getParentNode().removeChild(emp);
                    salvez(doc);
                    break;
                }
            }
        } catch (Exception d) {
            d.printStackTrace();
        }
        
        String team = (String) zile.getSelectionModel().getSelectedItem();
        incarc(team); 
    } 
    
    @FXML
    void inreg(ActionEvent event) {
        baza.incarcTrei();
    }
    
    @FXML
    void initialize() {
        zile.getItems().add("Luni");
        zile.getItems().add("Marti");
        zile.getItems().add("Miercuri");
        zile.getItems().add("Joi");
        zile.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        zile.getSelectionModel().select(0);

        cOra.setCellValueFactory(cellData -> cellData.getValue().ora);
        cNume.setCellValueFactory(cellData -> cellData.getValue().nume);
        cVarsta.setCellValueFactory(cellData -> cellData.getValue().varsta.asObject());
        String zilele = (String) zile.getSelectionModel().getSelectedItem();
        incarc(zilele);

        zile.getSelectionModel().selectedItemProperty().
                addListener((ov, valVeche, valNoua) -> {
                    String tmt = (String) valNoua;
                    incarc(tmt);
                });

        tabel.getSelectionModel().selectedIndexProperty().addListener((ov, valVeche, valNoua) -> {
            int linie = (int) valNoua;
            if (linie >= 0) {
                Pacient e = tabel.getItems().get(linie);
                ora.setText(e.ora.get());
                nume.setText(String.valueOf(e.nume.get()));
                varsta.setText(String.valueOf(e.varsta.get()));
                
            }
        });

    }
    
      private void incarc(String zil) {
        try {
            File fXmlFile = new File("planificari.xml");
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            Element radacina = doc.getDocumentElement();
            NodeList lista = doc.getElementsByTagName("pacient");

            tabel.getItems().clear();

            for (int contor = 0; contor < lista.getLength(); contor++) {
                Element pacient = (Element) lista.item(contor);
                if (pacient.getAttribute("zi").equals(zil)) {

                    String pora = pacient.getElementsByTagName("ora").item(0).getTextContent();
                    String pnume = pacient.getElementsByTagName("nume").item(0).getTextContent();
                    String pvarsta = pacient.getElementsByTagName("varsta").item(0).getTextContent();
                    double dvarsta = Double.parseDouble(pvarsta);
                    
                    Pacient pac;
                    pac = new Pacient(pora, pnume, dvarsta);

                    tabel.getItems().add(pac);
                }
            }
        } catch (IOException | NumberFormatException | ParserConfigurationException | DOMException | SAXException e) {
        }
    }
      
      private void salvez(Document doc) {
// Salvez pe disc
        try {
            TransformerFactory transformerFactory
                    = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource sursa = new DOMSource(doc);
            FileOutputStream fo = new FileOutputStream("planificari.xml");
            StreamResult rezultat = new StreamResult(fo);
            transformer.transform(sursa, rezultat);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
