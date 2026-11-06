import java.io.*;
import org.hibernate.query.Query;
import jakarta.persistence.LockModeType;
import java.util.logging.Logger;
import java.util.List;
import java.time.LocalDate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

class Security {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final Logger logger = Logger.getLogger(Security.class.getName());

    public static Employee authenticateUser(String user, String password, String role, Session session) {

        String hql = "FROM Employee c WHERE c.userName = :name AND c.password = :psd AND c.role = :rol";

        try {
            Query<Employee> query = session.createQuery(hql, Employee.class);
            query.setParameter("name", user);
            query.setParameter("psd", password); //
            query.setParameter("rol", role);

            return query.uniqueResult();

        } catch (Exception e) {
            logger.severe("Error in Authniticating user" + e.getMessage());
            return null;
        }
    }

    public static void processLogin(String user, String password, String role, Session session) {

        Employee loggedInUser = authenticateUser(user, password, role, session);
        if (loggedInUser != null) {
            System.out.println("Loggin SuccessFull :Welcome" + loggedInUser.getUserName());

            if (loggedInUser.getRole().equalsIgnoreCase("admin")) {

                boolean flag = true;
                while (flag) {
                    System.out.println("\n===== ADMIN MENU =====");
                    System.out.println(
                            "1. View Customer Data\n, 2. View Employee Data\n, 3. Close Account\n, 4. Delete Employee\n, 5. Add Employee\n, 6. Update role and Salary\n,7. View All Transaction\n, 8. Block Account\n,9.Exit");
                    System.out.println("Enter Your Choice");
                    int choice;
                    try {
                        choice = Validation.getValidInteger();

                    } catch (NumberFormatException e) {
                        System.out.println("Please Enter A Valid Numeric Choice");
                        continue; 

                    }
                    switch (choice) {
                        case 1:
                            Admin.viewCustomerData(session);
                            break;
                        case 2:
                            Admin.viewEmployeeData(session);
                            break;
                        case 3:
                            Admin.closeAccount(session);
                            break;
                        case 4:
                            Admin.deleteEmployee(session);
                            break;
                        case 5:
                            Admin.addEmployee(session);
                            break;
                        case 6:
                            Admin.updateroleSalary(session);
                            break;
                        case 7:
                            Admin.viewAllTransaction(session);
                            break;
                        case 8:
                            Admin.blockAccount(session);
                            break;
                        case 9:
                            System.out.println("Thanks Admin : Exiting Admin Menu");
                            flag = false;
                            break;
                        default:
                            System.out.println("Invalid Choice");
                    }
                }

            } else {
                System.out.println("Welcome" + " " + loggedInUser.getUserName());
                logger.info("Staff " + loggedInUser.getUserName() + " logged in successfully.");
                boolean flags = true;

                while (flags) {
                    System.out.println("\n===== EMPLOYEE MENU =====");
                    System.out.println(
                            "1. Open New Account\n2. Deposit Money\n3. Withdraw Money\n4. Transfer Money\n5. View Account Balance\n6. Get Transaction Statement\n7. Update Customer Details\n8. Close Account\n9. Exit Employee Menu");
                    System.out.println("Enter Your Choice");

                    int choice;
                    try {
                        choice = Validation.getValidInteger();

                    } catch (NumberFormatException e) {
                        System.out.println("Please Enter A Valid Numeric Choise");
                        continue; //
                    }
                    switch (choice) {
                        case 1:
                            Staff.openAccount(session);
                            break;
                        case 2:
                            Staff.depositMoney(session);
                            break;
                        case 3:
                            Staff.withdrawMoney(session);
                            break;
                        case 4:
                            Staff.transferMoney(session);
                            break;
                        case 5:
                            Staff.viewBalance(session);
                            break;
                        case 6:
                            Staff.transactionStatement(session);
                            break;
                        case 7:
                            Staff.updateCustomerDetails(session);
                            break;
                        case 8:
                            Admin.closeAccount(session);
                            break;
                        case 9:
                            System.out.println("Thanks : Existing Employee Menu");
                            flags = false;
                            break;

                        default:
                            System.out.println("Invalid Choice");
                    }
                }
            }
        } else {
            System.out.println("Invalid Username, Password, or Role! Access Denied.");
        }
    }

