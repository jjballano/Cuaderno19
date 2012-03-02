package cuaderno19

import cuaderno19.exceptions.Cuaderno19GenerationException;

abstract class Cuaderno19Structure {

	protected static final ZONE_A1_SIZE = 2
	protected static final ZONE_A2_SIZE = 2
	protected static final ZONE_B_SIZE = 24
	protected static final ZONE_B1_SIZE = 12
	protected static final ZONE_B1_NIF_SIZE = 9
	protected static final ZONE_B1_SUFIX_SIZE = 3
	protected static final ZONE_C_SIZE = 40
	protected static final ZONE_D_SIZE = 20
	protected static final ZONE_D1_ENTITY_SIZE = 4
	protected static final ZONE_D2_OFFICE_SIZE = 4
	protected static final ZONE_D3_DC_SIZE = 2
	protected static final ZONE_D4_ACCOUNT_NUMBER_SIZE = 10
	protected static final ZONE_E_SIZE = 20
	protected static final ZONE_F_SIZE = 40
	protected static final ZONE_G_SIZE = 14
	
	protected static final SUFIX = "000"
	protected static final DATE_FORMAT = "ddMMyy"
	
	abstract create()
	
	def nif = ""
	def name = ""
	
	def withNif(nif) {
		this.nif = nif
		this
	}
	
	def withName(name){
		this.name = name
		this
	}


	protected nif(){
		valueFilledWithZeros(nif, ZONE_B1_NIF_SIZE)
	}
	
	protected formatedDateOf(date){
		if (date == null){
			return emptyFieldOfSize(DATE_FORMAT.size())
		}
		date.format(DATE_FORMAT)
	}
	
	protected name() {
		stringToAValidFormat(name, ZONE_C_SIZE)
	}
	
	protected stringToAValidFormat(stringValue, size){
		def valueUpperCase = stringValue.toUpperCase()
				def validValue = removeAccentsFrom(valueUpperCase)
				if (validValue.size() > size){
					return validValue.substring(0, size)
				}
		validValue+emptyFieldOfSize(size - validValue.size())
	}
	
	protected parseToString(anAmount){
		if (anAmount.toString().contains(".")){
			return anAmount.toString().replaceFirst("\\.", "")
		}
		anAmount+"00"
	}
	
	protected emptyFieldOfSize(charEmptySize) {
		def blankField = ""
		def i=0
		while (i++ < charEmptySize){
			blankField += " "
		}
		blankField
	}
	
	
	protected valueFilledWithZeros(value, size){
		def quantityAsString = value.toString()
		if (quantityAsString.size() > size || quantityAsString.trim().size() == 0){
			throw new Cuaderno19GenerationException("La cantidad total no es válida, tiene que tener entre 1 y 10 dígitos: "+quantityAsString)
		}
		def zerosToAdd = size - quantityAsString.size()
		zerosInNumberOf(zerosToAdd) + quantityAsString
	}

	protected zerosInNumberOf(quantity){
		def i = 0
		def zeros = new StringBuilder(quantity)
		while (i++ < quantity){
			zeros.append("0")
		}
		zeros.toString()
	}
	
	protected removeAccentsFrom(stringValue){
		stringValue = stringValue.replaceAll("Á","A")
		stringValue = stringValue.replaceAll("É","E")
		stringValue = stringValue.replaceAll("Í","I")
		stringValue = stringValue.replaceAll("Ó","O")
		stringValue = stringValue.replaceAll("Ú","U")
		stringValue = stringValue.replaceAll("Ü","U")
		stringValue = stringValue.replaceAll("Ñ","N")
	}

}
