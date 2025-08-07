/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cineproyecto.models;

/**
 *
 * @author LENOVO
 */
import java.sql.Timestamp;

public class Usuarios {
    private int id_usuario;
    private String correo;
    private String contrasena_hash;
    private Timestamp ultimo_acceso;
    private boolean activo;
    
    // Constructores
    public Usuarios() {}
    
    public Usuarios(int id_usuario, String correo, boolean activo) {
        this.id_usuario = id_usuario;
        this.correo = correo;
        this.activo = activo;
    }
    
    // Getters y Setters
    public int getId_usuario() { return id_usuario; }
    public void setId_usuario(int id_usuario) { this.id_usuario = id_usuario; }
    
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    
    public String getContrasena_hash() { return contrasena_hash; }
    public void setContrasena_hash(String contrasena_hash) { this.contrasena_hash = contrasena_hash; }
    
    public Timestamp getUltimo_acceso() { return ultimo_acceso; }
    public void setUltimo_acceso(Timestamp ultimo_acceso) { this.ultimo_acceso = ultimo_acceso; }
    
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
