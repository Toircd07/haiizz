package project.model;

public class Customer {
    private int id;
    private int userId;
    private String address;
    private String membershipLevel;
    private String createdAt;

    public Customer() {}

    public Customer(int id, int userId, String address, String membershipLevel, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.address = address;
        this.membershipLevel = membershipLevel;
        this.createdAt = createdAt;
    }

    // Getters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getAddress() { return address; }
    public String getMembershipLevel() { return membershipLevel; }
    public String getCreatedAt() { return createdAt; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setAddress(String address) { this.address = address; }
    public void setMembershipLevel(String membershipLevel) { this.membershipLevel = membershipLevel; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
