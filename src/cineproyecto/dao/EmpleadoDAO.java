/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cineproyecto.dao;
import cineproyecto.models.Empleado;
import java.sql.SQLException;
import java.util.List;

public interface EmpleadoDAO {
    void insertar(Empleado empleado) throws SQLException;
    void actualizar(Empleado empleado) throws SQLException;
    void eliminar(String idEmpleado) throws SQLException;
    Empleado obtenerPorId(String idEmpleado) throws SQLException;
    List<Empleado> obtenerTodos() throws SQLException;
    
    boolean existeEmpleado(String idEmpleado) throws SQLException;
    List<Empleado> buscarPorNombre(String nombre) throws SQLException;
}