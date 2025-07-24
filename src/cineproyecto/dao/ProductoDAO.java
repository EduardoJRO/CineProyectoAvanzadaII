/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cineproyecto.dao;
import cineproyecto.models.Producto;
import java.sql.SQLException;
import java.util.List;
import java.math.BigInteger;
/**
 *
 * @author LENOVO
 */
public interface ProductoDAO {
    void insertar(Producto producto) throws SQLException;
    void actualizar(Producto producto) throws SQLException;
    void eliminar(BigInteger idProducto) throws SQLException;
    Producto obtenerPorId(BigInteger idProducto) throws SQLException;
    List<Producto> obtenerTodos() throws SQLException;
    
    boolean existeProducto(BigInteger idProducto) throws SQLException;
    List<Producto> buscarPorBarras(BigInteger codbarras) throws SQLException;
}
