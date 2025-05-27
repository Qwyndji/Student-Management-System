/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.information.system;

/**
 *
 *
 */
import java.sql.*;
import javax.swing.*;

public class db {
    static Connection conn = null;

    public static Connection java_db() {
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");

       
            String url = "jdbc:mysql://localhost:3306/studentmanagement";
            String user = "root";
            String password = ""; 

            conn = DriverManager.getConnection(url, user, password);
            return conn;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}
