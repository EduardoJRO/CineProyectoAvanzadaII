/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package cineproyecto.views;
import cineproyecto.connection.DatabaseConnection;
import cineproyecto.dao.DetalleCompraDAO;
import cineproyecto.dao.controller.DetalleCompraDAOImpl;
import cineproyecto.models.DetalleCompra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author LENOVO
 */
public class DetalleDialog extends javax.swing.JDialog {
 private final DetalleCompraDAO detalleDAO;
    /**
     * Creates new form DetalleDialog
     */
    public DetalleDialog(java.awt.Frame parent, boolean modal, Integer idCompraFiltro) {
        super(parent, modal);
        initComponents();
        detalleDAO = new DetalleCompraDAOImpl();
        
        cargarCombos();
        configurarTabla();
        cargarDetallesCompra(); // Cargar datos iniciales
    }
    
     private void cargarCombos() {
        // Cargar combobox de compras
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT idcompras FROM compras ORDER BY idcompras");
             ResultSet rs = stmt.executeQuery()) {
            
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            while (rs.next()) {
                model.addElement(rs.getString("idcompras"));
            }
            cmbidcompra.setModel(model);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar compras: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        // Cargar combobox de productos
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT idproducto, producto FROM productos ORDER BY producto");
             ResultSet rs = stmt.executeQuery()) {
            
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            while (rs.next()) {
                model.addElement(rs.getString("producto"));
            }
            cmbproductos.setModel(model);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar productos: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void configurarTabla() {
        String[] columnNames = {"ID Detalle", "Producto", "Cantidad", "Precio Unitario", "Subtotal"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblcompras.setModel(model);
    }
    
    private void cargarDetallesCompra() {
        String idCompraStr = (String) cmbidcompra.getSelectedItem();
        if (idCompraStr == null || idCompraStr.isEmpty()) return;
        
        try {
            int idCompra = Integer.parseInt(idCompraStr);
            List<DetalleCompra> detalles = detalleDAO.obtenerPorCompra(idCompra);
            
            DefaultTableModel model = (DefaultTableModel) tblcompras.getModel();
            model.setRowCount(0);
            
            for (DetalleCompra detalle : detalles) {
                String nombreProducto = obtenerNombreProducto(detalle.getIdProducto());
                BigDecimal subtotal = detalle.getPrecioUnitario().multiply(
                    new BigDecimal(detalle.getCantidad()));
                
                model.addRow(new Object[]{
                    detalle.getIdDetalle(),
                    nombreProducto,
                    detalle.getCantidad(),
                    "$" + detalle.getPrecioUnitario(),
                    "$" + subtotal
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar detalles: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String obtenerNombreProducto(long idProducto) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT producto FROM productos WHERE idproducto = ?")) {
            
            stmt.setLong(1, idProducto);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getString("producto") : "Desconocido";
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "Error";
        }
    }
    
    private long obtenerIdProducto(String nombreProducto) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT idproducto FROM productos WHERE producto = ?")) {
            
            stmt.setString(1, nombreProducto);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getLong("idproducto") : -1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }
    
    private BigDecimal obtenerPrecioProducto(long idProducto) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT precio_unitario FROM productos WHERE idproducto = ?")) {
            
            stmt.setLong(1, idProducto);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getBigDecimal("precio_unitario") : BigDecimal.ZERO;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return BigDecimal.ZERO;
        }
    }
    
    private boolean validarCampos() {
    // Validar que se haya seleccionado una compra
    if (cmbidcompra.getSelectedItem() == null) {
        JOptionPane.showMessageDialog(this,
            "Seleccione una compra",
            "Error", JOptionPane.ERROR_MESSAGE);
        cmbidcompra.requestFocus();
        return false;
    }
    
    // Validar que se haya seleccionado un producto
    if (cmbproductos.getSelectedItem() == null) {
        JOptionPane.showMessageDialog(this,
            "Seleccione un producto",
            "Error", JOptionPane.ERROR_MESSAGE);
        cmbproductos.requestFocus();
        return false;
    }
    
    // Validar que la cantidad sea mayor a 0
    if ((Integer) spncantidad.getValue() <= 0) {
        JOptionPane.showMessageDialog(this,
            "La cantidad debe ser mayor a 0",
            "Error", JOptionPane.ERROR_MESSAGE);
        spncantidad.requestFocus();
        return false;
    }
    
    return true;
}
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmbproductos = new javax.swing.JComboBox<>();
        cmbidcompra = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        spncantidad = new javax.swing.JSpinner();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblcompras = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

        jLabel8.setFont(new java.awt.Font("Empire", 0, 48)); // NOI18N
        jLabel8.setText("Compras Detalles");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Gadugi", 1, 12))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel1.setText("Compra:");

        jLabel2.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel2.setText("Productos:");

        cmbidcompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbidcompraActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel3.setText("Cantidad:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(28, 28, 28)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cmbproductos, javax.swing.GroupLayout.Alignment.LEADING, 0, 270, Short.MAX_VALUE)
                                .addComponent(cmbidcompra, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(spncantidad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbidcompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbproductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(spncantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        tblcompras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tblcompras);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(191, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(170, 170, 170))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
            // 1. Validar campos obligatorios
    if (!validarCampos()) {
        return;
    }
    
    try {
        // 2. Obtener datos del formulario
        int idCompra = Integer.parseInt((String) cmbidcompra.getSelectedItem());
        String nombreProducto = (String) cmbproductos.getSelectedItem();
        long idProducto = obtenerIdProducto(nombreProducto);
        int cantidad = (Integer) spncantidad.getValue();
        BigDecimal precioUnitario = obtenerPrecioProducto(idProducto);
        
        // 3. Crear objeto DetalleCompra
        DetalleCompra detalle = new DetalleCompra();
        detalle.setIdCompra(idCompra);
        detalle.setIdProducto(idProducto);
        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(precioUnitario);

        
        // 4. Guardar en la base de datos
        detalleDAO.insertar(detalle);
        
        // 5. Mostrar mensaje de éxito
        JOptionPane.showMessageDialog(this,
            "Detalle de compra guardado correctamente",
            "Éxito", JOptionPane.INFORMATION_MESSAGE);
        
        // 6. Actualizar la tabla
        cargarDetallesCompra();
        
        // 7. Limpiar campos para nueva entrada (opcional)
        spncantidad.setValue(1);
        
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this,
            "Error al guardar detalle: " + ex.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this,
            "ID de compra inválido",
            "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void cmbidcompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbidcompraActionPerformed
        // TODO add your handling code here:
        cargarDetallesCompra();
    }//GEN-LAST:event_cmbidcompraActionPerformed

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
            java.util.logging.Logger.getLogger(DetalleDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DetalleDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DetalleDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DetalleDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DetalleDialog dialog = new DetalleDialog(new javax.swing.JFrame(), true, null);
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
    private javax.swing.JComboBox<String> cmbidcompra;
    private javax.swing.JComboBox<String> cmbproductos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner spncantidad;
    private javax.swing.JTable tblcompras;
    // End of variables declaration//GEN-END:variables
}
