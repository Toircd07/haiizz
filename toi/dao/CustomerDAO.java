package toi.dao;

import toi.model.Customer;
import toi.model.CustomerWithUser; // Add this import statement
import toi.database.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CustomerDAO {

    // Thêm customer (gọi sau khi tạo user)
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (user_id, address, membership_level) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customer.getUserId());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getMembershipLevel());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy customer theo user_id
    public Customer getCustomerByUserId(int userId) {
        String sql = "SELECT * FROM customers WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("address"),
                    rs.getString("membership_level"),
                    rs.getString("created_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy danh sách tất cả khách hàng (kèm thông tin user)
    public List<CustomerWithUser> getAllCustomersWithUser() {
        List<CustomerWithUser> list = new ArrayList<>();
        String sql = "SELECT u.id as user_id, u.username, u.full_name, u.email, u.phone, " +
                     "c.id as customer_id, c.address, c.membership_level, c.created_at " +
                     "FROM users u JOIN customers c ON u.id = c.user_id WHERE u.type = 'customer'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CustomerWithUser cwu = new CustomerWithUser();
                cwu.setUserId(rs.getInt("user_id"));
                cwu.setUsername(rs.getString("username"));
                cwu.setFullName(rs.getString("full_name"));
                cwu.setEmail(rs.getString("email"));
                cwu.setPhone(rs.getString("phone"));
                cwu.setCustomerId(rs.getInt("customer_id"));
                cwu.setAddress(rs.getString("address"));
                cwu.setMembershipLevel(rs.getString("membership_level"));
                cwu.setCreatedAt(rs.getString("created_at"));
                list.add(cwu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Cập nhật địa chỉ và hạng thành viên
    public boolean updateCustomer(int customerId, String address, String membershipLevel) {
        String sql = "UPDATE customers SET address = ?, membership_level = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, address);
            ps.setString(2, membershipLevel);
            ps.setInt(3, customerId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa khách hàng (xóa luôn user vì type='customer')
    public boolean deleteCustomerByUserId(int userId) {
        String sql = "DELETE FROM users WHERE id = ? AND type = 'customer'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}