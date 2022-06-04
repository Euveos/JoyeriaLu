package com.example.joyerialu;

public class ObjetoCarrito {
    private String nombreProducto;
    private int idProducto;
    private int cantidad;
    private double precio;

    public ObjetoCarrito(String nombreProducto, int idProducto, int cantidad, double precio) {
        this.nombreProducto=nombreProducto;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public double getPrecio() {
        return precio;
    }
}
