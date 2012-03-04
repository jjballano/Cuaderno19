package cuaderno19

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cuaderno19.MainTotal;
import cuaderno19.Payment;
import cuaderno19.TotalIssuer;
import cuaderno19.exceptions.Cuaderno19GenerationException;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

class PaymentTest {
	
	def payment
	
	@Before
	void creanteInstanceWithMinimumData() {
		payment = new Payment().withNif("1").withReferenceCode("1").withAccountNumber("12345678901234567890").withAmount(1)
	}
	
	@Test void should_start_with_with_5680(){
		def chars = payment.create().substring(0, 4)
		assertThat chars, is("5680")
	}
	
	@Test void should_has_a_nif_between_5th_and_13th_position(){
		def chars = payment.withNif("123456789")
						  	 .create()
						  .substring(4, 13)
		assertThat chars, is("123456789")
	}
	
	@Test (expected = Cuaderno19GenerationException.class)
	void nif_should_not_be_empty(){
		def chars = payment.withNif("")
						  	 .create()
		fail("NIF should not be empty")
	}
	
	@Test (expected = Cuaderno19GenerationException.class)
	void should_throw_an_exception_if_nif_has_more_than_9_chars(){
		def chars = payment.withNif("123456789123")
						  	 .create()
		fail("Should throw an exception")
	}
	
	@Test void should_has_a_nif_with_9_chars_filling_with_0_left_positions_until_9(){
		def chars = payment.withNif("456789")
						  	 .create()
							 .substring(4, 13)
		assertThat chars, is("000456789")
	}
	
	@Test void should_has_a_sufix_between_14th_and_16th_position(){
		def chars = payment.create()
						  .substring(13, 16)
		assertThat chars, is("000")
	}
	
	@Test void should_has_a_reference_code_between_17th_and_28th_position(){
		def chars = payment.withReferenceCode("123456")
						   .create()
						   .substring(16, 28)
		assertThat chars, is("000000123456")
	}
	
	@Test void should_has_a_nominal_name_between_29th_and_68th_position(){
		def chars = payment.withName("Nombre y apellidos")
						  .create()
						  .substring(28, 68)
		
		assertThat chars.size(), is(40)
		assertThat chars[0], not(is(" "))
		assertThat chars.trim(), is("NOMBRE Y APELLIDOS")
	}
	
	@Test void should_has_to_change_to_uppercase_the_name(){
		def chars = payment.withName("Nombre y apellidos")
						  .create()
						  .substring(28, 68)
		
		assertThat chars.trim(), is("NOMBRE Y APELLIDOS")
	}

	@Test void should_has_to_remove_every_accent_in_the_name(){
		def chars = payment.withName("áéíóúñÁÉÍÓÚÑüÜ")
						  .create()
						  .substring(28, 68)
		
		assertThat chars.trim(), is("AEIOUNAEIOUNUU")
	}

	@Test void should_cut_the_name_with_more_than_40_chars(){
		def tooLongName = "A name of a nominal with more than fourty characters"
		def chars = payment.withName(tooLongName)
						  .create()
						  .substring(28, 68+1)
		def firstFourtyChars = tooLongName.toUpperCase().substring(0, 40)
		def nextChar = tooLongName.toUpperCase().substring(40,41)
		assertThat chars.substring(0, 40), is(firstFourtyChars)
		assertThat chars.substring(40), not(is(nextChar))
	}
	
	@Test void should_has_an_account_number_between_69th_and_88th_position(){
		def chars = payment.withAccountNumber("12345678901234567890")
						  .create()
						  .substring(68, 88)

		assertThat chars, is("12345678901234567890")
	}
	
	@Test (expected = Cuaderno19GenerationException.class)
	void should_throw_an_expcetion_if_account_number_has_less_than_20_chars(){
		def chars = payment.withAccountNumber("1234567890123456789")
						  .create()
		fail("Accounts numbers has always 20 chars")
	}
	
