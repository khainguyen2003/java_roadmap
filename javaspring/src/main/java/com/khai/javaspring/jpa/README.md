# JPA repository
## 1. Query creation
```java
// Query: select u from User u where u.emailAddress = ?1 and u.lastname = ?2
List<User> findByEmailAddressAndLastname(String emailAddress, String lastname);
```

## 2. key word support
![jpa_keyword.png](..%2F..%2F..%2F..%2F..%2Fresources%2Fstatic%2Fimg%2Fjpa_keyword.png)

### Native query
- sử dụng cách viết câu lệnh của một cơ sở dư liệu cụ thể -> tốc độ nhanh hơn nhưng sẽ giảm tính linh động

```java
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>{
    // get query
    @Query(value = "select * from author a where a.first_name= :firstName", nativeQuery = true)
    List<Author> getAuthorsByFirstName (String firstName);
    // update, delete query
    @Modifying
    @Query(value = "delete from author a where a.last_name= :lastName", nativeQuery = true)
    void deleteAuthorByLastName (@Param("lastName") String lastName);

    @Modifying
    @Query(value = "update author set last_name= :lastName where first_name = :firstName", nativeQuery = true)
    void updateAuthorByFirstName (String firstName, String lastName);
    
    // Phân trang
    @Query(value="select * from author a where a.last_name= ?1",
            countQuery = "select count(id) from author a where a.last_name= ?1",
            nativeQuery = true)
    Page<Author> getAuthorsByLastName(String lastname, Pageable page);
}
```

### 3. Query params
```java
@Query("select u from User u where u.firstname = :firstname or u.lastname = :lastname")
User findByLastnameOrFirstname(@Param("lastname") String lastname,
                                 @Param("firstname") String firstname);
```

### 4. SpEL
- Truy cập thuộc tính của một đối tượng: `objectName.propertyName`
- Truy cập phần tử của một mảng hoặc danh sách: `arrayName[index]` hoặc `listName[index]`
- Gọi một phương thức trên một đối tượng: `objectName.methodName()`
- Sử dụng các toán tử số học và logic: +, -, *, /, &&, ||, ==, !=, >, <, >=, <=

```java
@Component
public class OrderService {
    // truy cập thuộc tính đối tượng
    @Value("#{orderRepository.count()}")
    private int totalOrders;

    // sử dụng toán tử
    @Value("#{(4 + 5) * 2}")
    private int multiplication;
    
    // truy cập danh sách
    @Value("#{productList[0]}")
    private Product firstProduct;

    @Value("#{productList.size()}")
    private int totalProducts;
    
    // phân quyền
    @PreAuthorize("hasRole('ADMIN')") // Sử dụng SpEL để kiểm tra vai trò của người dùng
    public void deleteOrder(Order order) {
        // Xóa đơn hàng
    }
}
```

### 5. Transactional
Thêm @Transactional nếu muốn phương thức đó là transaction. Dùng annotation này cho các phương thức modify
```java
public interface UserRepository extends JpaRepository<User, Long> {

  @Override
  @Transactional(timeout = 10)
  public List<User> findAll();

  // Further query method declarations
}

// Using a facade to define transactions for multiple repository calls
@Service
class UserManagementImpl implements UserManagement {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserManagementImpl(UserRepository userRepository,
                              RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional(timeout = <seconds>)
    public void addRoleToAllUsers(String roleName) {

        Role role = roleRepository.findByName(roleName);

        for (User user : userRepository.findAll()) {
            user.addRole(role);
            userRepository.save(user);
        }
    }
}
```

### 6. Locking
- `PESSIMISTIC_READ` – cho chúng ta một chìa khoá gọi là shared lock, với khoá này chúng ta chỉ có thể đọc dữ liệu.
- `PESSIMISTIC_WRITE` – nhận một chìa khoá gọi là exclusive lock, chúng ta sẽ có toàn quyền đọc, xoá, chỉnh sửa dữ liệu và ngăn không cho các transaction khác đọc, xoá, và cập nhật dữ liệu lên chúng.
- `PESSIMISTIC_FORCE_INCREMENT` – giống với cơ chế hoạt động của PESSIMISTIC_WRITE, ngoài ra nó còn tăng giá trị của thuộc tính version trong entity.

[Tham khảo](https://shareprogramming.net/tim-hieu-pessimistic-locking-trong-jpa/)
```java
interface UserRepository extends Repository<User, Long> {

  // Plain query method
  @Lock(LockModeType.READ)
  List<User> findByLastname(String lastname);
}
```

### 7. Auditing
thuộc tính được đánh dấu sẽ tự động cập nhật giá trị trong cơ sở dữ liệu tương ứng với created_by, created_at, updated_by, updated_at
```java
class Customer {

  @CreatedBy
  private User user;
  
  @CreatedDate
  private DateTime createdDate;

  // … further properties omitted
}
```

### 8. pagination
```java
// repository
List<Product> findAllByPrice(double price, Pageable pageable);

// controller
Pageable sortedByName =
        PageRequest.of(0, 3, Sort.by("name"));

Pageable sortedByPriceDesc =
        PageRequest.of(0, 3, Sort.by("price").descending());

Pageable sortedByPriceDescNameAsc =
        PageRequest.of(0, 5, Sort.by("price").descending().and(Sort.by("name")));
```