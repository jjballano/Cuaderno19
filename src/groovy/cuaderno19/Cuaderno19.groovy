package cuaderno19

class Cuaderno19 {

	private presentatorHeader = new PresentatorHeader()
	private issuerHeader = new IssuerHeader()
	private totalIssuer = new TotalIssuer()
	private mainTotal = new MainTotal()
	private payment = null
	private issuerPayment = []
	private totalIssuerAmount = 0
	private numberOfIssuerRegistries = 2
	
	def newPaymentOfIssuer(nif){
		return new IssuerPayment(issuer:nif, cuaderno19: this)
	}
	
	def withPresentatorNif(nif){
		presentatorHeader.withNif(nif)
		mainTotal.withNif(nif)
		return this
	}
	
	def withPresentatorName(name){
		presentatorHeader.withName(name)
		return this
	}
	
	def withReceptorEntity(entity){
		presentatorHeader.withEntity(entity)
		return this
	}
	
	def withReceptorOffice(office){
		presentatorHeader.withOffice(office)
		return this
	}
	
	def withIssuerNif(nif){
 		issuerHeader.withNif(nif)
		totalIssuer.withNif(nif)
		return this
	}
	
	def withIssuerName(name){
		issuerHeader.withName(name)
		return this
	}
	
	def withAccountNumber(accountNumber){
		issuerHeader.withAccountNumber(accountNumber)
		return this
	}
	
	def withChargeDate(date){
		issuerHeader.withChargeDate(date)
		return this
	}
	
	def generate(){
		new StringBuilder().append(presentatorHeader.create())
						   .append(issuerHeader.create())
						   .append(payments())
						   .append(totalIssuer().create())
						   .append(mainTotal().create())
	}

	def payments(){
		def payments = new StringBuilder()
		for (payment in issuerPayment){
			numberOfIssuerRegistries++
			payments.append(payment)
		}
		return payments
	}	
	
	private totalIssuer(){
		totalIssuer.withTotalAmount(totalIssuerAmount)
				   .withNumberOfPayments(issuerPayment.size())
				   .withNumberOfRegistries(numberOfIssuerRegistries)
	}
	
	private mainTotal(){
		mainTotal.withTotalAmount(totalIssuerAmount)
				 .withNumberOfIndividualRegistries(issuerPayment.size())
				 .withNumberOfTotalRegistries(numberOfIssuerRegistries + 2)
				 .withNumberOfIssuers(1)
				 
	}
	
	def addPayment(payment){
		issuerPayment.add(payment.create())
		return this
	}
	
	def addAmountToTotal(amount){
		totalIssuerAmount += amount
		return this
	}

}