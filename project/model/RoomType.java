package project.model;

public class RoomType {
    private int id;
    private String name; // Tên loại phòng (ví dụ: Phòng 2 người, Phòng 4 người)
    private int capacity; // Số người
    private double pricePerDay; // Giá tiền mỗi ngày
    private String description; // Mô tả loại phòng

    public RoomType(int id, String name, int capacity, double pricePerDay, String description) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.pricePerDay = pricePerDay;
        this.description = description;
    }

    // Getters và Setters
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}