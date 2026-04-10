package project.model;

public class CustomerWithUser {
    private int userId;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private int customerId;
    private String address;
    private String membershipLevel;
    private String createdAt;

    // Getters
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public int getCustomerId() { return customerId; }
    public String getAddress() { return address; }
    public String getMembershipLevel() { return membershipLevel; }
    public String getCreatedAt() { return createdAt; }

    // Setters
    public void setUserId(int userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public void setAddress(String address) { this.address = address; }
    public void setMembershipLevel(String membershipLevel) { this.membershipLevel = membershipLevel; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
