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

    public void abrirAltaProductos(){
            try {

                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("alta_productos.fxml"));
                Parent root = fxmlLoader.load();
                AltaProductosController controlador = fxmlLoader.getController();

                Scene scene = new Scene(root);
                Stage stage = new Stage();

                stage.setScene(scene);
                stage.show();

                Stage myStage = (Stage) this.btn_AltaProductos.getScene().getWindow();
                myStage.close();

            }
            catch (IOException ioe){
                System.out.println(ioe.getMessage());

        }
    }

    public void abrirBajaProductos(){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("baja_producto.fxml"));
            Parent root = fxmlLoader.load();
            BajaProductosController controlador = fxmlLoader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.show();

            Stage myStage = (Stage) this.btn_BajaProductos.getScene().getWindow();
            myStage.close();

        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());

        }
    }

    public void abrirModificarProductos(){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("modificar_productos.fxml"));
            Parent root = fxmlLoader.load();
            ModificarProductosController controlador = fxmlLoader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.show();

            Stage myStage = (Stage) this.btn_ModificarProductos.getScene().getWindow();
            myStage.close();

        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());

        }
    }

}
