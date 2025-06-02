package pakhir.controller;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import pakhir.DataViewBio;
import pakhir.Login;
import pakhir.connector.connector;
import pakhir.mainPasien;

public class DataControllerLogin {
    Login l;
    
    public DataControllerLogin(Login l){
        this.l = l;
    }
    
    public void login(){
        String username = l.getjTextFieldUser().getText();
        String password = new String(l.getjPasswordField().getPassword());
        
        try {
            Connection conn = connector.connection();
            String sql = "SELECT * FROM login where user=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String keterangan = rs.getString("keterangan");
                int idPasien = rs.getInt("id_pasien");
                if (keterangan.equalsIgnoreCase("admin")) {
                    DataViewBio v = new DataViewBio();
                    v.setVisible(true);
                    v.setLocationRelativeTo(null);
                    l.dispose();
                }else if (keterangan.equalsIgnoreCase("pasien")){
                    mainPasien p = new mainPasien(idPasien);
                    p.setVisible(true);
                    p.setLocationRelativeTo(null);
                    l.dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "Peran tidak dikenali: " + keterangan);
                }  
        } else {
            JOptionPane.showMessageDialog(null, "Username atau password salah.");
        }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal login: " + e.getMessage());
        }
    }
}
