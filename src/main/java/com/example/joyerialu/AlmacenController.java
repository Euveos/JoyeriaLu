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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AlmacenController implements Initializable, Serializable {



    @FXML
    private Button btn_BajaProductos;

    @FXML
    private Button btn_AltaProductos;

    @FXML
    private Button btn_Regresar;

    @FXML
    private Button btn_ModificarProductos;

    @FXML
    private TableColumn<ObjetoAlmacen, Integer> tc_Codigo;

    @FXML
    private TableColumn<ObjetoAlmacen, String> tc_Nombre;

    @FXML
    private TableColumn<ObjetoAlmacen, Double> tc_Precio;

    @FXML
    private TableColumn<ObjetoAlmacen, String> tc_Descripcion;

    @FXML
    private TableColumn<ObjetoAlmacen, Integer> tc_Stock;

    @FXML
    private TableView<ObjetoAlmacen> tv_Producto;

    @FXML
    private TextField tf_Codigo;

    @FXML
    private TextField tf_Precio;

    @FXML
    private TextField tf_Descripcion;

    @FXML
    private TextField tf_Stock;

    @FXML
    private TextField tf_Nombre;


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
        tf_Codigo.clear();
        tf_Precio.clear();
        tf_Descripcion.clear();
        tf_Stock.clear();
        tf_Nombre.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        mostrarProductos();
    }

    public ObservableList<ObjetoAlmacen> getProductosList(){
        ObservableList<ObjetoAlmacen> listaProductos = FXCollections.observableArrayList();
        Connection conn = DbConnect.getConnection();
        String query="SELECT * FROM productos";
        Statement st;
        ResultSet rs;

        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            ObjetoAlmacen productos;
            while(rs.next()){
                productos = new ObjetoAlmacen(rs.getInt("CodigoProducto"), rs.getString("NombreProducto"),
                        rs.getDouble("PrecioUnitario"),rs.getString("Descripcion"),rs.getInt("Stock"));
                listaProductos.add(productos);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listaProductos;
    }

    public void mostrarProductos(){
        ObservableList<ObjetoAlmacen> lista = getProductosList();

        tc_Codigo.setCellValueFactory(new PropertyValueFactory<ObjetoAlmacen,Integer>("id"));
        tc_Nombre.setCellValueFactory(new PropertyValueFactory<ObjetoAlmacen,String>("nombre"));
        tc_Precio.setCellValueFactory(new PropertyValueFactory<ObjetoAlmacen,Double>("precio"));
        tc_Descripcion.setCellValueFactory(new PropertyValueFactory<ObjetoAlmacen,String>("descripcion"));
        tc_Stock.setCellValueFactory(new PropertyValueFactory<ObjetoAlmacen,Integer>("stock"));

        tv_Producto.setItems(lista);
    }

    @FXML
    private void altaProductos(ActionEvent event){
        String query = "INSERT INTO productos VALUES ("+tf_Codigo.getText()+", '"+tf_Nombre.getText()+"',"+tf_Precio.getText()+",'"+tf_Descripcion.getText()+"',"+tf_Stock.getText()+")";
        executeQuery(query);
        mostrarProductos();
        limpiar();
    }

    @FXML
    private void modificarProductos(ActionEvent event){
        String query = "UPDATE productos SET NombreProducto = '"+tf_Nombre.getText()+
                "', PrecioUnitario = "+tf_Precio.getText()+", Descripcion = '"+tf_Descripcion.getText()+"', Stock = "+tf_Stock.getText()+" WHERE CodigoProducto="+tf_Codigo.getText();
        executeQuery(query);
        mostrarProductos();
        limpiar();
    }

    @FXML
    private void bajaProductos(ActionEvent event){
        String query = "DELETE FROM productos WHERE CodigoProducto= "+tf_Codigo.getText()+"";
        executeQuery(query);
        mostrarProductos();
        limpiar();
    }

    @FXML
    private void seleccionRegistro(MouseEvent event){
        ObjetoAlmacen productos = tv_Producto.getSelectionModel().getSelectedItem();
        tf_Codigo.setText(""+productos.getId());
        tf_Nombre.setText(productos.getNombre());
        tf_Precio.setText(""+productos.getPrecio());
        tf_Descripcion.setText(productos.getDescripcion());
        tf_Stock.setText(""+productos.getStock());
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
