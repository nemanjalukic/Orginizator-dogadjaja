/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.etfbl.projektni2017;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.etfbl.dogadjaj.Dogadjaj;
import net.etfbl.dogadjaj.Izlozba;
import net.etfbl.dogadjaj.Koncert;
import net.etfbl.dogadjaj.Predavanje;
import net.etfbl.dogadjaj.Promocija;
import net.etfbl.korisnici.Organizator;
import net.etfbl.korisnici.Predavac;
import net.etfbl.korisnici.Ucesnik;
import static net.etfbl.projektni2017.FXMLDocumentController.ois;
import static net.etfbl.projektni2017.FXMLDocumentController.oos;

/**
 * FXML Controller class
 *
 * @author Office
 */
public class FXMLNewEventController implements Initializable {
    
 @FXML
    private Label labTema;

    @FXML
    private Label labSadrzaj;

    @FXML
    private ChoiceBox<String> vrstaChoice;

    @FXML
    private Label labTrajanje;

    @FXML
    private Label labPredavac;

    @FXML
    private ChoiceBox<Organizator> organizatorChoice;

    @FXML
    private MenuButton autoriMenu;

    @FXML
    private Label labIzvodjaci;

    @FXML
    private TextField TimeDo;

    @FXML
    private DatePicker DateOd;

    @FXML
    private TextField trajanje;

    @FXML
    private TextField kompanijaProizvod;

    @FXML
    private MenuButton dogadjajiMenu;

    @FXML
    private Button changeButton;

    @FXML
    private Button dodajButton;

    @FXML
    private TextField naziv;

    @FXML
    private Label labDogadjaji;

    @FXML
    private Button fajlButton;

    @FXML
    private TextField sadrzaj;

    @FXML
    private DatePicker DateDo;

    @FXML
    private MenuButton izvodjaciMenu;

    @FXML
    private ChoiceBox<Predavac> predavacChoice;

    @FXML
    private TextField TimeOd;

    @FXML
    private TextField tema;

    @FXML
    private Label labAutori;

    @FXML
    private MenuButton ucesniciMenu;

    @FXML
    private TextField opis;

    @FXML
    private Label labKompanijaProizvod;
   
    
    private ArrayList<Dogadjaj> d = new ArrayList<>();
    private ArrayList<Organizator> o = new ArrayList<>();
    private ArrayList<Ucesnik> u = new ArrayList<>();
    private ArrayList<Predavac> p = new ArrayList<>();
    private ArrayList<String> a= new ArrayList<>();
    private ArrayList<String> i= new ArrayList<>();
    private FileChooser fileChooser = new FileChooser();
    private File dodaniFajl=null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vrstaChoice.setItems(FXCollections.observableArrayList("Koncert", "Promocija", "Predavanje", "Izlozba", "Ostalo"));
//        organizatorChoice.setItems(FXCollections.observableArrayList("nulti", "prvi", "drugi", "treci", "neko"));   
         
        //ucesniciMenu.getItems().add(new CheckMenuItem("nesto"));
        //ucesniciMenu.getItems().add(new CheckMenuItem("nesto"));
        
          vrstaChoice.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                
                
