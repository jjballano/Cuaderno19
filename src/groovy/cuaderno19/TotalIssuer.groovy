package cuaderno19

import cuaderno19.exceptions.Cuaderno19GenerationException;

class TotalIssuer  extends Cuaderno19Structure {
	
	private static final REGISTER_CODE = "5880"
	private static final ZONE_B2_SIZE = 12
	private static final ZONE_E1_TOTAL_AMOUNT_SIZE = 10
	private static final ZONE_E2_SIZE = 6
	private static final ZONE_F1_NUMBER_OF_PAYMENTS_SIZE = 10
	private static final ZONE_F2_NUMBER_OF_REGISTRIES_SIZE = 10
	private static final ZONE_F3_SIZE = 20
	private static final ZONE_G_SIZE = 18
	
	private totalAmount = ""
	private numberOfPayments = ""
	private numberOfRegistries = ""
	
	def withTotalAmount(total){
		this.totalAmount = total
		return this
	}
	
	def withNumberOfPayments(number){
		this.numberOfPayments = number
		return this
	}

	def withNumberOfRegistries(number){
		this.numberOfRegistries = number
		return this
	}
	
	def create(){
		new StringBuilder(162).append(REGISTER_CODE)
							  .append(nif())
							  .append(SUFIX)
							  .append(emptyFieldOfSize(ZONE_B2_SIZE))
							  .append(emptyFieldOfSize(ZONE_C_SIZE))
							  .append(emptyFieldOfSize(ZONE_D_SIZE))
							  .append(totalAmount())
							  .append(emptyFieldOfSize(ZONE_E2_SIZE))
							  .append(numberOfPayments())
							  .append(numberOfRegistries())
							  .append(emptyFieldOfSize(ZONE_F3_SIZE))
							  .append(emptyFieldOfSize(ZONE_G_SIZE))
							  .toString()
	}
	
	private totalAmount(){
		valueFilledWithZeros(parseToString(totalAmount), ZONE_E1_TOTAL_AMOUNT_SIZE)
	}
	
	private numberOfPayments(){
		valueFilledWithZeros(numberOfPayments, ZONE_F1_NUMBER_OF_PAYMENTS_SIZE)
	}
	
	private numberOfRegistries(){
		valueFilledWithZeros(numberOfRegistries, ZONE_F2_NUMBER_OF_REGISTRIES_SIZE)
	}


}
