package com.example.joyerialu;

public class ObjetoCarrito {
    private ObjetoAlmacen producto;
    private int idProducto;
    private int cantidad;

    public ObjetoCarrito(int idProducto, int cantidad, ObjetoAlmacen producto) {
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.producto = producto;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public ObjetoAlmacen getProducto() {
        return producto;
    }
}
