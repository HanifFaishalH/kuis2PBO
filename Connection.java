import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




public class Connection extends javax.swing.JFrame {
    private static Connection con;
    public Connection() {
        initComponents();
    }

    private static void buka_koneksi(){
        if (con == null) {
            try{
                String url = "jdbc:mysql://localhost:3306/toko_komputer";
                String user = "root";
                String password = "";	
                con = DriverManager.getConnection(url, user, password);
            } catch(SQLException e){
                System.out.println("Error membuat koneksi");
            }
        }
    }
    
}