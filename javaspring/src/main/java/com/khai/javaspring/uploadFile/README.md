# Hande file upload
## 1. config upload
```
# File config
# Đường dẫn trên ổ đĩa
folder.path=C:\\Users\\Admin\\Documents\\Email 
spring.servlet.multipart.enabled=true
# Nếu lớn hơn kích thước này thì file được lưu vào ô đĩa
spring.servlet.multipart.file-size-threshold=2KB
# chỉ định kích thước tối đa của file upload
spring.servlet.multipart.max-file-size=200MB
# chỉ định kich thước tối đa của dữ liệu form (Tính cả file)
spring.servlet.multipart.max-request-size=215MB
```
## [2. FileUploadController (FileUploadController.java)](FileUploadController.java)
## [3. FileUpload service (StorageService.java)](StorageService.java)
## [4. FileUpload entity](File.java)
## [5. Storage file exception ]()