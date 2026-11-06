import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;

@Entity
@Table (name="transactiontable")
public class Transactions {
@Id
@Column(name="transaction_id")
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int transactionId;
@Column(name="source_account")
private int sourceAccount;
@Column(name="destination_account")
private int destinationAccount;
private BigDecimal amount;
@Column(name="transaction_type")
private String transactionType;;
private String status;
@Column(name="transaction_date")
private LocalDateTime transactionDate;
private String remarks;

public Transactions() {}

public Transactions(int sourceAccount, int destinationAccount,
				   BigDecimal amount, String transactionType, String status,
				   LocalDateTime transactionDate, String remarks) {
	this.sourceAccount = sourceAccount;
	this.destinationAccount = destinationAccount;
	this.amount = amount;
	this.transactionType = transactionType;
	this.status = status;
	this.transactionDate = transactionDate;
	this.remarks = remarks;
}

public int getTransactionId() {
	return transactionId;
}

public int getSourceAccount() {
	return sourceAccount;
}

public void setSourceAccount(int sourceAccount) {
	this.sourceAccount = sourceAccount;
}

public int getDestinationAccount() {
	return destinationAccount;
}

public void setDestinationAccount(int destinationAccount) {
	this.destinationAccount = destinationAccount;
}

public BigDecimal getAmount() {
	return amount;
}

public void setAmount(BigDecimal amount) {
	this.amount = amount;
}

public String getTransactionType() {
	return transactionType;
}

public void setTransactionType(String transactionType) {
	this.transactionType = transactionType;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public LocalDateTime getTransactionDate() {
	return transactionDate;
}

public void setTransactionDate(LocalDateTime transactionDate) {
	this.transactionDate = transactionDate;
}

public String getRemarks() {
	return remarks;
}

public void setRemarks(String remarks) {
	this.remarks = remarks;
}
}
