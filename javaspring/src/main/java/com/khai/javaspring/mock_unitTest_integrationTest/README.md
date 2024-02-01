# 1. Mock, Unit test, integration test
## 1. Mock
### 1.1 Khái niệm
- Mock Object (MO) là một đối tượng ảo, mô phỏng các tính chất và hành vi giống hệt như đối tượng thực. Được truyền vào bên trong khối, mã để kiểm tra tính đúng đắn của các hoạt động bên trong.
### 1.2. Đặc điểm
- Đơn giản hơn đối tượng thực nhưng vẫn giữ được sự tương tác với các đối tượng khác.
- Không lặp lại nội dung đối tượng thực.
- Cho phép thiết lập các trạng thái riêng trợ giúp cho việc thực hiện unit test.
### 1.3. Trường hợp sử dụng
- Đối tượng thật có những hành vi không đoán trước được.
- Đối tượng thật khó cài đặt.
- Đối tượng thật chậm.
- Đối tượng thật có / là giao diện người dùng. 
- Đối tượng thật không tồn tại.
## 2. unit test
### 2.1 Khái niệm
- Là 1 loại kiểm thử phần mềm, trong đó các đơn vị đơn lẻ của phần mềm được kiểm thử.
- Unit ở đây có thể hiểu là function, proceduce, method, class.