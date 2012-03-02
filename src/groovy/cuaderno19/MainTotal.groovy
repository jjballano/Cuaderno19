package cuaderno19

import cuaderno19.exceptions.Cuaderno19GenerationException;

class MainTotal  extends Cuaderno19Structure {
	
	private static final REGISTER_CODE = "5980"
	private static final ZONE_B2_SIZE = 12
	private static final ZONE_D1_NUMBER_OF_ISSUERS_SIZE = 4
	private static final ZONE_D2_SIZE = 16
	private static final ZONE_E1_TOTAL_AMOUNT_SIZE = 10
	private static final ZONE_E2_SIZE = 6
	private static final ZONE_F1_NUMBER_OF_INDIVIDUAL_REGISTRIES_SIZE = 10
	private static final ZONE_F2_NUMBER_OF_REGISTRIES_OF_THE_FILE_SIZE = 10
	private static final ZONE_F3_SIZE = 20
	private static final ZONE_G_SIZE = 18
	
	private totalAmount = ""
	private numberOfIndividualRegistries = ""
	private numberOfTotalRegistries = ""
	private numberOfIssuers = ""
	
	def withTotalAmount(total){
		this.totalAmount = total
		return this
	}
	
	def withNumberOfIndividualRegistries(number){
		this.numberOfIndividualRegistries = number
		return this
	}

	def withNumberOfTotalRegistries(number){
		this.numberOfTotalRegistries = number
		return this
	}
	
	def withNumberOfIssuers(number){
		this.numberOfIssuers = number
		return this
	}
	
	def create(){
		new StringBuilder(162).append(REGISTER_CODE)
							  .append(nif())
							  .append(SUFIX)
							  .append(emptyFieldOfSize(ZONE_B2_SIZE))
							  .append(emptyFieldOfSize(ZONE_C_SIZE))
							  .append(numberOfIssuers())
							  .append(emptyFieldOfSize(ZONE_D2_SIZE))
							  .append(totalAmount())
							  .append(emptyFieldOfSize(ZONE_E2_SIZE))
							  .append(numberOfIndividualRegistries())
							  .append(numberOfTotalRegistries())
							  .append(emptyFieldOfSize(ZONE_F3_SIZE))
							  .append(emptyFieldOfSize(ZONE_G_SIZE))
							  .toString()
	}
	
	private totalAmount(){
		valueFilledWithZeros(parseToString(totalAmount), ZONE_E1_TOTAL_AMOUNT_SIZE)
	}
	
	private numberOfIndividualRegistries(){
		valueFilledWithZeros(numberOfIndividualRegistries, ZONE_F1_NUMBER_OF_INDIVIDUAL_REGISTRIES_SIZE)
	}
	
	private numberOfTotalRegistries(){
		valueFilledWithZeros(numberOfTotalRegistries, ZONE_F2_NUMBER_OF_REGISTRIES_OF_THE_FILE_SIZE)
	}

	private numberOfIssuers(){
		valueFilledWithZeros(numberOfIssuers, ZONE_D1_NUMBER_OF_ISSUERS_SIZE)
	}

}
