package toi.run;

import toi.dao.*;
import toi.model.*;
import toi.database.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {

    private static User loggedUser = null;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            if (loggedUser == null) {
                showLoginMenu();
            } else {
                if (loggedUser.getType().equals("admin")) {
                    showAdminMenu();
                } else {
                    showCustomerMenu();
                }
            }
        }
    }


    // Tiến độ usecase 
 //ok   //1. login(toi) - nếu là "khách" thì tạo tk + dn , nếu là "qtv" thì login bằng t mặc định "admin / admin123" xem dc tt đăng nhập và tt khách
 //ok   //2. qlkhach(toi) CRUD -  cái này là qtv có quyền CRUD 
 //ok   //3. qlnhanvien (nhất) - CRUD  cái này là qtv có quyền CRUD 
 //ok   //4. qldichvu CRUD (đạt) - cái này là qtv có quyền CRUD  
 //ok   //5. đặt thêm dịch vụ  của Đạt xong ròi ma chua ghep , 
 //ok   //6. ql danh sách phong(hùng)  - ok
 //ok   //7. ql dặt phòng phía khách hàng (tới) 
 //loading   //8. ql hóa đơn CRUD nhất - đang làm
 //loading   //9. đánh giá trải nghiệm (nhất) - đang làm
 //loading   //10. thống kê doanh thu (tạm tính khi đã xuất hóa đơn) (all) - đang làm
 //ok   //11. đăng xuất (all) - cái này là cả khách và qtv đều có quyền đăng xuất
 //loading   //12 checkin checkout (hùng) - đang làm



    // ========== MENU ĐĂNG NHẬP & ĐĂNG KÝ ==========    //cái này ok rồi , khi run file sẽ hiển thị đầu tiên
    private static void showLoginMenu() {
        while (true) {
            System.out.println("\n===== HỆ THỐNG ĐẶT PHÒNG KHÁCH SẠN =====");
            System.out.println("");
            System.out.println("1. Đăng nhập");
            System.out.println("2. Đăng ký tài khoản ");
            System.out.println("0. Thoát");
            System.out.print("=> Xin mời bạn nhập lựa chọn: ");
            int choice = getIntInput();
            switch (choice) {
                case 1:
                    doLogin();
                    return;
                case 2:
                    registerCustomer();
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("---ERROR-- Lựa chọn không hợp lệ! --ERROR---");
            }
        }
    }
    // chỗ này laf hàm đăng nhập    // ok rồi 
    private static void doLogin() {
        System.out.println("\n===== ĐĂNG NHẬP =====");
        System.out.print("Tên đăng nhập: ");
        String username = sc.nextLine();
        System.out.print("Mật khẩu: ");
        String password = sc.nextLine();
        User user = new UserDAO().login(username, password);
        if (user != null) {
            loggedUser = user;
            System.out.println("Đăng nhập thành công! Xin chào " + user.getFullName());
        } else {
            System.out.println("---ERROR-- Sai tên đăng nhập hoặc mật khẩu! --ERROR--");
        }
    }
    // chõ này là đăng kí tài khoản của khách   // ok rồi  
    private static void registerCustomer() {
        System.out.println("\n===== ĐĂNG KÝ TÀI KHOẢN KHÁCH HÀNG =====");
        System.out.print("Tên đăng nhập: ");
        String username = sc.nextLine();
        System.out.print("Mật khẩu: ");
        String password = sc.nextLine();
        System.out.print("Họ và tên: ");
        String fullName = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Số điện thoại: ");
        String phone = sc.nextLine();
        System.out.print("Địa chỉ: ");
        String address = sc.nextLine();

        boolean ok = new UserDAO().registerCustomer(username, password, fullName, email, phone, address); // gọi hàm đăng ký trong UserDAO
        if (ok) {
            System.out.println("Đăng ký thành công! Vui lòng đăng nhập.");
        } else {
            System.out.println("---ERROR-- Sai tên đăng nhập hoặc mật khẩu! --ERROR--");
        }
    }
    
    // ========== MENU của quản trị viên ==========   
    private static void showAdminMenu() {
        while (true) {
            System.out.println("\n===== MENU QUẢN TRỊ =====");
            System.out.println("1. Quản lý nhân viên (CRUD)- OK ngon lành format lại tý");              // ok
            System.out.println("2. Quản lý phòng (CRUD) - OK ngon lành");                  // đang làm
            System.out.println("3. Quản lý dịch vụ (CRUD) - như cứt chưa ghép dc");                // xong rồi nhưng chưa ghép
            System.out.println("4. Xem tất cả đặt phòng/dịch vụ - chưa xong");          // chưa xong
            System.out.println("5. Xem tất cả hóa đơn - chưa xong");                    // chưa xong
            System.out.println("6. Quản lý khách hàng (RUD)- Ok ngon lành");              // ok 
            System.out.println("0. Đăng xuất");                             // ok
            System.out.print( "=> Nhập lựa chọn: ");
            int choice = getIntInput();
            switch (choice) {
                case 1: manageEmployee(); break;
                //  manageRoom
                case 2: manageRoom(); break;
                case 3: System.out.println("Chức năng quản lý dịch vụ đang phát triển."); break;
                case 4: System.out.println("Xem tất cả đặt phòng/dịch vụ đang phát triển."); break;
                case 5: System.out.println("Xem tất cả hóa đơn đang phát triển."); break;
                case 6: manageCustomers(); break;
                case 0: loggedUser = null; System.out.println("=> Đã đăng xuất khỏi hệ thống"); return;
                default: System.out.println("---ERROR-- Lựa chọn không hợp lệ! --ERROR---");
            }
        }
    }

    // CASE QUẢN LÝ PHÒNG (CRUD) quyền admin
