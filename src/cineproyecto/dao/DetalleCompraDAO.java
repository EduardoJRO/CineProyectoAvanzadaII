/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cineproyecto.dao;
import cineproyecto.models.DetalleCompra;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz DAO para operaciones CRUD de DetalleCompra
 */
public interface DetalleCompraDAO {
    void insertar(DetalleCompra detalle) throws SQLException;
    void actualizar(DetalleCompra detalle) throws SQLException;
    void eliminar(int idDetalle) throws SQLException;
    DetalleCompra obtenerPorId(int idDetalle) throws SQLException;
    List<DetalleCompra> obtenerPorCompra(int idCompra) throws SQLException;
    List<DetalleCompra> obtenerTodos() throws SQLException;
}
