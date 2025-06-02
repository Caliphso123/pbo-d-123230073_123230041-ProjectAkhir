
package pakhir.dataPasienDB;
import java.sql.*;
import pakhir.DBImplement.ImplementDBLogin;
import pakhir.connector.connector;

public class DBLogin implements ImplementDBLogin {
    Connection connection;

    public DBLogin() {
        connection = connector.connection();
    }

    @Override
    public void insertLogin(String username, String password, String keterangan, int id) {
        try {
            String sql = "INSERT INTO login (user, password, keterangan, id_pasien) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, keterangan);
            pst.setInt(4, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkLogin(String username, String password) {
        try {
            String sql = "SELECT * FROM login WHERE user=? AND password=?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getKeterangan(String username) {
        try {
            String sql = "SELECT keterangan FROM login WHERE user=?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("keterangan");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deletePasien(int id_pasien) {
        String sql = "DELETE FROM login WHERE id_pasien = ?";
    try {
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setInt(1, id_pasien);
        int rowsDeleted = pst.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Deleted successfully");
        } else {
            System.out.println("No record found with id = " + id_pasien);
        }
        //pst.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }
}