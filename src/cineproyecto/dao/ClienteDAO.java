/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineproyecto.dao;
import cineproyecto.models.Cliente;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author JimyR
 */
public interface ClienteDAO {
    // CRUD básico
    void insertar(Cliente cliente) throws SQLException;
    void actualizar(Cliente cliente) throws SQLException;
    void eliminar(String idCliente) throws SQLException;
    Cliente obtenerPorId(String idCliente) throws SQLException;
    List<Cliente> obtenerTodos() throws SQLException;
    
    // Métodos adicionales
    boolean existeCliente(String idCliente) throws SQLException;
    List<Cliente> buscarPorNombre(String nombre) throws SQLException;
}