	@Test (expected = Cuaderno19GenerationException.class)
	void should_throw_an_expcetion_if_account_number_has_more_than_20_chars(){
		def chars = payment.withAccountNumber("123456789012345678901")
						  .create()
		fail("Accounts numbers has always 20 chars")
	}
	
	@Test void should_has_an_amount_between_89th_and_98th_position_filled_with_zero_left(){
		def chars = payment.withAmount(345.67)
						  .create()
						  .substring(88, 98)
		assertThat chars, is("0000034567")
	}
	
	@Test void should_receive_every_amount_with_two_decimals_and_remove_the_dot(){
		def chars = payment.withAmount(345.67)
						  .create()
						  .substring(88, 98)
		assertThat chars, is("0000034567")
	}
	
	@Test void should_add_two_zeros_on_the_right_if_amount_has_not_decimal_dot(){
		def chars = payment.withAmount(345)
						  .create()
						  .substring(88, 98)
		assertThat chars, is("0000034500")
	}
	
	@Test void should_has_the_same_as_reference_code_between_99th_and_104th_position(){
		def chars = payment.withReferenceCode("123456")
						   .create()
						   .substring(98, 104)
		assertThat chars, is("123456")
	}
	
	@Test void should_has_the_current_date_as_internal_reference_between_105th_and_114th_position(){
		def chars = payment.create()
						   .substring(104, 114)
		def today = new Date().format("ddMMyyyy")				   
		assertThat chars, is("00"+today)
	}

	@Test void should_has_a_concept_between_115th_and_154th_position(){
		def chars = payment.withConcept("un concepto cualquiera")
						  .create()
						  .substring(114, 154)
		
		assertThat chars.size(), is(40)
		assertThat chars[0], not(is(" "))
		assertThat chars.trim(), is("UN CONCEPTO CUALQUIERA")
	}
	
	@Test void should_has_to_change_to_uppercase_the_concept(){
		def chars = payment.withConcept("un concepto cualquiera")
						  .create()
						  .substring(114, 154)
		
		assertThat chars.trim(), is("UN CONCEPTO CUALQUIERA")
	}

	@Test void should_has_to_remove_every_accent_in_the_concept(){
		def chars = payment.withConcept("áéíóúñÁÉÍÓÚÑüÜ")
						  .create()
						  .substring(114, 154)
		
		assertThat chars.trim(), is("AEIOUNAEIOUNUU")
	}

	@Test void should_cut_the_concept_with_more_than_40_chars(){
		def tooLongName = "A name of a concept with more than fourty characters"
		def chars = payment.withConcept(tooLongName)
						  .create()
						  .substring(114, 154+1)
		def firstFourtyChars = tooLongName.toUpperCase().substring(0, 40)
		def nextChar = tooLongName.toUpperCase().substring(40,41)
		assertThat chars.substring(0, 40), is(firstFourtyChars)
		assertThat chars.substring(40), not(is(nextChar))
	}
	
	@Test void should_has_empty_between_155th_and_162th_position(){
		def chars = payment.create()
						  .substring(154, 162)
		
		assertThat chars.size(), is(8)
		assertThat chars.trim(), is("")
	}

	@Test void should_be_162_characters_length(){
		def emptyStructure = payment.create()
		assertThat emptyStructure.size(), is(162)
	}
	
	@Test void should_allow_put_all_parameters_together(){
		def chars = payment.withNif("123456789")
						  .withReferenceCode("123456")
						  .withConcept("A Concept")
						  .withAmount(345.65)
						  .withName("Name and family name")
						  .withAccountNumber("12345678900987654321")
						  .create()
		def today = new Date().format("ddMMyyyy")
		assertThat chars, is ("5680123456789000000000123456NAME AND FAMILY NAME                    12345678900987654321000003456512345600"+today+"A CONCEPT                                       ");
	}
}
