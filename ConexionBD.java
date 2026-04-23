import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/inventario_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection conectar() {
        try {
            // carga el driver manualmente para evitar errores
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("conexion exitosa");
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("error de conexion: " + e.getMessage());
            return null;
        }
    }
}
