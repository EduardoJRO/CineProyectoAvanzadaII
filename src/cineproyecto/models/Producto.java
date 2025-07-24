/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cineproyecto.models;

import java.math.BigInteger;

/**
 *
 * @author LENOVO
 */
public class Producto {
    private BigInteger idProducto;
    private int idTipoProducto;
    private double precioUnitario;
    private String producto;
    
    public Producto() {}
    
    public Producto(BigInteger idProducto, int idTipoProducto, double PrecioUnitario, 
                  String Producto) {
        this.idProducto = idProducto;
        this.idTipoProducto = idTipoProducto;
        this.precioUnitario = PrecioUnitario;
        this.producto = Producto;
    }
    
    public BigInteger getIdProducto() { return idProducto; }
    public void setIdProducto(BigInteger idProducto) { this.idProducto = idProducto; }
    public int getIdTipoProducto() { return idTipoProducto; }
    public void setIdTipoProducto(int idTipoProducto) { this.idTipoProducto = idTipoProducto; }
    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
    public String getProducto() { return producto; }
    public void setProducto(String Producto) { this.producto = Producto; }
    
}
