import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "employeetable")
public class Employee {

    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int userId;
    
    @Column(name="userName")
    private String userName;
    
    private String role;
    private String department;
    
    @Column(name="email_id")
    private String emailID;
    
    private String phonenumber;
    private String address;
    private double salary;
    private String password;

    // Default Constructor
    public Employee() {}

    // Parameterized Constructor
    public Employee(String name, String role, String department, String emailID, String phonenumber, String address, double salary,String password) {
        this.userName = name;
        this.role = role;
        this.department = department;
        this.emailID = emailID;
        this.phonenumber = phonenumber;
        this.address = address;
        this.salary = salary;
        this.password = password;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getEmailID() { return emailID; }
    public void setEmailID(String emailID) { this.emailID = emailID; }
    public String getPhonenumber() { return phonenumber; }
    public void setPhonenumber(String phonenumber) { this.phonenumber = phonenumber; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    public String getPassword(){ return password;}
    public void setPassword(String password){this.password=password;}
    public String toString(){ 
        return "Employee {" +
           "userId=" + userId +
           ", userName='" + userName + '\'' +
           ", role='" + role + '\'' +
           ", department='" + department + '\'' +
           ", emailID='" + emailID + '\'' +
    
           '}'; 
    }
}
