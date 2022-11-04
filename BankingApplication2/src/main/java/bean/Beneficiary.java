package bean;

public class Beneficiary {
	private String beneficiaryName;
	private int transferLimit;
	private int accountNumber;
	private String aliasName;
	
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public int getTransferLimit() {
		return transferLimit;
	}
	public void setTransferLimit(int transferLimit) {
		this.transferLimit = transferLimit;
	}
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
}
