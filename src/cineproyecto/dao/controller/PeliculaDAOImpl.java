/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineproyecto.dao.controller;
import cineproyecto.dao.PeliculaDAO;
import cineproyecto.models.Pelicula;
import cineproyecto.connection.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author JimyR
 */
public class PeliculaDAOImpl implements PeliculaDAO {
    
    private static final String INSERT_SQL = "INSERT INTO pelicula (titulo, idioma_original, disponible, sinopsis, duracion_minutos, idclasificacion, idgenero) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE pelicula SET titulo=?, idioma_original=?, disponible=?, sinopsis=?, duracion_minutos=?, idclasificacion=?, idgenero=? WHERE idpelicula=?";
    private static final String DELETE_SQL = "DELETE FROM pelicula WHERE idpelicula=?";
    private static final String GET_BY_ID_SQL = "SELECT * FROM pelicula WHERE idpelicula=?";
    private static final String GET_ALL_SQL = "SELECT * FROM pelicula";
    private static final String SEARCH_SQL = "SELECT * FROM pelicula WHERE titulo LIKE ?";

    @Override
    public void insertar(Pelicula pelicula) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, pelicula.getTitulo());
            stmt.setString(2, pelicula.getIdiomaOriginal());
            stmt.setBoolean(3, pelicula.isDisponible());
            stmt.setString(4, pelicula.getSinopsis());
            stmt.setInt(5, pelicula.getDuracionMinutos());
            stmt.setInt(6, pelicula.getIdClasificacion());
            stmt.setInt(7, pelicula.getIdGenero());
            
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pelicula.setIdPelicula(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public void actualizar(Pelicula pelicula) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {
            
            stmt.setString(1, pelicula.getTitulo());
            stmt.setString(2, pelicula.getIdiomaOriginal());
            stmt.setBoolean(3, pelicula.isDisponible());
            stmt.setString(4, pelicula.getSinopsis());
            stmt.setInt(5, pelicula.getDuracionMinutos());
            stmt.setInt(6, pelicula.getIdClasificacion());
            stmt.setInt(7, pelicula.getIdGenero());
            stmt.setInt(8, pelicula.getIdPelicula());
            
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(int idPelicula) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {
            
            stmt.setInt(1, idPelicula);
            stmt.executeUpdate();
        }
    }

    @Override
    public Pelicula obtenerPorId(int idPelicula) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_BY_ID_SQL)) {
            
            stmt.setInt(1, idPelicula);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Pelicula(
                        rs.getInt("idpelicula"),
                        rs.getString("titulo"),
                        rs.getString("idioma_original"),
                        rs.getBoolean("disponible"),
                        rs.getString("sinopsis"),
                        rs.getInt("duracion_minutos"),
                        rs.getInt("idclasificacion"),
                        rs.getInt("idgenero")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Pelicula> obtenerTodos() throws SQLException {
        List<Pelicula> peliculas = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_SQL)) {
            
            while (rs.next()) {
                peliculas.add(new Pelicula(
                    rs.getInt("idpelicula"),
                    rs.getString("titulo"),
                    rs.getString("idioma_original"),
                    rs.getBoolean("disponible"),
                    rs.getString("sinopsis"),
                    rs.getInt("duracion_minutos"),
                    rs.getInt("idclasificacion"),
                    rs.getInt("idgenero")
                ));
            }
        }
        return peliculas;
    }

    @Override
    public List<Pelicula> buscarPorTitulo(String titulo) throws SQLException {
        List<Pelicula> peliculas = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SEARCH_SQL)) {
            
            stmt.setString(1, "%" + titulo + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    peliculas.add(new Pelicula(
                        rs.getInt("idpelicula"),
                        rs.getString("titulo"),
                        rs.getString("idioma_original"),
                        rs.getBoolean("disponible"),
                        rs.getString("sinopsis"),
                        rs.getInt("duracion_minutos"),
                        rs.getInt("idclasificacion"),
                        rs.getInt("idgenero")
                    ));
                }
            }
        }
        return peliculas;
    }
}