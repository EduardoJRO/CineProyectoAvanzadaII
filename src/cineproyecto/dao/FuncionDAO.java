/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cineproyecto.dao;
import cineproyecto.models.Funcion;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public interface FuncionDAO {
    void insertar(Funcion funcion) throws SQLException;
    void actualizar(Funcion funcion) throws SQLException;
    void eliminar(int idFuncion) throws SQLException;
    Funcion obtenerPorId(int idFuncion) throws SQLException;
    List<Funcion> obtenerTodos() throws SQLException;
    
    boolean existeFuncion(int idFuncion) throws SQLException;
    List<Funcion> buscarPorPelicula(int idPelicula) throws SQLException;
    List<Funcion> buscarPorSala(int idSala) throws SQLException;
}