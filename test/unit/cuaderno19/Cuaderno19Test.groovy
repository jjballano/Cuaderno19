package cuaderno19

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

import cuaderno19.Cuaderno19;

class Cuaderno19Test {

	static final String REFERENCE_CODE = "000000012345"

	static final String DEFAULT_NIF = "123456789"

	def cuaderno19
	
	@Before void create_instance_of_cuaderno19(){
		cuaderno19 = new Cuaderno19()
	}
	
	@Test void should_have_a_presentator_header_at_the_begging_of_the_text(){
		cuaderno19.withPresentatorNif("12345678C")
				  .withPresentatorName("Carlos López Saldaña")
				  .withReceptorEntity("1234")
				  .withReceptorOffice("4567")
		addDefaultParametersToIssuerHeader()		  
		def dateExpected = new Date().format("ddMMyy")	
		def presentatorHeader = cuaderno19.generate().substring(0,162)
		assertThat presentatorHeader, is ("518012345678C000"+dateExpected+spaces(6)+"CARLOS LOPEZ SALDANA"+spaces(40)+"12344567"+spaces(66))
				  
	}
	
	@Test void should_have_a_issuer_header_between_163rd_and_324th_position(){
		def chargeDate = tomorrow()
		addDefaultParametersToPresentatorHeader()
		
		cuaderno19.withIssuerNif("324562C")
				  .withIssuerName("María García Comesaña")
				  .withAccountNumber("12345678001234567890")
				  .withChargeDate(chargeDate)
		def issuerHeader = cuaderno19.generate().substring(162,324)

		def dateExpected = new Date().format("ddMMyy")
		def chargeDateExpected = chargeDate.format("ddMMyy")
		assertThat issuerHeader, is ("538000324562C000"+dateExpected+chargeDateExpected+"MARIA GARCIA COMESANA"+spaces(19)+"12345678001234567890"+spaces(8)+"01"+spaces(64))
				  
	}
	
	@Test void should_have_a_total_issuer_record_between_325th_and_486th_position_if_it_does_not_have_any_payment(){
		addDefaultParametersToPresentatorHeader()

		cuaderno19.withIssuerNif("324562C")
				  .withIssuerName("María García Comesaña")
				  .withAccountNumber("12345678001234567890")
				  .withChargeDate(tomorrow())
		def totalIssuer = cuaderno19.generate().substring(324,486)

		assertThat totalIssuer, is ("588000324562C000"+spaces(72)+"0000000000"+spaces(6)+"0000000000"+"0000000002"+spaces(38))
	}
	
	@Test void should_have_a_main_total_record_between_487th_and_648th_position_if_it_does_not_have_any_payment(){
		addDefaultParametersToPresentatorHeader()
		addDefaultParametersToIssuerHeader()

		def issuerHeader = cuaderno19.generate().substring(486,648)

		assertThat issuerHeader, is ("5980123456789000"+spaces(52)+"0001"+spaces(16)+"0000000000"+spaces(6)+"0000000000"+"0000000004"+spaces(38))
	}
	
	@Test void it_is_possible_to_add_one_payment_and_it_should_be_between_325th_and_486th_position(){
		addDefaultParametersToPresentatorHeader()
		cuaderno19.withIssuerNif("987654321")
				  .withIssuerName("Sender name")
				  .withAccountNumber("12345678001234567890")
				  .withChargeDate(new Date())

		cuaderno19.newPaymentOfIssuer("987654321").withName("Lucía Sánchez de la Rosa")
												  .withCCC("21324354657687980987")
												  .withAmount(125.30)
												  .withConcept("A new concept")
												  .withReferenceCode(REFERENCE_CODE)
												  .generate()
		def payment = cuaderno19.generate().substring(324,486)
		assertThat payment, is ("5680987654321000"+REFERENCE_CODE+"LUCIA SANCHEZ DE LA ROSA"+spaces(16)+"21324354657687980987"+"0000012530"+spaces(16)+"A NEW CONCEPT"+spaces(27)+spaces(8))
	}
	
