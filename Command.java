interface Command 
{
	void execute(String[] cmdParts) throws ExInsufficientCommand, ExBookIDAlreadyInUse, ExMemberNotFound, ExBookNotFound, ExBookNotAvailable, 
											ExLoanQuotaExceeded, ExBookIsAvailable, ExBookIsBorrowedByThisMember, ExRequestQuotaExceeded, 
											ExAlreadyRequested, ExRequestRecordNotFound, ExNotBorrowedByThisMember, ExMemberIDAlreadyInUse;
	
}
