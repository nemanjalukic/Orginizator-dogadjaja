/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.etfbl.projektni2017;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import net.etfbl.korisnici.Napomena;
import net.etfbl.korisnici.Organizator;
import net.etfbl.korisnici.Osoba;
import net.etfbl.korisnici.Predavac;
import net.etfbl.korisnici.Ucesnik;
import static net.etfbl.projektni2017.FXMLDocumentController.ois;
import static net.etfbl.projektni2017.FXMLDocumentController.oos;
import static net.etfbl.projektni2017.FXMLNewEventController.vratiListu;
import static net.etfbl.projektni2017.FXMLNewEventController.zatvori;

/**
 * FXML Controller class
 *
 * @author Office
 */
public class FXMLChangesController implements Initializable {

    @FXML
    private TextField ime;

    @FXML
    private TextField prezime;

    @FXML
    private ChoiceBox<Osoba> lista;

    @FXML
    private Label telLab;

    @FXML
    private TextField org;

    @FXML
    private Label napomeneLab;

    @FXML
    private Label prezimeLab;

    @FXML
    private Label eLab;

    @FXML
    private Label orgLab;

    @FXML
    private MenuButton napomene;

    @FXML
    private Button addButton;

    @FXML
    private Button okButton;

    @FXML
    private Label opisLab;

    @FXML
    private ChoiceBox<String> vrsta;

    @FXML
    private TextField napomena;

    @FXML
    private Label imeLab;

    @FXML
    private TextField tel;

    @FXML
    private TextField email;

    @FXML
    private ChoiceBox<String> akcija;

