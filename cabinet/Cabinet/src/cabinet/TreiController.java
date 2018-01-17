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
import javafx.scene.control.TextArea;
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
public class TreiController {

    Baza baza;

    public void setBaza(Baza baza) {
        this.baza = baza;
    }
    
    @FXML
    private ListView<String> zilele;
    
    @FXML
    private TableView<Consultatie> tabel2;

    @FXML
    private TableColumn<Consultatie, String> cNumele;

    @FXML
    private TableColumn<Consultatie, String> cDiagnostic;

    @FXML
    private TableColumn<Consultatie, String> cRecomandare;

    @FXML
    private TextField numele;

    @FXML
    private TextField diagnostic;

    @FXML
    private TextArea recomandare;
    
    
    @FXML
    void inapoi(ActionEvent event) {
        baza.incarcDoi();
    }

    @FXML
    void iesit(ActionEvent event) {
        Platform.exit();
    }
    
    
    @FXML
    void adaug(ActionEvent event) {
        try {
            File fXmlFile = new File("Consultatii.xml");
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            Element radacina = doc.getDocumentElement();
            
            Text valoare;  
            Element empNou = doc.createElement("consultatie");
            String zz
                    = (String) zilele.getSelectionModel().getSelectedItem();
            empNou.setAttribute("zi", zz);

            Element n = doc.createElement("numele");
            empNou.appendChild(n);
            valoare = doc.createTextNode(numele.getText());
            n.appendChild(valoare);

            Element diagNou = doc.createElement("diagnostic");
            empNou.appendChild(diagNou);
            valoare = doc.createTextNode(diagnostic.getText());
            diagNou.appendChild(valoare);

            Element reconNou = doc.createElement("recomandare");
            empNou.appendChild(reconNou);
            valoare = doc.createTextNode(recomandare.getText());
            reconNou.appendChild(valoare);

           

            radacina.appendChild(empNou);
            //  Salvez fisierul .xml
            salvez(doc);
            incarc(zz);
            
            numele.setText("");
            diagnostic.setText("");
            recomandare.setText("");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    

    @FXML
    void schimb(ActionEvent event) {
          Consultatie c = (Consultatie) tabel2.getSelectionModel().getSelectedItem();
        String nInitial = c.numele.get();
        try {
            File fXmlFile = new File("Consultatii.xml");
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            Element radacina = doc.getDocumentElement();

            NodeList lista = doc.getElementsByTagName("consultatie");

            for (int contor = 0; contor < lista.getLength(); contor++) {
                Element emp = (Element) lista.item(contor);
                String t
                        = emp.getElementsByTagName("numele").item(0).getTextContent();

                if (t.equals(nInitial)) {
                    emp.getElementsByTagName("numele").item(0).
                            setTextContent(numele.getText());
                    emp.getElementsByTagName("diagnostic").item(0).
                            setTextContent(diagnostic.getText());
                    emp.getElementsByTagName("recomandare").item(0).
                            setTextContent(recomandare.getText());
                    
                    salvez(doc);
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String team
                = (String) zilele.getSelectionModel().getSelectedItem();
        incarc(team);
    }

    @FXML
    void sterg(ActionEvent event) {
              Consultatie c
                = (Consultatie) tabel2.getSelectionModel().getSelectedItem();
        String nInitial = c.numele.get();
        try {
            File fXmlFile = new File("Consultatii.xml");
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            
            NodeList lista = doc.getElementsByTagName("consultatie");
            
            for (int contor = 0; contor < lista.getLength(); contor++) {
                Element emp = (Element) lista.item(contor);
                String t
                        = emp.getElementsByTagName("numele").item(0).getTextContent();
             
                if (t.equals(nInitial)) {
                    
                    emp.getParentNode().removeChild(emp);
                    salvez(doc);
                    break;
                }
            }
        } catch (Exception d) {
            d.printStackTrace();
        }
        
        String team = (String) zilele.getSelectionModel().getSelectedItem();
        incarc(team);
    }   
    
     @FXML
    void initialize() {
        zilele.getItems().add("Luni");
        zilele.getItems().add("Marti");
        zilele.getItems().add("Miercuri");
        zilele.getItems().add("Joi");
        zilele.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        zilele.getSelectionModel().select(0);

        cNumele.setCellValueFactory(cellData -> cellData.getValue().numele);
        cDiagnostic.setCellValueFactory(cellData -> cellData.getValue().diagnostic);
        cRecomandare.setCellValueFactory(cellData -> cellData.getValue().recomandare);
        String days = (String) zilele.getSelectionModel().getSelectedItem();
        incarc(days);

        zilele.getSelectionModel().selectedItemProperty().
                addListener((ov, valVeche, valNoua) -> {
                    String tm = (String) valNoua;
                    incarc(tm);
                });

        tabel2.getSelectionModel().selectedIndexProperty().addListener((ov, valVeche, valNoua) -> {
            int linie = (int) valNoua;
            if (linie >= 0) {
                Consultatie c = tabel2.getItems().get(linie);
                numele.setText(c.numele.get());
                diagnostic.setText(String.valueOf(c.diagnostic.get()));
                recomandare.setText(String.valueOf(c.recomandare.get()));
                
            }
        });               
    }
    
   private void incarc(String z) {
        try {
            File fXmlFile = new File("Consultatii.xml");
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            Element radacina = doc.getDocumentElement();
            NodeList lista = doc.getElementsByTagName("consultatie");

            tabel2.getItems().clear();

            for (int contor = 0; contor < lista.getLength(); contor++) {
                Element consultatie = (Element) lista.item(contor);
                if (consultatie.getAttribute("zi").equals(z)) {

                    String pnum = consultatie.getElementsByTagName("numele").item(0).getTextContent();
                    String pdiag = consultatie.getElementsByTagName("diagnostic").item(0).getTextContent();
                    String precom = consultatie.getElementsByTagName("recomandare").item(0).getTextContent();
                    
                    
                    Consultatie con;
                    con = new Consultatie(pnum, pdiag, precom);

                    tabel2.getItems().add(con);
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
            FileOutputStream fo = new FileOutputStream("Consultatii.xml");
            StreamResult rezultat = new StreamResult(fo);
            transformer.transform(sursa, rezultat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
 }
