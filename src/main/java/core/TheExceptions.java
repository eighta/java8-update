package core;

import java.beans.IntrospectionException;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

public class TheExceptions {

	{
/**
	Exceptions Terminology
	----------------------
	An exception is Java's way of saying, I give up. I don't know what to do right now. 
	You deal with it.
	
	Categories of Exceptions
	------------------------
	>>>Remember that a runtime exception, or unchecked exception, 
	may be caught, but it is not required that it be caught.
	
	>>>A checked exception is any class that extends Exception but is not a runtime exception. 
	Checked exceptions must follow the handle or declare rule where they are either
	caught or thrown to the caller. 
	
	>>>An error is fatal and should not be caught by the program.
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
	a string to a numeric type, but the string doesn't have an appropriate format.
	
	[[[CheckedExceptions]]]
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
		
/**		
Si se definen CheckedException, en los catch, es porque dentro del bloque try-catch
es posible que se lance, de lo contrario, si se definen CheckedException, pero evidentemente
estas no se lanzan, saldran un error de compilacion		
*/	
		try{
			
			boolean hmm = false;
			if (hmm) throw new IntrospectionException("Introspection");
		}catch(IntrospectionException i){}
/*
 Lo anterior no aplica para las RuntimeExceptions y Exception		
*/	
		try{}catch(ArrayIndexOutOfBoundsException e){ }
		
/**
Es posible definir un try sin catch (pero obligado el finally)
 */
try {}finally {}

/**
	Try-With-Resources Basics
	-------------------------
	Remember that only a try-with-resources statement is permitted to omit
	both the catch and finally blocks. 
	A traditional try statement must have either or both.
	
 */
		//try-with-resources statements
		//This feature is also known as automatic resource management
		try {
			AutoCloseable auto = () -> {};
			auto.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
/**
	Notice that one or more resources can be opened in the try clause. 
	Also, notice that parentheses are used to list those resources 
	and semicolons are used to separate the declarations. 
	This works just like declaring multiple indexes in a for loop.
 */
		
		try(
			CustomAutoCloseable customAuto0 = new CustomAutoCloseable(0,true);
			CustomAutoCloseable customAuto1 = new CustomAutoCloseable(1,false,true);
			CustomAutoCloseable customAuto2 = new CustomAutoCloseable(2,true);
			CustomAutoCloseable customAuto3 = new CustomAutoCloseable(3,true)
			){
			
			System.out.println("inside try begin");
			//if (true)throw new Custom2RuntimeException("La tipica exception");
			System.out.println("inside try end");
		} catch (Custom2RuntimeException e) {
			System.out.println("Ocurrio una Custom2RuntimeException: " + e);
			
		} catch (Custom1RuntimeException e) {
			System.out.println("Ocurrio una Custom1RuntimeException: " + e);
			
		} catch (RuntimeException e) {
			System.out.println("Ocurrio una RuntimeException: " + e);
			
		}finally {
			System.out.println("finally block still valid with 'Try-With-Resources'");
		}
		
/**
NOTA: al verificar el anterior bloque de codigo, se evicencia que:
1. los cerrados automaticos se hacen en forma inversa a como fueron declarados,
es decir, el ultimo en ser declarado, es el primero en intentar cerrarse

2. Si ocurre alguna exception al momento de cerrar alguno de recursos definidos en el
'Try-With-Resources', de todas maneras se intentan cerrar todos los recursos declarados alli. 

3. Si al intentar cerrar los recursos declarados en 'Try-With-Resources', mas de un recurso de estos
lanzan excepciones, solo se interpretara que la exception lanzada fue realizada por el primer recurso
que lanzo la exception, y aunque los otros recursos tambien se intentaron cerrar (y lanzaron excepciones)
solo aparece en la traza de la exception:  
-Suppressed: java.lang.Exception: Exception al cerrar [0]
es decir, la excepcion es swallow (es tragada la exception)
 
4. Si una expcetion es lanzada por uno de los recursos AutoClosable, aun es valido tener bloque 'catch'
para intentar atrapar dicha exception
 
5. OJO que ocurre un evento extraNo, y es, si se lanza una exception en el bloque de ejecucion normal 
(es decir entre el try y el catch) lo que ocurre a continuacion es lo siguiente:
 -a. el 'Try-With-Resources' al identificar la exception, lo que primero realiza es cerrar los 
  	Auto-Closable (que tambien pueden lanzar exceptions a su vez)
  	[The implicit finally block runs before any programmer-coded ones]
 -b. luego se ejecuta el bloque del catch, que atrapa la exception lanzada porque el bloque try-catch
 (pero las exceptiones lanzadas por el autoclosable no son trapadas)
 -c. cualquier exception lanzada por el autoclasable es swallow
 
6. Se evidencia, que el funcionamiento del 'Try-With-Resources' es encolado.
Teniendo en cuenta que, al instanciar los objetos AutoClosable dentro del bloque try(...),
durante suinstanciacion tambien pueden lanzar exceptions.
Al suceder esto, el comportamiento es el siguiente:

Durante la ejecucion de un 'Try-With-Resources' sucede:

 -a. Se intentan instanciar, los objetos Autoclosable definidos en el bloque try(...) de forma encolada.

 -b. Si al instanciar uno de estos objetos Autoclosable se lanza una exception, se detiene la ejecucion
del bloque try(...), pero se mantiene la cola de los recursos que alcanzaron a instanciarse en dicho bloque
(evidentemente, no se alcanza ejecutar el bloque "normal" que esta entre el try-catch)

 -c. Se (auto) cierran los objetos que alcanzaron a instanciarse dentro del bloque try(...) 
(de forma inversa, es decir, el ultimo que alcanzó a ser instanciado, es el primero en intentar cerrarse)

 -d. Luego se ejecuta el bloque del catch, que atrapa la exception lanzada (si existe uno)

 -e. Para finalmente ejecutar el bloque finally
  
 DEFINCION: swallow, by swallow me refiero a Suppressed Exceptions, que se habla mas adelante  
 */

/**
The resources created in the try clause are only in scope within the try block. 
(es decir, no lo ven los catch ni el finally) 
 */
		
/**
 AutoCloseable
 This interface requires a close() method to be implemented
 
 public void close() throws Exception;
 
 It's OK overriding a method that is allowed to declare more specific exceptions 
 than the parent or even none at all
 public void close(){}
 */
		
		try(
			CustomAutoCloseable r1 = new CustomAutoCloseable(0,false);
//			AutoCloseableCheckedExceptionAtClose r2 = new AutoCloseableCheckedExceptionAtClose()
					) {
			System.out.println("inside between try-catch");
//		} catch (CheckedException a) {
//			System.out.println("Ocurrio una CheckedException: " + a);
//		} catch (Exception e) {
//			System.out.println("Ocurrio una Exception: " + e);
		}
		
/**
 OJO: hay una parte curiosa con el override del Autoclosable,
 si al sobreescrbir el metodo close(), y le quita la exception en la declaracion del metodo (en el override)
 no es necesario definir ningun bloque catch en el 'Try-With-Resources',
 pero, si deja en la firma que se lanza una Exception, entonces, si se REQUIERE que exista
 un bloque catch, que atrape dicha Exception, osea java.lang.Exception OBLIGADO  
Tricky isn't it?

Java strongly recommends that close() not actually throw Exception. It is better to
throw a more specific exception. Java also recommends to make the close() method
idempotent. Idempotent means that the method can called be multiple times without any
side effects or undesirable behavior on subsequent runs.
 */
		
		
/**
AutoCloseable vs. Closeable

The AutoCloseable interface was introduced in Java 7. Before that, another interface
existed called Closeable. It was similar to what the language designers wanted, with the
following exceptions:

-Closeable restricts the type of exception thrown to IOException.
-Closeable requires implementations to be idempotent.
 
The language designers emphasize backward compatibility. Since changing the existing
interface was undesirable, they made a new one called AutoCloseable. This new
interface is less strict than Closeable. Since Closeable meets the requirements for
AutoCloseable, it started implementing AutoCloseable when the latter was introduced.
 */
		
/**
Suppressed Exceptions
---------------------
What happens if the close() method throws an exception?

Clearly we need to handle such a condition. 
We already know that the resources are closed before 
any programmer-coded catch blocks are run. 

This means that we can catch the exception thrown by close() if we wish.

Alternatively, we can allow the caller to deal with it. 
Just like a regular exception, checked exceptions must be handled or declared.

Runtime exceptions do not need to be acknowledged.

What happens if the try block also throws an exception?
Java 7 added a way to accumulate exceptions. 
When multiple exceptions are thrown, all but the first are called suppressed exceptions.
(La primera actuara de forma natural (primary exception),  las demas serán "suppressed exceptions")
 */
		
		System.out.println("Suppressed Exceptions");
		try(
			CustomAutoCloseable customAuto1 = new CustomAutoCloseable(1,true);
			CustomAutoCloseable customAuto2 = new CustomAutoCloseable(2,true);
			CustomAutoCloseable customAuto3 = new CustomAutoCloseable(3,true)
			){
			System.out.println("inside try begin");
			if (true)throw new Custom2RuntimeException("La tipica exception");
			System.out.println("inside try end");
		} catch (Exception e) {
			e.printStackTrace();
			
			System.out.println("==suppressed exceptions==");
			for (Throwable t: e.getSuppressed())
				System.out.println(t.getMessage());
		}
/**
Finally, keep in mind that suppressed exceptions 
apply only to exceptions thrown in the try clause.

The following example does not throw a suppressed exception:
 */
		try {
			
			try(
					CustomAutoCloseable customAuto1 = new CustomAutoCloseable(1,true);
					CustomAutoCloseable customAuto2 = new CustomAutoCloseable(2,true);
					
			){
				throw new Custom2RuntimeException("Otra tipica exception");
				
			}finally {
				if (true) throw new RuntimeException("Enviada desde el finally");
/*
Since the finally block throws an exception, the previous exception is lost. 
				
This has always been and continues to be bad programming practice. 
We don't want to lose exceptions.

Remember that Java needs to be backward compatible. try and finally were both
allowed to throw an exception long before Java 7. When this happened, the finally block
took precedence. This behavior needs to continue.
*/				
			}			
			
		}catch(Exception e) {
			System.out.println("MASTER BLOCK Exception");
			e.printStackTrace();
			System.out.println("Suppressed");
			for (Throwable t: e.getSuppressed())
				System.out.println(t.getMessage());
		}

/**
You've learned two new rules for the order in which code runs in 
a try-with-resources statement:
	-Resources are closed after the try clause ends and before any catch/finally clauses.
	-Resources are closed in the reverse order from which they were created.		
 */
		
/**
 Rethrowing Exceptions
 ---------------------
 It is a common pattern to log and then throw the same exception. Suppose that we have a
method that declares two checked exceptions:

	public void parseData() throws SQLException, DateTimeParseException {}
	
When calling this method, we need to handle or declare those two exception types.
There are few valid ways of doing this. We could have two catch blocks and duplicate the
logic. Or we could use multi-catch:

 */
		
		try {
			try {parseData();} catch (Exception e) {
				System.err.println(e);
				throw e;
			}
		
		}catch (SQLException | DateTimeParseException e) {
			System.out.println("SE CAPTURA EXCEPTION: " + e);
		}
		
/**
This doesn't seem bad. We only have one catch block, so we aren't duplicating code. Or are we? 
The list of exceptions in the catch block and the list of exceptions in the
method signature of multiCatch() are the same. This is duplication.
Since there were a number of changes in Java 7, the language designers decided to solve
this problem at the same time. They made it legal to write Exception in the catch block but
really only a limited set of exceptions.	

These changes are why many people prefer using unchecked exceptions.
You don't have this trickle of changes when a method changes which
exceptions it throws.
 */
		
		
/**
Working with Assertions
-----------------------
An assertion is a Boolean expression that you place at a point in your code where you
expect something to be true. The English definition of the word assert is to state that
something is true, which means that you assert that something is true. An assert statement
contains this statement along with an optional String message.

An assertion allows for detecting defects in the code. You can turn on assertions for testing
and debugging while leaving them off when your program is in production.

Why assert something when you know it is true? It is only true when everything is
working properly. If the program has a defect, it might not actually be true. Detecting this
earlier in the process lets you know something is wrong.

When troubleshooting a problem at work, developers might tell people
that they don't believe anything that they can't see. Often the process of
verifying something they have verbally asserted to be true proves the
assumption was false.


The assert Statement
 */
		boolean boolean_expression = false;
		String error_message = "Error en el assert";
		assert boolean_expression: error_message;
		
		assert boolean_expression;
		//optional parentesis
		assert (boolean_expression);
		

/**
The boolean expression must evaluate to true or false . It can be inside optional parenthesis.
The optional error message is a String used as the message for the AssertionError that is thrown.

That's right. An assertion throws an AssertionError if it is false. Since programs aren't
supposed to catch an Error , this means that assertion failures are fatal and end the program.		

The three possible outcomes of an assert statement are as follows:
-If assertions are disabled, Java skips the assertion and goes on in the code.
-If assertions are enabled and the boolean expression is true , then our assertion has
been validated and nothing happens. The program continues to execute in its normal
manner.
-If assertions are enabled and the boolean expression is false , then our assertion is
invalid and a java.lang.AssertionError is thrown.
 */
		
/**
 FOR ENABLE ASSERTIONS:$> java -ea MainClass
 
 eclipse
 -------
 [Specific]
 Menu -> Run -> Run Configurations... -> (left panel) Java Application -> MySpecificJavaProjectMainClass
 
 [Global]
 Menu -> Window -> Preferences -> (left panel) Java -> Installed JREs -> 
 	Select your JRE, and then click the Edit... button in the right panel. -> 
 	In the Default VM arguments field, add -ea
 
 */
		
		
		System.out.println("END");
	}
	
//-------------------------------------------------------------	

