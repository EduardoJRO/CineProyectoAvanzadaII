/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cineproyecto.dao;

import cineproyecto.models.Compra;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz DAO para operaciones CRUD de Compras
 */
public interface CompraDAO {
    void insertar(Compra compra) throws SQLException;
    void actualizar(Compra compra) throws SQLException;
    void eliminar(int idCompra) throws SQLException;
    Compra obtenerPorId(int idCompra) throws SQLException;
    List<Compra> obtenerTodos() throws SQLException;
}
