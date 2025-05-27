/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package student.information.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MajorManagement extends JFrame {
    private JTextField txtId, txtName, txtSearch;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JTable table;
    private DefaultTableModel model;

    public MajorManagement() {
        setTitle("Major Management");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblSearch = new JLabel("Search (ID/Name):");
        lblSearch.setBounds(20, 10, 150, 25);
        add(lblSearch);

        txtSearch = new JTextField();
        txtSearch.setBounds(150, 10, 200, 25);
        add(txtSearch);

        txtSearch.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                searchMajor(txtSearch.getText());
            }
        });

        JLabel lblId = new JLabel("Major ID:");
        lblId.setBounds(20, 50, 100, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(150, 50, 150, 25);
        add(txtId);

        JLabel lblName = new JLabel("Major Name:");
        lblName.setBounds(20, 90, 100, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(150, 90, 300, 25);
        add(txtName);

        btnAdd = new JButton("Add");
        btnAdd.setBounds(20, 130, 80, 30);
        add(btnAdd);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(110, 130, 90, 30);
        add(btnUpdate);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(210, 130, 90, 30);
        add(btnDelete);

        btnClear = new JButton("Clear");
        btnClear.setBounds(310, 130, 90, 30);
        add(btnClear);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name"});

        table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(20, 180, 540, 250);
        add(pane);

        showMajors();

        // Event Handlers
        btnAdd.addActionListener(e -> addMajor());
        btnUpdate.addActionListener(e -> updateMajor());
        btnDelete.addActionListener(e -> deleteMajor());
        btnClear.addActionListener(e -> clearFields());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                txtId.setText(model.getValueAt(row, 0).toString());
                txtName.setText(model.getValueAt(row, 1).toString());
            }
        });
    }

    private void addMajor() {
        try {
            Connection conn = db.java_db();
            String sql = "INSERT INTO majors (major_id, major_name) VALUES (?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtId.getText());
            pst.setString(2, txtName.getText());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Major Added!");
            showMajors();
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void updateMajor() {
        try {
            Connection conn = db.java_db();
            String sql = "UPDATE majors SET major_name = ? WHERE major_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtName.getText());
            pst.setString(2, txtId.getText());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Major Updated!");
            showMajors();
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void deleteMajor() {
        try {
            Connection conn = db.java_db();
            String sql = "DELETE FROM majors WHERE major_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txtId.getText());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Major Deleted!");
            showMajors();
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void showMajors() {
        model.setRowCount(0);
        try {
            Connection conn = db.java_db();
            String sql = "SELECT * FROM majors";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("major_id"),
                        rs.getString("major_name")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void searchMajor(String keyword) {
        model.setRowCount(0);
        try {
            Connection conn = db.java_db();
            String sql = "SELECT * FROM majors WHERE major_id LIKE ? OR major_name LIKE ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, "%" + keyword + "%");
            pst.setString(2, "%" + keyword + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("major_id"),
                        rs.getString("major_name")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MajorManagement().setVisible(true));
    }
}