private static void manageRoom() {
    RoomDAO dao = new RoomDAO();
    while (true) {
        System.out.println("\n--- QUẢN LÝ PHÒNG ---");
        System.out.println("1. Thêm phòng");
        System.out.println("2. Hiển thị danh sách phòng");
        System.out.println("3. Cập nhật phòng");
        System.out.println("4. Xóa phòng");
        System.out.println("0. Quay lại");
        System.out.print("=> Chọn: ");
        int choice = getIntInput();
        switch (choice) {
            case 1:
                themPhong(dao);
                break;
            case 2:
                hienThiDanhSachPhong(dao);
                break;
            case 3:
                capNhatPhong(dao);
                break;
            case 4:
                xoaPhong(dao);
                break;
            case 0:
                return;
            default:
                System.out.println("---ERROR-- Lựa chọn không hợp lệ! --ERROR---");
        }
    }
}  
private static void themPhong(RoomDAO dao) {
    System.out.println("\n--- THÊM PHÒNG MỚI ---");
    System.out.print("Số phòng : ");
    String roomNumber = sc.nextLine();
    System.out.print("Loại phòng : ");
    String roomType = sc.nextLine();
    System.out.print("Giá phòng : ");
    double price = getDoubleInput();
    System.out.print("Trạng thái (available || booked || maintenance): ");
    String status = sc.nextLine();
    System.out.print("Mô tả : ");
    String description = sc.nextLine();

    Room room = new Room(roomNumber, roomType, price, status, description);
    dao.addRoom(room);
}

private static void hienThiDanhSachPhongList(RoomDAO dao) {
    List<Room> list = dao.getAllRooms();
    if (list.isEmpty()) {
        System.out.println("Danh sách phòng hiện tại trống.");
        return;
    }
    System.out.println("\n========================================================================================================================");
    System.out.printf("%-5s %-15s %-20s %-12s %-12s %-40s%n", "ID", "Số phòng", "Loại phòng", "Giá/1 đêm (VND)", "Trạng thái", "Mô tả");
    System.out.println("------------------------------------------------------------------------------------------------------------------------");
    for (Room r : list) {
        System.out.printf("%-5d %-15s %-20s %-12.2f %-12s %-40s%n",
                r.getId(), r.getRoomNumber(), r.getRoomType(),
                r.getPrice(), r.getStatus(), r.getDescription());
    }
    System.out.println("========================================================================================================================");
}

