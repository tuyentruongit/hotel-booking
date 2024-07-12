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

## Mô tả cơ sở dữ liệu

### Bảng Người dùng (Users)
- `id`: Khóa chính
- `username`: Tên đăng nhập
- `password`: Mật khẩu (đã mã hóa)
- `email`: Địa chỉ email
- `role`: Vai trò (user, hotel, admin)

### Bảng Khách sạn (Hotels)
- `id`: Khóa chính
- `name`: Tên khách sạn
- `description`: Mô tả khách sạn
- `location`: Địa chỉ khách sạn
- `price`: Giá phòng mỗi đêm
- `amenities`: Tiện nghi (WiFi, điều hòa, bể bơi, v.v.)
- `owner_id`: Khóa ngoại đến bảng Người dùng (chủ sở hữu khách sạn)

### Bảng Đơn đặt phòng (Bookings)
- `id`: Khóa chính
- `user_id`: Khóa ngoại đến bảng Người dùng (người đặt phòng)
- `hotel_id`: Khóa ngoại đến bảng Khách sạn
- `check_in`: Ngày nhận phòng
- `check_out`: Ngày trả phòng
- `status`: Trạng thái đơn đặt phòng (đang chờ, xác nhận, hủy)

### Bảng Đánh giá (Reviews)
- `id`: Khóa chính
- `hotel_id`: Khóa ngoại đến bảng Khách sạn
- `user_id`: Khóa ngoại đến bảng Người dùng
- `rating`: Đánh giá (số sao)
- `comment`: Nhận xét

## API Endpoints

### Người dùng (User)
- `POST /api/register`: Đăng ký tài khoản
- `POST /api/login`: Đăng nhập
- `GET /api/hotels`: Xem danh sách khách sạn
- `GET /api/hotels/:id`: Xem chi tiết khách sạn
- `POST /api/bookings`: Đặt phòng
- `GET /api/bookings`: Xem đơn đặt phòng của mình
- `PUT /api/bookings/:id`: Sửa đơn đặt phòng
- `DELETE /api/bookings/:id`: Hủy đơn đặt phòng

### Quản trị viên (Admin)
- `POST /api/hotels`: Thêm khách sạn
- `PUT /api/hotels/:id`: Sửa thông tin khách sạn
- `DELETE /api/hotels/:id`: Xóa khách sạn
- `GET /api/users`: Xem danh sách người dùng
- `GET /api/bookings`: Xem danh sách đơn đặt phòng

### Khách sạn (Hotel)
- `PUT /api/hotels/:id`: Cập nhật thông tin khách sạn của mình
- `GET /api/bookings`: Xem danh sách đơn đặt phòng của khách sạn
- `PUT /api/bookings/:id`: Xác nhận hoặc hủy đơn đặt phòng
- `GET /api/reviews`: Xem đánh giá của người dùng

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
