package cuaderno19

protected class Presentator {
	
	private nif
	private name
	private entity
	private office
	private cuaderno19
	
	def withName(name){
		this.name = name
		return this
	}
	
	def withEntity(entity){
		this.entity = entity
		return this
	}
	
	def withOffice(office){
		this.office = office
		return this
	}
	
	def generate(){
		cuaderno19
	}

}
