package cuaderno19

import cuaderno19.exceptions.Cuaderno19GenerationException;

class IssuerHeader  extends Cuaderno19Structure {
	private static final REGISTER_CODE = "5380"
	private static final PROCEDURE = "01"
	private static final ZONE_E1_SIZE = 8
	private static final ZONE_E3_SIZE = 10
	
	private chargeDate
	private accountNumber
	
	def withChargeDate(date){
		this.chargeDate = date
		return this
	}
	
	def withAccountNumber(accountNumber){
		this.accountNumber = accountNumber
		return this
	}
	
	def create(){
		new StringBuilder(162).append(REGISTER_CODE)
							  .append(nif())
							  .append(SUFIX)
							  .append(formatedDateOf(new Date()))
							  .append(formatedDateOf(chargeDate))
							  .append(name())
							  .append(accountNumber())
							  .append(emptyFieldOfSize(ZONE_E1_SIZE))
							  .append(PROCEDURE)
							  .append(emptyFieldOfSize(ZONE_E3_SIZE))
							  .append(emptyFieldOfSize(ZONE_F_SIZE))
							  .append(emptyFieldOfSize(ZONE_G_SIZE))
							  .toString()
	}
	
	private accountNumber(){
		if (accountNumber.size() != ZONE_D_SIZE){
			throw new Cuaderno19GenerationException("La cuenta tiene que tener 20 caracteres")
		}
		accountNumber
	}
}