private static void capNhatPhong(RoomDAO dao) {
    System.out.print("Nhập ID phòng cần cập nhật: ");
    int id = getIntInput();
    System.out.print("Số phòng mới: ");
    String roomNumber = sc.nextLine();
    System.out.print("Loại phòng mới: ");
    String roomType = sc.nextLine();
    System.out.print("Giá mới: ");
    double price = getDoubleInput();
    System.out.print("Trạng thái mới (available || booked || maintenance):");
    String status = sc.nextLine();
    System.out.print("Mô tả mới: ");
    String description = sc.nextLine();

    Room room = new Room(id, roomNumber, roomType, price, status, description);
    dao.updateRoom(room);
}

private static void xoaPhong(RoomDAO dao) {
    System.out.print("Nhập ID phòng cần xóa: ");
    int id = getIntInput();
    System.out.print("Bạn có chắc chắn xóa phòng này? (y/n): ");
    String confirm = sc.nextLine();
    if (confirm.equalsIgnoreCase("y")) {
        dao.deleteRoom(id);
    } else {
        System.out.println("Hủy xóa phòng.");
    }
}

private static double getDoubleInput() {
    while (true) {
        try {
            return Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.print("Vui lòng nhập số thực hợp lệ: ");
        }
    }
}
    // ==========  QUẢN LÝ KHÁCH HÀNG quyền Admin ==========
    private static void manageCustomers() {
        CustomerDAO dao = new CustomerDAO();
        while (true) {
            System.out.println("\n--- QUẢN LÝ KHÁCH HÀNG ---");
            System.out.println("1. Xem danh sách khách hàng");
            System.out.println("2. Cập nhật thông tin (địa chỉ, hạng thành viên)");
            System.out.println("3. Xóa khách hàng");
            System.out.println("0. Quay lại");
            System.out.print("=> Chọn: ");
            int choice = getIntInput();
            switch (choice) {
                case 1: listCustomers(dao); break;
                case 2: updateCustomer(dao); break;
                case 3: deleteCustomer(dao); break;
                case 0: return;
                default: System.out.println("---ERROR-- Lựa chọn không hợp lệ! --ERROR---");
            }
        }
    }

    private static void listCustomers(CustomerDAO dao) {
        List<CustomerWithUser> list = dao.getAllCustomersWithUser();
        if (list.isEmpty()) {
            System.out.println("Danh sách khách hàng trống! ");
            return;
        }
        System.out.println("\n=====================================================================================================================================================");
        System.out.printf("%-5s %-15s %-20s %-25s %-12s %-30s %-15s %-20s%n",
            "UserID", "Username", "Họ tên", "Email", "Điện thoại", "Địa chỉ", "Hạng TV", "Ngày tạo");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");
        for (CustomerWithUser c : list) {
            System.out.printf("%-5d %-15s %-20s %-25s %-12s %-30s %-15s %-20s%n",
                c.getUserId(), c.getUsername(), c.getFullName(), c.getEmail(), c.getPhone(),
                c.getAddress(), c.getMembershipLevel(), c.getCreatedAt());
        }
        System.out.println("=====================================================================================================================================================");
    }

    private static void updateCustomer(CustomerDAO dao) {
        System.out.print("Nhập User ID của khách hàng cần sửa: ");
        int userId = getIntInput();
        Customer c = dao.getCustomerByUserId(userId);
        if (c == null) {
            System.out.println("Không tìm thấy khách hàng với User ID này.");
            return;
        }
        System.out.print("Địa chỉ mới (bỏ trống nếu giữ nguyên): ");
        String addr = sc.nextLine();
        if (addr.trim().isEmpty()) addr = c.getAddress();
        System.out.print("Hạng thành viên mới (Standard/Gold/Platinum, bỏ trống nếu giữ): ");
        String level = sc.nextLine();
        if (level.trim().isEmpty()) level = c.getMembershipLevel();
        boolean ok = dao.updateCustomer(c.getId(), addr, level);
        System.out.println(ok ? "Cập nhật thành công!" : "Cập nhật thất bại.");
    }

    private static void deleteCustomer(CustomerDAO dao) {
        System.out.print("Nhập User ID của khách hàng cần xóa: ");
        int userId = getIntInput();
        System.out.print("Bạn có chắc chắn xóa? (y/n): ");
        String confirm = sc.nextLine();
        if (confirm.equalsIgnoreCase("y")) {
            boolean ok = dao.deleteCustomerByUserId(userId);
            System.out.println(ok ? "Xóa thành công!" : "Xóa thất bại (có thể không tìm thấy).");
        } else {
            System.out.println("Hủy xóa.");
        }
    }

    // ========== MENU KHÁCH HÀNG quyền khách ==========
    private static void showCustomerMenu() {
        RoomDAO roomDAO = new RoomDAO(); // Tạo đối tượng RoomDAO
        while (true) {
            System.out.println("\n===== MENU KHÁCH HÀNG =====");
            System.out.println("1. Đặt phòng (CRUD)");
            // Thêm lựa chọn mới
            System.out.println("0. Đăng xuất");
            System.out.print("=> Chọn: ");
            int choice = getIntInput();
            switch (choice) {
                case 1: 
                    try {
                        Connection conn = DBConnection.getConnection(); // Lấy đối tượng Connection
                        BookingDAO bookingDAO = new BookingDAO(conn); // Tạo đối tượng BookingDAO
                        manageBooking(roomDAO, bookingDAO); // Gọi phương thức manageBooking
                    } catch (SQLException e) {
                        System.out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
                    }
                    break;
                case 0: 
                    loggedUser = null; 
                    System.out.println("=> Đã đăng xuất khỏi hệ thống");
                    return;
                default: 
                    System.out.println("---ERROR-- Lựa chọn không hợp lệ! --ERROR---");
            }
        }
    }

    // Các hàm giả lập cho khách hàng
    // private static void manageBooking() {
    //     System.out.println("Chức năng đặt phòng (CRUD) đang phát triển.");
    // }

    private static void manageBooking(RoomDAO roomDAO, BookingDAO bookingDAO) throws SQLException {
        while (true) {
            System.out.println("\n=== Quản Lý Đặt Phòng ===");
            System.out.println("1. Xem tất cả phòng");
            System.out.println("2. Đặt phòng mới");
            System.out.println("3. Sửa đặt phòng");
            System.out.println("4. Xem lại đặt phòng");
            System.out.println("5. Quay lại");
            System.out.print("Nhập lựa chọn của bạn: ");
            int choice = getIntInput();
    
            switch (choice) {
                case 1:
                    hienThiDanhSachPhong(roomDAO);
                    break;
                case 2:
                    datPhongMoi(roomDAO, bookingDAO);
                    break;
                case 3:
                    suaDatPhong(roomDAO, bookingDAO);
                    break;
                case 4:
                    xemLaiDatPhong(bookingDAO);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }


    private static void datPhongMoi(RoomDAO roomDAO, BookingDAO bookingDAO) throws SQLException {
        System.out.print("Nhập ID phòng muốn đặt: ");
        int roomId = getIntInput();
        Room room = roomDAO.getRoomById(roomId);
    
        if (room == null || !room.getStatus().equalsIgnoreCase("available")) {
            System.out.println("Phòng không tồn tại hoặc không khả dụng.");
            return;
        }
    
        System.out.print("Nhập ngày nhận phòng (yyyy-MM-dd): ");
        String checkInDateStr = sc.nextLine();
        System.out.print("Nhập ngày trả phòng (yyyy-MM-dd): ");
        String checkOutDateStr = sc.nextLine();
    
        // Chuyển đổi chuỗi sang Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date checkInDate;
        Date checkOutDate;
        try {
            checkInDate = dateFormat.parse(checkInDateStr);
            checkOutDate = dateFormat.parse(checkOutDateStr);
        } catch (ParseException e) {
            System.out.println("Định dạng ngày không hợp lệ. Vui lòng nhập theo định dạng yyyy-MM-dd.");
            return;
        }
    
        // Tính số ngày ở
        long diffInMillies = checkOutDate.getTime() - checkInDate.getTime();
        long numberOfDays = diffInMillies / (1000 * 60 * 60 * 24); // Chuyển đổi từ milliseconds sang ngày
    
        if (numberOfDays <= 0) {
            System.out.println("Ngày trả phòng phải sau ngày nhận phòng.");
            return;
        }
    
        // Tính tổng giá tiền
        double totalPrice = numberOfDays * room.getPrice();
    
        // Tạo đối tượng Booking và lưu vào cơ sở dữ liệu
        Booking booking = new Booking(0, loggedUser.getId(), roomId, checkInDate, checkOutDate, totalPrice, "confirmed");
        bookingDAO.addBooking(booking);
        room.setStatus("booked");
        System.out.println("Đặt phòng thành công!");
        System.out.printf("Phòng đã đặt trong %d ngày. Tổng tiền tạm tính: %.2f VND%n", numberOfDays, totalPrice);
    }

    private static void suaDatPhong(RoomDAO roomDAO, BookingDAO bookingDAO) throws SQLException {
        System.out.print("Nhập ID đặt phòng cần sửa: ");
        int bookingId = getIntInput();
        Booking booking = bookingDAO.getBookingById(bookingId);
    
        if (booking == null || booking.getUserId() != loggedUser.getId()) {
            System.out.println("Không tìm thấy đặt phòng hoặc bạn không có quyền sửa.");
            return;
        }
    
        System.out.print("Nhập ID phòng mới: ");
        int newRoomId = getIntInput();
        Room newRoom = roomDAO.getRoomById(newRoomId);
    
        if (newRoom == null || !newRoom.getStatus().equalsIgnoreCase("available")) {
            System.out.println("Phòng không tồn tại hoặc không khả dụng.");
            return;
        }
    
        System.out.print("Nhập ngày nhận phòng mới (yyyy-MM-dd): ");
        String checkInDateStr = sc.nextLine();
        System.out.print("Nhập ngày trả phòng mới (yyyy-MM-dd): ");
        String checkOutDateStr = sc.nextLine();
    
        // Chuyển đổi chuỗi sang Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date checkInDate;
        Date checkOutDate;
        try {
            checkInDate = dateFormat.parse(checkInDateStr);
            checkOutDate = dateFormat.parse(checkOutDateStr);
        } catch (ParseException e) {
            System.out.println("Định dạng ngày không hợp lệ. Vui lòng nhập theo định dạng yyyy-MM-dd.");
            return;
        }
    
        // Tính số ngày ở
        long diffInMillies = checkOutDate.getTime() - checkInDate.getTime();
        long numberOfDays = diffInMillies / (1000 * 60 * 60 * 24); // Chuyển đổi từ milliseconds sang ngày
    
        if (numberOfDays <= 0) {
            System.out.println("Ngày trả phòng phải sau ngày nhận phòng.");
            return;
        }
    
        // Tính tổng giá tiền
        double totalPrice = numberOfDays * newRoom.getPrice();
    
        // Cập nhật thông tin đặt phòng
        booking.setRoomId(newRoomId);
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);
        booking.setTotalPrice(totalPrice);
        bookingDAO.updateBooking(booking);
    
        // Cập nhật trạng thái phòng
        newRoom.setStatus("booked");
        System.out.println("Cập nhật đặt phòng thành công!");
        System.out.printf("Phòng đã cập nhật trong %d ngày. Tổng tiền tạm tính: %.2f VND%n", numberOfDays, totalPrice);
    }
    private static void xemLaiDatPhong(BookingDAO bookingDAO) throws SQLException {
        List<Booking> bookings = bookingDAO.getBookingsByUserId(loggedUser.getId());
        if (bookings.isEmpty()) {
            System.out.println("Bạn chưa có đặt phòng nào.");
            return;
        }
    
        System.out.println("\n          === Danh Sách Đặt Phòng ===");
        System.out.printf("%-5s %-10s %-15s %-15s %-10s %-10s%n", "STT", "ID_Phòng", "Check-In", "Check-Out", "Tạm tính", "Trạng thái");
        for (Booking booking : bookings) {
            System.out.printf("%-5d %-10d %-15s %-15s %-10.2f %-10s%n",
                    booking.getId(), booking.getRoomId(), booking.getCheckInDate(),
                    booking.getCheckOutDate(), booking.getTotalPrice(), booking.getStatus());
        }
    }
    private static void hienThiDanhSachPhong(RoomDAO dao) {
        List<Room> list = dao.getAllRooms();
        if (list.isEmpty()) {
            System.out.println("Danh sách phòng hiện tại trống.");
            return;
        }
        System.out.println("\n========================================================================================================================");
        System.out.printf("%-5s %-15s %-20s %-12s %-12s %-40s%n", "ID", "Số phòng", "Loại phòng", "Giá/1 đêm (VND)", "Trạng thái", "Mô tả");
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
        for (Room r : list) {
            System.out.printf("%-5d %-15s %-20s %-12.2f %-12s %-40s%n",
                    r.getId(), r.getRoomNumber(), r.getRoomType(),
                    r.getPrice(), r.getStatus(), r.getDescription());
        }
        System.out.println("========================================================================================================================");
    }

    
    // private static void manageServiceBooking() {
    //     System.out.println("Chức năng đặt dịch vụ (thêm/xóa) đang phát triển.");
    // }

    // private static void viewMyInvoices() {
    //     System.out.println("Chức năng xem hóa đơn của tôi đang phát triển.");
    // }















    // ========== QUẢN LÝ NHÂN VIÊN quyền admin ==========
    private static void manageEmployee() {
        NhanVienDAO dao = new NhanVienDAO();
        while (true) {
            System.out.println("\n--- QUẢN LÝ NHÂN VIÊN ---");
            System.out.println("1. Thêm nhân viên");
            System.out.println("2. Hiển thị danh sách");
            System.out.println("3. Cập nhật nhân viên");
            System.out.println("4. Xóa nhân viên");
            System.out.println("0. Quay lại");
            System.out.print("=> Chọn: ");
            int choice = getIntInput();
            switch (choice) {
                case 1: themNhanVien(sc, dao); break;
                case 2: hienThi(dao); break;
                case 3: capNhat(sc, dao); break;
                case 4: xoa(sc, dao); break;
                case 0: return;
                default: System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private static void themNhanVien(Scanner sc, NhanVienDAO dao) {
        System.out.print("Tên: ");
        String name = sc.nextLine();
        System.out.print("Năm sinh: ");
        int year = getIntInput();
        System.out.print("SDT: ");
        String sdt = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Chức vụ: ");
        String chucvu = sc.nextLine();
        NhanVien nv = new NhanVien(0, name, year, sdt, email, chucvu);
        dao.addNhanVien(nv);
    }

    private static void hienThi(NhanVienDAO dao) {
        var list = dao.getAll();
        if (list.isEmpty()) {
            System.out.println("Danh sách nhân viên trống.");
        } else {
            for (NhanVien nv : list) {
                System.out.println(nv.id + " | " + nv.name + " | " + nv.year + " | " + nv.sdt + " | " + nv.email + " | " + nv.chucvu);
            }
        }
    }

    private static void capNhat(Scanner sc, NhanVienDAO dao) {
        System.out.print("ID cần sửa: ");
        int id = getIntInput();
        System.out.print("Tên mới: ");
        String name = sc.nextLine();
        System.out.print("Năm sinh mới: ");
        int year = getIntInput();
        System.out.print("SDT mới: ");
        String sdt = sc.nextLine();
        System.out.print("Email mới: ");
        String email = sc.nextLine();
        System.out.print("Chức vụ mới: ");
        String chucvu = sc.nextLine();
        NhanVien nv = new NhanVien(id, name, year, sdt, email, chucvu);
        dao.updateNhanVien(nv);
    }

    private static void xoa(Scanner sc, NhanVienDAO dao) {
        System.out.print("ID cần xóa: ");
        int id = getIntInput();
        dao.deleteNhanVien(id);
    }

    private static int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Vui lòng nhập số nguyên hợp lệ: ");
            }
        }
    }
}