/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineproyecto.models;

/**
 *
 * @author JimyR
 */
public class Pelicula {
    private int idPelicula;
    private String titulo;
    private int idIdioma;
    private boolean disponible;
    private String sinopsis;
    private int duracionMinutos;
    private int idClasificacion;
    private int idGenero;

    // Constructores
    public Pelicula() {}

    public Pelicula(int idPelicula, String titulo, int idIdioma, boolean disponible, 
                   String sinopsis, int duracionMinutos, int idClasificacion, int idGenero) {
        this.idPelicula = idPelicula;
        this.titulo = titulo;
        this.idIdioma = idIdioma;
        this.disponible = disponible;
        this.sinopsis = sinopsis;
        this.duracionMinutos = duracionMinutos;
        this.idClasificacion = idClasificacion;
        this.idGenero = idGenero;
    }

   // Getters y Setters actualizados
    public int getIdPelicula() { return idPelicula; }
    public void setIdPelicula(int idPelicula) { this.idPelicula = idPelicula; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public int getIdIdioma() { return idIdioma; }
    public void setIdIdioma(int idIdioma) { this.idIdioma = idIdioma; }
    
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    
    public String getSinopsis() { return sinopsis; }
    public void setSinopsis(String sinopsis) { this.sinopsis = sinopsis; }
    
    public int getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(int duracionMinutos) { this.duracionMinutos = duracionMinutos; }
    
    public int getIdClasificacion() { return idClasificacion; }
    public void setIdClasificacion(int idClasificacion) { this.idClasificacion = idClasificacion; }
    
    public int getIdGenero() { return idGenero; }
    public void setIdGenero(int idGenero) { this.idGenero = idGenero; }
}