	@Test void should_have_a_total_issuer_record_between_487th_and_648th_position_if_it_has_one_payment(){
		addDefaultParametersToPresentatorHeader()
		cuaderno19.withIssuerNif(DEFAULT_NIF)
				  .withIssuerName("María García Comesaña")
				  .withAccountNumber("12345678001234567890")
				  .withChargeDate(tomorrow())
		 addDefaultPaymentOf(1)
		def totalIssuer = cuaderno19.generate().substring(486,648)

		assertThat totalIssuer, is ("5880"+DEFAULT_NIF+"000"+spaces(72)+"0000000100"+spaces(6)+"0000000001"+"0000000003"+spaces(38))
	}
	
	@Test void should_have_a_total_issuer_with_a_field_with_the_sum_of_all_of_its_payments_amounts(){
		addDefaultParametersToPresentatorHeader()
		cuaderno19.withIssuerNif(DEFAULT_NIF)
				  .withIssuerName("María García Comesaña")
				  .withAccountNumber("12345678001234567890")
				  .withChargeDate(tomorrow())
		addDefaultPaymentOf(125.45)
		def totalIssuer = cuaderno19.generate().substring(486,648)

		assertThat totalIssuer, is ("5880"+DEFAULT_NIF+"000"+spaces(72)+"0000012545"+spaces(6)+"0000000001"+"0000000003"+spaces(38))
	}
	
	@Test void should_have_a_total_issuer_with_a_field_with_the_sum_of_its_number_of_payments(){
		addDefaultParametersToPresentatorHeader()
		cuaderno19.withIssuerNif(DEFAULT_NIF)
				  .withIssuerName("María García Comesaña")
				  .withAccountNumber("12345678001234567890")
				  .withChargeDate(tomorrow())
		addDefaultPaymentOf(1)
		def totalIssuer = cuaderno19.generate().substring(486,648)

		assertThat totalIssuer, is ("5880"+DEFAULT_NIF+"000"+spaces(72)+"0000000100"+spaces(6)+"0000000001"+"0000000003"+spaces(38))
	}
	
	@Test void should_have_a_total_issuer_with_a_field_with_the_sum_of_its_number_of_registries_including_payments(){
		addDefaultParametersToPresentatorHeader()
		cuaderno19.withIssuerNif(DEFAULT_NIF)
				  .withIssuerName("María García Comesaña")
				  .withAccountNumber("12345678001234567890")
				  .withChargeDate(tomorrow())
		addDefaultPaymentOf(1)
		def totalIssuer = cuaderno19.generate().substring(486,648)

		assertThat totalIssuer, is ("5880"+DEFAULT_NIF+"000"+spaces(72)+"0000000100"+spaces(6)+"0000000001"+"0000000003"+spaces(38))
	}

	@Test void should_have_a_main_total_record_between_649th_and_810th_position_if_it_does_not_have_any_payment(){
		addDefaultParametersToPresentatorHeader()
		addDefaultParametersToIssuerHeader()
		addDefaultPaymentOf(1)
		def issuerHeader = cuaderno19.generate().substring(648,810)

		assertThat issuerHeader, is ("5980"+DEFAULT_NIF+"000"+spaces(52)+"0001"+spaces(16)+"0000000100"+spaces(6)+"0000000001"+"0000000005"+spaces(38))
	}
	
	@Test void should_have_a_main_total_with_a_field_with_the_sum_of_all_payments_amounts(){
		addDefaultParametersToPresentatorHeader()
		addDefaultParametersToIssuerHeader()
		addDefaultPaymentOf(116)
		def issuerHeader = cuaderno19.generate().substring(648,810)

		assertThat issuerHeader, is ("5980"+DEFAULT_NIF+"000"+spaces(52)+"0001"+spaces(16)+"0000011600"+spaces(6)+"0000000001"+"0000000005"+spaces(38))
	}
	
