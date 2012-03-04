package cuaderno19

import cuaderno19.exceptions.Cuaderno19GenerationException;

class Payment extends Cuaderno19Structure {

	private static final REGISTER_CODE = "5680"
	private static final ZONE_B2_REFERENCE_CODE_SIZE = 12
	private static final ZONE_E_SIZE = 10
	private static final ZONE_F1_RETURN_CODE_SIZE = 6
	private static final ZONE_F2_INTERNAL_REFERENCE_SIZE = 10
	private static final ZONE_G_SIZE = 40
	private static final ZONE_H_SIZE = 8
	
	private referenceCode = ""
	private accountNumber = ""
	private amount = ""
	private concept = ""
	
	def withReferenceCode(referenceCode){
		this.referenceCode = referenceCode
		return this
	}
	
	def withAccountNumber(accountNumber){
		this.accountNumber = accountNumber
		return this
	}
	
	def withAmount(amount){
		this.amount = amount
		return this
	}
	
	def withConcept(concept){
		this.concept = concept
		this
	}

	def create() {
		new StringBuilder(162).append(REGISTER_CODE)
							  .append(nif())
							  .append(SUFIX)
							  .append(referenceCode())
							  .append(name())
							  .append(accountNumber())
							  .append(amount())
							  .append(returnCode())
							  .append(internalReference())
							  .append(concept())
							  .append(emptyFieldOfSize(ZONE_H_SIZE))
							  .toString()
	}

	private referenceCode(){
		valueFilledWithZeros(referenceCode, ZONE_B2_REFERENCE_CODE_SIZE)
	}

	private returnCode(){
		valueFilledWithZeros(referenceCode, ZONE_F1_RETURN_CODE_SIZE)
	}

	private internalReference(){
		valueFilledWithZeros(new Date().format("ddMMyyyy"), ZONE_F2_INTERNAL_REFERENCE_SIZE)
	}
	
	private accountNumber(){
		if (accountNumber.size() != ZONE_D_SIZE){
			throw new Cuaderno19GenerationException("La cuenta tiene que tener 20 caracteres")
		}
		accountNumber
	}
	
	private amount(){
		valueFilledWithZeros(parseToString(amount), ZONE_E_SIZE)
	}

	private concept(){
		stringToAValidFormat(concept, ZONE_G_SIZE)
	}

}
