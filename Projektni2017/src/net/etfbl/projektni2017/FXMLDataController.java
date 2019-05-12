/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.etfbl.projektni2017;

import java.io.File;
import java.io.FileNotFoundException;
import net.etfbl.korisnici.Organizator;
import net.etfbl.korisnici.Ucesnik;
import net.etfbl.dogadjaj.Dogadjaj;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import net.etfbl.korisnici.Posijetilac;
import static net.etfbl.projektni2017.FXMLDocumentController.ois;
import static net.etfbl.projektni2017.FXMLDocumentController.ois2;
import static net.etfbl.projektni2017.FXMLDocumentController.oos;
import static net.etfbl.projektni2017.FXMLDocumentController.sock2;

/**
 * FXML Controller class
 *
 * @author Office
 */
public class FXMLDataController implements Initializable {

    @FXML
    private TableColumn<Dogadjaj, Organizator> colOrganizator;

    @FXML
    private TableColumn<Dogadjaj, String> colOpis;

    @FXML
    private Button downloadButton;

    @FXML
    private TableView<Dogadjaj> tabela;

    @FXML
    private Button deleteButton;

    @FXML
    private Button marketingButton;

    @FXML
    private Button newEventButton;

    @FXML
    private Button showButton;

    @FXML
    private Label greskaLab;

    @FXML
    private TableColumn<Dogadjaj, GregorianCalendar> colKraj;

    @FXML
    private TableColumn<Dogadjaj, String> colNaziv;

    @FXML
    private TableColumn<Dogadjaj, ArrayList<Ucesnik>> colUcesnici;

    @FXML
    private TableColumn<Dogadjaj, GregorianCalendar> colPocetak;

    private ArrayList<Dogadjaj> k = new ArrayList<>();
    
    public static Posijetilac pos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colNaziv.setCellValueFactory(new PropertyValueFactory<Dogadjaj, String>("naziv"));
        colPocetak.setCellValueFactory(new PropertyValueFactory<Dogadjaj, GregorianCalendar>("pocetak"));
        colKraj.setCellValueFactory(new PropertyValueFactory<Dogadjaj, GregorianCalendar>("kraj"));
        colOpis.setCellValueFactory(new PropertyValueFactory<Dogadjaj, String>("opis"));
        colOrganizator.setCellValueFactory(new PropertyValueFactory<Dogadjaj, Organizator>("organizator"));
        colUcesnici.setCellValueFactory(new PropertyValueFactory<Dogadjaj, ArrayList<Ucesnik>>("ucesnici"));

        try {
            pos = (Posijetilac) ois.readObject();

        } catch (IOException ex) {
            Logger.getLogger(FXMLDataController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDataController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                try {
                    while (ois2.readBoolean()) {
                        String poruka=(String) ois2.readObject();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Stage stage = new Stage();
                                StackPane root = new StackPane();
                                Label l = new Label(poruka);
                                root.getChildren().add(l);
                                Scene scene = new Scene(root, 300, 250);
                                stage.setTitle("Obavještenje!");
                                stage.setScene(scene);
                                stage.show();
                            }
                        });
                    }
                    ois2.close();
                    sock2.close();
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                } 

            }
        };
        new Timer().schedule(task, 0);
        newEventButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                try {
                    greskaLab.setText("");
                    Stage s = new Stage();
                    Pane myPane = (Pane) FXMLLoader.load(getClass().getResource("/net/etfbl/projektni2017/FXMLNewEvent.fxml"));
                    Scene myScene = new Scene(myPane);
                    s.setScene(myScene);
                    s.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
        showButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                try {
                    greskaLab.setText("");
                    oos.writeObject("pretraga");
                    oos.flush();
                    k = (ArrayList<Dogadjaj>) ois.readObject();
                    tabela.getItems().clear();
                    for (Dogadjaj k1 : k) {
                        tabela.getItems().add(k1);
                    }

                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }

            }
        });

        marketingButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                if (tabela.getSelectionModel().isEmpty()) {
                    greskaLab.setText("Greška");
                } 
                else {
                try {
                    greskaLab.setText("");
                    oos.writeObject("marketing");
                    oos.flush();
                    Dogadjaj pom = (Dogadjaj) tabela.getSelectionModel().getSelectedItem();
                    oos.writeObject(pom.getNaziv());
                    oos.flush();
                    boolean z=ois.readBoolean();
                    if(!z){
                        greskaLab.setText("Za ovaj dogadja nije moguće pokrenuti kampanju");
                    }
                        

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
            }
        });
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (tabela.getSelectionModel().isEmpty()) {
                    greskaLab.setText("Greška");
                } 
                else {
                try {
                    greskaLab.setText("");
                    oos.writeObject("izbrisi");
                    oos.flush();
                    Dogadjaj pom = (Dogadjaj) tabela.getSelectionModel().getSelectedItem();
                    oos.writeObject(pom.getNaziv());
                    oos.flush();
                    k = (ArrayList<Dogadjaj>) ois.readObject();
                    tabela.getItems().clear();
                    for (Dogadjaj k1 : k) {
                        tabela.getItems().add(k1);
                    }

                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }

            }
            }
        });
        
        downloadButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                PrintWriter writer = null;
                try {
                    File theDir = new File(pos.getIme());
                    theDir.mkdir();
                    writer = new PrintWriter("./" + pos.getIme() + "/" + "Lista.txt", "UTF-8");
                    for (Dogadjaj k1 :k) {
                        writer.println(k1.getNaziv()+ ","+ k1.getOrganizator().getIme() + ","+ k1.getUcesnici().toString());
                    }
                } catch (FileNotFoundException | UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                } finally {
                    writer.close();
                }
            }
        });

    }

}
