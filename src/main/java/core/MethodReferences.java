package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import pojo.Duck;

/**
 
 Method References
 =================
 
 Method references are a way to make the code shorter by reducing some of the code that
 can be inferred and simply mentioning the name of the method.
 
 There are four formats for method references:
 
	-Static methods
	-Instance methods on a particular instance
	-Instance methods on an instance to be determined at runtime
	-Constructors
 */
public class MethodReferences {

	{
		//Looping through a Collection
		List<String> cats = Arrays.asList("Annie", "Ripley");
		cats.forEach(c -> System.out.println(c));
		cats.forEach(System.out::println);
		
		//Updating All Elements
		//void replaceAll(UnaryOperator<E> o)
		List<Integer> numeros = Arrays.asList(1, 2, 3);
		numeros.replaceAll(x -> x*2);
		System.out.println(numeros);
		
		//Removing Conditionally
		List<String> list = new ArrayList<>();
		list.add("Magician");
		list.add("Assistant");
		System.out.println(list);
		list.removeIf(s -> s.startsWith("A"));
		System.out.println(list);

		//MethodRef: Constructor reference
		Supplier<List<Number>> methodRefConstructor = ArrayList::new;
		Supplier<List<Number>> lambdaMethodRefConstructor = () -> new ArrayList<>();
		
		//MethodRef: Instance methods (without knowing the instance in advance)
		Predicate<String> methodRefInstanceUnknownInstance = String::isEmpty;
		Predicate<String> lambdaMethodRefInstanceUnknownInstance = s -> s.isEmpty();
		
		//MethodRef: Instance methods
		String strInstance = "abc";
		Predicate<String> methodRefInstance = strInstance::startsWith;
		Predicate<String> lambdaMethodRefInstance = s -> strInstance.startsWith(s);
		
		//MethodRef: Static methods
		Consumer<List<Integer>> methodRefStatic = Collections::sort;
		Consumer<List<Integer>> lambdaMethodRefStatic = l -> Collections.sort(l);
		
		//Using Lambda
		Comparator<Duck> byWeight = (d1, d2) -> DuckHelper.compareByWeight(d1, d2);
		
		//Better using Method References
//		There’s a bit of redundancy, though. The lambda takes two parameters and
//		does nothing but pass those parameters to another method. Java 8 lets us remove that
//		redundancy and simply write this:
		Comparator<Duck> byWeightMethodRef = DuckHelper::compareByWeight;
		
		//NOT USED
		String.valueOf(methodRefConstructor);
		String.valueOf(lambdaMethodRefConstructor);
		String.valueOf(methodRefInstanceUnknownInstance);
		String.valueOf(lambdaMethodRefInstanceUnknownInstance);
		String.valueOf(methodRefInstance);
		String.valueOf(lambdaMethodRefInstance);
		String.valueOf(methodRefStatic);
		String.valueOf(lambdaMethodRefStatic);
		String.valueOf(byWeight);
		String.valueOf(byWeightMethodRef);
	}
	
	public static void main(String[] args) {
		new MethodReferences();
	}
}

class DuckHelper {
	public static int compareByWeight(Duck d1, Duck d2) {
		return d1.getWeight()-d2.getWeight();
	}
	
	public static int compareByName(Duck d1, Duck d2) {
		return d1.getName().compareTo(d2.getName());
	}
}