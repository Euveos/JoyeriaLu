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
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class VentasController implements Initializable, Serializable {
    @FXML
    private Button btn_Regresar;
    @FXML
    private Button btn_add;
    @FXML
    private Button btn_remove;
    @FXML
    private Button btn_finventa;
    @FXML
    private Button btn_clear;
    @FXML
    private TableColumn<ObjetoAlmacen,Integer> tc_codigo;
    @FXML
    private TableColumn<ObjetoAlmacen,String> tc_producto;
    @FXML
    private TableColumn<ObjetoAlmacen,Integer> tc_cantidad;
    @FXML
    private TableColumn<ObjetoAlmacen,Double> tc_precio;
    @FXML
    private DatePicker dp_fecha;
    @FXML
    private TableView<ObjetoAlmacen> tv_ventas;
    @FXML
    private TextField tf_total;
    @FXML
    private TextField tf_codEmp;
    @FXML
    private TextField tf_codProd;
    @FXML
    private TextField tf_cantidad;
    @FXML
    private TextField tf_factura;

    double cantidadTotal = 0.00;
    ObjetoAlmacen registroSeleccionado = null;
    ObservableList<ObjetoAlmacen> listaProductos = FXCollections.observableArrayList();
    ObservableList<ObjetoCarrito> listaCarrito = FXCollections.observableArrayList();

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

    private void generarAlerta(String razon, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setHeaderText(null);
        alerta.setTitle(razon);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    private void addProductos(){
        if(!"".equals(tf_codProd.getText())){
            try {
                int cantidad = Integer.parseInt(tf_cantidad.getText());
                int existencias = 0;

                Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM productos WHERE CodigoProducto=? ");
                ps.setString(1,tf_codProd.getText());
                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    existencias = rs.getInt("Stock");
                } else {
                    generarAlerta("Producto no encontrado","El producto solicitado no se encontró dentro de la base de datos.");
                    return;
                }

                if(existencias>=cantidad){
                    ObjetoAlmacen productos;
                    productos = new ObjetoAlmacen(rs.getInt("CodigoProducto"), rs.getString("NombreProducto"),
                            rs.getDouble("PrecioUnitario"),rs.getString("Descripcion"),rs.getInt("Stock"));
                    listaCarrito.add(new ObjetoCarrito(productos.getId(), cantidad, productos));
                    mostrarProductos(listaCarrito);
                } else {
                    generarAlerta("Cantidad Insuficiente","El producto solicitado no se tiene existencias suficientes.");
                }


                actualizarTotal();
                tf_codProd.clear();
                tf_cantidad.clear();
            } catch (Exception e){
                generarAlerta("Error",e.getMessage());
            }
        }
    }

    @FXML
    private void removeProductos(){
        int index = listaCarrito.size();
        for(int i = 0; i<index;i++){
            if(listaCarrito.get(i).getProducto().equals(registroSeleccionado)){
                listaCarrito.remove(i);
            }
        }
        mostrarProductos(listaCarrito);
        actualizarTotal();
    }

    @FXML
    private void finalizarVenta(){
        if (listaCarrito.isEmpty()){
            generarAlerta("Carrito vacío", "No ha seleccionado ningún producto para la venta,");
        }
        else {
            try {
                Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO ventas (CodigoEmpleado,Fecha,TotalVenta) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, Integer.parseInt(tf_codEmp.getText()));
                ps.setDate(2, Date.valueOf(dp_fecha.getValue()));
                ps.setDouble(3, Double.parseDouble(tf_total.getText()));
                ps.executeUpdate();

                ResultSet resultado = ps.getGeneratedKeys();
                resultado.next();
                int idVenta = resultado.getInt(1);

                if(idVenta==0){
                    generarAlerta("Error", "Ocurrió un error al guardar la venta.");
                    return;
                }
                actualizarTablaFicticia(idVenta);

            } catch (Exception e){
                generarAlerta("Error",e.getMessage());
            }
        }
    }

    private void actualizarTablaFicticia(int idVenta){
            int index = listaCarrito.size();
            Connection conn = DbConnect.getConnection();

            for(int i=0; i>index;i++){
                try{
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO productosventas (CodigoProducto, CodigoVenta, CantidadProducto) VALUES (?,?,?)");
                    ps.setInt(1,listaCarrito.get(i).getIdProducto());
                    ps.setInt(2,idVenta);
                    ps.setInt(3,listaCarrito.get(i).getCantidad());
                    ps.executeUpdate();

                    PreparedStatement pp = conn.prepareStatement("UPDATE productos SET Stock = (Stock - ?) WHERE CodigoProducto = ?");
                    pp.setInt(1,listaCarrito.get(i).getCantidad());
                    pp.setInt(2, listaCarrito.get(i).getIdProducto());
                    pp.executeUpdate();

                }catch (Exception e){
                    generarAlerta("Error",e.getMessage());
                }
            }
            limpiarTodo();
    }

    @FXML
    private void seleccionRegistro(MouseEvent event){
        registroSeleccionado = tv_ventas.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void getFecha(ActionEvent event){
        LocalDate miFecha = dp_fecha.getValue();
    }

    @FXML
    private void limpiarTodo(){
        cantidadTotal=0.00;
        tf_total.clear();
        tf_cantidad.clear();
        tf_codProd.clear();
        tf_codEmp.clear();
        tf_factura.clear();
        listaCarrito.clear();
        mostrarProductos(listaCarrito);
    }

    private void actualizarTotal(){
        int numeroFilas = tv_ventas.getItems().size();
        for(int i = 0; i<numeroFilas;i++){
            cantidadTotal += tv_ventas.getItems().get(i).getPrecio();
        }
        tf_total.setText(String.format("%.2f",cantidadTotal));
    }

    public void mostrarProductos(ObservableList<ObjetoCarrito> carrito){
        listaProductos.clear();

        int largo = carrito.size();
        for(int i=0;i<largo;i++){
            listaProductos.add(carrito.get(i).getProducto());
        }

        tc_codigo.setCellValueFactory(new PropertyValueFactory<ObjetoAlmacen,Integer>("id"));
        tc_producto.setCellValueFactory(new PropertyValueFactory<ObjetoAlmacen,String>("nombre"));
        tc_precio.setCellValueFactory(new PropertyValueFactory<ObjetoAlmacen,Double>("precio"));
        tc_cantidad.setCellValueFactory(new PropertyValueFactory<ObjetoAlmacen,Integer>("stock"));
        tv_ventas.setItems(listaProductos);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
