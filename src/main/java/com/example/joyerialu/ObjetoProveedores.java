package com.example.joyerialu;

public class ObjetoProveedores {

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    private int id;
    private String nombre;
    private String telefono;
    private String direccion;

    public ObjetoProveedores (int id, String nombre, String telefono, String direccion){
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }
}
