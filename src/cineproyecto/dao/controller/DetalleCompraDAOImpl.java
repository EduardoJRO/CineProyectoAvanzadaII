/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cineproyecto.dao.controller;
import cineproyecto.dao.DetalleCompraDAO;
import cineproyecto.models.DetalleCompra;
import cineproyecto.connection.DatabaseConnection;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleCompraDAOImpl implements DetalleCompraDAO {
    private static final String INSERT_SQL = "INSERT INTO detalle_compras (idcompra, idproducto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE detalle_compras SET idcompra=?, idproducto=?, cantidad=?, precio_unitario=? WHERE iddetalle=?";
    private static final String DELETE_SQL = "DELETE FROM detalle_compras WHERE iddetalle=?";
    private static final String GET_BY_ID_SQL = "SELECT * FROM detalle_compras WHERE iddetalle=?";
    private static final String GET_BY_COMPRA_SQL = "SELECT * FROM detalle_compras WHERE idcompra=?";
    private static final String GET_ALL_SQL = "SELECT * FROM detalle_compras";
    private static final String GET_PRODUCT_PRICE_SQL = "SELECT precio_unitario FROM productos WHERE idproducto=?";

    @Override
    public void insertar(DetalleCompra detalle) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            // Obtener el precio del producto desde la tabla productos
            BigDecimal precioUnitario = obtenerPrecioProducto(conn, detalle.getIdProducto());
            
            stmt.setInt(1, detalle.getIdCompra());
            stmt.setLong(2, detalle.getIdProducto());
            stmt.setInt(3, detalle.getCantidad());
            stmt.setBigDecimal(4, precioUnitario);

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    detalle.setIdDetalle(generatedKeys.getInt(1));
                    detalle.setPrecioUnitario(precioUnitario);
                }
            }
        }
    }

    @Override
    public void actualizar(DetalleCompra detalle) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            // Obtener el precio del producto desde la tabla productos
            BigDecimal precioUnitario = obtenerPrecioProducto(conn, detalle.getIdProducto());
            
            stmt.setInt(1, detalle.getIdCompra());
            stmt.setLong(2, detalle.getIdProducto());
            stmt.setInt(3, detalle.getCantidad());
            stmt.setBigDecimal(4, precioUnitario);
            stmt.setInt(5, detalle.getIdDetalle());

            stmt.executeUpdate();
            
            // Actualizar el precio en el objeto
            detalle.setPrecioUnitario(precioUnitario);
        }
    }

    @Override
    public void eliminar(int idDetalle) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {
            
            stmt.setInt(1, idDetalle);
            stmt.executeUpdate();
        }
    }

    @Override
    public DetalleCompra obtenerPorId(int idDetalle) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_BY_ID_SQL)) {

            stmt.setInt(1, idDetalle);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new DetalleCompra(
                        rs.getInt("iddetalle"),
                        rs.getInt("idcompra"),
                        rs.getLong("idproducto"),
                        rs.getInt("cantidad"),
                        rs.getBigDecimal("precio_unitario")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<DetalleCompra> obtenerPorCompra(int idCompra) throws SQLException {
        List<DetalleCompra> detalles = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_BY_COMPRA_SQL)) {

            stmt.setInt(1, idCompra);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    detalles.add(new DetalleCompra(
                        rs.getInt("iddetalle"),
                        rs.getInt("idcompra"),
                        rs.getLong("idproducto"),
                        rs.getInt("cantidad"),
                        rs.getBigDecimal("precio_unitario")
                    ));
                }
            }
        }
        return detalles;
    }

    @Override
    public List<DetalleCompra> obtenerTodos() throws SQLException {
        List<DetalleCompra> detalles = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_SQL)) {

            while (rs.next()) {
                detalles.add(new DetalleCompra(
                    rs.getInt("iddetalle"),
                    rs.getInt("idcompra"),
                    rs.getLong("idproducto"),
                    rs.getInt("cantidad"),
                    rs.getBigDecimal("precio_unitario")
                ));
            }
        }
        return detalles;
    }

    /**
     * Método auxiliar para obtener el precio de un producto desde la base de datos
     */
    private BigDecimal obtenerPrecioProducto(Connection conn, long idProducto) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(GET_PRODUCT_PRICE_SQL)) {
            stmt.setLong(1, idProducto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("precio_unitario");
                } else {
                    throw new SQLException("Producto no encontrado con ID: " + idProducto);
                }
            }
        }
    }
}
