/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cineproyecto.dao.controller;

import cineproyecto.connection.DatabaseConnection;
import cineproyecto.models.Usuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 * DAO para la gestión de usuarios en la base de datos
 */
public class UsuarioDAO {
    // Consultas SQL
    private static final String SQL_SELECT = "SELECT id_usuario, correo, activo FROM usuarios";
    private static final String SQL_INSERT = "INSERT INTO usuarios(correo, contrasena_hash, activo) VALUES(?, ?, ?)";
    private static final String SQL_CHECK_EMAIL = "SELECT id_usuario FROM usuarios WHERE correo = ?";
    
    // Patrón para validar contraseña: al menos 1 letra, 1 número y 1 caracter especial
    private static final Pattern PASSWORD_PATTERN = 
        Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
    
    // Instancia singleton
    private static UsuarioDAO instance;
    
    // Constructor privado para singleton
    private UsuarioDAO() {}
    
    /**
     * Obtiene la instancia única del DAO (patrón Singleton)
     */
    public static synchronized UsuarioDAO getInstance() {
        if (instance == null) {
            instance = new UsuarioDAO();
        }
        return instance;
    }
    
    public int actualizar(Usuarios usuario) throws SQLException {
    String sql;
    PreparedStatement stmt;
    
    try (Connection conn = DatabaseConnection.getConnection()) {
        // Si no se proporcionó nueva contraseña
        if (usuario.getContrasena_hash() == null || usuario.getContrasena_hash().isEmpty()) {
            sql = "UPDATE usuarios SET correo = ?, activo = ? WHERE id_usuario = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario.getCorreo());
            stmt.setBoolean(2, usuario.isActivo());
            stmt.setInt(3, usuario.getId_usuario());
        } 
        // Si se proporcionó nueva contraseña
        else {
            sql = "UPDATE usuarios SET correo = ?, contrasena_hash = ?, activo = ? WHERE id_usuario = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario.getCorreo());
            stmt.setString(2, usuario.getContrasena_hash()); // En producción usar BCrypt
            stmt.setBoolean(3, usuario.isActivo());
            stmt.setInt(4, usuario.getId_usuario());
        }
        
        return stmt.executeUpdate();
    }
}
    
    public int eliminar(int idUsuario) throws SQLException {
    String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, idUsuario);
        return stmt.executeUpdate();
    }
}
    
    /**
     * Verifica si un usuario es gerente mediante sus credenciales
     */
    public boolean validarCredencialesGerente(String password) {
    if (password == null || password.trim().isEmpty()) {
        return false;
    }

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(
             "SELECT u.contrasena_hash " +
             "FROM usuarios u " +
             "JOIN empleados e ON u.id_usuario = e.id_usuario " +
             "JOIN puestos p ON e.idpuesto = p.idpuesto " +
             "WHERE p.puesto = 'Gerente' AND u.activo = 1")) {
        
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String contrasenaHash = rs.getString("contrasena_hash");
                // Comparación directa (en producción usar BCrypt.checkpw)
                if (password.equals(contrasenaHash)) {
                    return true;
                }
            }
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, 
            "Error al validar credenciales de gerente: " + ex.getMessage(),
            "Error de base de datos", JOptionPane.ERROR_MESSAGE);
    }
    return false;
}
    
    /**
     * Obtiene todos los usuarios registrados
     */
    public List<Usuarios> listarUsuarios() {
        List<Usuarios> usuarios = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT);
             ResultSet rs = stmt.executeQuery()) {
            
            while(rs.next()) {
                usuarios.add(new Usuarios(
                    rs.getInt("id_usuario"),
                    rs.getString("correo"),
                    rs.getBoolean("activo")
                ));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, 
                "Error al listar usuarios: " + ex.getMessage(),
                "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
        return usuarios;
    }
    
    /**
     * Inserta un nuevo usuario en la base de datos
     */
    public int insertar(Usuarios usuario) {
        if (usuario == null) {
            JOptionPane.showMessageDialog(null, 
                "El usuario no puede ser nulo",
                "Error de validación", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
        
        // Validar formato de contraseña
        if (!validarFormatoContrasena(usuario.getContrasena_hash())) {
            JOptionPane.showMessageDialog(null, 
                "La contraseña debe contener al menos:\n" +
                "- Una letra\n- Un número\n- Un caracter especial (@$!%*#?&)\n" +
                "- Mínimo 8 caracteres", 
                "Error en contraseña", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            
            stmt.setString(1, usuario.getCorreo());
            stmt.setString(2, usuario.getContrasena_hash());
            stmt.setBoolean(3, usuario.isActivo());
            
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, 
                "Error al crear usuario: " + ex.getMessage(),
                "Error de base de datos", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }
    
    /**
     * Verifica si un correo electrónico ya está registrado
     */
    public boolean existeCorreo(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            return false;
        }
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_CHECK_EMAIL)) {
            
            stmt.setString(1, correo);
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, 
                "Error al verificar correo: " + ex.getMessage(),
                "Error de base de datos", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Valida el formato de una contraseña
     */
    private boolean validarFormatoContrasena(String contrasena) {
        return contrasena != null && PASSWORD_PATTERN.matcher(contrasena).matches();
    }
}
