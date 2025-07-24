/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cineproyecto.dao.controller;
import cineproyecto.dao.ProductoDAO;
import cineproyecto.models.Producto;
import cineproyecto.connection.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigInteger;
/**
 *
 * @author LENOVO
 */
public class ProductoDAOImpl implements ProductoDAO{
    private static final String INSERT_SQL = "INSERT INTO productos (idproducto, idtipoproducto, precio_unitario, producto) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE productos SET idtipoproducto=?, precio_unitario=?, producto=? WHERE idproducto=?";
    private static final String DELETE_SQL = "DELETE FROM productos WHERE idproducto=?";
    private static final String GET_BY_ID_SQL = "SELECT * FROM productos WHERE idproducto=?";
    private static final String GET_ALL_SQL = "SELECT * FROM productos";
    private static final String SEARCH_BY_BARCODE_SQL = "SELECT * FROM productos WHERE CAST(idproducto AS CHAR) LIKE ?";
    private static final String EXISTS_SQL = "SELECT 1 FROM productos WHERE idproducto=?";
    
    @Override
    public void insertar(Producto producto) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL)) {
            
            stmt.setObject(1, producto.getIdProducto(), Types.BIGINT);
            stmt.setInt(2, producto.getIdTipoProducto());
            stmt.setDouble(3, producto.getPrecioUnitario());
            stmt.setString(4, producto.getProducto());
            
            stmt.executeUpdate();
        }
    }

    @Override
    public void actualizar(Producto producto) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {
            
            stmt.setInt(1, producto.getIdTipoProducto());
            stmt.setDouble(2, producto.getPrecioUnitario());
            stmt.setString(3, producto.getProducto());
            stmt.setObject(4, producto.getIdProducto(), Types.BIGINT);
            
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(BigInteger idProducto) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {
            
            stmt.setObject(1, idProducto, Types.BIGINT);
            stmt.executeUpdate();
        }
    }

    @Override
    public Producto obtenerPorId(BigInteger idProducto) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_BY_ID_SQL)) {
            
            stmt.setObject(1, idProducto, Types.BIGINT);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Producto(
                        new BigInteger(rs.getString("idproducto")),
                        rs.getInt("idtipoproducto"),
                        rs.getDouble("precio_unitario"),
                        rs.getString("producto")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Producto> obtenerTodos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_SQL)) {
            
            while (rs.next()) {
                productos.add(new Producto(
                    new BigInteger(rs.getString("idproducto")),
                    rs.getInt("idtipoproducto"),
                    rs.getDouble("precio_unitario"),
                    rs.getString("producto")
                ));
            }
        }
        return productos;
    }

    @Override
    public boolean existeProducto(BigInteger idProducto) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(EXISTS_SQL)) {
            
            stmt.setObject(1, idProducto, Types.BIGINT);
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public List<Producto> buscarPorBarras(BigInteger codbarras) throws SQLException {
    List<Producto> productos = new ArrayList<>();
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SEARCH_BY_BARCODE_SQL)) {

            // Convertimos el BigInteger a String y agregamos % para búsqueda parcial
            stmt.setString(1, "%" + codbarras.toString() + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    productos.add(new Producto(
                        new BigInteger(rs.getString("idproducto")),
                        rs.getInt("idtipoproducto"),
                        rs.getDouble("precio_unitario"),
                        rs.getString("producto")
                    ));
                }
            }
        }
        return productos;
    }
}
