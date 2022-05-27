package com.example.joyerialu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AlmacenController {

    @FXML
    private Button btn_BajaProductos;

    @FXML
    private Button btn_AltaProductos;

    @FXML
    private Button btn_Regresar;

    @FXML
    private Button btn_ModificarProductos;

    public void regresar(){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("inicio.fxml"));
            Parent root = fxmlLoader.load();
            InicioController controlador = fxmlLoader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.show();

            Stage myStage = (Stage) this.btn_Regresar.getScene().getWindow();
            myStage.close();

        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }


}
