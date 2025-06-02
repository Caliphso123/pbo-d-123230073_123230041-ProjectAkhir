package pakhir.DBImplement;

public interface ImplementDBLogin {
    void insertLogin(String username, String password, String keterangan, int id);
    boolean checkLogin(String username, String password);
    String getKeterangan(String username);
    void deletePasien(int id_pasien);
}
