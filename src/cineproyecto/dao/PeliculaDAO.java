/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineproyecto.dao;
import cineproyecto.models.Pelicula;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author JimyR
 */
public interface PeliculaDAO {
    void insertar(Pelicula pelicula) throws SQLException;
    void actualizar(Pelicula pelicula) throws SQLException;
    void eliminar(int idPelicula) throws SQLException;
    Pelicula obtenerPorId(int idPelicula) throws SQLException;
    List<Pelicula> obtenerTodos() throws SQLException;
    List<Pelicula> buscarPorTitulo(String titulo) throws SQLException;
}