    public static void changePassword(Session session) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            System.out.println("Enter user id");
            int userId = Validation.getValidInteger();
            Employee emp = session.find(Employee.class, userId);

            if (emp != null) {
                System.out.println("Enter new password you want to set");
                String psd = br.readLine();
                emp.setPassword(psd);
                tx.commit();
                System.out.println("password changed Succefully");
            } else {
                System.out.println(" User ID Not Found");
                tx.rollback();

            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                System.out.println("Error in changing the password");
                logger.severe("Can not change password" + e.getMessage());
            }
        }
    }
}

class Admin {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final Logger logger = Logger.getLogger(Admin.class.getName());

    public static void viewCustomerData(Session session) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String viewData = "from Customer";
            Query<Customer> query = session.createQuery(viewData, Customer.class);
            List<Customer> customerList = query.list();

            for (Customer cust : customerList) {
                System.out.println("Customer Name: " + cust.getCustomername());
                System.out.println("Customer Account Number: " + cust.getAccountnumber());
                System.out.println("Customer Balance: " + cust.getCurrentbalance());
                System.out.println("Customer Address: " + cust.getAddress());
                System.out.println("Customer Phone Number: " + cust.getPhonenumber());
                System.out.println("Customer Email: " + cust.getEmailId());
                System.out.println("---------------------------------------------------");
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                logger.severe("Database Error: " + e.getMessage());
                System.out.println("Error in fetching tha data from the database");
            }
        }
    }

    public static void viewEmployeeData(Session session) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "from Employee";
            Query<Employee> query = session.createQuery(hql, Employee.class);
            List<Employee> employeeList = query.list();

            for (Employee emp : employeeList) {
                System.out.println("-----------Employee Data--------------");
                System.out.println("Employee user_id" + " " + emp.getUserId());
                System.out.println("Employee Name" + " " + emp.getUserName());
                System.out.println("Employee role" + " " + emp.getRole());
                System.out.println("Employee Department" + " " + emp.getDepartment());
                System.out.println("Employee email_id" + " " + emp.getEmailID());
                System.out.println("Employee Number" + " " + emp.getPhonenumber());
                System.out.println("Employee address" + " " + emp.getAddress());
                System.out.println("Employee salary" + " " + emp.getSalary());
                System.out.println("---------------------------------------------------");
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Error in fetching tha data from the database");

            logger.severe("Error in fetching the data from the database" + e.getMessage());
        }
    }

    public static void closeAccount(Session session) {
        System.out.println("Closing Account");

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            System.out.println("Enter Account Number: ");
            int accNumber = Validation.getValidInteger();
            Customer customer = session.find(Customer.class, accNumber);
            if (customer != null) {
                customer.setAccountstatus(Accountstatus.closed);
                tx.commit();
            } else {
                tx.rollback();
                System.out.println(" Account number not found");
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Failed in closing Account");
            logger.severe("Operation failed" + e.getMessage());
        }
    }

    public static void deleteEmployee(Session session) {
        System.out.println("Deleting Employee");

        String deleteEmployee = "delete Employee e where e.userId=:id";

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            System.out.println("Enter User ID: ");
            int userId = Validation.getValidInteger();

            int rowAffected = session.createMutationQuery(deleteEmployee).setParameter("id", userId).executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Employee Deleted");
                tx.commit();
            } else {
                System.out.println("Failed to Delete Employee");
                tx.commit();
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Please Enter Valid User Id");
        }
    }

    public static void addEmployee(Session session) {
        System.out.println("Adding Employee");

        Transaction tx = null;
        Employee emp = new Employee();
        try {
            tx = session.beginTransaction();
            System.out.println("Enter the Employee Name");
            String staffname = Validation.getValidString();

            System.out.println("Enter the Employee role");
            String staffrole = Validation.getValidString();

            System.out.println("Enter Department");
            String department = Validation.getValidString();

            System.out.println("Enter  email_id");
            String emailId = Validation.getValidEmail();

            System.out.println("Enter phonenumber");
            String phonenumber = Validation.getValidPhone();

            System.out.println("Enter address");
            String address = br.readLine();

            System.out.println("Enter salary");
            double salary = Validation.getValidDouble();

            System.out.println("Enter new Password ");
            String passwords = br.readLine();

            emp.setUserName(staffname);
            emp.setRole(staffrole);
            emp.setDepartment(department);
            emp.setEmailID(emailId);
            emp.setPhonenumber(phonenumber);
            emp.setAddress(address);
            emp.setSalary(salary);
            emp.setPassword(passwords);

            session.persist(emp);
            tx.commit();

        } catch (Exception e) {
            if (tx != null) {

                tx.rollback();
            }
            System.out.println("Failed in Adding Employee");
            logger.severe(e.getMessage());

        }
    }

    public static void updateroleSalary(Session session) {
        System.out.println("Employee role and salary");
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            System.out.println("Enter employee Id");
            int employeeId = Validation.getValidInteger();
            Employee emp = session.find(Employee.class, employeeId);
            if (emp != null) {
                System.out.println("Enter new  salary of the Employee");
                double salary = Validation.getValidDouble();
                System.out.println("Enter new role of Employee");
                String role = br.readLine();
                emp.setRole(role);
                emp.setSalary(salary);
                tx.commit();
                System.out.println("Employee Data Updated SuccessFully");
            } else {
                System.out.println("Employee not Founf");
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Failed updating role And Salary");
        }
    }

    public static void viewAllTransaction(Session session) {
        String hql = "from Transactions";
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Query<Transactions> query = session.createQuery(hql, Transactions.class);
            List<Transactions> transactionList = query.list();
            if (transactionList == null) {
                System.out.println("Transaction Not found");
                tx.rollback();
                return;
            }
            for (Transactions c : transactionList) {
                System.out.println("---------------Transaction------------------");
                System.out.println("Transaction id" + " " + c.getTransactionId());
                System.out.println("Source Account Number" + " " + c.getSourceAccount());
                System.out.println("Destination Account Number" + " " + c.getDestinationAccount());
                System.out.println("Transaction Amount" + " " + c.getAmount());
                System.out.println("Transaction Type" + " " + c.getTransactionType());
                System.out.println("Transaction status" + " " + c.getStatus());
                System.out.println("Transaction Date" + " " + c.getTransactionDate());
                System.out.println("Transaction Remarks" + " " + c.getRemarks());
                System.out.println("------------------------------------------");
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.print("Failed to Load Data From The Database");
            logger.severe("Failed to Load Data From The Database" + e.getMessage());
        }
    }

    public static void blockAccount(Session session) {
        int accountnumber = 0;
        System.out.println("Enter Account number");
        accountnumber = Validation.getValidInteger();
        Transaction tx = null;
        try {
            Customer customer = session.find(Customer.class, accountnumber);
            if (customer == null) {
                System.out.println("Account Not Found");
                return;
            }
            Accountstatus checkStatus = customer.getAccountstatus();
            if (Accountstatus.blocked == checkStatus) {
                System.out.println("Account is already blocked proceed with the other operation");
                return;
            }
            tx = session.beginTransaction();
            System.out.println("blocking Account");
            customer.setAccountstatus(Accountstatus.blocked);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Failed To block account");
            logger.severe("Blocking Failed" + e.getMessage());
        }
    }

    public static void unblockAccount(Session session) {
        System.out.println("Enter Account number");
        int accountnumber = Validation.getValidInteger();
        Transaction tx = null;
        try {
            Customer customer = session.find(Customer.class, accountnumber);
            if (customer == null) {
                System.out.println("Account not found");
                return;
            }
            Accountstatus checkStatus = customer.getAccountstatus();
            if (Accountstatus.active == checkStatus) {
                System.out.println("Account is Unblocked proceed with the other operation");
                return;
            }
            tx = session.beginTransaction();
            System.out.println("Unblocking Account");
            customer.setAccountstatus(Accountstatus.active);
            System.out.println("Account Unblocked Successfully");
            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                System.out.println("An unexpected error occurred.");
                logger.severe("System Error: " + e.getMessage());
            }
        }
    }
}