                    if(vrstaChoice.getSelectionModel().getSelectedItem().equals("Koncert")){
                        izvodjaciMenu.setVisible(true);
                        labIzvodjaci.setVisible(true);
                        trajanje.setVisible(true);
                        labTrajanje.setVisible(true);
                        sadrzaj.setVisible(false);
                        labSadrzaj.setVisible(false);
                        kompanijaProizvod.setVisible(false);
                        labKompanijaProizvod.setVisible(false);
                        predavacChoice.setVisible(false);
                        labPredavac.setVisible(false);
                        autoriMenu.setVisible(false);
                        labAutori.setVisible(false);
                        tema.setVisible(false);
                        labTema.setVisible(false);
                        izvodjaciMenu.relocate(168, 254);
                        labIzvodjaci.relocate(27, 258);
                        trajanje.relocate(404, 254);
                        labTrajanje.relocate(333, 258);
                        dogadjajiMenu.relocate(167,293);
                        labDogadjaji.relocate(27, 297);
                    }
                    else if(vrstaChoice.getSelectionModel().getSelectedItem().equals("Promocija"))
                    {
                        sadrzaj.setVisible(false);
                        labSadrzaj.setVisible(false);
                        kompanijaProizvod.setVisible(true);
                        labKompanijaProizvod.setVisible(true);
                        predavacChoice.setVisible(false);
                        labPredavac.setVisible(false);
                        autoriMenu.setVisible(false);
                        labAutori.setVisible(false);
                        tema.setVisible(false);
                        labTema.setVisible(false);
                        izvodjaciMenu.setVisible(false);
                        labIzvodjaci.setVisible(false);
                        trajanje.setVisible(false);
                        labTrajanje.setVisible(false);
                        dogadjajiMenu.relocate(403,254);
                        labDogadjaji.relocate(333, 258);
                    }
                    else if(vrstaChoice.getSelectionModel().getSelectedItem().equals("Predavanje"))
                    {
                        sadrzaj.setVisible(true);
                        labSadrzaj.setVisible(true);
                        kompanijaProizvod.setVisible(false);
                        labKompanijaProizvod.setVisible(false);
                        predavacChoice.setVisible(true);
                        labPredavac.setVisible(true);
                        autoriMenu.setVisible(false);
                        labAutori.setVisible(false);
                        tema.setVisible(true);
                        labTema.setVisible(true);
                        izvodjaciMenu.setVisible(false);
                        labIzvodjaci.setVisible(false);
                        trajanje.setVisible(false);
                        labTrajanje.setVisible(false);
                        dogadjajiMenu.relocate(167,293);
                        labDogadjaji.relocate(27, 297);
                        labPredavac.relocate(27, 258);
                        predavacChoice.relocate(168, 254);
                    }
                    else if(vrstaChoice.getSelectionModel().getSelectedItem().equals("Izlozba"))
                    {
                        sadrzaj.setVisible(false);
                        labSadrzaj.setVisible(false);
                        kompanijaProizvod.setVisible(false);
                        labKompanijaProizvod.setVisible(false);
                        predavacChoice.setVisible(false);
                        labPredavac.setVisible(false);
                        autoriMenu.setVisible(true);
                        labAutori.setVisible(true);
                        tema.setVisible(true);
                        labTema.setVisible(true);
                        izvodjaciMenu.setVisible(false);
                        labIzvodjaci.setVisible(false);
                        trajanje.setVisible(false);
                        labTrajanje.setVisible(false);
                        dogadjajiMenu.relocate(167,293);
                        labDogadjaji.relocate(27, 297);
                        labAutori.relocate(27, 258);
                        autoriMenu.relocate(168, 254);
                    }
                    else if(vrstaChoice.getSelectionModel().getSelectedItem().equals("Ostalo"))
                    {
                        sadrzaj.setVisible(false);
                        labSadrzaj.setVisible(false);
                        kompanijaProizvod.setVisible(false);
                        labKompanijaProizvod.setVisible(false);
                        predavacChoice.setVisible(false);
                        labPredavac.setVisible(false);
                        autoriMenu.setVisible(false);
                        labAutori.setVisible(false);
                        tema.setVisible(false);
                        labTema.setVisible(false);
                        izvodjaciMenu.setVisible(false);
                        labIzvodjaci.setVisible(false);
                        trajanje.setVisible(false);
                        labTrajanje.setVisible(false);
                        dogadjajiMenu.relocate(168, 254);
                        labDogadjaji.relocate(27, 258);
                    }
                
