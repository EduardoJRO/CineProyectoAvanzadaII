/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cineproyecto.dao.controller;
import cineproyecto.dao.CompraDAO;
import cineproyecto.models.Compra;
import cineproyecto.connection.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author LENOVO
 */
public class CompraDAOImpl implements CompraDAO{
    private static final String INSERT_SQL = "INSERT INTO compras (idcliente, idempleado, fecha_compra) VALUES (?, ?, NOW())";
    private static final String UPDATE_SQL = "UPDATE compras SET idcliente=?, idempleado=?, fecha_compra=NOW() WHERE idcompras=?";
    private static final String DELETE_SQL = "DELETE FROM compras WHERE idcompras=?";
    private static final String GET_BY_ID_SQL = "SELECT * FROM compras WHERE idcompras=?";
    private static final String GET_ALL_SQL = "SELECT * FROM compras";

    @Override
    public void insertar(Compra compra) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, compra.getIdCliente());
            stmt.setString(2, compra.getIdEmpleado());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    compra.setIdCompra(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public void actualizar(Compra compra) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setString(1, compra.getIdCliente());
            stmt.setString(2, compra.getIdEmpleado());
            stmt.setInt(3, compra.getIdCompra());

            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(int idCompra) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {
            
            stmt.setInt(1, idCompra);
            stmt.executeUpdate();
        }
    }

    @Override
    public Compra obtenerPorId(int idCompra) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_BY_ID_SQL)) {

            stmt.setInt(1, idCompra);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Compra(
                        rs.getInt("idcompras"),
                        rs.getString("idcliente"),
                        rs.getString("idempleado"),
                        rs.getDate("fecha_compra")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Compra> obtenerTodos() throws SQLException {
        List<Compra> compras = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_SQL)) {

            while (rs.next()) {
                compras.add(new Compra(
                    rs.getInt("idcompras"),
                    rs.getString("idcliente"),
                    rs.getString("idempleado"),
                    rs.getDate("fecha_compra")
                ));
            }
        }
        return compras;
    }
}
