package com.example.joyerialu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ProveedoresController {

    @FXML
    private Button btn_Regresar;

    @FXML
    private Button btn_AltaProveedor;

    @FXML
    private Button btn_BajaProveedor;

    @FXML
    private Button btn_ModificarProveedor;

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

    public void abrirAltaProveedores(){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("alta_proveedor.fxml"));
            Parent root = fxmlLoader.load();
            AltaProveedorController controlador = fxmlLoader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.show();

            Stage myStage = (Stage) this.btn_AltaProveedor.getScene().getWindow();
            myStage.close();

        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());

        }
    }

    public void abrirBajaProveedores(){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("baja_proveedor.fxml"));
            Parent root = fxmlLoader.load();
            BajaProveedorController controlador = fxmlLoader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.show();

            Stage myStage = (Stage) this.btn_BajaProveedor.getScene().getWindow();
            myStage.close();

        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());

        }
    }

    public void abrirModificarProveedores(){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("modificar_proveedores.fxml"));
            Parent root = fxmlLoader.load();
            ModificarProveedorController controlador = fxmlLoader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.show();

            Stage myStage = (Stage) this.btn_ModificarProveedor.getScene().getWindow();
            myStage.close();

        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());

        }
    }
}
