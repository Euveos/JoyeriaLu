package com.example.joyerialu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EmpleadosController {

    @FXML
    private Button btn_Regresar;

    @FXML
    private Button btn_AltaEmpleados;

    @FXML
    private Button btn_BajaEmpleados;

    @FXML
    private Button btn_ModificarEmpleados;

    @FXML
    private TableColumn<ObjetoEmpleados,int> tc_ID;

    @FXML
    private TableColumn<ObjetoEmpleados,String> tc_nombres;

    @FXML
    private TableColumn<ObjetoEmpleados,String> tc_ApPat;

    @FXML
    private TableColumn<ObjetoEmpleados,String> tc_ApMat;

    @FXML
    private TableColumn<ObjetoEmpleados,String> tc_FecNac;

    @FXML
    private TableColumn<ObjetoEmpleados,String> tc_Telefono;

    @FXML
    private TableColumn<ObjetoEmpleados,String> tc_Correo;

    @FXML
    private TableColumn<ObjetoEmpleados,String> tc_RFC;

    @FXML
    private TableColumn<ObjetoEmpleados,String> tc_Direccion;

    @FXML
    private TableColumn<ObjetoEmpleados,String> tc_FecReg;

    @FXML
    private TextField tf_ID;

    @FXML
    private TextField tf_nombres;

    @FXML
    private TextField tf_ApPat;

    @FXML
    private TextField tf_ApMat;

    @FXML
    private TextField tf_FecNac;

    @FXML
    private TextField tf_Telefono;

    @FXML
    private TextField tf_Correo;

    @FXML
    private TextField tf_RFC;

    @FXML
    private TextField tf_Direccion;

    @FXML
    private TextField tf_FecReg;

    @FXML
    private TableView tv_empleados;

    private void accionAlta(ActionEvent event){
        if(event.getSource()==btn_AltaEmpleados){
            altaEmpleados();
        }
    }

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

    public void initialize(URL url, ResourceBundle rb){
        mostrarEmpleados();
    }

    public Connection getConnection(){
        Connection conn;
        try{
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/joyeria","root","");
            return conn;
        } catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
            return null;
        }
    }

    public ObservableList<ObjetoEmpleados> getEmpleadosList(){
        ObservableList<ObjetoEmpleados> listaEmpleados = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query="SELECT * FROM empleados";
        Statement st;
        ResultSet rs;

        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            ObjetoEmpleados empleados;
            while(rs.next()){
                empleados = new ObjetoEmpleados(rs.getInt("CodigoEmpleado"), rs.getString("Nombre"),
                        rs.getString("PrimerApellido"),rs.getString("ApellidoMaterno"),rs.getString("FechaNac"),
                        rs.getString("Telefono"),rs.getString("CorreoE"),rs.getString("RFC"),rs.getString("Direccion"),
                        rs.getString("FechaRegistro"));
                listaEmpleados.add(empleados);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaEmpleados;
    }

    public void mostrarEmpleados(){
        ObservableList<ObjetoEmpleados> lista = getEmpleadosList();

        tc_ID.setCellValueFactory(new PropertyValueFactory<ObjetoEmpleados,int>("id"));
        tc_nombres.setCellValueFactory(new PropertyValueFactory<ObjetoEmpleados,String>("nombres"));
        tc_ApPat.setCellValueFactory(new PropertyValueFactory<ObjetoEmpleados,String>("appat"));
        tc_ApMat.setCellValueFactory(new PropertyValueFactory<ObjetoEmpleados,String>("apmat"));
        tc_FecNac.setCellValueFactory(new PropertyValueFactory<ObjetoEmpleados,String>("fecnac"));
        tc_Telefono.setCellValueFactory(new PropertyValueFactory<ObjetoEmpleados,String>("telefono"));
        tc_Correo.setCellValueFactory(new PropertyValueFactory<ObjetoEmpleados,String>("correo"));
        tc_RFC.setCellValueFactory(new PropertyValueFactory<ObjetoEmpleados,String>("rfc"));
        tc_Direccion.setCellValueFactory(new PropertyValueFactory<ObjetoEmpleados,String>("direccion"));
        tc_FecReg.setCellValueFactory(new PropertyValueFactory<ObjetoEmpleados,String>("fecreg"));

        tv_empleados.setItems(lista);
    }

    private void altaEmpleados(){
        String query = "INSERT INTO empleados VALUES ('"+tf_nombres.getText()+"','"+tf_ApPat.getText()+"','"+
                tf_ApMat.getText()+"','"+tf_Direccion.getText()+"','"+tf_Telefono.getText()+"','"+tf_FecNac.getText()+"','"+
                tf_FecReg.getText()+"','"+tf_RFC.getText()+"','"+tf_Correo.getText()+"')";
        executeQuery(query);
        mostrarEmpleados();
    }

    private void executeQuery(String query){
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
