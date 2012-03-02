package cuaderno19

import org.junit.Before;
import org.junit.Test;

import cuaderno19.MainTotal;
import cuaderno19.TotalIssuer;
import cuaderno19.exceptions.Cuaderno19GenerationException;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

class MainTotalTest {
	
	def structure
	
	@Before
	void creanteInstanceWithMinimumData() {
		structure = new MainTotal().withNif("1").withNumberOfIssuers(1).withTotalAmount(1.00).withNumberOfIndividualRegistries(1).withNumberOfTotalRegistries(1)
	}
	
	@Test void should_start_with_with_5980(){
		def chars = structure.create().substring(0, 4)
		assertThat chars, is("5980")
	}
	
	@Test void should_has_a_nif_between_5th_and_13th_position(){
		def chars = structure.withNif("123456789")
						  	 .create()
						  .substring(4, 13)
		assertThat chars, is("123456789")
	}
	
	@Test (expected = Cuaderno19GenerationException.class)
	void nif_should_not_be_empty(){
		def chars = structure.withNif("")
						  	 .create()
		fail("NIF should not be empty")
	}
	
	@Test (expected = Cuaderno19GenerationException.class)
	void should_throw_an_exception_if_nif_has_more_than_9_chars(){
		def chars = structure.withNif("123456789123")
						  	 .create()
		fail("Should throw an exception")
	}
	
	@Test void should_has_a_nif_with_9_chars_filling_with_0_left_positions_until_9(){
		def chars = structure.withNif("456789")
						  	 .create()
							 .substring(4, 13)
		assertThat chars, is("000456789")
	}
	
	@Test void should_has_a_sufix_between_14th_and_16th_position(){
		def chars = structure.create()
						  .substring(13, 16)
		assertThat chars, is("000")
	}
	
	@Test void should_has_empty_between_17th_and_68th_position(){
		def chars = structure.create()
						  .substring(16, 68)
		
		assertThat chars.size(), is(52)
		assertThat chars.trim(), is("")
	}
	
	@Test void should_has_a_number_of_issuers_between_69th_and_72th_position(){
		def chars = structure.withNumberOfIssuers(5)
						  .create()
						  .substring(68, 72)
		assertThat chars, is("0005")
	}
	
	@Test void should_has_empty_between_73th_and_88th_position(){
		def chars = structure.create()
						  .substring(72, 88)
		
		assertThat chars.size(), is(16)
		assertThat chars.trim(), is("")
	}

	@Test void should_has_a_total_amount_between_89th_and_98th_position_filled_with_zero_left(){
		def chars = structure.withTotalAmount(34567)
						  .create()
						  .substring(88, 98)
		assertThat chars, is("0003456700")
	}
	
	@Test void should_receive_every_amount_with_two_decimals_and_remove_the_dot(){
		def chars = structure.withTotalAmount(345.67)
						  .create()
						  .substring(88, 98)
		assertThat chars, is("0000034567")
	}
	
	@Test void should_add_two_zeros_on_the_right_if_amount_has_not_decimal_dot(){
		def chars = structure.withTotalAmount(345)
						  .create()
						  .substring(88, 98)
		assertThat chars, is("0000034500")
	}
	
	@Test void should_allow_receive_zero_as_total_amount(){
		def chars = structure.withTotalAmount(0)
						  .create()
						  .substring(88, 98)
		assertThat chars, is("0000000000")
	}

	@Test void should_has_empty_between_99th_and_104th_position(){
		def chars = structure.create()
						  .substring(98, 104)
		
		assertThat chars.size(), is(6)
		assertThat chars.trim(), is("")
	}
	
	@Test void should_has_a_number_of_payments_by_direct_debit_between_105th_and_114th_position_filled_with_zero_left(){
		def chars = structure.withNumberOfIndividualRegistries(192)
						  .create()
						  .substring(104, 114)
		assertThat chars, is("0000000192")
	}
	
	@Test void should_has_a_number_of_registries_between_115th_and_124th_position_filled_with_zero_left(){
		def chars = structure.withNumberOfTotalRegistries(86)
						  .create()
						  .substring(114, 124)
		assertThat chars, is("0000000086")
	}
	
	@Test void should_has_empty_between_125th_and_162th_position(){
		def chars = structure.create()
						  .substring(124, 162)
		
		assertThat chars.size(), is(38)
		assertThat chars.trim(), is("")
	}
	
	@Test void should_be_162_characters_length(){
		def emptyStructure = structure.create()
		assertThat emptyStructure.size(), is(162)
	}

}
