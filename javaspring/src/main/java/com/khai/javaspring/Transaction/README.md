# Transaction trong spring
### @Transaction annotation
- Nói với spring rằng phương thưc này cần thực hiện trong một transaction. Khi ta sử dụng method đó, Spring sẽ tạo ra một proxy object bao bọc object chứa method và cung cấp các đoạn mã cần thiết để bắt đầu một transaction.
- Mặc định, proxy sẽ start một transaction trước khi có một yêu cầu đến method được chú thích với @Transactional annotation. Sau khi method thực thi xong, proxy sẽ commit hoặc rollback transaction nếu có một RuntimeException hoặc Error xảy ra trong quá trình thực thi. Mọi thứ xảy ra ở giữa, chỉ là các đoạn mã code thực thi logic business do chính chúng ta viết.
- @Transactional annottion còn hỗ trợ cho chúng ta tuỳ biến một các hành vi của một transaction thông qua một số thuộc tính quan trọng như propagation, readOnly, rollbackFor, noRollbackFor.

### Transaction Propagation (Trong phiên bản mới sẽ là TxType)
Spring cung cấp 7 tuỳ biến cho Propagation trong @Transactional annotation như sau:
- `REQUIRED`: Nói với Spring rằng nếu có một` transaction đang hoạt động` thì nó sẽ `sử dụng chung`, nếu `không` có `transaction` nào `đang hoạt động`, method được gọi sẽ `tạo một transaction mới`. Đây là giá trị mặc định của Propagation.
- `SUPPORTS`: Chỉ đơn giản là `sử dụng lại transaction` hiện `đang hoạt động`. Nếu `không` thì method được gọi sẽ `thực thi` mà `không được đặt` trong một `transactional context` nào
- `MANDATORY:` `yêu cầu` phải có một transaction `đang hoạt động trước khi gọi`, nếu không method được gọi sẽ ném ra một `exception`.
- `NEVER:` sẽ ném một `exception` nếu method được gọi `trong một transaction hoạt động`
- `NESTED:` Method được gọi sẽ tạo một transaction mới nếu không có transaction nào đang hoạt động. Nếu method được gọi với một transaction đang hoạt động Spring sẽ tạo một `savepoint` và `rollback` tại đây nếu có `Exception xảy ra.`
### Handling Exceptions
Spring proxy sẽ tự động `rollback transaction` nếu có một `RuntimeException` xảy ra. Chúng ta có thể tùy biến bằng cách sử dụng thuộc tính `rollbackFor` và `noRollbackFor` của @Transactional annotation.
- `rollbackFor` cho phép bạn cung cấp một `mảng các Exception class` mà transaction sẽ bị `rollback` nếu chúng xảy ra.
- `noRollbackFor` được dùng để chỉ định một `mảng các Exception class` mà transaction sẽ `không rollback` khi chúng xảy ra.
### Isolation Level (Mức độ cô lập)
**Những vấn đề xảy ra khi truy xuất dữ liệu đồng thời**
- `Mất dữ liệu cập nhật (Lost update)`: 
Tình trạng này xảy ra khi có nhiều hơn một giao tác cùng thực hiện cập nhật trên 1 đơn vị dữ liệu. Khi đó, tác dụng của giao tác cập nhật thực hiện sau sẽ đè lên tác dụng của thao tác cập nhật trước.
- `Dirty Read: Đọc dữ liệu chưa được chuyển giao (Uncommited Dependence – Dữ liệu chưa được chuyển giao):`
Xảy ra khi một transaction thứ 2 chọn một hàng chưa được cập nhật 
bởi một transaction khác. Transaction thứ 2 đọc dữ liệu lúc chưa 
chuyển giao và có thể bị thay đổi bởi transaction đang thực hiện 
việc cập nhật. Ví dụ : 2 Transaction T3, T4 thực hiện đồng thời, 
T3 rút $20, T4 gửi thêm $200 nhưng lại hủy bỏ sau đó.
- `Phantoms:` Là một hàng phù hợp với tiêu chí tìm kiếm nhưng ban đầu lại ko tìm thấy
Vi dụ, ban đầu transaction T1 tìm kiếm được một tập kết quả theo 1 tiêu chí tìm kiếm
nào đó. Sau đó T2 thực hiện thêm một hàng mới phù hợp với tiêu chí tìm kiếm T1 thì khi 
T1 tìm lại sẽ cho kết quả mới

**Các mức độ cô lập**
- `SERIALIZABLE:` Một transaction chỉ được commit sau khi transaction thứ 2 được thực hiện -> có thể gây bế tắc 
- `REPEATABLE_READ:` Các bản ghi hiện có không thể được thay đổi bởi transaction T2 cho đến khi T1 đã commit nhưng có thể thêm bản ghi mới
- `READ_COMMITTED:` Nếu có 2 transaction thực hiện đồng thời, T2 có thể thay đổi và thêm mới bản ghi trước khi T1 được commit. Các bản ghi đẫ được cập nhật
có thể được sử dụng bởi T1
- ``

> Ví dụ
