import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity 
@Table(name ="Ankit")
public class Test {
    @Id
    private int ids;
     private String phonenumber;
    private String address;
    private String salary;
    Test(){

    }
    Test(String phonenumber,String address,String salary){
       this.phonenumber=phonenumber;
       this.address=address;
       this.salary=salary;

    }
    
    public static void main(String[] args) {
        
    }
	public int getId() {
		return ids;
	}
	public void setId(int ids) {
		this.ids=ids;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	@Override
	public String toString() {
		return "Test [ids=" + ids + ", phonenumber=" + phonenumber + "]";
	}
}
