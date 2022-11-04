package bean;

public class UtilityTransaction {
	private	String fromAccountNumber;
	private	String serviceConsumerNumber;
	private int serviceId;
	private int billAmount;
	private int id;
	private String transactionDate;
	private String purpose;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getFromAccountNumber() {
		return fromAccountNumber;
	}
	public void setFromAccountNumber(String fromAccountNumber) {
		this.fromAccountNumber = fromAccountNumber;
	}
	public String getServiceConsumerNumber() {
		return serviceConsumerNumber;
	}
	public void setServiceConsumerNumber(String serviceConsumerNumber) {
		this.serviceConsumerNumber = serviceConsumerNumber;
	}
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceName) {
		this.serviceId = serviceName;
	}
	public int getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(int billAmount) {
		this.billAmount = billAmount;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
}
