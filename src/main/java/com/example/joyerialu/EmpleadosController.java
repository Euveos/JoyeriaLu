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
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EmpleadosController implements Initializable, Serializable {

    @FXML
    private Button btn_Regresar;

    @FXML
    private Button btn_AltaEmpleados;

    @FXML
    private Button btn_BajaEmpleados;

    @FXML
    private Button btn_ModificarEmpleados;

    @FXML
    private TableColumn<ObjetoEmpleados,Integer> tc_ID;

    @FXML
    private TableColumn<ObjetoEmpleados,String> tc_Nombres;

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
    private TextField tf_Nombres;

    @FXML
    private TextField tf_ApPat;

    @FXML
    private TextField tf_ApMat;

    @FXML
    private DatePicker dp_FecNac;

    @FXML
    private TextField tf_Telefono;

    @FXML
    private TextField tf_Correo;

    @FXML
    private TextField tf_RFC;

    @FXML
    private TextField tf_Direccion;

    @FXML
    private DatePicker dp_FecReg;

    @FXML
    private TableView<ObjetoEmpleados> tv_empleados;

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
        tf_Nombres.clear();
        tf_ApPat.clear();
        tf_ApMat.clear();
        dp_FecNac.setValue(null);
        tf_Telefono.clear();
        tf_Correo.clear();
        tf_RFC.clear();
        tf_Direccion.clear();
        dp_FecReg.setValue(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        mostrarEmpleados();
    }

    public ObservableList<ObjetoEmpleados> getEmpleadosList(){
        ObservableList<ObjetoEmpleados> listaEmpleados = FXCollections.observableArrayList();
        Connection conn = DbConnect.getConnection();
        String query="SELECT * FROM empleados";
        Statement st;
        ResultSet rs;

        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            ObjetoEmpleados empleados;
            while(rs.next()){
                empleados = new ObjetoEmpleados(rs.getInt("CodigoEmpleado"), rs.getString("Nombre"),
                        rs.getString("PrimerApellido"),rs.getString("ApellidoMaterno"),rs.getDate("FechaNac").toLocalDate(),
                        rs.getString("Telefono"),rs.getString("CorreoE"),rs.getString("RFC"),rs.getString("Direccion"),
                        rs.getDate("FechaRegistro").toLocalDate());
                listaEmpleados.add(empleados);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaEmpleados;
    }

    public void mostrarEmpleados(){
        ObservableList<ObjetoEmpleados> lista = getEmpleadosList();

        tc_ID.setCellValueFactory(new PropertyValueFactory<ObjetoEmpleados,Integer>("id"));
        tc_Nombres.setCellValueFactory(new PropertyValueFactory<ObjetoEmpleados,String>("nombres"));
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

    @FXML
    private void altaEmpleados(){
        String query = "INSERT INTO empleados VALUES ("+tf_ID.getText()+", '"+tf_Nombres.getText()+"','"+tf_ApPat.getText()+"','"+
                tf_ApMat.getText()+"','"+tf_Direccion.getText()+"','"+tf_Telefono.getText()+"','"+dp_FecNac.getValue().toString()+"','"+
                dp_FecReg.getValue().toString()+"','"+tf_RFC.getText()+"','"+tf_Correo.getText()+"')";
        executeQuery(query);
        mostrarEmpleados();
        limpiar();
    }

    @FXML
    private void modificarEmpleados(){
        String query = "UPDATE empleados SET Nombre = '"+tf_Nombres.getText()+"', PrimerApellido = '"+tf_ApPat.getText()+"', ApellidoMaterno = '"+
                tf_ApMat.getText()+"', Direccion = '"+tf_Direccion.getText()+"', Telefono = '"+tf_Telefono.getText()+"', FechaNac = '"+
                dp_FecNac.getValue().toString()+"', FechaRegistro = '"+dp_FecReg.getValue().toString()+"', RFC = '"+tf_RFC.getText()+"', CorreoE = '"+tf_Correo.getText()+"' WHERE CodigoEmpleado="+tf_ID.getText();
        executeQuery(query);
        mostrarEmpleados();
        limpiar();
    }

    @FXML
    private void bajaEmpleados(){
        String query = "DELETE FROM empleados WHERE CodigoEmpleado= "+tf_ID.getText()+"";
        executeQuery(query);
        mostrarEmpleados();
        limpiar();
    }

    @FXML
    private void getFechaNacimiento(ActionEvent event){
        LocalDate miFecha = dp_FecNac.getValue();
    }

    @FXML
    private void getFecha(ActionEvent event){
        LocalDate miFecha = dp_FecReg.getValue();
    }

    @FXML
    private void seleccionRegistro(MouseEvent event){
        ObjetoEmpleados empleado = tv_empleados.getSelectionModel().getSelectedItem();
        tf_ID.setText(""+empleado.getId());
        tf_Nombres.setText(empleado.getNombres());
        tf_ApPat.setText(empleado.getAppat());
        tf_ApMat.setText(empleado.getApmat());
        tf_Direccion.setText(empleado.getDireccion());
        tf_Telefono.setText(empleado.getTelefono());
        tf_Correo.setText(empleado.getCorreo());
        tf_RFC.setText(empleado.getRfc());
        dp_FecNac.setValue(empleado.getFecnac());
        dp_FecReg.setValue(empleado.getFecreg());
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