	@Test void should_have_a_main_total_with_a_field_with_the_number_of_payments(){
		addDefaultParametersToPresentatorHeader()
		addDefaultParametersToIssuerHeader()
		addDefaultPaymentOf(5)
		def issuerHeader = cuaderno19.generate().substring(648,810)

		assertThat issuerHeader, is ("5980"+DEFAULT_NIF+"000"+spaces(52)+"0001"+spaces(16)+"0000000500"+spaces(6)+"0000000001"+"0000000005"+spaces(38))
	}
	
	@Test void should_have_a_main_total_with_the_number_of_registries_in_the_file(){
		addDefaultParametersToPresentatorHeader()
		addDefaultParametersToIssuerHeader()
		addDefaultPaymentOf(2)
		def issuerHeader = cuaderno19.generate().substring(648,810)

		assertThat issuerHeader, is ("5980"+DEFAULT_NIF+"000"+spaces(52)+"0001"+spaces(16)+"0000000200"+spaces(6)+"0000000001"+"0000000005"+spaces(38))
	}
	
	@Test void should_allow_create_two_payments(){
		addDefaultParametersToPresentatorHeader()
		cuaderno19.withIssuerNif("987654321")
				  .withIssuerName("Sender name")
				  .withAccountNumber("12345678001234567890")
				  .withChargeDate(new Date())
				  
		cuaderno19.newPaymentOfIssuer("987654321").withName("Lucía Sánchez de la Rosa")
												  .withCCC("21354242146782748987")
												  .withAmount(10023.25)
												  .withConcept("A new concept")
												  .withReferenceCode(REFERENCE_CODE)
												  .generate()
				  .newPaymentOfIssuer("987654321").withName("Carlos García de Marcos")
												  .withCCC("21324354657687980987")
												  .withAmount(800)
												  .withConcept("A new concept 2")
												  .withReferenceCode(REFERENCE_CODE)
												  .generate()
		def file = cuaderno19.generate()
		def firstPayment = file.substring(324,486)
		assertThat firstPayment, is ("5680987654321000"+REFERENCE_CODE+"LUCIA SANCHEZ DE LA ROSA"+spaces(16)+"21354242146782748987"+"0001002325"+spaces(16)+"A NEW CONCEPT"+spaces(27)+spaces(8))
		def secondPayment = file.substring(486,648)
		assertThat secondPayment, is ("5680987654321000"+REFERENCE_CODE+"CARLOS GARCIA DE MARCOS"+spaces(17)+"21324354657687980987"+"0000080000"+spaces(16)+"A NEW CONCEPT 2"+spaces(25)+spaces(8))
		
	}
	
	private tomorrow(){
		new Date() + 1
	}
	
	private addDefaultParametersToIssuerHeader(){
		cuaderno19.withIssuerNif("987654321")
				  .withIssuerName("Sender name")
				  .withAccountNumber("12345678001234567890")
				  .withChargeDate(new Date())
	}
	
	private addDefaultParametersToPresentatorHeader(){
		cuaderno19.withPresentatorNif(DEFAULT_NIF)
				  .withPresentatorName("Presentator name")
				  .withReceptorEntity("1234")
				  .withReceptorOffice("4567")
	}
	
	private addDefaultPaymentOf(amount){
		cuaderno19.newPaymentOfIssuer(DEFAULT_NIF).withName("Lucía Sánchez de la Rosa")
												  .withCCC("21324354657687980987")
												  .withAmount(amount)
												  .withConcept("A new concept")
												  .withReferenceCode(REFERENCE_CODE)
												  .generate()
	}
	
	private spaces(number){
		def spaces = new StringBuilder()
		def i=0
		while (i++ < number){
			spaces.append(" ")
		}
		spaces.toString()
	}

}
