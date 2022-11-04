package bean;

public class GiftCard {
	private String cardNumber;
	private int cvv;
	private int cardLimit;
	
	public int getCardLimit() {
		return cardLimit;
	}
	public void setCardLimit(int cardLimit) {
		this.cardLimit = cardLimit;
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
