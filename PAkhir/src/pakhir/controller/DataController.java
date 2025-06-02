package pakhir.controller;

import java.util.*;
import javax.swing.JOptionPane;
import pakhir.DBImplement.*;
import pakhir.dataPasienDB.*;
import pakhir.models.*;
import pakhir.DataViewBio;
import pakhir.connector.connector;
import pakhir.mainPasien;
import java.sql.*;

public class DataController {
    mainPasien p;
    DataViewBio v;
    ImplementDB DBI;
    List<Biopasien> bp;
    ImplementDBLogin DBL;
    
    public DataController(DataViewBio v){
        this.v = v;
        DBI = new DBdataPasien();
        bp = DBI.getAll();
        DBL = new DBLogin();
    }
    
    public DataController(mainPasien p){
        this.p = p;
        DBI = new DBdataPasien();
        bp = DBI.getAll();
        DBL = new DBLogin();
    }
    
    public void isiTabel(){
        bp = DBI.getAll();
        modelPasien mp = new modelPasien(bp);
        if (v != null) {
            v.getjTable1().setModel(mp);
        }else if (p != null) {
            p.getjTable1().setModel(mp);
        }
    }
    
    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return sb.toString();
}
    
    public void insert(){
        String namas = v.getjTextFieldNama().getText();
        Biopasien bp = new Biopasien();
        bp.setNama(v.getjTextFieldNama().getText());
        bp.setUmur(Integer.parseInt(v.getjTextFieldUmur().getText()));  
        bp.setKeluhan(v.getjTextFieldKeluhan().getText());  
        bp.setPrioritas(bp.hitungPrioritas());  
        DBI.insert(bp);
        
        String password = generateRandomPassword(5);
        DBL.insertLogin(namas, password, "pasien", bp.getId());
    }
    
    public void update(){
        Biopasien bp = new Biopasien();
        bp.setNama(v.getjTextFieldNama().getText());
        bp.setUmur(Integer.parseInt(v.getjTextFieldUmur().getText()));  
        bp.setKeluhan(v.getjTextFieldKeluhan().getText());  
        bp.setPrioritas(bp.hitungPrioritas());
        bp.setId(Integer.parseInt(v.getjTextFieldID().getText()));
        DBI.update(bp);
    }
    
    public void delete(){
        int id = Integer.parseInt(v.getjTextFieldID().getText());
        DBI.delete(id);
        DBL.deletePasien(id);
    }
    
    public void next(){
        String nama = DBI.hapuspasien();
        if (nama != null) {
        JOptionPane.showMessageDialog(null, "Pasien dengan nama " + nama + " silakan masuk ke ruangan.");
    } else {
        JOptionPane.showMessageDialog(null, "Tidak ada pasien dalam antrian.");
    }
    }
    
    public void batalkanKunjungan(int idPasien) {
    try {
        Connection conn = connector.connection();
        
        
        
        String deleteLogin = "DELETE FROM login WHERE id_pasien = ?";
        PreparedStatement pstLogin = conn.prepareStatement(deleteLogin);
        pstLogin.setInt(1, idPasien);
        pstLogin.executeUpdate();

        String deletePasien = "DELETE FROM pasien WHERE id = ?";
        PreparedStatement pstPasien = conn.prepareStatement(deletePasien);
        pstPasien.setInt(1, idPasien);
        pstPasien.executeUpdate();

        JOptionPane.showMessageDialog(null, "Kunjungan berhasil dibatalkan.");
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Gagal membatalkan kunjungan.");
    }
}
    
    public void highlightPasien(int idPasienLogin) {
    for (int i = 0; i < p.getjTable1().getRowCount(); i++) {
        int idTabel = Integer.parseInt(p.getjTable1().getValueAt(i, 0).toString()); // kolom 0 = id
        if (idTabel == idPasienLogin) {
             p.getjTable1().setRowSelectionInterval(i, i); // highlight baris ke-i
            p.getjTable1().scrollRectToVisible(p.getjTable1().getCellRect(i, 0, true)); // auto scroll
            break;
        }
    }
}

}
