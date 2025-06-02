package pakhir.models;
import java.util.*;
import javax.swing.table.AbstractTableModel;

public class modelPasien extends AbstractTableModel{
    
    List<Biopasien> bp;
   public modelPasien(List<Biopasien> bp){
       this.bp = bp;
   }

    @Override
    public int getRowCount() {
        return bp.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }
    
    @Override
    public String getColumnName(int column){
        switch(column){
            case 0:
                return "ID";
            case 1:
                return "Nama";
            case 2:
                return "Umur";
            case 3:
                return "Keluhan";
            case 4:
                return "Prioritas";
            case 5:
                return "Waktu Daftar";
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int row, int column) {
         switch (column) {
            case 0:
                return bp.get(row).getId();
            case 1:
                return bp.get(row).getNama();
            case 2:
                return bp.get(row).getUmur();
            case 3:
                return bp.get(row).getKeluhan();
            case 4:
                return bp.get(row).getPrioritas();
            case 5:
                return bp.get(row).getWaktudaftar();
            default:
                return null;
        }
    }
    
}
