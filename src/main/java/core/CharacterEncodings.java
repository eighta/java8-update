package core;

public class CharacterEncodings {
//https://www.w3.org/International/questions/qa-what-is-encoding
	
	{
/**
	First, why should I care?
	-------------------------
	
	If you use anything other than the most basic English text, 
	people may not be able to read the content you create unless you 
	say what character encoding you used.
	
	For example, you may intend the text to look like this:
	https://www.w3.org/International/questions/qa-what-is-encoding-data/mojibake1.gif
	
	but it may actually display like this:
	https://www.w3.org/International/questions/qa-what-is-encoding-data/mojibake2.gif
	
	Not only does lack of character encoding information spoil the readability of displayed text, 
	but it may mean that your data cannot be found by a search engine, 
	or reliably processed by machines in a number of other ways.
	
	So what's a character encoding?
	-------------------------------
	Words and sentences in text are created from characters. 
	
	Examples of characters include the Latin letter á 
	or the Chinese ideograph 請 
	or the Devanagari character ह.
	
	Characters that are needed for a specific purpose are grouped into a character set (also called a repertoire). 
	(To refer to characters in an unambiguous way, each character is associated with a number, called a code point.)
	
	The characters are stored in the computer as one or more bytes.
	
	Basically, you can visualise this by assuming that all characters are stored in computers using a special code, 
	like the ciphers used in espionage. A character encoding provides a key to unlock (ie. crack) the code. 
	It is a set of mappings between the bytes in the computer and the characters in the character set. 
	Without the key, the data looks like garbage.
	
	So, when you input text using a keyboard or in some other way, the character encoding maps characters you 
	choose to specific bytes in computer memory, and then to display the text it reads the bytes back into 
	characters.
	
	Unfortunately, there are many different character sets and character encodings, 
	ie. many different ways of mapping between bytes, code points and characters. 
	The section Additional information provides a little more detail for those who are interested.

	ADDITIONAL INFORMATION
	----------------------
	This section provides a little additional information on mapping between bytes,
	code points and characters for those who are interested.

	Note that code point numbers are commonly expressed in hexadecimal notation 
	- ie. base 16. For example, 233 in hexadecimal form is E9. 
	Unicode code point values are typically written in the form U+00E9.

	In the coded character set called ISO 8859-1 (also known as Latin1) 
	the decimal code point value for the letter é is 233. 
	However, in ISO 8859-5, the same code point represents the Cyrillic character щ.
	
	These character sets contain fewer than 256 characters and map code points to byte values directly, 
	so a code point with the value 233 is represented by a single byte with a value of 233. 
	Note that it is only the context that determines whether that byte represents either é or щ.

	There are other ways of handling characters from a range of scripts. 
	For example, with the Unicode character set, you can represent both characters in the same set. 
	In fact, Unicode contains, in a single set, probably all the characters you are likely to ever need. 
	While the letter é is still represented by the code point value 233, 
	the Cyrillic character щ now has a code point value of 1097.

	Bytes these days are usually made up of 8 bits. There are only 28 (ie. 256) unique ways of combining 8 bits.

	On the other hand, 1097 is too large a number to be represented by a single byte*. 
	So, if you use the character encoding for Unicode text called UTF-8, щ will be represented by two bytes. 
	However, the code point value is not simply derived from the value of the two bytes spliced together 
	– some more complicated decoding is needed.

	Other Unicode characters map to one, three or four bytes in the UTF-8 encoding.

	Furthermore, note that the letter é is also represented by two bytes in UTF-8, 
	not the single byte used in ISO 8859-1. (Only ASCII characters are encoded with a single byte in UTF-8.)

	UTF-8 is the most widely used way to represent Unicode text in web pages, 
	and you should always use UTF-8 when creating your web pages and databases. 
	But, in principle, UTF-8 is only one of the possible ways of encoding Unicode characters. 
	In other words, a single code point in the Unicode character set can actually be mapped 
	to different byte sequences, depending on which encoding was used for the document. 
	
	Unicode code points could be mapped to bytes using any one of the encodings called UTF-8, UTF-16 or UTF-32. 
	The Devanagari character क, with code point 2325 (which is 915 in hexadecimal notation), 
	will be represented by two bytes when using the UTF-16 encoding (09 15), 
	three bytes with UTF-8 (E0 A4 95), or four bytes with UTF-32 (00 00 09 15).

	There can be further complications beyond those described in this section 
	(such as byte order and escape sequences), but the detail described here shows 
	why it is important that the application you are working with knows which character encoding 
	is appropriate for your data, and knows how to handle that encoding.
	
	FURTHER READING
	---------------
	https://www.w3.org/International/questions/qa-what-is-encoding#endlinks
	
	Character encodings: Essential concepts
	---------------------------------------
	https://www.w3.org/International/articles/definitions-characters/
 */
	}
	
	public static void main(String[] args) {
		new CharacterEncodings();
	}
	
}