class Staff {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final Logger logger = Logger.getLogger(Staff.class.getName());

    public static void openAccount(Session session) {
        System.out.println("Opening Account");
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            System.out.print("Enter Customer Name: ");
            String customerName = br.readLine();

            System.out.print("Enter Date of Birth format(YYYY-MM-DD): ");
            LocalDate dateOfBirth = LocalDate.parse(br.readLine().trim());

            System.out.print("Enter Gender: ");
            String genderInput = br.readLine();
            Gender gender = Gender.valueOf(genderInput.toLowerCase());

            System.out.print("Enter Father's Name: ");
            String fathername = br.readLine();

            System.out.print("Enter Address: ");
            String address = br.readLine();

            System.out.print("Enter Phone Number: ");
            String phonenumber = br.readLine();

            System.out.print("Enter Email ID: ");
            String emailId = br.readLine();

            System.out.print("Enter Account Type: ");
            String accountTypeInput = br.readLine();
            AccountType accountType = AccountType.valueOf(accountTypeInput.toLowerCase().trim());

            System.out.print("Enter Money Depositing: ");
            int currentbalance = Integer.parseInt(br.readLine());

            System.out.print("Enter Account Status:(active or inactive) ");
            String accountStatusInput = br.readLine();
            Accountstatus accountstatus = Accountstatus.valueOf(accountStatusInput.trim().toLowerCase());

            System.out.println("Enter Kyc Status");
            String kycStatus = br.readLine();

            System.out.println("Enter Aadhar Card Number");
            String aadharNumber = br.readLine();

            System.out.println("Enter Pan Card Number");
            String panNumber = br.readLine();

            Customer customer = new Customer();

            customer.setCustomername(customerName);
            customer.setDateOfBirth(dateOfBirth);
            customer.setGender(gender);
            customer.setFathername(fathername);
            customer.setAddress(address);
            customer.setPhonenumber(phonenumber);
            customer.setEmailId(emailId);
            customer.setAccounttype(accountType);
            customer.setCurrentbalance(currentbalance);
            customer.setAccountstatus(accountstatus);
            customer.setKycStatus(kycStatus);
            customer.setAdharCard(aadharNumber);
            customer.setPanCard(panNumber);

            session.persist(customer);
            tx.commit();
            System.out.println("Account created SuccessFully And Saved");

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Error While Opening Account");
            logger.severe("Error While Opening Account" + e.getMessage());
        }
    }

    public static void depositMoney(Session session) {
        System.out.println("Depositing Money");
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            System.out.print("Enter Account Number: ");
            int accountNumber = Validation.getValidInteger();
            Customer customer = session.find(Customer.class, accountNumber);
            if (customer != null) {
                System.out.print("Enter Amount to Deposit: ");
                int amount = Validation.getValidInteger();
                if (amount <= 0) {
                    System.out.println("Amount can not be less than 0");
                    tx.rollback();
                    return;
                }
                int balance = customer.getCurrentbalance();
                customer.setCurrentbalance(balance + amount);
                tx.commit();
                System.out.println("Money Deposited successfully");

            } else {
                System.out.println("Accoount Not Found");
                tx.rollback();
                return;
            }

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Server Down");
            logger.severe("Failed in depositing money" + e.getMessage());
        }
    }

    public static void withdrawMoney(Session session) {
        Transaction tx = null;
        try {

            System.out.println("Enter Account Number");
            int accountNumber = Validation.getValidInteger();
            Customer customer = session.find(Customer.class, accountNumber);
            if (customer != null) {
                Accountstatus status = customer.getAccountstatus();
                if (status == Accountstatus.inactive) {
                    System.out.println("Account is blocked You can not withdraw Money");
                    return;
                }
                System.out.println("Enter Amount to Withdraw: ");
                int amount = Validation.getValidInteger();
                if (amount <= 0) {
                    System.out.println("Amount must be greater than 0");
                    return;
                }
                int balance = customer.getCurrentbalance();

                if (amount > balance) {
                    System.out.println("Insufficient Balance");
                    return;
                }
                tx = session.beginTransaction();
                customer.setCurrentbalance(balance - amount);
                tx.commit();
                System.out.println("Amount Withdraw Successfull");
            } else {
                System.out.println("Invalid Account Number");
                return;
            }

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Server Down");
            logger.severe("System not working" + e.getMessage());
        }
    }

    public static void transferMoney(Session session) {

        System.out.println("Enter Source Account number ");
        int sourceAccountNumber = Validation.getValidInteger();
        Customer sourceAccount = session.find(Customer.class, sourceAccountNumber);
        if (sourceAccount == null) {
            System.out.println("Sender Account Not found");
            return;
        }
        Accountstatus status = sourceAccount.getAccountstatus();
        if (status == Accountstatus.inactive) {
            System.out.println("Account is not Active Please visit you branch");
            return;
        }
        System.out.println("Enter Destination Account number ");
        int destinationAccountNumber = Validation.getValidInteger();
        Customer destinationAccount = session.find(Customer.class, destinationAccountNumber);
        if (destinationAccount == null) {
            System.out.println("Destination account not found");
            return;
        }
        Accountstatus destiStatus = destinationAccount.getAccountstatus();
        if (destiStatus == Accountstatus.inactive || destiStatus == Accountstatus.blocked) {
            System.out.println("Account is blocked or inactive ");
            return;
        }
        if (sourceAccount == destinationAccount) {
            System.out.println("Account Number Cannot be same");
            return;
        }
        System.out.println("Enter Amount to transfer");
        int amount = Validation.getValidInteger();
        if (amount <= 0) {
            System.out.println("Amount must be greater than 0");
            return;
        }
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Customer sender = null;
            Customer receiver = null;
            if (sourceAccountNumber < destinationAccountNumber) {
                sender = session.find(Customer.class, sourceAccountNumber, LockModeType.PESSIMISTIC_WRITE);
                receiver = session.find(Customer.class, destinationAccountNumber, LockModeType.PESSIMISTIC_WRITE);
            } else {
                receiver = session.find(Customer.class, destinationAccountNumber, LockModeType.PESSIMISTIC_WRITE);
                sender = session.find(Customer.class, sourceAccountNumber, LockModeType.PESSIMISTIC_WRITE);
            }
            if (receiver == null || sender == null) {
                System.out.println("Destination or Sender Account not found");
                tx.rollback();
                return;
            }
            int balance = sender.getCurrentbalance();
            if (balance < amount) {
                System.out.println("Insufficient fund");
                tx.rollback();
                return;
            }
            int senderBalance = sender.getCurrentbalance();
            sender.setCurrentbalance(senderBalance - amount);
            int recieverBalance = receiver.getCurrentbalance();
            receiver.setCurrentbalance(recieverBalance + amount);
            tx.commit();
            System.out.println("Amount Tranferred successfully");
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Server Down");
            logger.severe("Failed In Transferring money" + e.getMessage());
        }
    }

    public static void viewBalance(Session session) {
        System.out.println("Viewing Balance");
        try {
            System.out.println("Enter Account Number: ");
            int accountNumber = Validation.getValidInteger();
            Customer accNumber = session.find(Customer.class, accountNumber);
            if (accNumber == null) {
                System.out.println("Account Not found");
                return;
            }
            System.out.println("Customer Balance" + " " + accNumber.getCurrentbalance());
        } catch (Exception e) {
            System.out.println("Error in Fetching Balanace");
            logger.severe(e.getMessage());
        }
    }

    public static void transactionStatement(Session session) {
        System.out.println("Account Statement");
        Transaction tx = null;
        try {
            System.out.println("Enter Account Number: ");
            int accountnumber = Validation.getValidInteger();
            tx = session.beginTransaction();
            String statement = "select t.transactionId, t.sourceAccount, t.destinationAccount, "
                    + "t.transactionType, t.amount, t.transactionDate "
                    + "from Transaction t where t.sourceAccount = :accountNumber "
                    + "or t.destinationAccount = :accountNumber order by t.transactionDate";
            Query<Object[]> query = session.createQuery(statement, Object[].class);
            query.setParameter("accountNumber", accountnumber);
            List<Object[]> result = query.getResultList();
            if (result.isEmpty()) {
                System.out.println("No Transaction found");
                tx.rollback();
                return;
            }
            for (Object[] row : result) {
                System.out.println("Transaction ID: " + row[0]);
                System.out.println("Source Account: " + row[1]);
                System.out.println("Destination Account: " + row[2]);
                System.out.println("Transaction Type: " + row[3]);
                System.out.println("Amount: " + row[4]);
                System.out.println("Transaction Date: " + row[5]);
                System.out.println("---------------------------------------------");
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Account Not Found");
            logger.severe("Database Related Issues" + e.getMessage());
        }
    }

    public static void updateCustomerDetails(Session session) {
        System.out.println("Updating Customer Details");
        Transaction tx = null;
        try {
            System.out.print("Enter Account Number: ");
            int accountNumber = Validation.getValidInteger();

            Customer customer = session.find(Customer.class, accountNumber);
            if (customer == null) {
                System.out.println("Customer Not found");
                return;
            }
            tx = session.beginTransaction();
            System.out.println("[Note: Press 'Enter' directly to SKIP updating that field and keep old value]");
            System.out.print("Enter Customer Name (" + customer.getCustomername() + ")");
            String customerName = br.readLine().trim();
            if (!customerName.isEmpty()) {
                customer.setCustomername(customerName);
            }
            System.out.println("Enter Date of Birth(" + customer.getDateOfbirth() + ")");
            LocalDate date = Validation.getValidDate();
            if (date != null) {
                customer.setDateOfBirth(date);
            }
            System.out.println("Enter Gender (" + customer.getGender() + ")");
            String newGender = br.readLine();
            if (!newGender.isEmpty()) {
                Gender gender = Gender.valueOf(newGender.toLowerCase());
                customer.setGender(gender);
            }
            System.out.println("Enter Father Name(" + customer.getFathername() + ")");
            String fatherName = Validation.getValidString();
            if (!fatherName.isEmpty()) {
                customer.setFathername(fatherName);
            }
            System.out.println("Enter Address(" + customer.getAddress() + ")");
            String newAddress = Validation.getValidString();
            if (!newAddress.isEmpty()) {
                customer.setAddress(newAddress);
            }
            System.out.println("Enter Phone number(" + customer.getPhonenumber() + ")");
            String newNumber = Validation.getValidString();
            if (!newNumber.isEmpty()) {
                customer.setPhonenumber(newNumber);
            }
            System.out.println("Enter Email Id(" + customer.getEmailId() + ")");
            String emailId = Validation.getValidEmail();
            if (!emailId.isEmpty()) {
                customer.setEmailId(emailId);
            }
            System.out.println("Enter Account Type(" + customer.getAccounttype() + ")");
            String accountType = br.readLine();
            if (!accountType.isEmpty()) {
                AccountType account = AccountType.valueOf(accountType.toLowerCase());
                customer.setAccounttype(account);
            }
            session.merge(customer);
            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                System.out.println("Failed In Updating The details");
            }
            logger.severe("user CAnnot update the customer details" + e.getMessage());
        }

    }
}

