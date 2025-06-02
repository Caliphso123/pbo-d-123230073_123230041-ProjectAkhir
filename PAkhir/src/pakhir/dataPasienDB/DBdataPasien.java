package pakhir.dataPasienDB;
import java.sql.*;
import java.util.*;
import pakhir.connector.connector;
import pakhir.models.*;
import pakhir.DBImplement.ImplementDB;

public class DBdataPasien implements ImplementDB{
    Connection connection;
    final String select = "SELECT * FROM pasien ORDER BY prioritas DESC, waktu_daftar ASC";
    final String insert = "INSERT INTO pasien(nama, umur, keluhan, prioritas) VALUES (?, ?, ?, ?);";
    final String update = "UPDATE pasien SET nama=?, umur=?, keluhan=?, prioritas=? WHERE id=?";
    final String delete = "DELETE FROM pasien WHERE id=?";
    
    public DBdataPasien(){
        connection = connector.connection();
    }

    
    
    @Override
    public void insert(Biopasien b) {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, b.getNama());
            stmt.setInt(2, b.getUmur());
            stmt.setString(3, b.getKeluhan());
            stmt.setInt(4, b.getPrioritas());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            while (rs.next()) {
                b.setId(rs.getInt(1));    
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Biopasien b) {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(update);
            stmt.setString(1, b.getNama());
            stmt.setInt(2, b.getUmur());
            stmt.setString(3, b.getKeluhan());
            stmt.setInt(4, b.getPrioritas());
            stmt.setInt(5, b.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement stmt = null; 
        try{
            stmt = connection.prepareStatement(delete); 
            stmt.setInt(1,id); 
            stmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                stmt.close();
            } catch (SQLException e) { 
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Biopasien> getAll() {
        List<Biopasien> bp = null;
        try {
            bp = new ArrayList<Biopasien>();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(select);
            while (rs.next()) {
                Biopasien b = new Biopasien();
                b.setId(rs.getInt("id"));
                b.setNama(rs.getString("nama"));
                b.setUmur(rs.getInt("umur"));
                b.setKeluhan(rs.getString("keluhan"));
                b.setPrioritas(rs.getInt("prioritas"));
                b.setWaktudaftar(rs.getTimestamp("waktu_daftar"));
                bp.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bp;
    }

    @Override
    public String hapuspasien() {
         String namaPasien = null;

    try {
        Connection conn = connector.connection();

        // Ambil pasien teratas
        String ambilSql = "SELECT id, nama FROM pasien ORDER BY prioritas DESC, waktu_daftar ASC LIMIT 1";
        PreparedStatement ambilStmt = conn.prepareStatement(ambilSql);
        ResultSet rs = ambilStmt.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            namaPasien = rs.getString("nama");

            // Hapus berdasarkan id
            String hapusSql = "DELETE FROM pasien WHERE id = ?";
            PreparedStatement hapusStmt = conn.prepareStatement(hapusSql);
            hapusStmt.setInt(1, id);
            hapusStmt.executeUpdate();
            
            DBLogin loginDB = new DBLogin();
            loginDB.deletePasien(id);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return namaPasien;
    }
    
    
   
}
