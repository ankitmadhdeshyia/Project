import org.hibernate.Session;
import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Ankit {
    public static void main(String[] args) {
        // Test t1=new Test();
        // t1.setId(101);
        // t1.setAddress("sdjnkasjdas");
        // t1.setPhonenumber("2349184083650");
        // t1.setSalary("263494");

        //Hey hibernate save this t1 object
        Configuration cfg=new Configuration();

        cfg.addAnnotatedClass(Test.class);

        cfg.configure();


        SessionFactory factory=cfg.buildSessionFactory();

        Session session=factory.openSession();
       
        Test t2=session.find(Test.class,101);
        System.out.println(t2);


          
          
           session.close();
        factory.close();
    }
}
