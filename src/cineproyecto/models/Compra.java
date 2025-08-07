/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cineproyecto.models;

import java.util.Date;

/**
 * Modelo que representa una compra en el sistema de cine
 */
public class Compra {
    private int idCompra;
    private String idCliente;
    private String idEmpleado;
    private Date fechaCompra;
    
    public Compra() {}
    
    public Compra(int idCompra, String idCliente, String idEmpleado, Date fechaCompra) {
        this.idCompra = idCompra;
        this.idCliente = idCliente;
        this.idEmpleado = idEmpleado;
        this.fechaCompra = fechaCompra;
    }
    
    // Getters y Setters con validaciones
    
    public int getIdCompra() { 
        return idCompra; 
    }
    
    public void setIdCompra(int idCompra) { 
        if (idCompra <= 0) {
            throw new IllegalArgumentException("El ID de compra debe ser un número positivo");
        }
        this.idCompra = idCompra; 
    }
    
    public String getIdCliente() { 
        return idCliente; 
    }
    
    public void setIdCliente(String idCliente) { 
        if (!validarIdCliente(idCliente)) {
            throw new IllegalArgumentException("Formato de ID de cliente inválido");
        }
        this.idCliente = idCliente; 
    }
    
    // Método de validación para ID de cliente
    private boolean validarIdCliente(String id) {
        return id != null && id.matches("^\\d{13}$");
    }
    
    public String getIdEmpleado() { 
        return idEmpleado; 
    }
    
    public void setIdEmpleado(String idEmpleado) { 
        if (!validarIdEmpleado(idEmpleado)) {
            throw new IllegalArgumentException("Formato de ID de empleado inválido");
        }
        this.idEmpleado = idEmpleado; 
    }
    
    // Reutilizamos la validación de empleado
    private boolean validarIdEmpleado(String id) {
        return id != null && id.matches("^\\d{13}$");
    }
    
    public Date getFechaCompra() { 
        return fechaCompra; 
    }
    
    public void setFechaCompra(Date fechaCompra) { 
        if (fechaCompra == null) {
            throw new IllegalArgumentException("La fecha de compra no puede ser nula");
        }
        
        // Validar que la fecha no sea futura
        Date hoy = new Date();
        if (fechaCompra.after(hoy)) {
            throw new IllegalArgumentException("La fecha de compra no puede ser futura");
        }
        
        this.fechaCompra = fechaCompra; 
    }
    
    @Override
    public String toString() {
        return "Compra{" +
               "idCompra=" + idCompra +
               ", idCliente='" + idCliente + '\'' +
               ", idEmpleado='" + idEmpleado + '\'' +
               ", fechaCompra=" + fechaCompra +
               '}';
    }
}
