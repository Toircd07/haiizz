package toi.model;

import java.util.Date;

public class Booking {
    private int id;
    private int userId;
    private int roomId;
    private Date checkInDate;
    private Date checkOutDate;
    private double totalPrice;
    private String status;

    public Booking(int id, int userId, int roomId, Date checkInDate, Date checkOutDate, double totalPrice, String status) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}