class Account {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final Logger logger = Logger.getLogger(Account.class.getName());

    public static String checkAccountStatus(Session session) {
        Transaction tx = null;
        String status = null;
        try {
            String hql = "select c.accountstatus from Customer c where c.accountnumber=:accNumber";
            tx = session.beginTransaction();

            System.out.println("Enter Account Number");
            int accountNumber = Validation.getValidInteger();

            Query<String> query = session.createQuery(hql, String.class);
            query.setParameter("accNumber", accountNumber);

            status = query.uniqueResult();

            if (status != null) {
                System.out.println("Current Status = " + status);

            } else {
                System.out.println("Please enter Valid Account number");
            }
            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Account Status can not be checked at this moment Please try again later");
            logger.severe("Checking Account Status Failed " + e.getMessage());
        }
        return status;
    }

    public static void checkACustomerData(Session session) {
        String customerData = " from Customer c where  c.accountnumber=:accNumber";
        Transaction tx = null;
        Customer customer = null;

        try {
            tx = session.beginTransaction();
            System.out.println("Enter Account Number Whose data You want to check");
            int accountNumber = Validation.getValidInteger();
            Query<Customer> query = session.createQuery(customerData, Customer.class);

            query.setParameter("accNumber", accountNumber);
            customer = query.uniqueResult();

            if (customer != null) {
                System.out.println("----------- Customer Data --------------");
                System.out.println("Customer Name:    " + customer.getCustomername());
                System.out.println("Date of Birth:    " + customer.getDateOfbirth());
                System.out.println("Gender:           " + customer.getGender());
                System.out.println("Father's Name:    " + customer.getFathername());
                System.out.println("Address:          " + customer.getAddress());
                System.out.println("Phone Number:     " + customer.getPhonenumber());
                System.out.println("Email ID:         " + customer.getEmailId());
                System.out.println("Account Number:   " + customer.getAccountnumber());
                System.out.println("Current Balance:  " + customer.getCurrentbalance());
                System.out.println("Account Type:     " + customer.getAccounttype());
                System.out.println("Account Status:   " + customer.getAccountstatus());
                System.out.println("Kyc Status:       " + customer.getKycStatus());
                System.out.println("Last Kyc Date:   " + customer.getLastKycDate());
                System.out.println("Aadhar Number:   " + customer.getAdharCard());
                System.out.println("Pan Number:   " + customer.getPanCard());

                System.out.println("----------------------------------------");
            } else {
                System.out.println("Please Enter Valid Account Number");
            }
            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Can not fetch The data from tha databse");
            logger.severe("Error in fetching tha data" + e.getMessage());
        }
    }

