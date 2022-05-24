package com.example.joyerialu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioController {

    @FXML
    private Button btn_Abrir_Ventas;

    @FXML
    private Button btn_Abrir_Almacen;

    @FXML
    private Button btn_Abrir_Empleados;

    @FXML
    private Button btn_Abrir_Proveedores;

    @FXML
    private Button btn_Salir;

    public void cerrarMenu(){
        try {

                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("acceso.fxml"));
                Parent root = fxmlLoader.load();
                AccesoController controlador = fxmlLoader.getController();

                Scene scene = new Scene(root);
                Stage stage = new Stage();

                stage.setScene(scene);
                stage.show();

                Stage myStage = (Stage) this.btn_Salir.getScene().getWindow();
                myStage.close();

        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }

    public void abrir_Ventas(){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ventas.fxml"));
            Parent root = fxmlLoader.load();
            VentasController controlador = fxmlLoader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.show();

            Stage myStage = (Stage) this.btn_Abrir_Ventas.getScene().getWindow();
            myStage.close();

        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }

    public void abrir_Almacen(){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("almacen.fxml"));
            Parent root = fxmlLoader.load();
            AlmacenController controlador = fxmlLoader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.show();

            Stage myStage = (Stage) this.btn_Abrir_Almacen.getScene().getWindow();
            myStage.close();

        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }

    public void abrir_Empleados(){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("empleados.fxml"));
            Parent root = fxmlLoader.load();
            EmpleadosController controlador = fxmlLoader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.show();

            Stage myStage = (Stage) this.btn_Abrir_Empleados.getScene().getWindow();
            myStage.close();

        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }

    public void abrir_Proveedores(){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("proveedores.fxml"));
            Parent root = fxmlLoader.load();
            ProveedoresController controlador = fxmlLoader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.show();

            Stage myStage = (Stage) this.btn_Abrir_Proveedores.getScene().getWindow();
            myStage.close();

        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }


}
