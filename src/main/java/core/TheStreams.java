package core;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 
	A stream in Java is a sequence of data. 
	A stream pipeline is the operations that run on a stream to produce a result.
 
 	Think of a stream pipeline as an assembly line in a factory.
 
 	Finite streams have a limit
 	Other assembly lines essentially run forever, like one for food production
 	
 	Many things can happen in the assembly line stations along the way. 
 	In programming, these are called stream operations.
 	
 	Just like with the assembly line, operations occur in a pipeline. 
 	Someone has to start and end the work, and there can be any number of stations
	in between.
	
	There are three parts to a stream pipeline:
	-Source: Where the stream comes from.
	-Intermediate operations: Transforms the stream into another one.
		Since streams use lazy evaluation, the intermediate operations 
		do not run until the terminal operation runs.
	-Terminal operation: Actually produces a result.
	
	Supongamos un ejemplo:
	Un Stream pipeline con un solo "Intermediate Operation"
	
	El snapshot para este Stream pipeline inicia con un "Signal", que es "Take sign out of the box"
	
	La "Intermediate Operation" es Paint sign, asi que se necisita de un Worker para que pinte los letreros
	pero debe esperar la señal de inicio
	
	Finalmente, en el Stream pipeline la "Intermidiate Operation" cuando termina su labor, 
	apila los letreros recien pintandos
	
	Suppose that there are two signs in the box. Step 1 is the first worker taking one sign out
	of the box and handing it to the second worker. Step 2 is the second worker painting it and
	handing it to the third worker. Step 3 is the third worker putting it in the pile. Steps 4–6
	are this same process for the other sign. Then the foreman sees that there are no more signs
	left and shuts down the entire enterprise.  
	  
	Using parallel streams is like setting up multiple tables of workers who
	are able to do the same task. Painting would be a lot faster if we could have fi ve painters
	painting different signs at once. Just keep in mind that it isn’t worth working in parallel for
	small streams. There is an overhead cost in coordinating the work among all of the workers
	operating in parallel.
 	
 	Common Terminal Operations
 	--------------------------
	You can perform a terminal operation without any intermediate operations but not the
	other way around.
	
	Reductions are a special type of terminal operation where all of the contents of the stream are combined
	into a single primitive or Object.
 	
 	PAG 193 method reduce
 */


public class TheStreams {
	
	{
		
		//[[[Methods on Stream]]]
		Stream<Double> numerosInfinitos = Stream.generate(Math::random);
		
		List<Integer> enteros = Arrays.asList(500, 0, 1, 2, 3, 500, 99, 101, 200);
		Stream<Integer> enterosStream = enteros.stream();
		
		//forEach()
		//A looping construct is available. As expected, calling forEach() on an infi nite stream does
		//not terminate. Since there is no return value, it is not a reduction.
		//Notice that this is the only terminal operation with a return type of void
		
		//While forEach() sounds like a loop, it is really a terminal operator for streams. Streams
		//cannot use a traditional for loop to run because they don’t implement the Iterable interface.
		enteros.forEach(System.out::print);
		
		
		
		//Reductions
		
		//allMatch() , anyMatch() and noneMatch()
		//The allMatch() , anyMatch() and noneMatch() methods search a stream and return infor-
		//mation about how the stream pertains to the predicate. These may or may not terminate
		//for infi nite streams. It depends on the data. Like the fi nd methods, they are not reductions
		//because they do not necessarily look at all of the elements.
		List<String> miscList = Arrays.asList("monkey", "2", "chimp");
		Predicate<String> predicate = x -> Character.isLetter(x.charAt(0));
		System.out.println( miscList.stream().anyMatch(predicate) );
		System.out.println( miscList.stream().allMatch(predicate) );
		System.out.println( miscList.stream().noneMatch(e -> false) );
		
		
		
		//findAny() and findFirst()
		//These methods are terminal operations but not reductions. The reason is that they some-
		//times return without processing all of the elements. This means that they return a value
		//based on the stream but do not reduce the entire stream into one value.
		
		//The findAny() and findFirst() methods return an element of the stream unless the stream
		//is empty.
//		Optional<Integer> findFirst = enterosStream.findFirst();
//		System.out.println(findFirst.get());
		
		Optional<Integer> findAny = enterosStream.findAny();
		System.out.println(findAny.get());
		
		Optional<Double> findAnyInfinite = numerosInfinitos.findAny();
		System.out.println(findAnyInfinite.get());
		
		
		//The count() method determines the number of elements in a finite stream.
		//count() is a reduction because it looks at each element in the stream and returns a single value.
//stream has already been operated upon or closed
//		System.out.println(enterosStream.count());
		
		//min() and max()
		//The min() and max() methods allow you to pass a custom comparator and find 
		//the smallest or largest value in a finite stream according to that sort order.
//		Optional<Integer> min = enterosStream.min( (i1, i2)-> i1-i2);
//		System.out.println("The min: " + min.get() );
//		Optional<Integer> max = enterosStream.min( (i1, i2)-> i2-i1);
//		System.out.println("The max: " + max.get() );

		
		//Infinite Streams
		//generates a stream of random numbers
		Stream<Double> randoms = Stream.generate(Math::random);
//infinito		randoms.forEach(System.out::println);
		//gives you more control. iterate() takes a seed or starting value as the first parameter.
		//numeros impares empezando desde el 1, el cual es tomado como primer parametro en el metodo
		Stream<Integer> oddNumbers = Stream.iterate(1, n -> n + 2);
//infinito		oddNumbers.forEach(System.out::println);
		
		//Stream from a list
		List<String> stringList = Arrays.asList("sophie","ochoa","vasquez");
		Stream<String> streamFromList = stringList.stream();
		//or (stream that is allowed to process elements in parallel)
		Stream<String> parallelStreamFromList = stringList.parallelStream();
		
		
		//Stream with a array element
		Stream<Integer> arrayElementStream = Stream.of(1,2,3);
		
		//Stream with a single element
		Stream<Integer> singleElementStream = Stream.of(1);
		
		//EMPTY STREAM
		Stream<String> emptyStream = Stream.empty();
		
	}
	
	public static void main(String[] args) {
		new TheStreams();
	}
	
}
