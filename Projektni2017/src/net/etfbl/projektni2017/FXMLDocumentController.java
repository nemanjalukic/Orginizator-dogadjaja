/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.etfbl.projektni2017;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Office
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField UserName;

    @FXML
    private Label LabelUsername;

    @FXML
    private Button CancelButton;

    @FXML
    private Button OKButton;

    @FXML
    private Label PasswordLabel;

    @FXML
    private Label NotifyLabel;

    @FXML
    private PasswordField Password;
    private String userName = "";
    private String password = "";
    public static final int TCP_PORT = 9000;
    public static ObjectOutputStream oos;
    public static ObjectInputStream ois;
    public static Socket sock;
    public static Socket sock2;
    public static ObjectInputStream ois2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        OKButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                userName = UserName.getText();
                password = Password.getText();
                if (userName.equals("")) {
                    userName = " ";
                }
                if (password.equals("")) {
                    password = " ";
                }
                UserName.clear();
                Password.clear();
                try {
                    InetAddress addr = InetAddress.getByName("127.0.0.1");

                    sock = new Socket(addr, TCP_PORT);
                    sock2 = new Socket(addr, TCP_PORT+2);
                    oos = new ObjectOutputStream(sock.getOutputStream());
                    ois = new ObjectInputStream(sock.getInputStream());
                    ois2 = new ObjectInputStream(sock2.getInputStream());

                    oos.writeObject(userName + "#" + password);
                    oos.flush();

                    String check = (String) ois.readObject();
                    if ("OK".equals(check)) {
                        NotifyLabel.setText("");
                        try {
                            Stage s = new Stage();
                            Pane myPane = (Pane) FXMLLoader.load(getClass().getResource("/net/etfbl/projektni2017/FXMLData.fxml"));
                            Scene myScene = new Scene(myPane);
                            s.setScene(myScene);
                            s.show();
                                   s.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                @Override
                                public void handle(WindowEvent event) {
                                    try {
                                        oos.writeObject("END");
                                        oos.flush();
                                        oos.close();
                                        ois.close();
                                        sock.close();
                                        System.exit(0);
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });
                            Node source = (Node) t.getSource();
                            Stage stage = (Stage) source.getScene().getWindow();
                            stage.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        NotifyLabel.setText(check);
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        CancelButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });

    }

}
