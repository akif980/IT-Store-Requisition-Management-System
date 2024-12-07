/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import database.DB;
import server.ServerClient;
import table.TableCustom;

/**
 *
 * @author Rafeed
 */
public class UnresolvedDeliveryForm extends javax.swing.JPanel implements RefreshButtonFunction {

    private ServerClient client;
    /**
     * Creates new form UnresolvedDeliveryForm
     */
    public UnresolvedDeliveryForm() {
        initComponents();
    }
    
    public UnresolvedDeliveryForm(ServerClient client) {
        this();
        this.client = client;
        TableCustom.apply(jScrollPane1, TableCustom.TableType.MULTI_LINE);
        showUnresolvedDelivery();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tableScrollButton1 = new table.TableScrollButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setOpaque(false);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order ID", "Product Name", "Quantity", "Branch", "Date"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        tableScrollButton1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tableScrollButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 886, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tableScrollButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private table.TableScrollButton tableScrollButton1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void refresh() {
        showUnresolvedDelivery();
    }
    
    private ArrayList<Order> unresolvedDeliveryList() {
        ArrayList<Order> list = new ArrayList<>();
        try {
            ResultSet rs = new DB().executeQuery("SELECT * FROM UnresolvedDelivary ORDER BY OrderID DESC");
            Order order;
            while (rs.next()) {
                order = new Order(rs.getInt("OrderID"), rs.getInt("ProductID"), rs.getInt("Quantity"), 0, rs.getString("Date"), "declined", rs.getInt("BranchID"));
                list.add(order);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderRequestForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    private void showUnresolvedDelivery() {
        ArrayList<Order> unresolveDeliveries = unresolvedDeliveryList();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        Object[] row = new Object[5];
        for (int i = 0; i < unresolveDeliveries.size(); i++) {
            row[0] = unresolveDeliveries.get(i).getOrderID();
            row[1] = unresolveDeliveries.get(i).getProductName();
            row[2] = unresolveDeliveries.get(i).getQuantity();
            row[3] = unresolveDeliveries.get(i).getBranchName();
            row[4] = unresolveDeliveries.get(i).getDate();
            model.addRow(row);
        }
    }
}