package toi.run;

import toi.dao.*;
import toi.model.*;
import java.util.List;
import java.util.Scanner;

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
 //ok   //5. đặt thêm dichvu  của Đạt xong ròi ma chua ghep , 
 //ok   //6. qldsphong(hùng)  - đang làm
 //loading   //7. ql dặt phòng phía khách hàng (tới) - đang làm
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

private static void hienThiDanhSachPhong(RoomDAO dao) {
    List<Room> list = dao.getAllRooms();
    if (list.isEmpty()) {
        System.out.println("Danh sách phòng hiện tại trống.");
        return;
    }
    System.out.println("\n========================================================================================================================");
    System.out.printf("%-5s %-15s %-20s %-12s %-12s %-40s%n", "ID", "Số phòng", "Loại phòng", "Giá (VND)", "Trạng thái", "Mô tả");
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
            return Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.print("Vui lòng nhập số thực hợp lệ: ");
        }
    }
}

    // ========== CASE QUẢN LÝ KHÁCH HÀNG quyền (ADMIN) ==========
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
        while (true) {
            System.out.println("\n===== MENU KHÁCH HÀNG =====");
            System.out.println("1. Xem danh sách phòng trống");
            System.out.println("2. Đặt phòng (CRUD)");
            System.out.println("3. Xem danh sách dịch vụ");
            System.out.println("4. Đặt dịch vụ (thêm/xóa)");
            System.out.println("5. Xem hóa đơn của tôi");
            System.out.println("0. Đăng xuất");
            System.out.print("=> Chọn: ");
            int choice = getIntInput();
            switch (choice) {
                case 1: System.out.println("Chức năng xem phòng trống đang phát triển."); break;
                case 2: manageBooking(); break;
                case 3: System.out.println("Chức năng xem dịch vụ đang phát triển."); break;
                case 4: manageServiceBooking(); break;
                case 5: viewMyInvoices(); break;
                case 0: loggedUser = null; System.out.println("Đã đăng xuất."); return;
                default: System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // Các hàm giả lập cho khách hàng
    private static void manageBooking() {
        System.out.println("Chức năng đặt phòng (CRUD) đang phát triển.");
    }

    private static void manageServiceBooking() {
        System.out.println("Chức năng đặt dịch vụ (thêm/xóa) đang phát triển.");
    }

    private static void viewMyInvoices() {
        System.out.println("Chức năng xem hóa đơn của tôi đang phát triển.");
    }

    // ========== QUẢN LÝ NHÂN VIÊN quyền admin (GIỮ NGUYÊN) ==========
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
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Vui lòng nhập số hợp lệ: ");
            }
        }
    }
}