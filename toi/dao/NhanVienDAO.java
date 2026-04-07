
package toi.dao;

import toi.model.NhanVien;
import toi.database.DBConnection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.*;

public class NhanVienDAO {
   
    
    // CREATE
    public void addNhanVien(NhanVien nv) {
        String sql = "INSERT INTO bangnhanvien(name, year, sdt, email, chucvu) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nv.name);
            ps.setInt(2, nv.year);
            ps.setString(3, nv.sdt);
            ps.setString(4, nv.email);
            ps.setString(5, nv.chucvu);
            ps.executeUpdate();
            System.out.println("| Thêm thành công! |");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // READ
    public List<NhanVien> getAll() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM bangnhanvien";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new NhanVien(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("year"),
                        rs.getString("sdt"),
                        rs.getString("email"),
                        rs.getString("chucvu")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // UPDATE
    public void updateNhanVien(NhanVien nv) {
        String sql = "UPDATE bangnhanvien SET name=?, year=?, sdt=?, email=?, chucvu=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nv.name);
            ps.setInt(2, nv.year);
            ps.setString(3, nv.sdt);
            ps.setString(4, nv.email);
            ps.setString(5, nv.chucvu);
            ps.setInt(6, nv.id);

            ps.executeUpdate();
            System.out.println("Update thành công!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void deleteNhanVien(int id) {
        String sql = "DELETE FROM bangnhanvien WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Xóa thành công!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}