package cuaderno19

protected class IssuerPayment {
	private issuer
	private cuaderno19
	private payment = new Payment()
	private amount
	
	def withName(name){
		payment.withName(name)
		return this
	}
	
	def withCCC(ccc){
		payment.withAccountNumber(ccc)
		return this
	}
	
	def withAmount(amount){
		this.amount = amount
		payment.withAmount(amount)
		return this
	}
	
	def withConcept(concept){
		payment.withConcept(concept)
		return this
	}
	
	def withReferenceCode(code){
		payment.withReferenceCode(code)
		return this
	}
	
	def generate(){
		return cuaderno19.addPayment(payment.withNif(issuer))
				  		 .addAmountToTotal(amount)
	}

}

