# Cuaderno19 plugin for Grails

## Author
 - Jesús J. Ballano <jjballano@gmail.com>

## Company
 - <a href="http://becodemyfriend.com">BeCodeMyFriend</a>

Cuaderno19 is a file needed to order a payment to a bank in Spain. It can be done very easy with this plugin.

It just makes a simple Cuaderno19, but it can be completed so easy.

Grails version is 2.0 or later. It is not tested on previous versions

This is an example to create a Cuaderno19:

<pre><code>def cuaderno19 = new Cuaderno19().withPresentatorNif("12345678")
                                 .withPresentatorName("Presentator name")
                                 .withReceptorEntity("1234")
								.withReceptorOffice("4567")
								.withIssuerNif("987654321")
								.withIssuerName("Sender name")
								.withAccountNumber("12345678001234567890")
								.withChargeDate(new Date())
								.newPaymentOfIssuer().withName("Sixth payment")
													 .withCCC("11112222146782748987")
													 .withAmount(10023.25)
													 .withConcept("A new concept")
													 .withReferenceCode("012345")
													 .generate()
								.newPaymentOfIssuer().withName("Third payment")
													 .withCCC("01112222657687980987")
													 .withAmount(800)
													 .withConcept("A new concept 2")
													 .withReferenceCode("012345678")
													 .generate()
def file = cuaderno19.generate()
</code></pre>

This is a text file with the structure defined in <a href="https://github.com/jjballano/Cuaderno19/blob/master/docs/Cuaderno19.pdf">Cuaderno19.pdf</a> (in Spanish).


## Support

Please log issues in the <a href="https://github.com/jjballano/Cuaderno19/issues">github repository</a>. Pull requests with improvements and bug fixes are welcome.
