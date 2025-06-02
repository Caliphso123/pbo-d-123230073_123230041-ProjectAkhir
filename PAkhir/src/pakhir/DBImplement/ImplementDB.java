package pakhir.DBImplement;
import pakhir.models.*;
import java.util.*;

public interface ImplementDB {
    public void insert(Biopasien b);
    public void update(Biopasien b);
    public void delete(int id);
    public String hapuspasien();
    public List<Biopasien>getAll();
}
