package pakhir.connector;
import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.*;

public class connector {
    static Connection conn;
    
    public static Connection connection(){
        if (conn == null) {
            MysqlDataSource data = new MysqlDataSource();
            data.setDatabaseName("antrianrs");
            data.setUser("root");
            data.setPassword("");
            try {
                conn = data.getConnection();
                System.out.println("Koneksi Berhasil");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Koneksi Gagal");
            }
        }
        return conn;
    }
    
}
