package toi.dao;

import toi.model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    private Connection conn;

    public BookingDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Booking> getAllBookings() throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                bookings.add(new Booking(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("room_id"),
                        rs.getDate("check_in_date"),
                        rs.getDate("check_out_date"),
                        rs.getDouble("total_price"),
                        rs.getString("status")
                ));
            }
        }
        return bookings;
    }

    public void addBooking(Booking booking) throws SQLException {
        String sql = "INSERT INTO bookings (user_id, room_id, check_in_date, check_out_date, total_price, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getRoomId());
            stmt.setDate(3, new java.sql.Date(booking.getCheckInDate().getTime()));
            stmt.setDate(4, new java.sql.Date(booking.getCheckOutDate().getTime()));
            stmt.setDouble(5, booking.getTotalPrice());
            stmt.setString(6, booking.getStatus());
            stmt.executeUpdate();
        }
    }

    public void updateBooking(Booking booking) throws SQLException {
        String sql = "UPDATE bookings SET room_id = ?, check_in_date = ?, check_out_date = ?, total_price = ?, status = ? " +
                     "WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, booking.getRoomId());
            stmt.setDate(2, new java.sql.Date(booking.getCheckInDate().getTime()));
            stmt.setDate(3, new java.sql.Date(booking.getCheckOutDate().getTime()));
            stmt.setDouble(4, booking.getTotalPrice());
            stmt.setString(5, booking.getStatus());
            stmt.setInt(6, booking.getId());
            stmt.executeUpdate();
        }
    }

    public Booking getBookingById(int id) throws SQLException {
        String sql = "SELECT * FROM bookings WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Booking(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getInt("room_id"),
                            rs.getDate("check_in_date"),
                            rs.getDate("check_out_date"),
                            rs.getDouble("total_price"),
                            rs.getString("status")
                    );
                }
            }
        }
        return null;
    }
    public List<Booking> getBookingsByUserId(int userId) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = new Booking(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("room_id"),
                        rs.getDate("check_in_date"),
                        rs.getDate("check_out_date"),
                        rs.getDouble("total_price"),
                        rs.getString("status")
                    );
                    bookings.add(booking);
                }
            }
        }
        return bookings;
    }
}