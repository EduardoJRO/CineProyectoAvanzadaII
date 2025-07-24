/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cineproyecto.models;

/**
 *
 * @author LENOVO
 */
public class Empleado {
    private String idEmpleado;
    private String nombreEmpleado;
    private String telefonoEmpleado;
    private String correoEmpleado;
    private int idPuesto;
    
    public Empleado() {}
    
    public Empleado(String idEmpleado, String nombreEmpleado, String telefonoEmpleado, 
                  String correoEmpleado, int idPuesto) {
        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.telefonoEmpleado = telefonoEmpleado;
        this.correoEmpleado = correoEmpleado;
        this.idPuesto = idPuesto;
    }
    
    //Retornos y Set/Get
    public String getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(String idEmpleado) { 
        if (!validarIdEmpleado(idEmpleado)) {
            throw new IllegalArgumentException("Formato de ID inválido");
        }
        this.idEmpleado = idEmpleado; 
    }
    // Método de validación
    private boolean validarIdEmpleado(String id) {
         return id != null && id.matches("^\\d{13}$");
    }
    
    public String getNombreEmpleado() { return nombreEmpleado; }
    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }
    
    public String getTelefonoEmpleado() { return telefonoEmpleado; }
    public void setTelefonoEmpleado(String telefonoEmpleado) {
        // Validación básica de teléfono (ajusta según tu formato requerido)
         if (telefonoEmpleado != null && !telefonoEmpleado.isEmpty()) {
        // Permite: 8 dígitos (00000000) O 4 dígitos + guión + 4 dígitos (0000-0000)
        if (!telefonoEmpleado.matches("^(\\d{8}|\\d{4}-\\d{4})$")) {
            throw new IllegalArgumentException(
                "Formato de teléfono inválido. Use:\n" +
                "- 8 dígitos continuos (ej: 12345678) O\n" +
                "- 4 dígitos, guión, 4 dígitos (ej: 1234-5678)");
        }
    }
    this.telefonoEmpleado = telefonoEmpleado;
    }
    
    public String getCorreoEmpleado() { return correoEmpleado; }
    public void setCorreoEmpleado(String correoEmpleado) {
        // Validación básica de correo electrónico
        if (correoEmpleado != null && !correoEmpleado.isEmpty() && 
            !correoEmpleado.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Formato de correo electrónico inválido");
        }
        this.correoEmpleado = correoEmpleado;
    }
    
    
    public int getIdPuesto() { return idPuesto; }
    public void setIdPuesto(int idPuesto) { this.idPuesto = idPuesto; }
    
    
}
