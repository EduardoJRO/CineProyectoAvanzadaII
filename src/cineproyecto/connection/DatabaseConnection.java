/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineproyecto.connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author JimyR
 */
public class DatabaseConnection {
         private static final String URL = "jdbc:mysql://localhost:3308/cineproyecto";
    private static final String USER = "root"; // Cambiar por tu usuario MySQL
    private static final String PASS = "monoenoshima.01"; // Cambiar por tu contraseña
    
    public static Connection getConnection() throws SQLException {
         return DriverManager.getConnection(URL, USER, PASS);
    }
    
    public static void main(String[] args) {
        try {
            Connection conn = getConnection();
            System.out.println("¡Conexión exitosa a la base de datos!");
            conn.close(); // Cerrar la conexión
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos:");
            e.printStackTrace();
}
    }
}
