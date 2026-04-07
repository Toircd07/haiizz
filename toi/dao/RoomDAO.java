package toi.dao;

import toi.model.Room;
import toi.database.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    // Lấy danh sách phòng trống (status = 'available')
    public List<Room> getAvailableRooms() {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE status = 'available'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Room(
                        rs.getInt("id"),
                        rs.getString("room_number"),
                        rs.getString("room_type"),
                        rs.getDouble("price"),
                        rs.getString("status"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách phòng trống:");
            e.printStackTrace();
        }
        return list;
    }

    // Lấy thông tin phòng theo ID
    public Room getRoomById(int id) {
        String sql = "SELECT * FROM rooms WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Room(
                            rs.getInt("id"),
                            rs.getString("room_number"),
                            rs.getString("room_type"),
                            rs.getDouble("price"),
                            rs.getString("status"),
                            rs.getString("description")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy phòng theo ID " + id);
            e.printStackTrace();
        }
        return null;
    }

    // Thêm phòng mới
    public void addRoom(Room room) {
        String sql = "INSERT INTO rooms(room_number, room_type, price, status, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, room.getRoomNumber());
            ps.setString(2, room.getRoomType());
            ps.setDouble(3, room.getPrice());
            ps.setString(4, room.getStatus());
            ps.setString(5, room.getDescription());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("| Thêm phòng thành công! |");
            } else {
                System.out.println("| Thêm phòng thất bại, không có dòng nào được chèn. |");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm phòng:");
            e.printStackTrace();
        }
    }

    // Lấy tất cả phòng
    public List<Room> getAllRooms() {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM rooms";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Room(
                        rs.getInt("id"),
                        rs.getString("room_number"),
                        rs.getString("room_type"),
                        rs.getDouble("price"),
                        rs.getString("status"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách tất cả phòng:");
            e.printStackTrace();
        }
        return list;
    }

    // Cập nhật thông tin phòng
    public void updateRoom(Room room) {
        String sql = "UPDATE rooms SET room_number=?, room_type=?, price=?, status=?, description=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, room.getRoomNumber());
            ps.setString(2, room.getRoomType());
            ps.setDouble(3, room.getPrice());
            ps.setString(4, room.getStatus());
            ps.setString(5, room.getDescription());
            ps.setInt(6, room.getId());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Cập nhật phòng thành công!");
            } else {
                System.out.println("Cập nhật phòng thất bại, không tìm thấy ID " + room.getId());
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật phòng ID " + room.getId());
            e.printStackTrace();
        }
    }

    // Xóa phòng theo ID
    public void deleteRoom(int id) {
        String sql = "DELETE FROM rooms WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Xóa phòng thành công!");
            } else {
                System.out.println("Xóa phòng thất bại, không tìm thấy ID " + id);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa phòng ID " + id);
            e.printStackTrace();
        }
    }

    // (Tuỳ chọn) Kiểm tra phòng có tồn tại không
    public boolean isRoomExists(int id) {
        String sql = "SELECT 1 FROM rooms WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}