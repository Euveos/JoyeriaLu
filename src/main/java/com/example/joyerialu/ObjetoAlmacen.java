package com.example.joyerialu;

public class ObjetoAlmacen {

    private int id;
    private String nombre;
    private double precio;
    private String descripcion;
    private int stock;

    public ObjetoAlmacen(int id, String nombre, double precio, String descripcion, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getStock() {
        return stock;
    }
}
