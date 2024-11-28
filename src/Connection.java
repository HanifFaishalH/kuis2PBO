import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

public class Connection extends javax.swing.JFrame {

        private Connection con;

        private void buka_koneksi() {
            if (con == null) {
                try {
                    String url = "jdbc:mysql://localhost:3306/toko_komputer";
                    String user = "root";
                    String password = "";
                    con = (Connection)DriverManager.getConnection(url, user, password);
                } catch (SQLException e) {
                    System.out.println("Error membuat koneksi");
                }
            }
        }

    
    }
