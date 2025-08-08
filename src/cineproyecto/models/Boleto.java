/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineproyecto.models;

import java.math.BigDecimal;

public class Boleto {
    private int idBoleto;
    private int idFuncion;
    private int idCompra;
    private String asiento;
    private BigDecimal precioFinal;
    
    // Constructor vacío para nuevos registros
    public Boleto() {
    }
    
    // Constructor completo para cargar datos existentes
    public Boleto(int idBoleto, int idFuncion, int idCompra, String asiento, BigDecimal precioFinal) {
        this.idBoleto = idBoleto;
        this.idFuncion = idFuncion;
        this.idCompra = idCompra;
        this.asiento = asiento;
        this.precioFinal = precioFinal;
    }

    // Getters y Setters
    public int getIdBoleto() {
        return idBoleto;
    }

    public void setIdBoleto(int idBoleto) {
        this.idBoleto = idBoleto;
    }

    public int getIdFuncion() {
        return idFuncion;
    }

    public void setIdFuncion(int idFuncion) {
        this.idFuncion = idFuncion;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    public BigDecimal getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(BigDecimal precioFinal) {
        this.precioFinal = precioFinal;
    }
    
    // Método toString para representación textual
    @Override
    public String toString() {
        return "Boleto{" +
               "idBoleto=" + idBoleto +
               ", idFuncion=" + idFuncion +
               ", idCompra=" + idCompra +
               ", asiento='" + asiento + '\'' +
               ", precioFinal=" + precioFinal +
               '}';
    }
    
    // Método equals para comparación (opcional pero recomendado)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Boleto boleto = (Boleto) o;
        
        return idBoleto == boleto.idBoleto;
    }
    
    // Método hashCode (opcional pero recomendado cuando se implementa equals)
    @Override
    public int hashCode() {
        return idBoleto;
    }
}