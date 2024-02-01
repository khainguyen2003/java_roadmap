# Kiến trúc spring
## 1. Tổng quan kiến trúc spring

Spring boot tuân theo 2 mô hình
- MVC
- Mô hình 3 lơps (three tier)

![alt](https://images.viblo.asia/fdbe3b44-aa91-4a88-9202-814c56ef9178.png)

Từ mô hình trên, ta có thể tổ chức dữ liệu thành 3 lớp Controller, Service và Repository

![alt](https://images.viblo.asia/0b2971bd-7098-4652-a677-c61aea36e032.png)

Mỗi layer chỉ nên xử lý một số loại data nhất định. Mỗi dạng data sẽ có nhiệm vụ, mục đích khác nhau.
Ví dụ, trong sơ đồ thì Controller không nên đụng tới data dạng domain model hoặc entity, chỉ được phép nhận và trả về DTO.

### Tại sao phải chia nhiều data
- Do tuân theo nguyên tắc SoC - separation of concerns - chia tách các mối quan tâm trong thiết kế phần mềm. Cụ thể sẽ phân chia thành 
```
Spring Boot = Presentation layer + Service layer + Data access layer
```
- Nếu data chỉ có một dạng thì sẽ bị lộ những thông tin nhạy cảm khi trả về tất cả thông tin của đối tượng
## Các loại dữ liệu
- **DTO (Data transfer object):** là các class đóng gói data để chuyển giữa client - server hoặc giữa các service trong microservice. Mục đích tạo ra DTO là để giảm bớt lượng info không cần thiết phải chuyển đi, và cũng tăng cường độ bảo mật.
- **Domain model:** là các class đại diện cho các domain, hiểu là các đối tượng thuộc business như Client, Report, Department,... chẳng hạn. Trong ứng dụng thực, các class đại diện cho kết quả tính toán, các class làm tham số đầu vào cho service tính toán,... được coi là domain model.
- **Entity:** cũng là domain model nhưng tương ứng với table trong DB, có thể map vào DB được. Lưu ý chỉ có entity mới có thể đại diện cho data trong DB.
## các nguyên tắc sử dụng các loại dữ liệu
- **Web layer:** chỉ nên xử lý DTO, đồng nghĩa với việc các Controller chỉ nên nhận và trả về dữ liệu là DTO.
- **Service layer:** nhận vào DTO (từ controller gửi qua) hoặc Domain model (từ các service nội bộ khác). Dữ liệu được xử lý (có thể tương tác với DB), cuối cùng được Service trả về Web layer dưới dạng DTO.
- **Repository layer:** chỉ thao tác trên Entity, vì đó là đối tượng thích hợp, có thể mapping vào DB.
## Model mapping
Khi data đi qua các layer khác nhau, nó biến đổi thành các dạng khác nhau. Ví dụ DTO từ controller đi vào service, thì nó sẽ được map thành domain model hoặc entity, rồi khi vào Repository bắt buộc phải trở thành Entity.
Việc chuyển các dạng dữ liệu này được gọi là model mapping

Việc mapping này sẽ được thực hiện ở service hoặc controller. Tuy nhiên, nếu entity không chứa thông tin nhạy cảm thì có thể dùng trực tiếp entity và model

# Ví dụ
Khi lấy thông tin user thì việc lấy password là không cần thiết. 
```
public class UserDTO {
    private int id;
    private string username;
    private String email;
    private String role;

    public userDTO() {
    }

    public userDTO(User user) {
        this.Id = user.getId();
        this.userName = user.getUsername();
        this.eMail = user.getEmail();
        this.role = user.getRole().getName();
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }public userDTO() {
    }

    public userDTO(User user) {
        this.Id = user.getId();
        this.userName = user.getUsername();
        this.eMail = user.getEmail();
        this.role = user.getRole().getName();
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
```
>RestAPI.java
```java
    @GetMapping(value = "/admin/user")
    public List<userDTO> getAllUser() {
        List<userDTO> userDTOList = new ArrayList<>();
        userDTOList = userService.findAll().stream().map(user -> new userDTO(user)).collect(Collectors.toList());
        return userDTOList;
    }

    @GetMapping(value = "/admin/user/{id}")
    public userDTO getUser(@PathVariable("id") int Id) {
        return new userDTO(userService.findById(Id));
    }
```

`ModelMapper` là công cụ hữu ích để chuyển đổi giữa các Object.

```java
    ModelMapper mapper = new ModelMapper();
    UserDTO userDTO = mapper.map(user, UserDTO.class);
```