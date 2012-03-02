package cuaderno19

import cuaderno19.PresentatorHeader;
import cuaderno19.exceptions.Cuaderno19GenerationException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

class CreatePresentatorHeaderTest {

	def header
	
	@Before
	void creanteInstanceWithMinimumData() {
		header = new PresentatorHeader().withEntity("1357")
										.withOffice("1357")
										.withNif("1234")
	}
	
	@Test void should_start_with_with_5180(){
		def chars = header.create().substring(0, 4)
		assertThat chars, is("5180")
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
	
	@Test void should_has_a_date_when_file_was_built_between_17th_and_22th_position(){
		def chars = header.create()
						  .substring(16, 22)
									
		def dateExpected = new Date().format("ddMMyy")							
		assertThat chars, is(dateExpected)
	}
	
	@Test void should_has_empty_between_23th_and_28th_position(){
		def chars = header.create()
						  .substring(22, 28)
		
		assertThat chars.size(), is(6)				  							
		assertThat chars.trim(), is("")
	}
	
	@Test void should_has_presentator_name_between_29th_and_68th_position(){
		def chars = header.withName("Nombre y apellidos")
						  .create()
						  .substring(28, 68)
		
		assertThat chars.size(), is(40)
		assertThat chars[0], not(is(" "))							
		assertThat chars.trim(), is("NOMBRE Y APELLIDOS")
	}
	
	@Test void should_has_to_change_to_uppercase_the_name(){
		def chars = header.withName("Nombre y apellidos")
						  .create()
						  .substring(28, 68)
		
		assertThat chars.trim(), is("NOMBRE Y APELLIDOS")
	}

	@Test void should_has_to_remove_every_accent_in_the_name(){
		def chars = header.withName("áéíóúñÁÉÍÓÚÑüÜ")
						  .create()
						  .substring(28, 68)
		
		assertThat chars.trim(), is("AEIOUNAEIOUNUU")
	}

	@Test void should_cut_the_presentator_name_with_more_than_40_chars(){
		def tooLongName = "A name of a presentator with more than fourty characters"
		def chars = header.withName(tooLongName)
						  .create()
						  .substring(28, 68+1)
		def firstFourtyChars = tooLongName.toUpperCase().substring(0, 40)
		def nextChar = tooLongName.toUpperCase().substring(40,41)
		assertThat chars.substring(0, 40), is(firstFourtyChars)
		assertThat chars.substring(40), not(is(nextChar))
	}
	
	@Test void should_has_empty_between_69th_and_88th_position(){
		def chars = header.create()
						  .substring(68, 88)
		
		assertThat chars.size(), is(20)
		assertThat chars.trim(), is("")
	}

	@Test void should_has_a_entity_receptor_number_between_89th_and_92th_position(){
		def chars = header.withEntity("1357")
						  .create()
						  .substring(88, 92)
		
		assertThat chars, is("1357")
	}
	
	@Test (expected = Cuaderno19GenerationException.class)
	void should_throw_an_exception_if_entity_receptor_has_more_than_4_chars(){
		def chars = header.withEntity("13579")
						  .create()
		fail("Should throw an exception")
	}
	
	@Test (expected = Cuaderno19GenerationException.class)
	void should_throw_an_exception_if_entity_receptor_has_less_than_4_chars(){
		def chars = header.withEntity("135")
						  .create()
		fail("Should throw an exception")
	}
	
	@Test void should_has_a_office_receptor_number_between_93th_and_96th_position(){
		def chars = header.withOffice("1111")
						  .create()
						  .substring(92, 96)
		
		assertThat chars, is("1111")
	}
	
	@Test (expected = Cuaderno19GenerationException.class)
	void should_throw_an_exception_if_office_receptor_has_more_than_4_chars(){
		def chars = header.withOffice("13579")
						  .create()
		fail("Should throw an exception")
	}
	
	@Test (expected = Cuaderno19GenerationException.class)
	void should_throw_an_exception_if_office_receptor_has_less_than_4_chars(){
		def chars = header.withOffice("135")
						  .create()
		fail("Should throw an exception")
	}

	
	@Test void should_has_empty_between_97th_and_162th_position(){
		def chars = header.create()
						  .substring(96, 162)
		
		assertThat chars.size(), is(66)
		assertThat chars.trim(), is("")
	}

	
	@Test void should_be_162_characters_length(){
		def emptyHeader = header.create()
		assertThat emptyHeader.size(), is(162)
	}
	
	@Test void should_allow_put_all_parameters_together(){
		def chars = header.withNif("12345654")
						  .withName("Nuevo nombré")
						  .withEntity("1234")
						  .withOffice("4567")
						  .create()
		def dateExpected = new Date().format("ddMMyy")
		assertThat chars, is ("5180012345654000"+dateExpected+"      NUEVO NOMBRE                                                12344567                                                                  ")
	}
}
