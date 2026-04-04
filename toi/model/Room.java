package toi.model;

public class Room {
    private int id;
    private String roomNumber;   
    private String roomType;     
    private double price;
    private String status;
    private String description;

   // Constructor có id (dùng khi lấy dữ liệu từ DB)
    public Room(int id, String roomNumber, String roomType, double price, String status, String description) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.status = status;
        this.description = description;
    }

    // Constructor không có id (dùng khi thêm mới)
    public Room(String roomNumber, String roomType, double price, String status, String description) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.status = status;
        this.description = description;
    }

   
    public int getId(){
        return id;
    }

     public void setId(int id){
        this.id = id;
    }

       public String getRoomNumber(){
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber){
        this.roomNumber = roomNumber;
    }

    public String getRoomType(){
        return roomType;
    }

    public void setRoomType(String roomType){
        this.roomType = roomType;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }
}