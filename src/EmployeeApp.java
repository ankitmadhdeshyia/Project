import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class EmployeeApp {
    public static void main(String[] args) {
        
        //Load Hibernate configuration And create SessionFactory
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml"); // This File Will Load your Database Setting
        
        // Agar aapne XML file mein Employee class map nahi ki hai, toh aap code se bhi add kar sakte hain:
        // cfg.addAnnotatedClass(Employee.class); 
          //session factory will work like a connection 
        SessionFactory factory = cfg.buildSessionFactory();

        //opening the session(Connection) With Database
        Session session = factory.openSession();
        //Starting The Transaction
        Transaction tx = session.beginTransaction();

        try {
            // Creating The Object Of Employee
           Test tt=new Test("9125232353","parasiya","20000");

            // 5. Object ko database table ("employeetable") mein save karna
            session.persist(tt); 

            // 6. Transaction ko commit karna taaki data permanently save ho jaye
            tx.commit();
            System.out.println("Employee data successfully database mein save ho gaya hai!");
            System.out.flush();  

        } catch (Exception e) {
            // Agar koi error aati hai toh purana badlav cancel (rollback) karna
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Data save nahi ho paya. Error occurred:");
            e.printStackTrace();
        } finally {
            // 7. Resources ko sahi se band karna
            session.close();
            factory.close();
        }
    }
}
