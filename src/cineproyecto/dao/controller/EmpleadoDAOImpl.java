/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cineproyecto.dao.controller;
import cineproyecto.dao.EmpleadoDAO;
import cineproyecto.models.Empleado;
import cineproyecto.connection.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author LENOVO
 */
public class EmpleadoDAOImpl implements EmpleadoDAO{
    private static final String INSERT_SQL = "INSERT INTO empleados(id_empleado, nombre_empleado, telefono_empleado, correo_empleado, idpuesto) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE empleados SET nombre_empleado=?, telefono_empleado=?, correo_empleado=?, idpuesto=? WHERE id_empleado=?";
    private static final String DELETE_SQL = "DELETE FROM empleados WHERE id_empleado=?";
    private static final String GET_BY_ID_SQL = "SELECT * FROM empleados WHERE id_empleado=?";
    private static final String GET_ALL_SQL = "SELECT * FROM empleados ORDER BY nombre_empleado";
    private static final String EXISTS_SQL = "SELECT 1 FROM empleados WHERE id_empleado=?";
    private static final String SEARCH_SQL = "SELECT * FROM empleados WHERE nombre_empleado LIKE ?";
    
    @Override
    public void insertar(Empleado empleado) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL)) {
            
            stmt.setString(1, empleado.getIdEmpleado());
            stmt.setString(2, empleado.getNombreEmpleado());
            stmt.setString(3, empleado.getTelefonoEmpleado());
            stmt.setString(4, empleado.getCorreoEmpleado());
            stmt.setInt(5, empleado.getIdPuesto());
            
            stmt.executeUpdate();
        }
    }

    @Override
    public void actualizar(Empleado empleado) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {
            
            stmt.setString(1, empleado.getNombreEmpleado());
            stmt.setString(2, empleado.getTelefonoEmpleado());
            stmt.setString(3, empleado.getCorreoEmpleado());
            stmt.setInt(4, empleado.getIdPuesto());
            stmt.setString(5, empleado.getIdEmpleado());
            
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(String idEmpleado) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {
            
            stmt.setString(1, idEmpleado);
            stmt.executeUpdate();
        }
    }

    @Override
    public Empleado obtenerPorId(String idEmpleado) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_BY_ID_SQL)) {
            
            stmt.setString(1, idEmpleado);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Empleado(
                        rs.getString("id_empleado"),
                        rs.getString("nombre_empleado"),
                        rs.getString("telefono_empleado"),
                        rs.getString("correo_empleado"),
                        rs.getInt("idpuesto")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Empleado> obtenerTodos() throws SQLException {
        List<Empleado> empleados = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_SQL)) {
            
            while (rs.next()) {
                empleados.add(new Empleado(
                    rs.getString("id_empleado"),
                    rs.getString("nombre_empleado"),
                    rs.getString("telefono_empleado"),
                    rs.getString("correo_empleado"),
                    rs.getInt("idpuesto")
                ));
            }
        }
        return empleados;
    }

    @Override
    public boolean existeEmpleado(String idEmpleado) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(EXISTS_SQL)) {
            
            stmt.setString(1, idEmpleado);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public List<Empleado> buscarPorNombre(String nombre) throws SQLException {
        List<Empleado> empleados = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SEARCH_SQL)) {
            
            stmt.setString(1, "%" + nombre + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    empleados.add(new Empleado(
                        rs.getString("id_empleado"),
                        rs.getString("nombre_empleado"),
                        rs.getString("telefono_empleado"),
                        rs.getString("correo_empleado"),
                        rs.getInt("idpuesto")
                    ));
                }
            }
        }
        return empleados;
    }
}
