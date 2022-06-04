package com.example.joyerialu;

import java.sql.Date;

public class ObjetoVentas {

    private int idVentas;
    private int codigoEmpleado;
    private Date fecha;
    private double totalVenta;

    public ObjetoVentas(int id, int codigoEmpleado, Date fecha, double totalVenta){
        this.idVentas = id;
        this.codigoEmpleado = codigoEmpleado;
        this.fecha = fecha;
        this.totalVenta = totalVenta;
    }

    public int getIdVentas() {
        return idVentas;
    }

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public Date getFecha() {
        return fecha;
    }

    public double getTotalVenta() {
        return totalVenta;
    }
}
