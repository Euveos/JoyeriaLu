package com.example.joyerialu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AccesoController {

    @FXML
    private TextField txt_usuario;

    @FXML
    private PasswordField txt_pswrd;

    @FXML
    private Button btn_ingresar;

    @FXML
    void acceso(ActionEvent event) throws IOException {
            try {
                if (txt_usuario.getText().equals("acc") && txt_pswrd.getText().equals("emp")) {

                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("inicio.fxml"));
                    Parent root = fxmlLoader.load();
                    InicioController controlador = fxmlLoader.getController();

                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                    stage.setOnCloseRequest(e -> controlador.cerrarMenu());

                    Stage myStage = (Stage) this.btn_ingresar.getScene().getWindow();
                    myStage.close();
                }
            }
            catch (IOException ioe){
                System.out.println(ioe.getMessage());
            }

    }

}
