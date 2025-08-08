/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package cineproyecto.views;
import cineproyecto.connection.DatabaseConnection;
import cineproyecto.dao.CompraDAO;
import cineproyecto.dao.controller.CompraDAOImpl;
import cineproyecto.models.Compra;
import cineproyecto.view.log.SessionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
/**
 *
 * @author LENOVO
 */
public class ComprasDialog extends javax.swing.JDialog {
private final CompraDAO compraDAO;
    private final Compra compra;
    /**
     * Creates new form ComprasDialog
     */
    public ComprasDialog(java.awt.Frame parent, boolean modal, Compra compra) {
        super(parent, modal);
        
        compraDAO = new CompraDAOImpl();
        this.compra = compra;
        if (!SessionManager.getInstance().isLoggedIn()) {
            JOptionPane.showMessageDialog(parent, 
                "Sesión requerida", 
                "Error de autenticación", 
                JOptionPane.ERROR_MESSAGE);
            this.dispose(); // Cierra el diálogo
            return;
        }
        initComponents();
        configurarInterfaz();
        cargarCombos();
    }
    
     private void configurarInterfaz() {
        if (compra != null) { // Modo edición
            seleccionarClienteEnCombo(compra.getIdCliente());
            seleccionarEmpleadoEnCombo(compra.getIdEmpleado());
            // La fecha se maneja automáticamente en la base de datos
            btnGuardar.setText("Actualizar");
        } else { // Modo nuevo
            btnGuardar.setText("Guardar");
        }
    }
    
    private void cargarCombos() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Cargar clientes
            PreparedStatement stmtClientes = conn.prepareStatement(
                "SELECT idcliente, nombre_cliente FROM clientes ORDER BY nombre_cliente");
            ResultSet rsClientes = stmtClientes.executeQuery();
            
            DefaultComboBoxModel<String> modelClientes = new DefaultComboBoxModel<>();
            while (rsClientes.next()) {
                modelClientes.addElement(rsClientes.getString("nombre_cliente"));
            }
            cmbCliente.setModel(modelClientes);
            
            // Cargar empleados
            PreparedStatement stmtEmpleados = conn.prepareStatement(
                "SELECT id_empleado, nombre_empleado FROM empleados ORDER BY nombre_empleado");
            ResultSet rsEmpleados = stmtEmpleados.executeQuery();
            
            DefaultComboBoxModel<String> modelEmpleados = new DefaultComboBoxModel<>();
            while (rsEmpleados.next()) {
                modelEmpleados.addElement(rsEmpleados.getString("nombre_empleado"));
            }
            cmbEmpleado.setModel(modelEmpleados);
            
            // Seleccionar valores actuales si estamos editando
            if (compra != null) {
                seleccionarClienteEnCombo(compra.getIdCliente());
                seleccionarEmpleadoEnCombo(compra.getIdEmpleado());
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar datos: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void seleccionarClienteEnCombo(String idCliente) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT nombre_cliente FROM clientes WHERE idcliente = ?")) {
            
            stmt.setString(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String nombreCliente = rs.getString("nombre_cliente");
                cmbCliente.setSelectedItem(nombreCliente);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private void seleccionarEmpleadoEnCombo(String idEmpleado) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT nombre_empleado FROM empleados WHERE id_empleado = ?")) {
            
            stmt.setString(1, idEmpleado);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String nombreEmpleado = rs.getString("nombre_empleado");
                cmbEmpleado.setSelectedItem(nombreEmpleado);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private boolean validarCampos() {
        if (cmbCliente.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente", "Error", JOptionPane.ERROR_MESSAGE);
            cmbCliente.requestFocus();
            return false;
        }
        
        if (cmbEmpleado.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado", "Error", JOptionPane.ERROR_MESSAGE);
            cmbEmpleado.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void guardarCompra() {
        if (!validarCampos()) return;
        
        try {
            Compra compraActual = (compra == null) ? new Compra() : compra;
            
            // Obtener ID del cliente seleccionado
            String nombreCliente = cmbCliente.getSelectedItem().toString();
            String idCliente = obtenerIdCliente(nombreCliente);
            compraActual.setIdCliente(idCliente);
            
            // Obtener ID del empleado seleccionado
            String nombreEmpleado = cmbEmpleado.getSelectedItem().toString();
            String idEmpleado = obtenerIdEmpleado(nombreEmpleado);
            compraActual.setIdEmpleado(idEmpleado);
            
            // La fecha se manejará automáticamente en la base de datos con GETDATE()
            
            if (compra == null) {
                compraDAO.insertar(compraActual);
                JOptionPane.showMessageDialog(this, "Compra registrada exitosamente", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                compraDAO.actualizar(compraActual);
                JOptionPane.showMessageDialog(this, "Compra actualizada exitosamente", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
            
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar compra: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String obtenerIdCliente(String nombreCliente) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT idcliente FROM clientes WHERE nombre_cliente = ?")) {
            
            stmt.setString(1, nombreCliente);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getString("idcliente") : "";
        }
    }
    
    private String obtenerIdEmpleado(String nombreEmpleado) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT id_empleado FROM empleados WHERE nombre_empleado = ?")) {
            
            stmt.setString(1, nombreEmpleado);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getString("id_empleado") : "";
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

        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmbCliente = new javax.swing.JComboBox<>();
        cmbEmpleado = new javax.swing.JComboBox<>();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel8.setFont(new java.awt.Font("Empire", 0, 48)); // NOI18N
        jLabel8.setText("Compras");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Gadugi", 1, 12))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel1.setText("Empleado:");

        jLabel2.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel2.setText("Cliente:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cmbCliente, javax.swing.GroupLayout.Alignment.LEADING, 0, 270, Short.MAX_VALUE)
                            .addComponent(cmbEmpleado, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        btnGuardar.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        btnGuardar.setText("Aceptar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Gadugi", 1, 12)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(jLabel8)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        this.guardarCompra();
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
            java.util.logging.Logger.getLogger(ComprasDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ComprasDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ComprasDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ComprasDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ComprasDialog dialog = new ComprasDialog(new javax.swing.JFrame(), true, null);
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
    private javax.swing.JComboBox<String> cmbCliente;
    private javax.swing.JComboBox<String> cmbEmpleado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
