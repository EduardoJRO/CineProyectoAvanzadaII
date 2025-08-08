/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cineproyecto.view.log;

public class SessionManager {
    private static SessionManager instance;
    private boolean loggedIn = false;
    private String userEmail;
    
    private SessionManager() {}
    
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    
    public void login(String email) {
        this.loggedIn = true;
        this.userEmail = email;
    }
    
    public void logout() {
        this.loggedIn = false;
        this.userEmail = null;
    }
    
    public boolean isLoggedIn() {
        return loggedIn;
    }
    
    public String getUserEmail() {
        return userEmail;
    }
}
