
package toi.model;

public class NhanVien {
    public int id;
    public String name;
    public int year;
    public String sdt;
    public String email;
    public String chucvu;
    
    public NhanVien() {}
    public NhanVien(int id, String name, int year, String sdt, String email, String chucvu) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.sdt = sdt;
        this.email = email;
        this.chucvu = chucvu;
    }
    // viết getter và setter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public String getSdt() {
        return sdt;
    }
    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getChucvu() {
        return chucvu;
    }
    public void setChucvu(String chucvu) {
        this.chucvu = chucvu;
    }

}