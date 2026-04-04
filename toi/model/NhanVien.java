
package toi.model;

public class NhanVien {
    public int id;
    public String name;
    public int year;
    public String sdt;
    public String email;
    public String chucvu;

    public NhanVien(int id, String name, int year, String sdt, String email, String chucvu) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.sdt = sdt;
        this.email = email;
        this.chucvu = chucvu;
    }
    
}