    private ArrayList<Organizator> o = new ArrayList<>();
    private ArrayList<Ucesnik> u = new ArrayList<>();
    private ArrayList<Predavac> p = new ArrayList<>();
    private ArrayList<Napomena> n = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        akcija.setItems(FXCollections.observableArrayList("Dodaj", "Izmijeni", "Izbrisi"));
        vrsta.setItems(FXCollections.observableArrayList("Organizator", "Ucesnik", "Predavac"));
        try {
            o = (ArrayList<Organizator>) ois.readObject();
            u = (ArrayList<Ucesnik>) ois.readObject();
            p = (ArrayList<Predavac>) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLChangesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        vrsta.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                if (akcija.getSelectionModel().getSelectedItem() != null) {
                    prikaz();
                }
            }
        });

        akcija.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                if (vrsta.getSelectionModel().getSelectedItem() != null) {
                    prikaz();
                }
            }
        });

        lista.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {

                if (akcija.getSelectionModel().getSelectedItem() != null && vrsta.getSelectionModel().getSelectedItem() != null) {
                    if (vrsta.getSelectionModel().getSelectedItem().equals("Organizator") && akcija.getSelectionModel().getSelectedItem().equals("Izmijeni") && lista.getSelectionModel().getSelectedItem() != null) {
                        Organizator org = (Organizator) lista.getSelectionModel().getSelectedItem();
                        napomene.getItems().clear();
                        napomena.clear();
                        n.clear();
                        if (org.getNapomene() != null) {
                            for (Napomena nap : org.getNapomene()) {
                                CheckMenuItem cm = new CheckMenuItem(nap.getNapomena());
                                cm.setSelected(true);
                                napomene.getItems().add(cm);
                            }
                        }
                        ime.setText(org.getIme());
                        prezime.setText(org.getPrezime());
                        tel.setText(org.getTelefon());
                        email.setText(org.getEmail());
                    } else if (vrsta.getSelectionModel().getSelectedItem().equals("Ucesnik") && akcija.getSelectionModel().getSelectedItem().equals("Izmijeni") && lista.getSelectionModel().getSelectedItem() != null) {
                        Ucesnik uc = (Ucesnik) lista.getSelectionModel().getSelectedItem();
                        ime.setText(uc.getIme());
                        prezime.setText(uc.getPrezime());
                        org.setText(uc.getOrganizacija());
                    } else if (vrsta.getSelectionModel().getSelectedItem().equals("Predavac") && akcija.getSelectionModel().getSelectedItem().equals("Izmijeni") && lista.getSelectionModel().getSelectedItem() != null) {
                        Predavac pred = (Predavac) lista.getSelectionModel().getSelectedItem();
                        ime.setText(pred.getIme());
                        prezime.setText(pred.getPrezime());

                    }

                }
            }
        });
        addButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                if(!napomena.getText().isEmpty() && provjeraListe(napomene,napomena.getText())){
                CheckMenuItem cm = new CheckMenuItem(napomena.getText());
                cm.setSelected(true);
                napomene.getItems().add(cm);
                Napomena na = new Napomena(new GregorianCalendar(), napomena.getText());
                n.add(na);
                napomena.clear();
                }
                else{
                    Stage stage = new Stage();
                    StackPane root = new StackPane();
                    Label l = new Label("Napomena sa ovim imenom već postoji");
                    root.getChildren().add(l);
                    Scene scene = new Scene(root, 300, 250);
                    stage.setTitle("Obavještenje!");
                    stage.setScene(scene);
                    stage.show();
                }
                
            }
        });

        okButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                if (akcija.getSelectionModel().getSelectedItem() != null && vrsta.getSelectionModel().getSelectedItem() != null) {
                    if (akcija.getSelectionModel().getSelectedItem().equals("Dodaj") && vrsta.getSelectionModel().getSelectedItem().equals("Organizator")) {
                        if (!ime.getText().isEmpty() && !prezime.getText().isEmpty() && !tel.getText().isEmpty() && !email.getText().isEmpty()) {
                            try {
                                oos.writeObject("Dodaj#organizator#" + ime.getText() + "#" + prezime.getText() + "#" + tel.getText() + "#" + email.getText());
                                oos.flush();
                                if (ois.readBoolean()) {
                                    oos.writeObject(new Organizator(ime.getText(), prezime.getText(), tel.getText(), email.getText(), vratiListu(napomene, n)));
                                    oos.flush();
                                    vratiSeNazad(t);
                                }
                                else {
                                    Stage stage = new Stage();
                                    StackPane root = new StackPane();
                                    Label l = new Label("Organizator sa ovim podacima već postoji");
                                    root.getChildren().add(l);
                                    Scene scene = new Scene(root, 300, 250);
                                    stage.setTitle("Obavještenje!");
                                    stage.setScene(scene);
                                    stage.show();
                                }

                            } catch (IOException ex) {
                                Logger.getLogger(FXMLChangesController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else {
                            Stage stage = new Stage();
                            StackPane root = new StackPane();
                            Label l = new Label("Nisu unijeti svi podaci");
                            root.getChildren().add(l);
                            Scene scene = new Scene(root, 300, 250);
                            stage.setTitle("Obavještenje!");
                            stage.setScene(scene);
                            stage.show();
                        }
                    }
                    else if (akcija.getSelectionModel().getSelectedItem().equals("Izmijeni") && vrsta.getSelectionModel().getSelectedItem().equals("Organizator")) {
                        try {
                            oos.writeObject("Izmijeni#organizator");
                            oos.flush();
                            Organizator organizator = (Organizator) lista.getSelectionModel().getSelectedItem();
                            oos.writeObject(organizator);
                            oos.flush();

                            if (!ime.getText().isEmpty() && !prezime.getText().isEmpty() && !tel.getText().isEmpty() && !email.getText().isEmpty()) {
                                oos.writeBoolean(true);
                                oos.flush();
                                oos.writeObject(new Organizator(ime.getText(), prezime.getText(), tel.getText(), email.getText(), vratiNapomene(organizator, n, napomene)));
                                oos.flush();
                                vratiSeNazad(t);
                            }
                            else {
                                oos.writeBoolean(false);
                                oos.flush();
                                Stage stage = new Stage();
                                StackPane root = new StackPane();
                                Label l = new Label("Nisu unijeti svi podaci");
                                root.getChildren().add(l);
                                Scene scene = new Scene(root, 300, 250);
                                stage.setTitle("Obavještenje!");
                                stage.setScene(scene);
                                stage.show();
                            }

                        } catch (IOException ex) {
                            Logger.getLogger(FXMLChangesController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                    else if (akcija.getSelectionModel().getSelectedItem().equals("Izbrisi") && vrsta.getSelectionModel().getSelectedItem().equals("Organizator")) {
                        try {
                            oos.writeObject("Izbrisi#organizator");
                            oos.flush();
                            Organizator organizator = (Organizator) lista.getSelectionModel().getSelectedItem();
                            oos.writeObject(organizator);
                            oos.flush();
                            vratiSeNazad(t);
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLChangesController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else if (akcija.getSelectionModel().getSelectedItem().equals("Dodaj") && vrsta.getSelectionModel().getSelectedItem().equals("Ucesnik")) {
                        if (!ime.getText().isEmpty() && !prezime.getText().isEmpty() && !org.getText().isEmpty()) {
                            try {
                                oos.writeObject("Dodaj#ucesnik#" + ime.getText() + "#" + prezime.getText() + "#" + org.getText());
                                oos.flush();
                                if (ois.readBoolean()) {
                                    oos.writeObject(new Ucesnik(ime.getText(), prezime.getText(), org.getText()));
                                    oos.flush();
                                    vratiSeNazad(t);
                                }
                                else {
                                    Stage stage = new Stage();
                                    StackPane root = new StackPane();
                                    Label l = new Label("Ucesnik sa ovim podacima već postoji");
                                    root.getChildren().add(l);
                                    Scene scene = new Scene(root, 300, 250);
                                    stage.setTitle("Obavještenje!");
                                    stage.setScene(scene);
                                    stage.show();
                                }

                            } catch (IOException ex) {
                                Logger.getLogger(FXMLChangesController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else {
                            Stage stage = new Stage();
                            StackPane root = new StackPane();
                            Label l = new Label("Nisu unijeti svi podaci");
                            root.getChildren().add(l);
                            Scene scene = new Scene(root, 300, 250);
                            stage.setTitle("Obavještenje!");
                            stage.setScene(scene);
                            stage.show();
                        }
                    }
                    else if (akcija.getSelectionModel().getSelectedItem().equals("Izmijeni") && vrsta.getSelectionModel().getSelectedItem().equals("Ucesnik")) {
                        try {
                            oos.writeObject("Izmijeni#ucesnik");
                            oos.flush();
                            Ucesnik ucesnik = (Ucesnik) lista.getSelectionModel().getSelectedItem();
                            oos.writeObject(ucesnik);
                            oos.flush();

                            if (!ime.getText().isEmpty() && !prezime.getText().isEmpty() && !org.getText().isEmpty()) {
                                oos.writeBoolean(true);
                                oos.flush();
                                oos.writeObject(new Ucesnik(ime.getText(), prezime.getText(), org.getText()));
                                oos.flush();
                                vratiSeNazad(t);
                            }
                            else {
                                oos.writeBoolean(false);
                                Stage stage = new Stage();
                                StackPane root = new StackPane();
                                Label l = new Label("Nisu unijeti svi podaci");
                                root.getChildren().add(l);
                                Scene scene = new Scene(root, 300, 250);
                                stage.setTitle("Obavještenje!");
                                stage.setScene(scene);
                                stage.show();
                            }

                        } catch (IOException ex) {
                            Logger.getLogger(FXMLChangesController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                    else if (akcija.getSelectionModel().getSelectedItem().equals("Izbrisi") && vrsta.getSelectionModel().getSelectedItem().equals("Ucesnik")) {
                        try {
                            oos.writeObject("Izbrisi#ucesnik");
                            oos.flush();
                            Ucesnik ucesnik = (Ucesnik) lista.getSelectionModel().getSelectedItem();
                            oos.writeObject(ucesnik);
                            oos.flush();
                            vratiSeNazad(t);
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLChangesController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (akcija.getSelectionModel().getSelectedItem().equals("Dodaj") && vrsta.getSelectionModel().getSelectedItem().equals("Predavac")) {
                        if (!ime.getText().isEmpty() && !prezime.getText().isEmpty()) {
                            try {
                                oos.writeObject("Dodaj#predavac#" + ime.getText() + "#" + prezime.getText() );
                                oos.flush();
                                if (ois.readBoolean()) {
                                    oos.writeObject(new Predavac(ime.getText(), prezime.getText()));
                                    oos.flush();
                                    vratiSeNazad(t);
                                }
                                else {
                                    Stage stage = new Stage();
                                    StackPane root = new StackPane();
                                    Label l = new Label("Predavac sa ovim podacima već postoji");
                                    root.getChildren().add(l);
                                    Scene scene = new Scene(root, 300, 250);
                                    stage.setTitle("Obavještenje!");
                                    stage.setScene(scene);
                                    stage.show();
                                }

                            } catch (IOException ex) {
                                Logger.getLogger(FXMLChangesController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else {
                            Stage stage = new Stage();
                            StackPane root = new StackPane();
                            Label l = new Label("Nisu unijeti svi podaci");
                            root.getChildren().add(l);
                            Scene scene = new Scene(root, 300, 250);
                            stage.setTitle("Obavještenje!");
                            stage.setScene(scene);
                            stage.show();


                        }
                    }
                    else if (akcija.getSelectionModel().getSelectedItem().equals("Izmijeni") && vrsta.getSelectionModel().getSelectedItem().equals("Predavac")) {
                        try {
                            oos.writeObject("Izmijeni#predavac");
                            oos.flush();
                            Predavac predavac = (Predavac) lista.getSelectionModel().getSelectedItem();
                            oos.writeObject(predavac);
                            oos.flush();

                            if (!ime.getText().isEmpty() && !prezime.getText().isEmpty()) {
                                oos.writeBoolean(true);
                                oos.flush();
                                oos.writeObject(new Predavac(ime.getText(), prezime.getText()));
                                oos.flush();
                                vratiSeNazad(t);
                            }
                            else {
                                oos.writeBoolean(false);
                                Stage stage = new Stage();
                                StackPane root = new StackPane();
                                Label l = new Label("Nisu unijeti svi podaci");
                                root.getChildren().add(l);
                                Scene scene = new Scene(root, 300, 250);
                                stage.setTitle("Obavještenje!");
                                stage.setScene(scene);
                                stage.show();
                            }

                        } catch (IOException ex) {
                            Logger.getLogger(FXMLChangesController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                    else if (akcija.getSelectionModel().getSelectedItem().equals("Izbrisi") && vrsta.getSelectionModel().getSelectedItem().equals("Predavac")) {
                        try {
                            oos.writeObject("Izbrisi#predavac");
                            oos.flush();
                            Predavac predavac = (Predavac) lista.getSelectionModel().getSelectedItem();
                            oos.writeObject(predavac);
                            oos.flush();
                            vratiSeNazad(t);
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLChangesController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                else {
                    vratiSeNazad(t);
                }

            }
        });

    }

    private ArrayList<Napomena> vratiNapomene(Organizator org, ArrayList<Napomena> n, MenuButton mb) {
        ArrayList<Napomena> pom = new ArrayList();
        ArrayList<Napomena> pom2 = new ArrayList();
        pom2.addAll(org.getNapomene());
        pom2.addAll(n);
                for(MenuItem item : mb.getItems()) {
                    CheckMenuItem checkMenuItem = (CheckMenuItem) item;
                    if(checkMenuItem.isSelected()) {
                        for(Napomena t:pom2){
                            if(t.toString().equals(checkMenuItem.getText()))
                                pom.add(t); 
                        }
                    }
         
        }
        return pom;

    }
    private boolean provjeraListe(MenuButton mb,String naziv){
         boolean pom=true;
         for(MenuItem item : mb.getItems()) {
                    CheckMenuItem checkMenuItem = (CheckMenuItem) item;
                    if(checkMenuItem.getText().equals(naziv)) {
                       pom=false;
                    }
          }

     return pom;
     }
    private void vratiSeNazad(ActionEvent t){
        try {
            Stage s = new Stage();
            Pane myPane = (Pane) FXMLLoader.load(getClass().getResource("/net/etfbl/projektni2017/FXMLNewEvent.fxml"));
            Scene myScene = new Scene(myPane);
            s.setScene(myScene);
            s.show();
            zatvori(t);
        } catch (IOException ex) {
            Logger.getLogger(FXMLChangesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void prikaz() {
        ime.clear();
        prezime.clear();
        org.clear();
        tel.clear();
        email.clear();
        napomena.clear();
        napomene.getItems().clear();
        n.clear();
        if (akcija.getSelectionModel().getSelectedItem().equals("Dodaj") && vrsta.getSelectionModel().getSelectedItem().equals("Organizator")) {
            ime.setVisible(true);
            prezime.setVisible(true);
            imeLab.setVisible(true);
            prezimeLab.setVisible(true);
            org.setVisible(false);
            orgLab.setVisible(false);
            tel.setVisible(true);
            telLab.setVisible(true);
            email.setVisible(true);
            eLab.setVisible(true);
            napomena.setVisible(true);
            napomene.setVisible(true);
            addButton.setVisible(true);
            napomeneLab.setVisible(true);
            okButton.setText("Dodaj");
            opisLab.setVisible(false);
            lista.setVisible(false);
            telLab.relocate(14, 124);
            imeLab.relocate(14, 84);
            tel.relocate(74, 120);
            ime.relocate(74, 80);

        } else if (akcija.getSelectionModel().getSelectedItem().equals("Izmijeni") && vrsta.getSelectionModel().getSelectedItem().equals("Organizator")) {
            ime.setVisible(true);
            prezime.setVisible(true);
            imeLab.setVisible(true);
            prezimeLab.setVisible(true);
            org.setVisible(false);
            orgLab.setVisible(false);
            tel.setVisible(true);
            telLab.setVisible(true);
            email.setVisible(true);
            eLab.setVisible(true);
            napomena.setVisible(true);
            napomene.setVisible(true);
            addButton.setVisible(true);
            napomeneLab.setVisible(true);
            okButton.setText("Izmijeni");
            opisLab.setVisible(true);
            opisLab.setText("Organizatori:");
            lista.setVisible(true);
            telLab.relocate(14, 166);
            imeLab.relocate(14, 124);
            opisLab.relocate(14, 84);
            tel.relocate(74, 162);
            ime.relocate(74, 120);
            lista.relocate(90, 80);
            lista.setItems(FXCollections.observableArrayList(o));
        } else if (akcija.getSelectionModel().getSelectedItem().equals("Izbrisi") && vrsta.getSelectionModel().getSelectedItem().equals("Organizator")) {
            ime.setVisible(false);
            imeLab.setVisible(false);
            prezime.setVisible(false);
            prezimeLab.setVisible(false);
            org.setVisible(false);
            orgLab.setVisible(false);
            tel.setVisible(false);
            telLab.setVisible(false);
            email.setVisible(false);
            eLab.setVisible(false);
            napomena.setVisible(false);
            napomene.setVisible(false);
            addButton.setVisible(false);
            napomeneLab.setVisible(false);
            okButton.setText("Izbrisi");
            opisLab.setVisible(true);
            opisLab.setText("Organizatori:");
            lista.setVisible(true);
            opisLab.relocate(14, 84);
            lista.relocate(90, 80);
            lista.setItems(FXCollections.observableArrayList(o));
        } else if (akcija.getSelectionModel().getSelectedItem().equals("Dodaj") && vrsta.getSelectionModel().getSelectedItem().equals("Predavac")) {
            ime.setVisible(true);
            prezime.setVisible(true);
            imeLab.setVisible(true);
            prezimeLab.setVisible(true);
            org.setVisible(false);
            orgLab.setVisible(false);
            tel.setVisible(false);
            telLab.setVisible(false);
            email.setVisible(false);
            eLab.setVisible(false);
            napomena.setVisible(false);
            napomene.setVisible(false);
            addButton.setVisible(false);
            napomeneLab.setVisible(false);
            okButton.setText("Dodaj");
            opisLab.setVisible(false);
            lista.setVisible(false);
            telLab.relocate(14, 124);
            imeLab.relocate(14, 84);
            tel.relocate(74, 120);
            ime.relocate(74, 80);

        } else if (akcija.getSelectionModel().getSelectedItem().equals("Izmijeni") && vrsta.getSelectionModel().getSelectedItem().equals("Predavac")) {
            ime.setVisible(true);
            prezime.setVisible(true);
            imeLab.setVisible(true);
            prezimeLab.setVisible(true);
            org.setVisible(false);
            orgLab.setVisible(false);
            tel.setVisible(false);
            telLab.setVisible(false);
            email.setVisible(false);
            eLab.setVisible(false);
            napomena.setVisible(false);
            napomene.setVisible(false);
            addButton.setVisible(false);
            napomeneLab.setVisible(false);
            okButton.setText("Izmijeni");
            opisLab.setVisible(true);
            opisLab.setText("Predavaci:");
            lista.setVisible(true);
            telLab.relocate(14, 166);
            imeLab.relocate(14, 124);
            opisLab.relocate(14, 84);
            tel.relocate(74, 162);
            ime.relocate(74, 120);
            lista.relocate(74, 80);
            lista.setItems(FXCollections.observableArrayList(p));

        } else if (akcija.getSelectionModel().getSelectedItem().equals("Izbrisi") && vrsta.getSelectionModel().getSelectedItem().equals("Predavac")) {
            ime.setVisible(false);
            imeLab.setVisible(false);
            prezime.setVisible(false);
            prezimeLab.setVisible(false);
            org.setVisible(false);
            orgLab.setVisible(false);
            tel.setVisible(false);
            telLab.setVisible(false);
            email.setVisible(false);
            eLab.setVisible(false);
            napomena.setVisible(false);
            napomene.setVisible(false);
            addButton.setVisible(false);
            napomeneLab.setVisible(false);
            okButton.setText("Izbrisi");
            opisLab.setVisible(true);
            opisLab.setText("Predavaci:");
            lista.setVisible(true);
            opisLab.relocate(14, 84);
            lista.relocate(74, 80);
            lista.setItems(FXCollections.observableArrayList(p));
        } else if (akcija.getSelectionModel().getSelectedItem().equals("Izbrisi") && vrsta.getSelectionModel().getSelectedItem().equals("Ucesnik")) {
            ime.setVisible(false);
            imeLab.setVisible(false);
            prezime.setVisible(false);
            prezimeLab.setVisible(false);
            org.setVisible(false);
            orgLab.setVisible(false);
            tel.setVisible(false);
            telLab.setVisible(false);
            email.setVisible(false);
            eLab.setVisible(false);
            napomena.setVisible(false);
            napomene.setVisible(false);
            addButton.setVisible(false);
            napomeneLab.setVisible(false);
            okButton.setText("Izbrisi");
            opisLab.setVisible(true);
            opisLab.setText("Ucesnici:");
            lista.setVisible(true);
            opisLab.relocate(14, 84);
            lista.relocate(74, 80);
            lista.setItems(FXCollections.observableArrayList(u));
        } else if (akcija.getSelectionModel().getSelectedItem().equals("Dodaj") && vrsta.getSelectionModel().getSelectedItem().equals("Ucesnik")) {
            ime.setVisible(true);
            prezime.setVisible(true);
            imeLab.setVisible(true);
            prezimeLab.setVisible(true);
            org.setVisible(true);
            orgLab.setVisible(true);
            tel.setVisible(false);
            telLab.setVisible(false);
            email.setVisible(false);
            eLab.setVisible(false);
            napomena.setVisible(false);
            napomene.setVisible(false);
            addButton.setVisible(false);
            napomeneLab.setVisible(false);
            okButton.setText("Dodaj");
            opisLab.setVisible(false);
            lista.setVisible(false);
            imeLab.relocate(14, 84);
            ime.relocate(74, 80);
            orgLab.relocate(300, 124);
            org.relocate(387, 120);
        } else if (akcija.getSelectionModel().getSelectedItem().equals("Izmijeni") && vrsta.getSelectionModel().getSelectedItem().equals("Ucesnik")) {
            ime.setVisible(true);
            prezime.setVisible(true);
            imeLab.setVisible(true);
            prezimeLab.setVisible(true);
            org.setVisible(true);
            orgLab.setVisible(true);
            tel.setVisible(false);
            telLab.setVisible(false);
            email.setVisible(false);
            eLab.setVisible(false);
            napomena.setVisible(false);
            napomene.setVisible(false);
            addButton.setVisible(false);
            napomeneLab.setVisible(false);
            okButton.setText("Izmijeni");
            opisLab.setVisible(true);
            opisLab.setText("Ucesnici:");
            lista.setVisible(true);
            telLab.relocate(14, 166);
            imeLab.relocate(14, 124);
            opisLab.relocate(14, 84);
            tel.relocate(74, 162);
            ime.relocate(74, 120);
            lista.relocate(74, 80);
            orgLab.relocate(300, 124);
            org.relocate(387, 120);
            lista.setItems(FXCollections.observableArrayList(u));
        }

    }

}
