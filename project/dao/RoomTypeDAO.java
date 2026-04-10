package project.dao;

import project.model.RoomType;
import project.database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomTypeDAO {

    // Thêm loại phòng mới
    public boolean addRoomType(RoomType roomType) {
        String sql = "INSERT INTO roomtype (name, capacity, price_per_day, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, roomType.getName());
            ps.setInt(2, roomType.getCapacity());
            ps.setDouble(3, roomType.getPricePerDay());
            ps.setString(4, roomType.getDescription());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy loại phòng theo ID
    public RoomType getRoomTypeById(int id) {
        String sql = "SELECT * FROM roomtype WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new RoomType(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("capacity"),
                        rs.getDouble("price_per_day"),
                        rs.getString("description")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy danh sách tất cả các loại phòng
    public List<RoomType> getAllRoomTypes() {
        List<RoomType> roomTypes = new ArrayList<>();
        String sql = "SELECT * FROM roomtype";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RoomType roomType = new RoomType(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("capacity"),
                    rs.getDouble("price_per_day"),
                    rs.getString("description")
                );
                roomTypes.add(roomType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomTypes;
    }

    // Cập nhật thông tin loại phòng
    public boolean updateRoomType(int id, String name, int capacity, double pricePerDay, String description) {
        String sql = "UPDATE roomtype SET name = ?, capacity = ?, price_per_day = ?, description = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, capacity);
            ps.setDouble(3, pricePerDay);
            ps.setString(4, description);
            ps.setInt(5, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa loại phòng theo ID
    public boolean deleteRoomType(int id) {
        String sql = "DELETE FROM roomtype WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}