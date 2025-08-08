/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineproyecto.dao.controller;

import cineproyecto.dao.BoletoDAO;
import cineproyecto.models.Boleto;
import cineproyecto.connection.DatabaseConnection;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoletoDAOImpl implements BoletoDAO {
    private static final String INSERT_SQL = "INSERT INTO boletos (idfuncion, idcompra, asiento, precio_final) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE boletos SET idfuncion=?, idcompra=?, asiento=?, precio_final=? WHERE id_boleto=?";
    private static final String DELETE_SQL = "DELETE FROM boletos WHERE id_boleto=?";
    private static final String GET_BY_ID_SQL = "SELECT * FROM boletos WHERE id_boleto=?";
    private static final String GET_BY_FUNCION_SQL = "SELECT * FROM boletos WHERE idfuncion=?";
    private static final String GET_BY_COMPRA_SQL = "SELECT * FROM boletos WHERE idcompra=?";
    private static final String GET_ALL_SQL = "SELECT * FROM boletos";
    private static final String GET_FUNCION_PRICE_SQL = "SELECT precio_unitario FROM funciones WHERE idfunciones=?";

    private Connection connection;

    public BoletoDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insertar(Boleto boleto) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            BigDecimal precioFinal = obtenerPrecioFuncion(boleto.getIdFuncion());
            
            stmt.setInt(1, boleto.getIdFuncion());
            stmt.setInt(2, boleto.getIdCompra());
            stmt.setString(3, boleto.getAsiento());
            stmt.setBigDecimal(4, precioFinal);

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    boleto.setIdBoleto(generatedKeys.getInt(1));
                    boleto.setPrecioFinal(precioFinal);
                }
            }
        }
    }

    @Override
    public void actualizar(Boleto boleto) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_SQL)) {
            BigDecimal precioFinal = obtenerPrecioFuncion(boleto.getIdFuncion());
            
            stmt.setInt(1, boleto.getIdFuncion());
            stmt.setInt(2, boleto.getIdCompra());
            stmt.setString(3, boleto.getAsiento());
            stmt.setBigDecimal(4, precioFinal);
            stmt.setInt(5, boleto.getIdBoleto());

            stmt.executeUpdate();
            
            boleto.setPrecioFinal(precioFinal);
        }
    }

    @Override
    public void eliminar(int idBoleto) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_SQL)) {
            stmt.setInt(1, idBoleto);
            stmt.executeUpdate();
        }
    }

    @Override
    public Boleto obtenerPorId(int idBoleto) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(GET_BY_ID_SQL)) {
            stmt.setInt(1, idBoleto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Boleto(
                        rs.getInt("id_boleto"),
                        rs.getInt("idfuncion"),
                        rs.getInt("idcompra"),
                        rs.getString("asiento"),
                        rs.getBigDecimal("precio_final")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Boleto> obtenerPorFuncion(int idFuncion) throws SQLException {
        List<Boleto> boletos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(GET_BY_FUNCION_SQL)) {
            stmt.setInt(1, idFuncion);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    boletos.add(new Boleto(
                        rs.getInt("id_boleto"),
                        rs.getInt("idfuncion"),
                        rs.getInt("idcompra"),
                        rs.getString("asiento"),
                        rs.getBigDecimal("precio_final")
                    ));
                }
            }
        }
        return boletos;
    }

    @Override
    public List<Boleto> obtenerPorCompra(int idCompra) throws SQLException {
        List<Boleto> boletos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(GET_BY_COMPRA_SQL)) {
            stmt.setInt(1, idCompra);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    boletos.add(new Boleto(
                        rs.getInt("id_boleto"),
                        rs.getInt("idfuncion"),
                        rs.getInt("idcompra"),
                        rs.getString("asiento"),
                        rs.getBigDecimal("precio_final")
                    ));
                }
            }
        }
        return boletos;
    }

    @Override
    public List<Boleto> obtenerTodos() throws SQLException {
        List<Boleto> boletos = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_SQL)) {
            while (rs.next()) {
                boletos.add(new Boleto(
                    rs.getInt("id_boleto"),
                    rs.getInt("idfuncion"),
                    rs.getInt("idcompra"),
                    rs.getString("asiento"),
                    rs.getBigDecimal("precio_final")
                ));
            }
        }
        return boletos;
    }

    private BigDecimal obtenerPrecioFuncion(int idFuncion) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(GET_FUNCION_PRICE_SQL)) {
            stmt.setInt(1, idFuncion);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("precio_unitario");
                } else {
                    throw new SQLException("Función no encontrada con ID: " + idFuncion);
                }
            }
        }
    }
}