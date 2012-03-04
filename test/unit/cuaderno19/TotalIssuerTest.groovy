package cuaderno19

import org.junit.Before;
import org.junit.Test;

import cuaderno19.TotalIssuer;
import cuaderno19.exceptions.Cuaderno19GenerationException;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

class TotalIssuerTest {
	
	def structure
	
	@Before
	void creanteInstanceWithMinimumData() {
		structure = new TotalIssuer().withNif("1").withTotalAmount(1).withNumberOfPayments(1).withNumberOfRegistries(1)
	}
	
	@Test void should_start_with_with_5880(){
		def chars = structure.create().substring(0, 4)
		assertThat chars, is("5880")
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
	
	@Test void should_has_empty_between_17th_and_88th_position(){
		def chars = structure.create()
						  .substring(16, 88)
		
		assertThat chars.size(), is(72)
		assertThat chars.trim(), is("")
	}
	
	@Test void should_has_a_total_amount_between_89th_and_98th_position_filled_with_zero_left(){
		def chars = structure.withTotalAmount(345.67)
						  .create()
						  .substring(88, 98)
		assertThat chars, is("0000034567")
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

	@Test void should_has_empty_between_99th_and_104th_position(){
		def chars = structure.create()
						  .substring(98, 104)
		
		assertThat chars.size(), is(6)
		assertThat chars.trim(), is("")
	}
	
	@Test void should_has_a_number_of_payments_by_direct_debit_between_105th_and_114th_position_filled_with_zero_left(){
		def chars = structure.withNumberOfPayments(192)
						  .create()
						  .substring(104, 114)
		assertThat chars, is("0000000192")
	}
	
	@Test void should_has_a_number_of_registries_between_115th_and_124th_position_filled_with_zero_left(){
		def chars = structure.withNumberOfRegistries(86)
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
	
	@Test void should_allow_put_all_parameters_together(){
		def chars = structure.withNif("123456789")
						  .withTotalAmount(225.67)
						  .withNumberOfPayments(19)
						  .withNumberOfRegistries(25)
						  .create()
		assertThat chars, is ("5880123456789000                                                                        0000022567      00000000190000000025                                      ");
	}

}
