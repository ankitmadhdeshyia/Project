import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;

 @Entity 
 @Table(name="customertable")
public class Customer {
    private String customername;
    @Column(name="date_of_birth")
    private LocalDate dateOfBirth;
   @Enumerated(EnumType.STRING)
    private Gender gender;
    private String fathername;
    private String address;
    private String phonenumber;
    @Column(name="email_id")
    private String emailId;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountnumber;
    @Enumerated(EnumType.STRING) 
    private AccountType accounttype;
    private int currentbalance;
    @Enumerated (EnumType.STRING)
    private Accountstatus accountstatus;
    @Column(name="kyc_status")
    private String kycStatus;
    @CreationTimestamp 
     @Column(name="last_kyc_date",updatable=false)
     private LocalDateTime lastKycDate;
      @Column(name="aadhar_card")
      private String adharCard;
      @Column(name="pan_card")
      private String panCard;
        public Customer(){}

        public Customer(String customername, LocalDate dateOfBirth, Gender gender, String fathername,
          String address, String phonenumber, String emailId, int accountnumber,
          AccountType accounttype, int currentbalance, Accountstatus accountstatus,
          String kycStatus, LocalDateTime lastKycDate, String adharCard, String panCard) {
          this.customername = customername;
          this.dateOfBirth = dateOfBirth;
          this.gender = gender;
          this.fathername = fathername;
          this.address = address;
          this.phonenumber = phonenumber;
          this.emailId = emailId;
          this.accountnumber = accountnumber;
          this.accounttype = accounttype;
          this.currentbalance = currentbalance;
          this.accountstatus = accountstatus;
          this.kycStatus = kycStatus;
          this.lastKycDate = lastKycDate;
          this.adharCard = adharCard;
          this.panCard = panCard;
        }

        public String getCustomername() {
          return customername;
        }

        public void setCustomername(String customername) {this.customername = customername;}

        public LocalDate getDateOfbirth() {return dateOfBirth;}

        public void setDateOfBirth(LocalDate dateOfBirth) {this.dateOfBirth = dateOfBirth;}

        public Gender getGender() {return gender;}

        public void setGender(Gender gender) {this.gender = gender; }

        public String getFathername() {return fathername; }

        public void setFathername(String fathername) { this.fathername = fathername; }

        public String getAddress() { return address;}

        public void setAddress(String address) { this.address = address; }

        public String getPhonenumber() { return phonenumber;}

        public void setPhonenumber(String phonenumber) { this.phonenumber = phonenumber; }

        public String getEmailId() { return emailId;}

        public void setEmailId(String emailId) {this.emailId = emailId; }

        public int getAccountnumber() {return accountnumber;}

        public AccountType getAccounttype() { return accounttype;}

        public void setAccounttype(AccountType accounttype) {this.accounttype = accounttype;}

        public int getCurrentbalance() {return currentbalance;}

        public void setCurrentbalance(int currentbalance) {this.currentbalance = currentbalance;}

        public Accountstatus getAccountstatus() {return accountstatus;}

        public void setAccountstatus(Accountstatus accountstatus) {this.accountstatus = accountstatus;}

        public String getKycStatus() {return kycStatus; }

        public void setKycStatus(String kycStatus) {this.kycStatus = kycStatus; }

        public LocalDateTime getLastKycDate() {return lastKycDate; }

        public String getAdharCard() {return adharCard;}

        public void setAdharCard(String adharCard) { this.adharCard = adharCard; }

        public String getPanCard() {return panCard;}

        public void setPanCard(String panCard) {this.panCard = panCard;}
      
        public String toString(){
          return "Customer {" + customername + " \n+ Fathers Name+"+fathername+" \n+ aadhar Card"+adharCard+"\n+Phone Number"+phonenumber+"}"; 
        }
}
