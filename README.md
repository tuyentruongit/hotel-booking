# Dự án Booking Hotel

## Giới thiệu
Dự án này là một ứng dụng web đặt phòng khách sạn trực tuyến cho phép người dùng đăng ký, đăng nhập, xem danh sách khách sạn, xem chi tiết khách sạn, và đặt phòng trực tuyến. Quản trị viên có thể quản lý khách sạn và các đơn đặt phòng. Vai trò khách sạn có thể quản lý thông tin và đặt phòng của khách sạn mình.

## Tính năng cơ bản 

### Người dùng
- Đăng ký tài khoản
- Đăng nhập và đăng xuất
- Xem danh sách khách sạn
- Tìm kiếm khách sạn theo địa điểm, giá cả, và tiện nghi,...
- Xem chi tiết khách sạn bao gồm mô tả, hình ảnh, và đánh giá
- Đặt phòng trực tuyến, xác nhận thông tin đặt phòng , có thể thanh toán đơn đặt phòng qua Vn Pay
- Xem và hủy các đơn đặt phòng của mình
- Các tính năng khác như: Thay đổi thông tin cá nhân, đổi mật khẩu, xem lịch sử đặt, quên mật khẩu, ...
- ...

### Quản trị viên (Admin)
- Đăng nhập và đăng xuất
- Theo dõi số lượng booking , người dùng đăng ký mới và khách sạn đăng ký mới
- Thống kê, báo cáo, biểu đồ thể hiện doanh thu của trang web (ngày , tháng, năm )
- Quản lý danh sách khách sạn
- Quản lý các bài viết về hỗ trợ người dùng
- Quản lý người dùng
- Xem lịch sử giao dịch thanh toán

### Khách sạn (Hotel)
- Đăng nhập và đăng xuất
- Cập nhật thông tin khách sạn của mình(tiện ích, hình ảnh, mô tả)
- Quản lý các đơn đặt phòng (xem, xác nhận, từ chối)
- Quản lý các phòng trong khách sạn (thêm, sửa, xóa)
- Quản lý các đơn đặt phòng (xem, xác nhận, hủy)
- Thay đổi giá phòng theo ngày cụ thể
- Chỉnh sửa các quy định chung của khách sạn

## Mô tả cơ sở dữ liệu
### Biểu Đồ Class Diagram
![Hình ảnh](ManagementCenimaDiagram.drawio.png)

## Công nghệ sử dụng
- Frontend: HTML, CSS, JavaScript, Thymeleaf, Bootstrap
- Backend: Java, Spring Boot, Hibernate , Lombok, Spring Security, Spring Data JPA
- Cơ sở dữ liệu: MySQL


## Hướng dẫn cài đặt
1. Clone repository: `git clone <repository-url>`
2. Cài đặt các phụ thuộc backend: `cd backend && mvn install`
3. Cài đặt các phụ thuộc frontend: `cd frontend && npm install`
4. Cấu hình cơ sở dữ liệu trong `application.properties`
5. Chạy backend: `mvn spring-boot:run`
6. Chạy frontend: `npm start`
