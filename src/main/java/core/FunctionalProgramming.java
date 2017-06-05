package core;

/**
 * Java defines a functional interface as an interface that contains a single abstract method.
 * 
 * Functional interfaces are used as the basis for lambda expressions in functional programming.
 * 
 * A lambda expression is a block of code that gets passed around, like an anonymous method.
 *
 * Recall that lambda expressions rely on the notion of deferred execution.
 * Deferred execution means that code is specified now but runs later. In this
 * case, later is when the print() method calls it. Even though the execution
 * is deferred, the compiler will still validate that the code syntax is properly
 * formed.
 * 
 * 			a  ->          a.canHop()
 * 
 * 	(Animal a) -> { return a.canHop(); }
 * 
 * The parentheses () can be omitted in a lambda expression if there is exactly one input
 * parameter and the type is not explicitly stated in the expression. This means that
 * expressions that have zero or more than one input parameter will still require parentheses.
 * 	
 * 	() -> new Duck()
 * 
 * a pair of statement braces {} around the body of
 * the lambda expression. This allows you to write multiple lines of code in the body of the
 * lambda expression, as you might do when working with an if statement or while loop.
 * 
 * What’s tricky here is that when you add braces {}, you must explicitly terminate each
 * statement in the body with a semicolon;
 * 
 * we were able to omit the braces {}, semi‐colon;, and return statement,
 * because this is a special shortcut that Java allows for single‐line lambda bodies.
 * This special shortcut doesn’t work when you have two or more statements.
 * 
 * When using {} in the body of the lambda expression, 
 * you must use the return statement if the functional interface method
 * that lambda implements returns a value.
 * 
 * Alternatively, a return statement is optional when the return type of the method is void.
 * 
 */
public class FunctionalProgramming {
	
	{
		//Java relies on context when figuring out what lambda expressions mean.
		Sprint sprint = i -> {return 1;};
	}
	
	
	/*if a interface is marked with the @FunctionalInterface
	annotation contains more than one abstract method, 
	or no abstract methods at all, then
	the compiler will detect this error and not compile.*/
	@FunctionalInterface
	public interface Sprint {
		public int sprint(Integer i);
		//ERROR WITH A SECOND METHOD 
		//public void anotherMethod();
		
		default void defaultMethod(){System.out.println("defaultMethod");};
		static void metodoEstatico(){};
	}
	
	class ClaseConInterfaceFuncional implements Sprint{

		@Override
		public void sprint(Integer i) {}
		
		@Override
		public void defaultMethod(){System.out.println("defaultMethod OVERRIDE");};
	}
	
	public static void main(String[] args) {
		Sprint sprint = new FunctionalProgramming().new ClaseConInterfaceFuncional();
		sprint.defaultMethod();
	}
	
}
