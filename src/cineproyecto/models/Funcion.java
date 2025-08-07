/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cineproyecto.models;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author LENOVO
 */
public class Funcion {
    private int idFuncion;
    private int idPelicula;
    private Date fecha;
    private Time horaInicio;
    private int idSala;
    private double precioUnitario;
    
    public Funcion() {}
    
    public Funcion(int idFuncion, int idPelicula, Date fecha, Time horaInicio, 
                  int idSala, double precioUnitario) {
        this.idFuncion = idFuncion;
        this.idPelicula = idPelicula;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.idSala = idSala;
        this.precioUnitario = precioUnitario;
    }
    
    // Getters y Setters
    public int getIdFuncion() { return idFuncion; }
    public void setIdFuncion(int idFuncion) { this.idFuncion = idFuncion; }
    
    public int getIdPelicula() { return idPelicula; }
    public void setIdPelicula(int idPelicula) { this.idPelicula = idPelicula; }
    
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    
    public Time getHoraInicio() { return horaInicio; }
    public void setHoraInicio(Time horaInicio) { this.horaInicio = horaInicio; }
    
    public int getIdSala() { return idSala; }
    public void setIdSala(int idSala) { this.idSala = idSala; }
    
    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { 
        this.precioUnitario = precioUnitario; 
    }
    
    @Override
    public String toString() {
        return "Funcion{" + "idFuncion=" + idFuncion + ", idPelicula=" + idPelicula + 
               ", fecha=" + fecha + ", horaInicio=" + horaInicio + 
               ", idSala=" + idSala + ", precioUnitario=" + precioUnitario + '}';
    }
}