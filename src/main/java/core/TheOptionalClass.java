package core;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 
 	Optional
 	--------
 
	Starting with Java 8
	
	An Optional is created using a factory
	
	You can either request an empty Optional 
		or pass a value for the Optional to wrap
 */

public class TheOptionalClass {

	{
		
		Optional<Integer> optionalInteger = Optional.of(123);
		System.out.println("optionalInteger: " + optionalInteger);
		
		//Consumer
		optionalInteger.ifPresent( i-> {System.out.println(i - 23);} );

		//NULL (NO VALUE)
		optionalInteger = Optional.ofNullable(null);
		System.out.println("optionalInteger: " + optionalInteger);
		
		//Supplier
		int orElse = optionalInteger.orElseGet( () -> new Integer(321) );
		System.out.println(orElse);
		
		try{
			optionalInteger.orElseThrow( () -> new RuntimeException("orElseThrow"));
		}catch(Exception e){System.out.println("Exception: " + e);}
		
		Optional<Float> optionalValue = Optional.ofNullable(Float.valueOf(3.14f));
		Consumer<Float> ifPresentConsumer = (f) -> {System.out.println("doing something for: " + f);};
		optionalValue.ifPresent(ifPresentConsumer);
		
		
		
		
		//this is such a common pattern, Java provides a factory method to do the same thing:
		Integer value = null;
		Optional<Integer> optionalEmpty = (value== null) ? Optional.empty(): Optional.of(value);
		Optional<Integer> o2 = Optional.ofNullable(value);
		
		System.out.println("optionalEmpty.orElse: " + optionalEmpty.orElse(-500));
		
		Supplier<Integer> optionalOrElseGetSupplier = () -> Integer.MAX_VALUE;
		System.out.println("optionalEmpty.orElseGet: " + optionalEmpty.orElseGet(optionalOrElseGetSupplier));
		
		Supplier<RuntimeException> optionalExceptionSupplier = () ->  new RuntimeException("optionalExceptionSupplier");
		//optionalEmpty.orElseThrow(optionalExceptionSupplier);
		
		
		Optional<String> normalOptional = Optional.of("Normal");
		System.out.println("normalOptional.isPresent(): " + normalOptional.isPresent());
		System.out.println("normalOptional.get(): " + normalOptional.get());
		
		Optional<?> emptyOptional = Optional.empty();
		System.out.println("emptyOptional.isPresent(): " + emptyOptional.isPresent());
		//System.out.println("emptyOptional.get(): " + emptyOptional.get());
		//java.util.NoSuchElementException: No value present
		
		System.out.println(Double.NaN);
		
		//NOT USED
		String.valueOf(o2);
		String.valueOf(optionalExceptionSupplier);
		
	}
	
	public static void main(String[] args) {
		new TheOptionalClass();
	}
	
}
