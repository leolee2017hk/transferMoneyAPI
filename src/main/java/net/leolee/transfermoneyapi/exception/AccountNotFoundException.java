package net.leolee.transfermoneyapi.exception;

public class AccountNotFoundException extends TransferMoneyBaseException {
	
	private String missingAccountNo;
	
	public AccountNotFoundException(String missingAccountNo){
		super("Account <" + missingAccountNo + "> does not exist !");
		this.missingAccountNo = missingAccountNo;
	}

	@Override
	public ErrorCode getErrorCode() {
		return ErrorCode.E001;
	}

}
