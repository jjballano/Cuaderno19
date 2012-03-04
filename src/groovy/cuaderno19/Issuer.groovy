package cuaderno19

protected class Issuer {
	
	private nif;
	private name;
	private ccc;
	private payments = new ArrayList()
	private cuaderno19
	
	def withName(name){
		this.name = name
		return this
	}
	
	def withCCC(ccc){
		this.ccc = ccc
		return this
	}
	
	def withNewPayment(){
		def payment = new Payment().withNif(nif)
		this.payments.add(payment)
		return payment
	}
	
	def generate(){
		return cuaderno19
	}
}

