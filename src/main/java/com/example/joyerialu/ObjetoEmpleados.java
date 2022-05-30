package com.example.joyerialu;

import java.time.LocalDate;
import java.util.Date;

public class ObjetoEmpleados {
    public int getId() {
        return id;
    }

    public String getNombres() {
        return nombres;
    }

    public String getAppat() {
        return appat;
    }

    public String getApmat() {
        return apmat;
    }

    public LocalDate getFecnac() {
        return fecnac;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getRfc() {
        return rfc;
    }

    public String getDireccion() {
        return direccion;
    }

    public LocalDate getFecreg() {
        return fecreg;
    }

    private int id;
    private String nombres;
    private String appat;
    private String apmat;
    private LocalDate fecnac;
    private String telefono;
    private String correo;
    private String rfc;
    private String direccion;
    private LocalDate fecreg;

    public ObjetoEmpleados(int id, String nombres, String appat, String apmat, LocalDate fecnac, String telefono, String correo, String rfc, String direccion, LocalDate fecreg) {
        this.id = id;
        this.nombres = nombres;
        this.appat = appat;
        this.apmat = apmat;
        this.fecnac = fecnac;
        this.telefono = telefono;
        this.correo = correo;
        this.rfc = rfc;
        this.direccion = direccion;
        this.fecreg = fecreg;
    }

}
