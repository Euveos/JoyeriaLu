package com.example.joyerialu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ProveedoresController implements Initializable, Serializable {

    @FXML
    private Button btn_Regresar;

    @FXML
    private Button btn_AltaProveedor;

    @FXML
    private Button btn_BajaProveedor;

    @FXML
    private Button btn_ModificarProveedor;

    @FXML
    private TableView<ObjetoProveedores> tv_Proveedores;

    @FXML
    private TextField tf_ID;

    @FXML
    private TextField tf_Nombre;

    @FXML
    private TextField tf_Telefono;

    @FXML
    private TextField tf_Direccion;

    @FXML
    private TableColumn<ObjetoProveedores,Integer> tc_ID;

    @FXML
    private TableColumn<ObjetoProveedores,String> tc_Nombre;

    @FXML
    private TableColumn<ObjetoProveedores,String> tc_Telefono;

    @FXML
    private TableColumn<ObjetoProveedores,String> tc_Direccion;


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

    public void limpiar(){
        tf_ID.clear();
        tf_Nombre.clear();
        tf_Telefono.clear();
        tf_Direccion.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        mostrarProveedores();
    }

    public ObservableList<ObjetoProveedores> getProveedoresList(){
        ObservableList<ObjetoProveedores> listaProveedores = FXCollections.observableArrayList();
        Connection conn = DbConnect.getConnection();
        String query="SELECT * FROM proveedores";
        Statement st;
        ResultSet rs;

        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            ObjetoProveedores proveedores;
            while(rs.next()){
                proveedores = new ObjetoProveedores(rs.getInt("IdProveedor"), rs.getString("Nombre"),
                        rs.getString("Telefono"),rs.getString("Direccion"));
                listaProveedores.add(proveedores);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaProveedores;
    }

    public void mostrarProveedores(){
        ObservableList<ObjetoProveedores> lista = getProveedoresList();

        tc_ID.setCellValueFactory(new PropertyValueFactory<ObjetoProveedores,Integer>("id"));
        tc_Nombre.setCellValueFactory(new PropertyValueFactory<ObjetoProveedores,String>("nombre"));
        tc_Telefono.setCellValueFactory(new PropertyValueFactory<ObjetoProveedores,String>("telefono"));
        tc_Direccion.setCellValueFactory(new PropertyValueFactory<ObjetoProveedores,String>("direccion"));

        tv_Proveedores.setItems(lista);
    }

    @FXML
    private void altaProveedores(ActionEvent event){
        String query = "INSERT INTO proveedores VALUES ("+tf_ID.getText()+", '"+tf_Nombre.getText()+"','"+tf_Direccion.getText()+"','"+tf_Telefono.getText()+"')";
        executeQuery(query);
        mostrarProveedores();
        limpiar();
    }

    @FXML
    private void modificarProveedores(ActionEvent event){
        String query = "UPDATE proveedores SET IdProveedor = "+tf_ID.getText()+", Nombre = '"+tf_Nombre.getText()+
                "', Direccion = '"+tf_Direccion.getText()+"', Telefono = '"+tf_Telefono.getText()+"'";
        executeQuery(query);
        mostrarProveedores();
        limpiar();
    }

    @FXML
    private void bajaProveedores(ActionEvent event){
        String query = "DELETE FROM proveedores WHERE IdProveedor= "+tf_ID.getText()+"";
        executeQuery(query);
        mostrarProveedores();
        limpiar();
    }

    @FXML
    private void seleccionRegistro(MouseEvent event){
        ObjetoProveedores proveedores = tv_Proveedores.getSelectionModel().getSelectedItem();
        tf_ID.setText(""+proveedores.getId());
        tf_Nombre.setText(proveedores.getNombre());
        tf_Direccion.setText(proveedores.getDireccion());
        tf_Telefono.setText(proveedores.getTelefono());
    }

    private void executeQuery(String query){
        Connection conn = DbConnect.getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch (Exception e){
            generarAlerta("Error",e.getMessage());
        }
    }

    private void generarAlerta(String razon, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setHeaderText(null);
        alerta.setTitle(razon);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