                try {    
                    oos.writeObject("podaci");
                    oos.flush();
                    d=(ArrayList<Dogadjaj>) ois.readObject();
                    o=(ArrayList<Organizator>) ois.readObject();
                    u=(ArrayList<Ucesnik>) ois.readObject();
                    p=( ArrayList<Predavac>) ois.readObject();
                    a=(ArrayList<String>) ois.readObject();
                    i=(ArrayList<String>) ois.readObject();
                    
                    organizatorChoice.setItems(FXCollections.observableArrayList(o));
                    predavacChoice.setItems(FXCollections.observableArrayList(p));
                    ucesniciMenu.getItems().clear();
                    for(Ucesnik uc:u)
                    {
                        ucesniciMenu.getItems().add(new CheckMenuItem(uc.toString()));
                    }
                     autoriMenu.getItems().clear();
                     for(String au:a)
                    {
                        autoriMenu.getItems().add(new CheckMenuItem(au));
                    }
                     izvodjaciMenu.getItems().clear();
                     for(String iz:i)
                    {
                        izvodjaciMenu.getItems().add(new CheckMenuItem(iz));
                    }
                      dogadjajiMenu.getItems().clear();
                    for(Dogadjaj dog:d)
                    {
                        dogadjajiMenu.getItems().add(new CheckMenuItem(dog.getNaziv()));
                    }
                    
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(FXMLNewEventController.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            }
            });
          
          fajlButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    Stage s = new Stage();
                    File file = fileChooser.showOpenDialog(s);
                    if (file != null) {
                        dodaniFajl=file;
                    }
                }
            });
          dodajButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                
                if(vrstaChoice.getSelectionModel().getSelectedItem()!= null && organizatorChoice.getSelectionModel().getSelectedItem()!= null && !naziv.getText().equals("") && DateDo.getValue()!=null && DateOd.getValue()!=null && provjeraVremena(TimeOd.getText()) && provjeraVremena(TimeDo.getText()) && !opis.getText().equals("") && provjeraListe(ucesniciMenu))
                 {
                     try {
                         oos.writeObject("novidogadjaj");
                         oos.flush();
                         oos.writeObject(naziv.getText());
                         oos.flush();
                         String poruka= (String) ois.readObject();

                         
                         if(poruka.equals("ok")){
                             
                         if(vrstaChoice.getSelectionModel().getSelectedItem().equals("Koncert") && provjeraListe(izvodjaciMenu) && provjeraVremena(trajanje.getText())){
                             oos.writeObject("koncert");
                             oos.flush();
                            
                             Koncert kon=new Koncert(vratiListu(izvodjaciMenu) ,vratiVrijeme(trajanje.getText()),naziv.getText(),vratiKalendar(DateOd,TimeOd.getText()),vratiKalendar(DateOd,TimeDo.getText()),opis.getText(),organizatorChoice.getValue(),vratiListu(ucesniciMenu,u),vratiListu(dogadjajiMenu,d),dodaniFajl);                            
                             oos.writeObject(kon);
                             oos.flush();
                             zatvori(t);
                             
                         }
                         else if(vrstaChoice.getSelectionModel().getSelectedItem().equals("Promocija") && !kompanijaProizvod.getText().equals("") ){
                             oos.writeObject("promocija");
                             oos.flush();
                             Promocija prom= new Promocija(kompanijaProizvod.getText(),naziv.getText(),vratiKalendar(DateOd,TimeOd.getText()),vratiKalendar(DateOd,TimeDo.getText()),opis.getText(),organizatorChoice.getValue(),vratiListu(ucesniciMenu,u),vratiListu(dogadjajiMenu,d),dodaniFajl);
                             oos.writeObject(prom);
                             oos.flush();
                             zatvori(t);
                         }
                         else if(vrstaChoice.getSelectionModel().getSelectedItem().equals("Predavanje") && !tema.getText().equals("") && !sadrzaj.getText().equals("") && predavacChoice.getSelectionModel().getSelectedItem()!=null){
                             oos.writeObject("predavanje");
                             oos.flush();
                             Predavanje pred= new Predavanje(tema.getText(),predavacChoice.getSelectionModel().getSelectedItem(),sadrzaj.getText(),naziv.getText(),vratiKalendar(DateOd,TimeOd.getText()),vratiKalendar(DateOd,TimeDo.getText()),opis.getText(),organizatorChoice.getValue(),vratiListu(ucesniciMenu,u),vratiListu(dogadjajiMenu,d),dodaniFajl);
                             oos.writeObject(pred);
                             oos.flush();

                             zatvori(t);
                         }
                         else if(vrstaChoice.getSelectionModel().getSelectedItem().equals("Izlozba") && !tema.getText().equals("")  && provjeraListe(autoriMenu)){
                             oos.writeObject("izlozba");
                             oos.flush();
                             Izlozba izl=new Izlozba(tema.getText(),vratiListu(autoriMenu),naziv.getText(),vratiKalendar(DateOd,TimeOd.getText()),vratiKalendar(DateOd,TimeDo.getText()),opis.getText(),organizatorChoice.getValue(),vratiListu(ucesniciMenu,u),vratiListu(dogadjajiMenu,d),dodaniFajl);
                             oos.writeObject(izl);
                             oos.flush();
                             zatvori(t);
                         }
                         else if(vrstaChoice.getSelectionModel().getSelectedItem().equals("Ostalo")){
                             oos.writeObject("ostalo");
                             oos.flush();
                             Dogadjaj dog=new Dogadjaj(naziv.getText(),vratiKalendar(DateOd,TimeOd.getText()),vratiKalendar(DateOd,TimeDo.getText()),opis.getText(),organizatorChoice.getValue(),vratiListu(ucesniciMenu,u),vratiListu(dogadjajiMenu,d),dodaniFajl);
                             oos.writeObject(dog);
                             oos.flush();

                             zatvori(t);
                         }
                         else{
                            oos.writeObject("nešto");
                            oos.flush();
                             Stage stage = new Stage();
                            StackPane root = new StackPane();
                            Label l = new Label("Greška u unosu podataka");
                            root.getChildren().add(l);
                            Scene scene = new Scene(root, 300, 250);
                            stage.setTitle("Obavještenje!");
                            stage.setScene(scene);
                            stage.show();
                             
                         }
                        }
                        else{
                            Stage stage = new Stage();
                            StackPane root = new StackPane();
                            Label l = new Label(poruka);
                            root.getChildren().add(l);
                            Scene scene = new Scene(root, 300, 250);
                            stage.setTitle("Obavještenje!");
                            stage.setScene(scene);
                            stage.show();
                         }
                     } catch (IOException | ClassNotFoundException ex) {
                         Logger.getLogger(FXMLNewEventController.class.getName()).log(Level.SEVERE, null, ex);
                     }
            }
            else
                 {
                      Stage stage = new Stage();
                            StackPane root = new StackPane();
                            Label l = new Label("Podaci nisu unijeti ili su uneseni pogrešno");
                            root.getChildren().add(l);
                            Scene scene = new Scene(root, 300, 250);
                            stage.setTitle("Obavještenje!");
                            stage.setScene(scene);
                            stage.show();
                 }
            
            
            }
            });
          changeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try {
                    oos.writeObject("dodajpodaci");
                    oos.flush();
                    Stage s = new Stage();
                    Pane myPane = (Pane) FXMLLoader.load(getClass().getResource("/net/etfbl/projektni2017/FXMLChanges.fxml"));
                    Scene myScene = new Scene(myPane);
                    s.setScene(myScene);
                    s.show();
                    zatvori(t);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLNewEventController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }
          });

         
    }    
    private boolean provjeraListe(MenuButton mb){
         int br=0;
         for(MenuItem item : mb.getItems()) {
                    CheckMenuItem checkMenuItem = (CheckMenuItem) item;
                    if(checkMenuItem.isSelected()) {
                       br++;
                    }  
          }

     return br>0;
     }
     
   private boolean provjeraVremena(String time){
         if(time.contains(":")){
             String [] st=new String[2];
             st = time.split(":");
             return (Integer.parseInt(st[0])) < 24 && (Integer.parseInt(st[1]) <60);
         }
         else
         {
             return false;
         }
     }
     
    private LocalTime vratiVrijeme(String time){
         String [] st=new String[2];
         st = time.split(":");
         LocalTime lt=LocalTime.of(Integer.parseInt(st[0]),Integer.parseInt(st[1]));
         return lt;
         
     }
    private ArrayList<String> vratiListu(MenuButton mb){
          ArrayList<String> pom=new ArrayList();
     
         for(MenuItem item : mb.getItems()) {
                    CheckMenuItem checkMenuItem = (CheckMenuItem) item;
                    if(checkMenuItem.isSelected()) {
                        pom.add(checkMenuItem.getText());
                    }
         }
         return pom;
     }
     public static <T> ArrayList<T> vratiListu(MenuButton mb, ArrayList<T> list){
          ArrayList<T> pom=new ArrayList();
               for(MenuItem item : mb.getItems()) {
                    CheckMenuItem checkMenuItem = (CheckMenuItem) item;
                    if(checkMenuItem.isSelected()) {
                        for(T t:list){
                            if(t.toString().equals(checkMenuItem.getText()))
                                pom.add(t); 
                        }
                    }
         }
         return pom;
     }
     
    public static void zatvori(ActionEvent t){
          Node source = (Node) t.getSource();
                            Stage stage = (Stage) source.getScene().getWindow();
                            stage.close();
     }
     
    private GregorianCalendar vratiKalendar(DatePicker dp, String time){
            LocalDate ld= dp.getValue();
            LocalTime lt=vratiVrijeme(time);
         return new GregorianCalendar(ld.getYear(),ld.getMonthValue()-1,ld.getDayOfMonth(),lt.getHour(),lt.getMinute());
     }
     
}

