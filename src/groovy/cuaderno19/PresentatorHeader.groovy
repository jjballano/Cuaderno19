package cuaderno19

import cuaderno19.exceptions.Cuaderno19GenerationException;

class PresentatorHeader extends Cuaderno19Structure {
	private static final REGISTER_CODE = "5180"
	private static final ENTITY = 4
	private static final OFFICE = 4
	private static final ZONE_E3_SIZE = 12
	private static final ZONE_B3_SIZE = 6
	
	def entity = ""
	def office = ""
	
	def withEntity(number){
		this.entity = number
		this
	}
	
	def withOffice(number){
		this.office = number
		this
	}

	def create(){
		new StringBuilder(162).append(REGISTER_CODE)
							  .append(nif())
							  .append(SUFIX)
							  .append(formatedDateOf(new Date()))
							  .append(emptyFieldOfSize(ZONE_B3_SIZE))
							  .append(name())
							  .append(emptyFieldOfSize(ZONE_D_SIZE))
							  .append(entity())
							  .append(office())
							  .append(emptyFieldOfSize(ZONE_E3_SIZE))
							  .append(emptyFieldOfSize(ZONE_F_SIZE))
							  .append(emptyFieldOfSize(ZONE_G_SIZE))
							  .toString()
	}

	private entity(){
		if (entity.size() != ENTITY){
			throw new Cuaderno19GenerationException("La entidad del receptor no tiene un tamaño válido ("+ENTITY+"): "+entity)
		}
		return entity
	}
	
	private office(){
		if (office.size() != OFFICE){
			throw new Cuaderno19GenerationException("La oficina del receptor no tiene un tamaño válido ("+OFFICE+"): "+office)
		}
		return office
	}

}
