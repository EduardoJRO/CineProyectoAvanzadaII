
package cineproyecto.dao;

import cineproyecto.models.Boleto;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz DAO para operaciones CRUD de Boletos
 */
public interface BoletoDAO {
    void insertar(Boleto boleto) throws SQLException;
    void actualizar(Boleto boleto) throws SQLException;
    void eliminar(int idBoleto) throws SQLException;
    Boleto obtenerPorId(int idBoleto) throws SQLException;
    List<Boleto> obtenerTodos() throws SQLException;
    List<Boleto> obtenerPorFuncion(int idFuncion) throws SQLException;
    List<Boleto> obtenerPorCompra(int idCompra) throws SQLException;
}
