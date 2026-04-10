package project.dao;


import project.model.User;
import project.model.Customer;
import project.database.DBConnection;
import java.sql.*;

public class UserDAO {

    // Đăng nhập
    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("type"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("phone")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Đăng ký khách hàng
    public boolean registerCustomer(String username, String password, String fullName,
                                    String email, String phone, String address) {
        if (isUsernameExists(username)) {
            return false;
        }

        String sqlUser = "INSERT INTO users (username, password, type, full_name, email, phone, created_at) " +
                         "VALUES (?, ?, 'customer', ?, ?, ?, NOW())";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);  

            ps = conn.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, fullName);
            ps.setString(4, email);
            ps.setString(5, phone);
            int affected = ps.executeUpdate();
            if (affected == 0) {
                conn.rollback();
                return false;
            }

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);
                Customer customer = new Customer();
                customer.setUserId(userId);
                customer.setAddress(address);
                customer.setMembershipLevel("Standard");
                // Dùng chính connection này để thêm customer (tránh mở connection riêng)
                if (addCustomerWithConnection(conn, customer)) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            // Đóng tài nguyên và reset auto commit
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);  // Trả về chế độ mặc định
                    conn.close();
                }
            } catch (SQLException e) {}
        }
    }

    // Hàm riêng để thêm customer dùng chung connection 
    private boolean addCustomerWithConnection(Connection conn, Customer customer) {
        String sql = "INSERT INTO customers (user_id, address, membership_level) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customer.getUserId());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getMembershipLevel());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Kiểm tra username tồn tại (nếu lỗi kết nối, trả false để cho phép thử)
    private boolean isUsernameExists(String username) {
        String sql = "SELECT 1 FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;   
        }
    }
}