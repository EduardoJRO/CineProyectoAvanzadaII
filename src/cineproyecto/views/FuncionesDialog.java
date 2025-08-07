/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package cineproyecto.views;
import cineproyecto.connection.DatabaseConnection;
import cineproyecto.dao.FuncionDAO;
import cineproyecto.dao.controller.FuncionesDAOImpl;
import cineproyecto.models.Funcion;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.DefaultComboBoxModel;
/**
 *
 * @author LENOVO
 */
public class FuncionesDialog extends javax.swing.JDialog {
private final FuncionDAO funcionDAO;
    private final Funcion funcion;
    /**
     * Creates new form FuncionesDialog
     */
    public FuncionesDialog(java.awt.Frame parent, boolean modal, Funcion funcion) {
        super(parent, modal);
        initComponents();
        funcionDAO = new FuncionesDAOImpl();
        this.funcion = funcion;
        configurarInterfaz();
        cargarPeliculas();
        cargarSalas();
    }
    
    
    private void configurarInterfaz() {
    if (funcion != null) { // Modo edición
        txtid.setText(String.valueOf(funcion.getIdFuncion()));
        txtFecha.setText(new SimpleDateFormat("yyyy-MM-dd").format(funcion.getFecha()));
        txtHora.setText(new SimpleDateFormat("HH:mm").format(funcion.getHoraInicio()));
        txtPrecio.setText(String.valueOf(funcion.getPrecioUnitario()));
        btnGuardar.setText("Actualizar");
        txtid.setEnabled(false);
    } else { // Modo nuevo
        btnGuardar.setText("Guardar");
        txtFecha.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        txtHora.setText("00:00");
        txtid.setText("Automático"); // Mostrar que es generado por la BD
        txtid.setEnabled(false); // Deshabilitar el campo para nuevos registros
    }
    }
    
    private void cargarPeliculas() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT idpelicula, titulo FROM pelicula");
             ResultSet rs = stmt.executeQuery()) {
            
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            while (rs.next()) {
                model.addElement(rs.getString("titulo"));
            }
            cmbPelicula.setModel(model);
            
            // Seleccionar valor actual si estamos editando
            if (funcion != null) {
                seleccionarPelicula(funcion.getIdPelicula());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar películas: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarSalas() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT idsalas, nombre_sala FROM salas");
             ResultSet rs = stmt.executeQuery()) {
            
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            while (rs.next()) {
                model.addElement(rs.getString("nombre_sala"));
            }
            cmbSala.setModel(model);
            
            // Seleccionar valor actual si estamos editando
            if (funcion != null) {
                seleccionarSala(funcion.getIdSala());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar salas: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void seleccionarPelicula(int idPelicula) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT titulo FROM pelicula WHERE idpelicula = ?")) {
            
            stmt.setInt(1, idPelicula);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                cmbPelicula.setSelectedItem(rs.getString("titulo"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private void seleccionarSala(int idSala) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT nombre_sala FROM salas WHERE idsalas = ?")) {
            
            stmt.setInt(1, idSala);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                cmbSala.setSelectedItem(rs.getString("nombre_sala"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private int obtenerIdPeliculaSeleccionada() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT idpelicula FROM pelicula WHERE titulo = ?")) {
            
            stmt.setString(1, cmbPelicula.getSelectedItem().toString());
            ResultSet rs = stmt.executeQuery();
            
            return rs.next() ? rs.getInt("idpelicula") : -1;
        }
    }
    
    private int obtenerIdSalaSeleccionada() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT idsalas FROM salas WHERE nombre_sala = ?")) {
            
            stmt.setString(1, cmbSala.getSelectedItem().toString());
            ResultSet rs = stmt.executeQuery();
            
            return rs.next() ? rs.getInt("idsalas") : -1;
        }
    }
    
    private boolean validarCampos() {
        // Eliminar validación del ID para nuevos registros
        if (funcion != null) { // Solo validar ID en edición
            try {
                Integer.parseInt(txtid.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número válido", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                txtid.requestFocus();
                return false;
            }
        }

        // Resto de validaciones permanecen igual...
        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(txtFecha.getText().trim());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido (yyyy-MM-dd)", 
                "Error", JOptionPane.ERROR_MESSAGE);
            txtFecha.requestFocus();
            return false;
        }

        try {
            new SimpleDateFormat("HH:mm").parse(txtHora.getText().trim());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de hora inválido (HH:mm)", 
                "Error", JOptionPane.ERROR_MESSAGE);
            txtHora.requestFocus();
            return false;
        }

        try {
            Double.parseDouble(txtPrecio.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un precio válido", 
                "Error", JOptionPane.ERROR_MESSAGE);
            txtPrecio.requestFocus();
            return false;
        }

        return true;
    }
    
    
     private void guardarFuncion() {
            if (!validarCampos()) return;

            try {
                Funcion funcionActual = (funcion == null) ? new Funcion() : funcion;

                // Solo establecer ID si estamos editando
                if (funcion != null) {
                    funcionActual.setIdFuncion(Integer.parseInt(txtid.getText().trim()));
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                funcionActual.setFecha(dateFormat.parse(txtFecha.getText().trim()));

                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                funcionActual.setHoraInicio(new Time(timeFormat.parse(txtHora.getText().trim()).getTime()));

                funcionActual.setPrecioUnitario(Double.parseDouble(txtPrecio.getText().trim()));

                int idPelicula = obtenerIdPeliculaSeleccionada();
                if (idPelicula == -1) {
                    JOptionPane.showMessageDialog(this, "Seleccione una película válida", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                funcionActual.setIdPelicula(idPelicula);

                int idSala = obtenerIdSalaSeleccionada();
                if (idSala == -1) {
                    JOptionPane.showMessageDialog(this, "Seleccione una sala válida", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                funcionActual.setIdSala(idSala);

                if (funcion == null) { // Nuevo
                    funcionDAO.insertar(funcionActual);
                    JOptionPane.showMessageDialog(this, "Función registrada exitosamente", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else { // Edición
                    funcionDAO.actualizar(funcionActual);
                    JOptionPane.showMessageDialog(this, "Función actualizada exitosamente", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }

                dispose();
            } catch (SQLException | ParseException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar función: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();
        txtHora = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cmbPelicula = new javax.swing.JComboBox<>();
        cmbSala = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Gadugi", 1, 12))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel2.setText("ID de la Funcion:");

        jLabel3.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel3.setText("Fecha:");

        jLabel4.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel4.setText("Hora de Inicio:");

        txtid.setEditable(false);

        jLabel6.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel6.setText("Pelicula:");

        jLabel7.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel7.setText("Sala:");

        jLabel5.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel5.setText("Precio Unitario:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(cmbSala, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cmbPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cmbSala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Controles", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Gadugi", 1, 12))); // NOI18N

        btnGuardar.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        btnGuardar.setText("Aceptar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))
                .addGap(14, 14, 14))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );

        jLabel1.setFont(new java.awt.Font("Empire", 0, 48)); // NOI18N
        jLabel1.setText("Funciones");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(261, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(257, 257, 257))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        this.guardarFuncion();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FuncionesDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FuncionesDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FuncionesDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FuncionesDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FuncionesDialog dialog = new FuncionesDialog(new javax.swing.JFrame(), true, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbPelicula;
    private javax.swing.JComboBox<String> cmbSala;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtHora;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtid;
    // End of variables declaration//GEN-END:variables
}
