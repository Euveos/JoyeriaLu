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
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
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
    private TableColumn<ObjetoCarrito,Integer> tc_codigo;
    @FXML
    private TableColumn<ObjetoCarrito,String> tc_producto;
    @FXML
    private TableColumn<ObjetoCarrito, Integer> tc_cantidad;
    @FXML
    private TableColumn<ObjetoCarrito,Double> tc_precio;
    @FXML
    private DatePicker dp_fecha;
    @FXML
    private TableView<ObjetoCarrito> tv_ventas;
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

    //Valor del total monetario de la venta
    double cantidadTotal = 0.00;
    //ObjetoCarrito usado para el borrado de datos de la tabla
    ObjetoCarrito registroSeleccionado = null;
    //Lista de los objetos que almacenan los productos ingresados al carrito, junto con el id del producto y la cantidad de éste
    ObservableList<ObjetoCarrito> listaCarrito = FXCollections.observableArrayList();

    //Método que permite el regreso a la vista del menú
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

    //Método que permite generar cuadros de alerta para notificaciones al usuario
    private void generarAlerta(String razon, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setHeaderText(null);
        alerta.setTitle(razon);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void limpiar(){
        dp_fecha.setValue(null);
        tf_total.clear();
        tf_codEmp.clear();
        tf_codProd.clear();
        tf_cantidad.clear();
        tf_factura.clear();
    }

    //Método para agregado de productos al carrito
    @FXML
    private void addProductos(){

        //Condición en caso de que se presione agregar sin que exista un valor dentro del campo de código de productos
        if(!"".equals(tf_codProd.getText())){
            try {
                //Cantidad de elementos a agregar al carrito
                if(tf_cantidad.equals("")){
                    generarAlerta("No se ingresó cantidad", "No ha ingresado un valor numérico dentro del campo de cantidades");
                }
                //Se almacena la cantidad del producto en una variable
                int cantidad = Integer.parseInt(tf_cantidad.getText());
                //Variable que representará el stock dentro de la tabla de productos
                int existencias;

                //Verificación para evitar duplicidad en los productos guardados
                int index = listaCarrito.size();
                for(int i=0;i<index;i++){
                    if(tf_codProd.getText().equals(listaCarrito.get(i).getIdProducto())){
                        generarAlerta("Error en adición de producto","Este producto ya se encuentra dentro del carrito.");
                        return;
                    }
                }

                //Llamado a la conexión
                Connection conn = DbConnect.getConnection();
                //Query para la selección del producto seleccionado
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM productos WHERE CodigoProducto=? ");
                //Se define el valor del código del producto dentro del query
                ps.setInt(1, Integer.parseInt(tf_codProd.getText()));
                ResultSet rs = ps.executeQuery();

                //Condición para verificar si se encontró un resultado. De ser así,
                //almacena el valor del stock en la variable
                if(rs.next()){
                    existencias = rs.getInt("Stock");
                } else {
                    generarAlerta("Producto no encontrado","El producto solicitado no se encontró dentro de la base de datos.");
                    return;
                }

                //Comprobación de que hay cantidades suficientes para la venta
                if(existencias>=cantidad){
                    //Se genera un nuevo producto almacen para guardar los valores del objeto
                    ObjetoAlmacen productos = new ObjetoAlmacen(rs.getInt("CodigoProducto"), rs.getString("NombreProducto"),
                            rs.getDouble("PrecioUnitario"),rs.getString("Descripcion"),rs.getInt("Stock"));
                    //Se agrega el objeto a la lista del carrito y se despliega en la tabla
                    listaCarrito.add(new ObjetoCarrito(productos.getNombre(), productos.getId(), cantidad, productos.getPrecio()));
                    mostrarProductos(listaCarrito);
                } else {
                    generarAlerta("Cantidad Insuficiente","El producto solicitado no se tiene existencias suficientes.");
                }

                //Se actualiza el valor del total monetario y se limpian los campos de cantidad y código de producto
                actualizarTotal();
                tf_codProd.clear();
                tf_cantidad.clear();
            } catch (Exception e){
                generarAlerta("Error",e.getMessage());
            }
        }
    }

    //Método para eliminar productos de la tabla y la lista de carrito
    @FXML
    private void removeProductos(){
        listaCarrito.remove(registroSeleccionado);
        //Se actualiza la tabla y el total
        mostrarProductos(listaCarrito);
        actualizarTotal();
    }

    //Método ejecutado al presionar el botón de Finalizar Venta
    @FXML
    private void finalizarVenta(){
        //En caso de encontrarse vacío el carrito, se arroja un error.
        if (listaCarrito.isEmpty()){
            generarAlerta("Carrito vacío", "No ha seleccionado ningún producto para la venta,");
        }
        else {
            try {
                //Se genera la conexión y se inserta en la tabla de ventas los valores del empleado, la fecha y el total de la venta
                Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO ventas (CodigoEmpleado,Fecha,TotalVenta,NumeroFactura) VALUES (?,?,?,?)");
                ps.setInt(1, Integer.parseInt(tf_codEmp.getText()));
                ps.setDate(2, Date.valueOf(dp_fecha.getValue()));
                ps.setDouble(3, Double.parseDouble(tf_total.getText()));
                ps.setInt(4,Integer.parseInt(tf_factura.getText()));
                ps.executeUpdate();

                //En caso de que progresara correctamente, se ejecuta la actualización de la tabla ficticia
                actualizarTablaFicticia();
                limpiar();
                numeroFactura();
            } catch (Exception e){
                generarAlerta("Error",e.getMessage());
            }
        }
    }

    //Método para actualizar la tabla ficticia relacionando código de producto, código de venta y cantidad de producto
    private void actualizarTablaFicticia(){
            int index = listaCarrito.size();
            Connection conn = DbConnect.getConnection();

            for(int i=0; i<index;i++){
                try{
                    //Se inserta dentro de la tabla ficticia los valores necesarios
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO productosventas (CodigoProducto, CodigoVenta, CantidadProducto) VALUES (?,?,?)");
                    ps.setInt(1,listaCarrito.get(i).getIdProducto());
                    ps.setInt(2,Integer.parseInt(tf_factura.getText()));
                    ps.setInt(3,listaCarrito.get(i).getCantidad());
                    ps.executeUpdate();

                    //Se actualizan los nuevos valores de Stock dentro de la tabla de productos
                    PreparedStatement pp = conn.prepareStatement("UPDATE productos SET Stock = (Stock - ?) WHERE CodigoProducto = ?");
                    pp.setInt(1,listaCarrito.get(i).getCantidad());
                    pp.setInt(2, listaCarrito.get(i).getIdProducto());
                    pp.executeUpdate();

                }catch (Exception e){
                    generarAlerta("Error",e.getMessage());
                }
            }

            //Una vez se realizó correctamente, limpia todos los campos y la tabla
            limpiarTodo();
            //Se actualiza el número de la factura
            numeroFactura();
    }

    //Se guarda el ObjetoCarrito seleccionado en la tabla
    @FXML
    private void seleccionRegistro(MouseEvent event){
        registroSeleccionado = tv_ventas.getSelectionModel().getSelectedItem();
    }

    //Método para el despliegue de la fecha dentro de la vista
    @FXML
    private void getFecha(ActionEvent event){
        LocalDate miFecha = dp_fecha.getValue();
    }

    //Método de limpieza de campos y borrado de datos
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

    //Método para la actualización del valor total
    private void actualizarTotal(){
        cantidadTotal=0.00;
        int numeroFilas = tv_ventas.getItems().size();
        for(int i = 0; i<numeroFilas;i++){
            cantidadTotal += tv_ventas.getItems().get(i).getPrecio() * tv_ventas.getItems().get(i).getCantidad();
        }
        tf_total.setText(String.format("%.2f",cantidadTotal));
    }

    //Método para el despliegue de datos
    public void mostrarProductos(ObservableList<ObjetoCarrito> carrito){
        tc_codigo.setCellValueFactory(new PropertyValueFactory<ObjetoCarrito,Integer>("idProducto"));
        tc_producto.setCellValueFactory(new PropertyValueFactory<ObjetoCarrito,String>("nombreProducto"));
        tc_precio.setCellValueFactory(new PropertyValueFactory<ObjetoCarrito,Double>("precio"));
        tc_cantidad.setCellValueFactory(new PropertyValueFactory<ObjetoCarrito,Integer>("cantidad"));
        tv_ventas.setItems(carrito);
    }

    //Metodo para la inserción automática del número de factura correspondiente
    public void numeroFactura(){
        try{
            //Llamado a la conexión
            Connection conn = DbConnect.getConnection();
            //Query para la selección del producto seleccionado
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet resultado = stmt.executeQuery("SELECT COUNT(*) FROM ventas");
            resultado.next();
            int cuenta = resultado.getInt(1)+1;
            tf_factura.setText(""+cuenta);
        } catch(Exception e){
            generarAlerta("Error al obtener número de factura", e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numeroFactura();
    }
}
