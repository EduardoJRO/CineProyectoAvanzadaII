/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineproyecto.dao.controller;
import cineproyecto.dao.ClienteDAO;
import cineproyecto.models.Cliente;
import cineproyecto.connection.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author JimyR
 */
public class ClienteDAOImpl implements ClienteDAO {
    // Consultas SQL
    private static final String INSERT_SQL = "INSERT INTO clientes VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE clientes SET nombre_cliente=?, correo_cliente=?, telefono_cliente=?, fecha_registro=? WHERE idcliente=?";
    private static final String DELETE_SQL = "DELETE FROM clientes WHERE idcliente=?";
    private static final String GET_BY_ID_SQL = "SELECT * FROM clientes WHERE idcliente=?";
    private static final String GET_ALL_SQL = "SELECT * FROM clientes ORDER BY nombre_cliente";
    private static final String EXISTS_SQL = "SELECT 1 FROM clientes WHERE idcliente=?";
    private static final String SEARCH_SQL = "SELECT * FROM clientes WHERE nombre_cliente LIKE ?";

    @Override
    public void insertar(Cliente cliente) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL)) {
            
            stmt.setString(1, cliente.getIdCliente());
            stmt.setString(2, cliente.getNombreCliente());
            stmt.setString(3, cliente.getCorreoCliente());
            stmt.setString(4, cliente.getTelefonoCliente());
            stmt.setDate(5, Date.valueOf(cliente.getFechaRegistro()));
            
            stmt.executeUpdate();
        }
    }

    @Override
    public void actualizar(Cliente cliente) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {
            
            stmt.setString(1, cliente.getNombreCliente());
            stmt.setString(2, cliente.getCorreoCliente());
            stmt.setString(3, cliente.getTelefonoCliente());
            stmt.setDate(4, Date.valueOf(cliente.getFechaRegistro()));
            stmt.setString(5, cliente.getIdCliente());
            
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(String idCliente) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {
            
            stmt.setString(1, idCliente);
            stmt.executeUpdate();
        }
    }

    @Override
    public Cliente obtenerPorId(String idCliente) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_BY_ID_SQL)) {
            
            stmt.setString(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                        rs.getString("idcliente"),
                        rs.getString("nombre_cliente"),
                        rs.getString("correo_cliente"),
                        rs.getString("telefono_cliente"),
                        rs.getDate("fecha_registro").toLocalDate()
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Cliente> obtenerTodos() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_SQL)) {
            
            while (rs.next()) {
                clientes.add(new Cliente(
                    rs.getString("idcliente"),
                    rs.getString("nombre_cliente"),
                    rs.getString("correo_cliente"),
                    rs.getString("telefono_cliente"),
                    rs.getDate("fecha_registro").toLocalDate()
                ));
            }
        }
        return clientes;
    }

    @Override
    public boolean existeCliente(String idCliente) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(EXISTS_SQL)) {
            
            stmt.setString(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public List<Cliente> buscarPorNombre(String nombre) throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SEARCH_SQL)) {
            
            stmt.setString(1, "%" + nombre + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    clientes.add(new Cliente(
                        rs.getString("idcliente"),
                        rs.getString("nombre_cliente"),
                        rs.getString("correo_cliente"),
                        rs.getString("telefono_cliente"),
                        rs.getDate("fecha_registro").toLocalDate()
                    ));
                }
            }
        }
        return clientes;
    }
}
