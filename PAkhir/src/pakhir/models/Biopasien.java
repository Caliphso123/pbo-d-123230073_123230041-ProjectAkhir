package pakhir.models;
import java.sql.Timestamp;

public class Biopasien {
    private int id;
    private String nama;
    private int umur;
    private String keluhan;
    private int prioritas;
    private Timestamp waktudaftar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getUmur() {
        return umur;
    }

    public void setUmur(int umur) {
        this.umur = umur;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public int getPrioritas() {
        return prioritas;
    }

    public void setPrioritas(int prioritas) {
        this.prioritas = prioritas;
    }
    
    public int hitungPrioritas(){
        if (umur >= 50) {
            return 3;
        }if (umur <= 15) {
            return 2;
        }else return 1;
        
    }

    public Timestamp getWaktudaftar() {
        return waktudaftar;
    }

    public void setWaktudaftar(Timestamp waktudaftar) {
        this.waktudaftar = waktudaftar;
    }
    
}