    public static void verifyKyc(Session session) {
        Transaction tx = null;
        try {
            System.out.println("Enter Account Number");
            int accountNumber = Validation.getValidInteger();
            tx = session.beginTransaction();
            Customer customer = session.find(Customer.class, accountNumber);
            if (customer != null) {
                customer.setKycStatus("updated");
                System.out.println("Kyc Updated successfullyy");
                tx.commit();
            } else {
                System.out.println("Customer Not Found");
                tx.rollback();
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("kyc update Failed");
            logger.severe("System Error occured" + e.getMessage());
        }
    }

    public static void Kyc(Session session) {

        Transaction tx = null;
        try {
            System.out.println("Enter Account Number");
            int accountNumber = Validation.getValidInteger();
            tx = session.beginTransaction();
            Customer customer = session.find(Customer.class, accountNumber);
            if (customer != null) {

                System.out.println("Enter Aadhar number");
                String aadharNumber = Validation.getValidString();

                System.out.println("Enter Pan card Number");
                String panNumber = Validation.getValidString();

                customer.setAdharCard(aadharNumber);
                customer.setPanCard(panNumber);
                tx.commit();
                System.out.println("KYC Updated Successfully");
            } else {
                System.out.println("Customer Not Found");
                tx.rollback();
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }
}

class Validation {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static int getValidInteger() {
        while (true) {
            try {
                return Integer.parseInt(br.readLine());
            } catch (NumberFormatException e) {
                System.out.println("Please Enter A valid Number");
            } catch (IOException e) {
                System.out.println("invalid input ");
            }
        }

    }

    public static double getValidDouble() {
        while (true) {
            try {
                return Double.parseDouble(br.readLine());
            } catch (NumberFormatException e) {
                System.out.println("Please Enter A valid Number");
            } catch (IOException e) {
                System.out.println("invalid input ");
            }
        }

    }

    public static LocalDate getValidDate() {
        while (true) {
            try {
                String input = br.readLine().trim();
                if (input.isEmpty()) {
                    return null;
                }
                return LocalDate.parse(input);

            } catch (java.time.format.DateTimeParseException e) {
                System.out.print("Please enter date in YYYY-MM-DD format (or press Enter to skip): ");
            } catch (IOException e) {
                System.out.print("Invalid input! Try again: ");
            }
        }
    }

    public static String getValidPhone() {
        while (true) {
            try {
                System.out.println("Enter Phone number");
                String phonenumber = br.readLine();
                if (phonenumber.matches("\\d{10}")) {
                    return phonenumber;
                }
                System.out.println("Please enter a valid 10-digit phone number.");
            } catch (IOException e) {
                System.out.println("invalid input ");
            }
        }
    }

    public static String getValidEmail() {
        while (true) {
            try {
                System.out.print("Enter Email ID: ");
                String email = br.readLine();

                if (email.matches("^[A-Za-z0-9+._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
                    return email;
                } else {
                    System.out.println("Please enter a valid email address.");
                }
            } catch (IOException e) {
                System.out.println("Please Enter Email in valid Format");

            }
        }

    }

    public static String getValidAccountStatus() {
        while (true) {
            try {
                System.out.println("Enter Account status");
                String accountstatus = br.readLine();
                if ("active".equalsIgnoreCase(accountstatus)) {
                    return "active";
                } else if ("inactive".equalsIgnoreCase(accountstatus)) {
                    return "inactive";

                } else if ("blocked".equalsIgnoreCase(accountstatus)) {
                    return "blocked";

                } else if ("closed".equalsIgnoreCase(accountstatus)) {
                    return "closed";

                } else if ("unblocked".equalsIgnoreCase(accountstatus)) {
                    return "unblocked";
                } else {
                    System.out.println("Please enter Valid Account status.");
                }
            } catch (IOException e) {
                System.out.println("Enter A Valid Character Account");
            }
        }

    }

    public static String getValidAccountType() {
        while (true) {
            try {
                System.out.println("Enter Account type");
                String accounttypes = br.readLine();
                if ("saving".equalsIgnoreCase(accounttypes)) {
                    return "saving";
                } else if ("current".equalsIgnoreCase(accounttypes)) {
                    return "current";
                } else {
                    System.out.println("Please enter saving or current.");
                }
            } catch (IOException e) {
                System.out.println("Please enter valid account type");
            }
        }
    }

    public static String getValidString() {
        while (true) {
            try {
                return br.readLine();
            } catch (IOException e) {
                System.out.println("Please Enter A valid Value");

            }
        }
    }
}

public class Home {

    private static final Logger logger = Logger.getLogger(Home.class.getName());
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        Transaction trans = null;

        try (SessionFactory factory = cfg.buildSessionFactory();
                Session session = factory.openSession()) {
          
            System.out.println("Enter Your  Name");
            String name = br.readLine();
            System.out.println("Enter Your Password");
            String psd = br.readLine();
            System.out.println("Enter Your role");
            String role = br.readLine();
            trans = session.beginTransaction();
            Security.processLogin(name, psd, role, session);

            trans.commit();
            System.out.println("Operation Successfull");
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }
            System.out.println("Failed to Execute the operation");
            logger.severe("Operation failed" + e.getMessage());
        }

    }
}
