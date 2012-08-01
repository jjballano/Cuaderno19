# Cuaderno19 plugin for Grails

### Author
 - Jesús J. Ballano <jjballano@gmail.com>

### Company
 - <a href="http://becodemyfriend.com" target"_blank">BeCodeMyFriend</a>

## Description 
"Cuaderno 19" is a standard electronic document format used by Spanish banks. It is normally used by bank customers to request a recursive payment (domiciliación) from a final customer or client.

This plugin can be used to generate a basic Cuaderno 19 request, but it is very easy to customize it to suit your purposes.

The plugin is compatible with Grails version 2.0 or later and it has not been tested on previous versions.

## How to use it
This is an example to create a Cuaderno19 file:

<pre><code>
def cuaderno19 = new Cuaderno19().withPresentatorNif("12345678")
                                 .withPresentatorName("Presentator name")
                                 .withReceptorEntity("1234")
								 .withReceptorOffice("4567")
								 .withIssuerNif("987654321")
								 .withIssuerName("Sender name")
								 .withAccountNumber("12345678001234567890")
								 .withChargeDate(new Date())
								 .newPaymentOfIssuer().withName("First payment")
													  .withCCC("11112222146782748987")
													  .withAmount(10023.25)
													  .withConcept("A new concept")
													  .withReferenceCode("012345")
													  .generate()
								 .newPaymentOfIssuer().withName("Second payment")
													  .withCCC("01112222657687980987")
													  .withAmount(800)
													  .withConcept("A new concept 2")
													  .withReferenceCode("012345678")
													  .generate()
def file = cuaderno19.generate()
</code></pre>

This is a text file with the structure defined in <a href="https://github.com/jjballano/Cuaderno19/blob/master/docs/Cuaderno19.pdf" target"_blank">Cuaderno19.pdf</a> (in Spanish).


## Support

Please log issues in the <a href="https://github.com/jjballano/Cuaderno19/issues">github repository</a>. Pull requests with improvements and bug fixes are welcome.
