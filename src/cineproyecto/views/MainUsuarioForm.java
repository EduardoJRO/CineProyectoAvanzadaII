/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package cineproyecto.views;
import cineproyecto.dao.controller.UsuarioDAO;
import cineproyecto.models.Usuarios;
import cineproyecto.view.log.MainAcceso;
import cineproyecto.view.log.SessionManager;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author LENOVO
 */
public class MainUsuarioForm extends javax.swing.JFrame {
private final UsuarioDAO usuarioDAO;
private Usuarios usuarioActual;
    /**
     * Creates new form MainUsuarioForm
     */
    public MainUsuarioForm() {
        usuarioDAO = UsuarioDAO.getInstance();
        if (!SessionManager.getInstance().isLoggedIn()) {
            JOptionPane.showMessageDialog(null, 
                "Acceso no autorizado", 
                "Debe iniciar sesión", 
                JOptionPane.ERROR_MESSAGE);
            this.dispose(); // Cierra este formulario
            new MainAcceso().setVisible(true); // Redirige al login
            return;
        }
        initComponents();
        setLocationRelativeTo(null); // Centrar en la pantalla
        
        
        
        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {}, // Datos vacíos inicialmente
            new String [] {"ID Usuario", "Correo", "Activo"} // Nombres de columnas
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        
        // Listener para doble clic en tabla
        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    cargarDatosDesdeTabla();
                }
            }
        });
        
        cargarTablaUsuarios();
        setLocationRelativeTo(null); // Centrar la ventana
        setTitle("Gestión de Usuarios"); // Título de la ventana
        
         setAlwaysOnTop(true); // Temporalmente para traerlo al frente
    toFront(); // Forzar al frente
    requestFocus(); // Solicitar foco
    
    // Quitar el alwaysOnTop después de mostrarse
    addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowOpened(java.awt.event.WindowEvent e) {
            setAlwaysOnTop(false);
        }
    });
        
    }
    
    
    
    public void setUsuarioActual(Usuarios usuario) {
        this.usuarioActual = usuario;
    }

    private void cargarTablaUsuarios() {
    try {
        // Obtener los datos de la base de datos
        List<Usuarios> usuarios = usuarioDAO.listarUsuarios();
        
        // Obtener el modelo de la tabla
        DefaultTableModel model = (DefaultTableModel) tblUsuarios.getModel();
        
        // Limpiar la tabla
        model.setRowCount(0);
        
        // Llenar la tabla con los datos
        for (Usuarios usuario : usuarios) {
            Object[] fila = {
                usuario.getId_usuario(),
                usuario.getCorreo(),
                usuario.isActivo() ? "Activo" : "Inactivo" // Mostrar estado legible
            };
            model.addRow(fila);
        }
        
        // Ajustar el ancho de las columnas
        if (tblUsuarios.getColumnModel().getColumnCount() > 0) {
            tblUsuarios.getColumnModel().getColumn(0).setPreferredWidth(80);  // ID
            tblUsuarios.getColumnModel().getColumn(1).setPreferredWidth(250); // Correo
            tblUsuarios.getColumnModel().getColumn(2).setPreferredWidth(80);  // Estado
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Error al cargar los usuarios: " + e.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
    private void cargarDatosDesdeTabla() {
        int filaSeleccionada = tblUsuarios.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor seleccione un usuario de la tabla",
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        DefaultTableModel model = (DefaultTableModel) tblUsuarios.getModel();
        int idUsuario = (int) model.getValueAt(filaSeleccionada, 0);
        String correo = (String) model.getValueAt(filaSeleccionada, 1);
        String estado = (String) model.getValueAt(filaSeleccionada, 2);
        
        txtCorreo.setText(correo);
        txtPass.setText("");
        chkDisponible.setSelected(estado.equals("Activo"));
        
        // Guardar el ID para actualización
        txtCorreo.putClientProperty("id_usuario", idUsuario);
    }
    
    private void limpiarCampos() {
        txtCorreo.setText("");
        txtPass.setText("");
        txtPassAdmin.setText("");
        chkDisponible.setSelected(false);
        limpiarSeleccion();
    }
    
    private void limpiarSeleccion() {
        txtCorreo.putClientProperty("id_usuario", null);
        tblUsuarios.clearSelection();
    }
    
    private boolean validarFormatoCorreo(String correo) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return correo.matches(regex);
    }
    
    private boolean validarFormatoContrasena(String contrasena) {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
        return contrasena.matches(regex);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        txtPassAdmin = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        txtPass = new javax.swing.JPasswordField();
        chkDisponible = new javax.swing.JCheckBox();
        btnCancelar = new javax.swing.JButton();
        btnactualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel4.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel4.setText("Contraseña del Administrador:");

        txtPassAdmin.setToolTipText("Es necesario confirmar que usted es el administrador.");

        jLabel1.setFont(new java.awt.Font("Empire", 0, 48)); // NOI18N
        jLabel1.setText("Nuevo Usuario");

        btnGuardar.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        btnGuardar.setText("Aceptar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Gadugi", 1, 12))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel2.setText("Correo del Empleado:");

        jLabel3.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel3.setText("Contraseña del Empleado:");

        chkDisponible.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        chkDisponible.setText("Disponible");
        chkDisponible.setToolTipText("La Pelicula aun esta en las Salas de Cine");
        chkDisponible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDisponibleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                    .addComponent(txtPass))
                .addGap(32, 32, 32)
                .addComponent(chkDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkDisponible))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        btnCancelar.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnactualizar.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        btnactualizar.setText("Actualizar");
        btnactualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnactualizarActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("Gadugi", 1, 18)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblUsuarios);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(142, 142, 142))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4)
                            .addComponent(txtPassAdmin)
                            .addComponent(btnactualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnGuardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPassAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String correo = txtCorreo.getText().trim();
        String password = new String(txtPass.getPassword());
        String adminPass = new String(txtPassAdmin.getPassword());
        boolean activo = chkDisponible.isSelected();
        
        // Validaciones
        if (correo.isEmpty() || password.isEmpty() || adminPass.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Todos los campos son obligatorios", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!validarFormatoCorreo(correo)) {
            JOptionPane.showMessageDialog(this, 
                "El formato del correo no es válido",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (usuarioDAO.existeCorreo(correo)) {
            JOptionPane.showMessageDialog(this, 
                "El correo ya está registrado",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!validarFormatoContrasena(password)) {
            JOptionPane.showMessageDialog(this, 
                "La contraseña debe contener:\n- 8+ caracteres\n- 1 letra\n- 1 número\n- 1 caracter especial",
                "Error en contraseña", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!usuarioDAO.validarCredencialesGerente(adminPass)) {
            JOptionPane.showMessageDialog(this, 
                "Contraseña de administrador incorrecta",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Crear y guardar usuario
        Usuarios nuevoUsuario = new Usuarios();
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setContrasena_hash(password);
        nuevoUsuario.setActivo(activo);
        
        try {
            int resultado = usuarioDAO.insertar(nuevoUsuario);
            
            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, 
                    "Usuario creado exitosamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarTablaUsuarios();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al crear usuario",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void chkDisponibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDisponibleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkDisponibleActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.limpiarCampos();
        this.limpiarCampos();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnactualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizarActionPerformed
         Object idObj = txtCorreo.getClientProperty("id_usuario");
        if (idObj == null) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione un usuario haciendo doble clic en la tabla",
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int idUsuario = (int) idObj;
        String correo = txtCorreo.getText().trim();
        String password = new String(txtPass.getPassword());
        String adminPass = new String(txtPassAdmin.getPassword());
        boolean activo = chkDisponible.isSelected();
        
        // Validaciones
        if (correo.isEmpty() || adminPass.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Correo y contraseña de admin son obligatorios", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!usuarioDAO.validarCredencialesGerente(adminPass)) {
            JOptionPane.showMessageDialog(this, 
                "Solo gerentes pueden actualizar usuarios",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Actualizar usuario
        Usuarios usuario = new Usuarios();
        usuario.setId_usuario(idUsuario);
        usuario.setCorreo(correo);
        usuario.setActivo(activo);
        
        if (!password.isEmpty()) {
            if (!validarFormatoContrasena(password)) {
                JOptionPane.showMessageDialog(this, 
                    "Formato de contraseña inválido",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            usuario.setContrasena_hash(password);
        }
        
        try {
            int resultado = usuarioDAO.actualizar(usuario);
            
            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, 
                    "Usuario actualizado",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarTablaUsuarios();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al actualizar",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnactualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int filaSeleccionada = tblUsuarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione un usuario de la tabla",
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        DefaultTableModel model = (DefaultTableModel) tblUsuarios.getModel();
        int idUsuario = (int) model.getValueAt(filaSeleccionada, 0);
        String correo = (String) model.getValueAt(filaSeleccionada, 1);
        
        // Validar que no se elimine a sí mismo
        if (usuarioActual != null && usuarioActual.getId_usuario() == idUsuario) {
            JOptionPane.showMessageDialog(this,
                "No puede eliminarse a sí mismo",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Eliminar al usuario " + correo + "?",
            "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        
        String adminPass = JOptionPane.showInputDialog(this,
            "Ingrese contraseña de administrador:");
        
        if (adminPass == null || adminPass.isEmpty()) {
            return;
        }
        
        if (!usuarioDAO.validarCredencialesGerente(adminPass)) {
            JOptionPane.showMessageDialog(this,
                "Contraseña de admin incorrecta",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            int resultado = usuarioDAO.eliminar(idUsuario);
            
            if (resultado > 0) {
                JOptionPane.showMessageDialog(this,
                    "Usuario eliminado",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarTablaUsuarios();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al eliminar: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

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
            java.util.logging.Logger.getLogger(MainUsuarioForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainUsuarioForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainUsuarioForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainUsuarioForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainUsuarioForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnactualizar;
    private javax.swing.JCheckBox chkDisponible;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JPasswordField txtPassAdmin;
    // End of variables declaration//GEN-END:variables
}
