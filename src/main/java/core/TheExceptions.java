package core;

public class TheExceptions {

	{
/**
	Exceptions Terminology
	----------------------
	An exception is Java’s way of saying, “I give up. I don’t know what to do right now. You
	deal with it.”
	
	Categories of Exceptions
	------------------------
	Remember that a runtime exception, or unchecked
	exception, may be caught, but it is not required that it be caught.
	
	A checked exception is any class that extends Exception but is not a runtime
	exception. Checked exceptions must follow the handle or declare rule where they are either
	caught or thrown to the caller. 
	
	An error is fatal and should not be caught by the program.
	While it is legal to catch an error, it is not a good practice.
	
	java.lang.Object < Throwable < Error
	java.lang.Object < Throwable < Exception
	java.lang.Object < Throwable < Exception < RuntimeException
	
	Commons Exceptions
	------------------
	ArithmeticException
	Thrown by the JVM when code attempts to divide by zero.
	
	ArrayIndexOutOfBoundsException
	Thrown by the JVM when code uses an illegal index to access an array
	
	ClassCastException
	Thrown by the JVM when an attempt is made to cast an object to a subclass of which it is not an instance.
	
	IllegalArgumentException
	Thrown by the program to indicate that a method has been passed an illegal or inappropriate argument
	
	NullPointerException
	Thrown by the JVM when there is a null reference where an object is required
	
	NumberFormatException
	Thrown by the program when an attempt is made to convert
	a string to a numeric type, but the string doesn’t have an appropriate format.
	
	CheckedExceptions
	java.io.IOException is an example of a checked exception.
	remember that IO, parsing, and SQL exceptions are checked.
	
	
	Try Statement
	-------------
	
	
	PAGE 288
 */
		
	}
	
}
