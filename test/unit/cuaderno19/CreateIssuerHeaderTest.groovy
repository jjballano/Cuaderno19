package cuaderno19

import cuaderno19.IssuerHeader;
import cuaderno19.exceptions.Cuaderno19GenerationException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

class CreateIssuerHeaderTest{

	def header
	
	@Before
	void creanteInstanceWithMinimumData() {
		header = new IssuerHeader().withNif("1234").withAccountNumber("12345678901234567890")
	}
	
	@Test void should_start_with_with_5380(){
		def chars = header.create().substring(0, 4)
		assertThat chars, is("5380")
	}
	
	@Test void should_has_a_nif_between_5th_and_13th_position(){
		def chars = header.withNif("123456789")
						  .create()
						  .substring(4, 13)
		assertThat chars, is("123456789")
	}
	
	@Test (expected = Cuaderno19GenerationException.class)
	void nif_should_not_be_empty(){
		def chars = header.withNif("")
						  .create()
		fail("NIF should not be empty")
	}
	
	@Test (expected = Cuaderno19GenerationException.class)
	void should_throw_an_exception_if_nif_has_more_than_9_chars(){
		def chars = header.withNif("123456789123")
						  .create()
		fail("Should throw an exception")
	}
	
	@Test void should_has_a_nif_with_9_chars_filling_with_0_left_positions_until_9(){
		def chars = header.withNif("456789")
						  .create()
						  .substring(4, 13)
		assertThat chars, is("000456789")
	}
	
	@Test void should_has_a_sufix_between_14th_and_16th_position(){
		def chars = header.create()
						  .substring(13, 16)
		assertThat chars, is("000")
	}
	
	@Test void should_has_a_date_when_file_was_built_between_17th_and_22nd_position(){
		def chars = header.create()
						  .substring(16, 22)
									
		def dateExpected = new Date().format("ddMMyy")
		assertThat chars, is(dateExpected)
	}
	
	@Test void should_has_a_charge_date_between_23rd_and_28th_position(){
		def chargeDate = new Date() + 3
		def chars = header.withChargeDate(chargeDate)
						  .create()
						  .substring(22, 28)

		assertThat chars, is(chargeDate.format("ddMMyy"))
	}
	
	@Test void should_has_issuer_name_between_29th_and_68th_position(){
		def chars = header.withName("Nombre y apellidos")
						  .create()
						  .substring(28, 68)
		
		assertThat chars.size(), is(40)
		assertThat chars[0], not(is(" "))
		assertThat chars.trim(), is("NOMBRE Y APELLIDOS")
	}
	
	@Test void should_cut_the_issuer_name_with_more_than_40_chars(){
		def tooLongName = "A name of a issuer with more than fourty characters"
		def chars = header.withName(tooLongName)
						  .create()
						  .substring(28, 68+1)
		def firstFourtyChars = tooLongName.toUpperCase().substring(0, 40)
		def nextChar = tooLongName.toUpperCase().substring(40,41)
		assertThat chars.substring(0, 40), is(firstFourtyChars)
		assertThat chars.substring(40), not(is(nextChar))
	}
	
	@Test void should_has_a_account_number_between_69th_and_88th_position(){
		def chars = header.withAccountNumber("12345678901234567890")
						  .create()
						  .substring(68, 88)

		assertThat chars, is("12345678901234567890")
	}
	
	@Test (expected = Cuaderno19GenerationException.class)
	void should_throw_an_expcetion_if_account_number_has_less_than_20_chars(){
		def chars = header.withAccountNumber("1234567890123456789")
						  .create()
		fail("Accounts numbers has always 20 chars")
	}
	
	@Test (expected = Cuaderno19GenerationException.class)
	void should_throw_an_expcetion_if_account_number_has_more_than_20_chars(){
		def chars = header.withAccountNumber("123456789012345678901")
						  .create()
		fail("Accounts numbers has always 20 chars")
	}
	
	@Test void should_has_empty_between_89th_and_96th_position(){
		def chars = header.create()
						  .substring(88, 96)
		
		assertThat chars.size(), is(8)
		assertThat chars.trim(), is("")
	}
	
	@Test void should_has_a_procedure_97th_and_98th_position_with_value_01(){
		def chars = header.create()
						  .substring(96, 98)
		assertThat chars, is("01")
	}
	
	@Test void should_has_empty_between_99th_and_162nd_position(){
		def chars = header.create()
						  .substring(98, 162)
		
		assertThat chars.size(), is(64)
		assertThat chars.trim(), is("")
	}
	
	@Test void should_be_162_characters_length(){
		def emptyHeader = header.create()
		assertThat emptyHeader.size(), is(162)
	}
	
	@Test void should_allow_put_all_parameters_together(){
		def chars = header.withNif("12345654")
						  .withName("Nuevo nombré")
						  .withChargeDate(new Date())
						  .withAccountNumber("12345678900987654321")
						  .create()
		def dateExpected = new Date().format("ddMMyy")
		assertThat chars, is ("5380012345654000"+dateExpected+dateExpected+"NUEVO NOMBRE                            12345678900987654321        01                                                                ");
	}

}
