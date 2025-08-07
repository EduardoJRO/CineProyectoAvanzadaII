/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cineproyecto.dao.controller;

import cineproyecto.dao.FuncionDAO;
import cineproyecto.models.Funcion;
import cineproyecto.connection.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JimyR
 */
public class FuncionesDAOImpl implements FuncionDAO {
    
    private static final String INSERT_SQL = "INSERT INTO funciones (idpelicula, fecha, hora_inicio, idsalas, precio_unitario) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE funciones SET idpelicula=?, fecha=?, hora_inicio=?, idsalas=?, precio_unitario=? WHERE idfunciones=?";
    private static final String DELETE_SQL = "DELETE FROM funciones WHERE idfunciones=?";
    private static final String GET_BY_ID_SQL = "SELECT * FROM funciones WHERE idfunciones=?";
    private static final String GET_ALL_SQL = "SELECT * FROM funciones";
    private static final String GET_BY_PELICULA_SQL = "SELECT * FROM funciones WHERE idpelicula=?";
    private static final String GET_BY_SALA_SQL = "SELECT * FROM funciones WHERE idsalas=?";

    @Override
    public void insertar(Funcion funcion) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, funcion.getIdPelicula());
            stmt.setDate(2, new java.sql.Date(funcion.getFecha().getTime()));
            stmt.setTime(3, funcion.getHoraInicio());
            stmt.setInt(4, funcion.getIdSala());
            stmt.setDouble(5, funcion.getPrecioUnitario());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    funcion.setIdFuncion(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public void actualizar(Funcion funcion) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {

            stmt.setInt(1, funcion.getIdPelicula());
            stmt.setDate(2, new java.sql.Date(funcion.getFecha().getTime()));
            stmt.setTime(3, funcion.getHoraInicio());
            stmt.setInt(4, funcion.getIdSala());
            stmt.setDouble(5, funcion.getPrecioUnitario());
            stmt.setInt(6, funcion.getIdFuncion());

            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(int idFuncion) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {
            
            stmt.setInt(1, idFuncion);
            stmt.executeUpdate();
        }
    }

    @Override
    public Funcion obtenerPorId(int idFuncion) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_BY_ID_SQL)) {

            stmt.setInt(1, idFuncion);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Funcion(
                        rs.getInt("idfunciones"),
                        rs.getInt("idpelicula"),
                        rs.getDate("fecha"),
                        rs.getTime("hora_inicio"),
                        rs.getInt("idsalas"),
                        rs.getDouble("precio_unitario")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Funcion> obtenerTodos() throws SQLException {
        List<Funcion> funciones = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_SQL)) {

            while (rs.next()) {
                funciones.add(new Funcion(
                    rs.getInt("idfunciones"),
                    rs.getInt("idpelicula"),
                    rs.getDate("fecha"),
                    rs.getTime("hora_inicio"),
                    rs.getInt("idsalas"),
                    rs.getDouble("precio_unitario")
                ));
            }
        }
        return funciones;
    }

    @Override
    public boolean existeFuncion(int idFuncion) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_BY_ID_SQL)) {

            stmt.setInt(1, idFuncion);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public List<Funcion> buscarPorPelicula(int idPelicula) throws SQLException {
        List<Funcion> funciones = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_BY_PELICULA_SQL)) {

            stmt.setInt(1, idPelicula);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    funciones.add(new Funcion(
                        rs.getInt("idfunciones"),
                        rs.getInt("idpelicula"),
                        rs.getDate("fecha"),
                        rs.getTime("hora_inicio"),
                        rs.getInt("idsalas"),
                        rs.getDouble("precio_unitario")
                    ));
                }
            }
        }
        return funciones;
    }

    @Override
    public List<Funcion> buscarPorSala(int idSala) throws SQLException {
        List<Funcion> funciones = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_BY_SALA_SQL)) {

            stmt.setInt(1, idSala);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    funciones.add(new Funcion(
                        rs.getInt("idfunciones"),
                        rs.getInt("idpelicula"),
                        rs.getDate("fecha"),
                        rs.getTime("hora_inicio"),
                        rs.getInt("idsalas"),
                        rs.getDouble("precio_unitario")
                    ));
                }
            }
        }
        return funciones;
    }
}
