/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineproyecto.models;
import java.time.LocalDate;
import java.util.Objects;
/**
 *
 * @author JimyR
 */
public class Cliente {
    private String idCliente; // Ejemplo: "0011234567890"
    private String nombreCliente;
    private String correoCliente;
    private String telefonoCliente;
    private LocalDate fechaRegistro;
    
    // Constructores
    public Cliente() {
        this.fechaRegistro = LocalDate.now();
    }
    
    public Cliente(String idCliente, String nombreCliente, String correoCliente, 
                  String telefonoCliente, LocalDate fechaRegistro) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.correoCliente = correoCliente;
        this.telefonoCliente = telefonoCliente;
        this.fechaRegistro = fechaRegistro != null ? fechaRegistro : LocalDate.now();
    }
    
    // Getters y Setters completos
    public String getIdCliente() { 
        return idCliente; 
    }
    
    public void setIdCliente(String idCliente) { 
        if (!validarIdCliente(idCliente)) {
            throw new IllegalArgumentException("Formato de ID inválido");
        }
        this.idCliente = idCliente; 
    }
    
    public String getNombreCliente() {
        return nombreCliente;
    }
    
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    
    public String getCorreoCliente() {
        return correoCliente;
    }
    
    public void setCorreoCliente(String correoCliente) {
        // Validación básica de correo electrónico
        if (correoCliente != null && !correoCliente.isEmpty() && 
            !correoCliente.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Formato de correo electrónico inválido");
        }
        this.correoCliente = correoCliente;
    }
    
    public String getTelefonoCliente() {
        return telefonoCliente;
    }
    
    public void setTelefonoCliente(String telefonoCliente) {
        // Validación básica de teléfono (ajusta según tu formato requerido)
         if (telefonoCliente != null && !telefonoCliente.isEmpty()) {
        // Permite: 8 dígitos (00000000) O 4 dígitos + guión + 4 dígitos (0000-0000)
        if (!telefonoCliente.matches("^(\\d{8}|\\d{4}-\\d{4})$")) {
            throw new IllegalArgumentException(
                "Formato de teléfono inválido. Use:\n" +
                "- 8 dígitos continuos (ej: 12345678) O\n" +
                "- 4 dígitos, guión, 4 dígitos (ej: 1234-5678)");
        }
    }
    this.telefonoCliente = telefonoCliente;
    }
    
    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro != null ? fechaRegistro : LocalDate.now();
    }
    
    // Método de validación
    private boolean validarIdCliente(String id) {
        // Implementa tu lógica de validación para números de identidad
         return id != null && id.matches("^\\d{13}$");
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return idCliente.equals(cliente.idCliente);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(idCliente);
    }
    
    @Override
    public String toString() {
        return nombreCliente + " (" + idCliente + ")";
    }
}