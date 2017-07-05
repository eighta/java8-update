package core;

import java.beans.IntrospectionException;
import java.io.InvalidClassException;
import java.sql.SQLException;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

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
 */
		try{
			
			boolean hmm = true;
			
			if(hmm) throw new ArrayIndexOutOfBoundsException("array desbordamiento");
			
			//UNCHECKED EXCEPTIONS
			//Son aquellas que extienden de RuntimeException o Error
			if(hmm) throw new Error("error y tales");
			
			//if(hmm) throw new Exception("Toma lo tuyo");
			
			//RUNTIME EXCEPTIONS
			//if(hmm) throw new RuntimeException("Toma lo tuyo");
			
			//WHAT THE FUCK ARE THIS?
			if(hmm) throw new CustomThrowable("Toma lo tuyo");
			
			System.out.println("pailas no llega");
		
		//ONE CATCH IS MANDATORY if not finally
		} catch (NoSuchElementException c) {
			c.printStackTrace();
			
		//RUNTIME EXPCETIONS
		} catch (ArrayIndexOutOfBoundsException | ClassCastException m) {
			
			System.out.println("Multiple catch");
			m.printStackTrace();
			
			
		} catch (Error e) {
			System.out.println("Error catch");
			e.printStackTrace();
			
			
		} catch (Throwable t) {
			System.out.println("Throwable catch");
			t.printStackTrace();
			
		//ONLY ONE ADMITED
		}finally{
			System.out.println("finally");
		}
		
		
		try{
			
			boolean hmm = false;
			if (hmm) throw new IntrospectionException("Introspection");
			
		}catch(IntrospectionException i){
			
		}
		
		
		try{
			
		}catch(Exception e){
			
		}

/**
	Try-With-Resources Basics
	-------------------------
	PAGE 298
	
 */
		
		
		//try-with-resources statements
		//This feature is also known as automatic resource management
		AutoCloseable auto = () -> {};
		
		try(
			CustomAutoCloseable customAuto1 = new CustomAutoCloseable();
			CustomAutoCloseable customAuto2 = new CustomAutoCloseable()
			
			){
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void getDataFromDatabase() throws SQLException {
		
		//throw new UnsupportedOperationException();
		boolean hmm = false;
		if (hmm) throw new ArrayIndexOutOfBoundsException();
	}
	
	public static void main(String[] args) {
		new TheExceptions();
	}
	
}

class CustomAutoCloseable implements AutoCloseable{
	@Override
	public void close() throws Exception {
		System.out.println("CustomAutoCloseable.close()");
	}
}
class CustomThrowable extends Throwable{
	private static final long serialVersionUID = -7738508243873726788L;
	public CustomThrowable(String msg) {super(msg);}
}