	public void parseData() throws SQLException, DateTimeParseException {
		throw new SQLException("Toma tu SQLException");
		
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

class AutoCloseableCheckedExceptionAtClose implements AutoCloseable{

	@Override
	public void close() throws Exception {
		//throw new CheckedException("Toma tu checkedException");
		throw new Exception("Toma tu Exception");
	}
	
}

class CheckedException extends Exception{
	private static final long serialVersionUID = 2432254758585394061L;
	public CheckedException(String msg) {super(msg);}
}

class CustomAutoCloseable implements AutoCloseable{
	
	private int id;
	private boolean exceptionOnClose;
	public CustomAutoCloseable(int id, boolean exceptionOnClose) {
		System.out.println("build CustomAutoCloseable ["+id+"]");
		this.id=id;
		this.exceptionOnClose = exceptionOnClose;
	}
	
	public CustomAutoCloseable(int id, boolean exceptionOnClose,boolean exceptionOnInit) {
		this(id,exceptionOnClose);
		if(exceptionOnInit) throw new RuntimeException("Exception on Init [" +id+ "]");
	}
	
	@Override
	public void close() {
		System.out.println("CustomAutoCloseable.close() [" +id+ "]");
		if(exceptionOnClose){
			
			if(id == 0) {
				throw new Custom1RuntimeException("Fue el id 0");
			}
			
			throw new RuntimeException("Exception al cerrar ["+id+"]");
		}
		
	}
}
class CustomThrowable extends Throwable{
	private static final long serialVersionUID = -7738508243873726788L;
	public CustomThrowable(String msg) {super(msg);}
}
class Custom2RuntimeException extends RuntimeException{
	private static final long serialVersionUID = -7724388878955079701L;
	public Custom2RuntimeException(String msg) {super(msg);}
}
class Custom1RuntimeException extends RuntimeException{
	private static final long serialVersionUID = -7724388878955079702L;
	public Custom1RuntimeException(String msg) {super(msg);}
} 