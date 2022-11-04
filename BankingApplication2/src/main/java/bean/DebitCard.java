package bean;

public class DebitCard {
	private String cardNumber;
	private int cvv;
	private int isApproved;
	private int associatedAccountNumber;
	
	public int getAssociatedAccountNumber() {
		return associatedAccountNumber;
	}
	public void setAssociatedAccountNumber(int associatedAccountNumber) {
		this.associatedAccountNumber = associatedAccountNumber;
	}
	public int getIsApproved() {
		return isApproved;
	}
	public void setIsApproved(int isApproved) {
		this.isApproved = isApproved;
	}
	public int getCvv() {
		return cvv;
	}
	public void setCvv(int cvv) {
		this.cvv = cvv;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
}
