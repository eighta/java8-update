package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TheStreams {

	{
/**
 	Streams
 
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
	
				-----------------------------
	Source 	->	|	Intermediate operations	| -> Output from terminal operation
				-----------------------------
 
	Supongamos un ejemplo:
	Un Stream pipeline con un solo "Intermediate Operation"
	
	El snapshot para este Stream pipeline inicia con un "Signal", que es "Take sign out of the box"
	
	La "Intermediate Operation" es Paint sign, asi que se necesita de un Worker para que pinte los letreros
	pero debe esperar la señal de inicio
	
	Finalmente, en el Stream pipeline la "Intermidiate Operation" cuando termina su labor, 
	apila los letreros recien pintandos
	
	Suppose that there are two signs in the box. 
	
	-Step 1 is the first worker taking one sign out of the box 
		and handing it to the second worker. 
	
	-Step 2 is the second worker painting it 
		and handing it to the third worker. 
	
	-Step 3 is the third worker putting it in the pile. 
	
	-Steps 4's are this same process for the other sign. 
	
	Then the foreman sees that there are no more signs left 
		and shuts down the entire enterprise.  
	  
	Using parallel streams is like setting up multiple tables of workers who
	are able to do the same task. Painting would be a lot faster if we could have five painters
	painting different signs at once. Just keep in mind that it isnt worth working in parallel for
	small streams. There is an overhead cost in coordinating the work among all of the workers
	operating in parallel.
	
	Creating Stream Sources
	-----------------------
	In Java, the Stream interface is in the java.util.stream package. There are a few ways to
	create a finite stream:
 */
		
		//EMPTY STREAM
		Stream<String> emptyStream = Stream.empty();
		System.out.println("emptyStream: " + emptyStream.count());
		
		//Stream with a single element
		Stream<Integer> singleElementStream = Stream.of(199);
		System.out.println("singleElementStream: " + singleElementStream.count());
		
		//Stream with a array element
		Stream<Integer> arrayElementStream = Stream.of(1,2,3);
		System.out.println("arrayElementStream: " + arrayElementStream.count());
		
		//Stream from a list
		List<String> stringList = Arrays.asList("sophie","ochoa","vasquez");
		Stream<String> streamFromList = stringList.stream();
		System.out.println("streamFromList: " + streamFromList.count());
		//or (stream that is allowed to process elements in parallel)
		Stream<String> parallelStreamFromList = stringList.parallelStream();
		System.out.print("parallelStreamFromList: " + parallelStreamFromList.count());
		
/**
 	We can’t create an infinite list, though, which makes streams more powerful:
 */
		
		//Infinite Streams
		//-----------------
		//generates a stream of random numbers
		Stream<Double> randoms = Stream.generate(Math::random);
		System.out.print("\nrandoms: ");
		randoms.limit(5).forEach(System.out::print);
		
		//gives you more control. iterate() takes a seed or starting value as the first parameter.
		//numeros impares empezando desde el 1, el cual es tomado como primer parametro en el metodo
		Stream<Integer> oddNumbers = Stream.iterate(1, n -> n + 2);
		System.out.print("\noddNumbers: ");
		oddNumbers.limit(5).forEach(System.out::print);
		
/**
 	Common Terminal Operations
 	--------------------------
	You can perform a terminal operation without any intermediate operations but not the
	other way around.
	
	Reductions are a special type of "terminal operation" where all of the contents of the stream 
		are combined into a single primitive or Object.
		
	-count()
	The count() method determines the number of elements in a finite stream.
	count() is a reduction because it looks at each element in the stream and returns a single value.
 */
		Stream<Integer> finiteStream = Stream.of(1,2,3,4,5,6);
		System.out.println("\nfiniteStream: " + finiteStream.count());

/**
 	-min() and max()
 	The min() and max() methods allow you to pass a custom comparator and find 
		the smallest or largest value in a finite stream according to that sort order.
		
	Optional<T> min(<? super T> comparator)
	Optional<T> max(<? super T> comparator)
 */
		Stream<Integer> numerosFinite1Stream = Stream.of(1,2,3,4,5,6);
		Optional<Integer> min = numerosFinite1Stream.min( (i1, i2)-> i1-i2);
		System.out.println("The min: " + min.get() );
		
		Stream<Integer> numerosFinite2Stream = Stream.of(1,2,3,4,5,6);
		Optional<Integer> max = numerosFinite2Stream.max( (i1, i2)-> i1-i2);
		System.out.println("The max: " + max.get() );

/**
 	-findAny() and findFirst()
 	The findAny() and findFirst() methods return an element of the stream unless the stream
		is empty. If the stream is empty, they return an empty Optional. This is the first method
		you’ve seen that works with an infinite stream.
		
	findAny() is useful when you are working with a parallel stream. 
		It gives Java the flexibility to return to you the first element it comes 
		by rather than the one that needs to be first in the stream based
		on the intermediate operations.
		
	These methods are terminal operations but not reductions. The reason is that they sometimes
	return without processing all of the elements. This means that they return a value
	based on the stream but do not reduce the entire stream into one value.
 	
 		Optional<T> findAny()
		Optional<T> findFirst()
 		
 */
		Stream<Integer> numerosInfinitos = Stream.iterate(1, n -> ++n);
		Optional<Integer> findAny = numerosInfinitos.findAny();
		System.out.println("\nfindAny: " + findAny.get());
		
		numerosInfinitos = Stream.iterate(1, n -> ++n);
		Optional<Integer> findAnyInfinite = numerosInfinitos.findAny();
		System.out.println("findAnyInfinite: " + findAnyInfinite.get());
		
/**
 	-allMatch() , anyMatch() and noneMatch()
 	The allMatch() , anyMatch() and noneMatch() methods search a stream and return information
	about how the stream pertains to the predicate. These may or may not terminate
	for infinite streams. It depends on the data. Like the fi nd methods, they are not reductions
	because they do not necessarily look at all of the elements.
 		
 		boolean anyMatch(Predicate <? super T> predicate)
		boolean allMatch(Predicate <? super T> predicate)
		boolean noneMatch(Predicate <? super T> predicate)
 */
		List<String> miscList = Arrays.asList("monkey", "2", "chimp");
		Predicate<String> predicate = x -> Character.isLetter(x.charAt(0));
		System.out.println( miscList.stream().anyMatch(predicate) );
		System.out.println( miscList.stream().allMatch(predicate) );
		System.out.println( miscList.stream().noneMatch(e -> false) );
		
/**
 	-forEach()
 	A looping construct is available. As expected, calling forEach() on an infi nite stream does
	not terminate. Since there is no return value, it is not a reduction.
 	
 		void forEach(Consumer<? super T> action)
 		
 	Notice that this is the only terminal operation with a return type of void .
 	
 	While forEach() sounds like a loop, it is really a terminal operator for streams. Streams
		cannot use a traditional for loop to run because they don’t implement the Iterable interface.
 */
		List<Integer> enteros = Arrays.asList(1,2,3);
		System.out.print("forEach: ");
		enteros.stream().forEach(System.out::print);
	
/**
 	-reduce()
 	The reduce() method combines a stream into a single object. As you can tell from the
		name, it is a reduction. 
		
	The method signatures are these:
		T 			reduce(T identity, BinaryOperator<T> accumulator)
		Optional<T> reduce(BinaryOperator<T> accumulator)
		<U> U 		reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)
 		
 */
		//>>>using identity
		Stream<String> stream = Stream.of("w", "o", "l", "f");
		String identity = "";
		String word = stream.reduce(identity, (s, c) -> s + c);
		System.out.println(word);
		//or using method reference
		String word2 = Stream.of("w", "o", "l", "f").reduce("", String::concat);
		System.out.println(word2);
		//>>>sin el identity (solo el acumulator)
		Optional<Integer> reduce = Stream.of(1,2,3).reduce((a, b) -> a*b);
		System.out.println(reduce);
		
		//>>>processing collections in parallel
		//when we are processing collections in parallel
		BiFunction<Integer,Integer,Integer> op = (acum, currentData) -> {System.out.println("(" +acum+","+currentData +")"); return acum * currentData;};
		BinaryOperator<Integer> op2 = (a, b) -> a * b;
		
		System.out.println("->" + Stream.of(3, 5, 6).reduce(1, op, op2));
		System.out.println("->" + Stream.<Integer>empty().reduce(1, op, op2));
		
/**
 	-collect()
 	The collect() method is a special type of reduction called a mutable reduction. It is
	more efficient than a regular reduction because we use the same mutable object while
 	accumulating. Common mutable objects include StringBuilder and ArrayList. This is a
	really useful method, because it lets us get data out of streams and into another form. The
	method signatures are as follows:
	
		<R> R collect(	Supplier<R> supplier, 
						BiConsumer<R, ? super T> accumulator,
						BiConsumer<R, R> combiner)
						
		<R,A> R collect(Collector<? super T, 	A,	R> collector)
 */
		Stream<String> streamCollector = Stream.of("w", "o", "l", "f");
		StringBuilder wordCollector = streamCollector.collect(
									StringBuilder::new,
									StringBuilder::append, 
									StringBuilder::append);
		System.out.println("wordCollector: " + wordCollector);
		
		//Commons Collectors
		class MyTreeSet extends TreeSet<String>{
			private static final long serialVersionUID = 7470509819437048738L;
			//default
			MyTreeSet(){System.out.println("MyTreeSet is Instancing...");}
			
			@Override
			public boolean add(String e){
				System.out.println("Adding: " + e);
				return super.add(e);
			}
			
			@Override
			public  boolean addAll(Collection<? extends String> c){
				System.out.println("addAll: " + c);
				return super.addAll(c);
			}
		}
		
		//Using supplier, acumulator, combinator
		Stream<String> flujoDeLetras = Stream.of("w","o","l","f");
		MyTreeSet myTreeSet = flujoDeLetras.collect(MyTreeSet::new, MyTreeSet::add, MyTreeSet::addAll);
		System.out.println(myTreeSet);
		//or using Collectors pre-definidos
		Stream<String> streamC = Stream.of("w", "o", "l", "f");
		TreeSet<String> setC = streamC.collect(Collectors.toCollection(MyTreeSet::new));
		System.out.println(setC);
		//or a Set
		Set<String> setCollect = Stream.of("w", "o", "l", "f").collect(Collectors.toSet());
		System.out.println(setCollect); // [f, w, l, o]
		
/**
	Using Common Intermediate Operations
	------------------------------------
	Unlike a terminal operation, intermediate operations deal with infi nite streams simply by
		returning an infi nite stream.
		 
	-filter()
	The filter() method returns a Stream with elements that match a given expression.
	
	Stream<T> filter(Predicate<? super T> predicate)
	
*/
		Stream<String> nombresStream = Stream.of("milton","javier","ochoa","larios","mother");
		Predicate<String> filtroIniciaConM = x -> x.startsWith("m");
		Stream<String> nombresFiltradosStream = nombresStream.filter(filtroIniciaConM);
		System.out.print("\nfilter: ");
		nombresFiltradosStream.forEach(System.out::print);
		
/**
	-distinct()
 	The distinct() method returns a stream with duplicate values removed.
 	
 	Stream<T> distinct()
 */
		Stream<String> repetidosStream = Stream.of("goose", "duck", "duck", "duck", "goose", "cat" );
		System.out.print("\ndistinct: ");
		repetidosStream.distinct().forEach(System.out::print);
		
/**
	-limit() and skip()
	The limit() and skip() methods make a Stream smaller. They could make a fi nite stream
	smaller, or they could make a fi nite stream out of an infi nite stream. 
	The method signatures are shown here:
	
	Stream<T> limit(int maxSize)
	Stream<T> skip(int n)
 */
		
		Stream<Integer> enterosInfinitosStream = Stream.iterate(1, n -> n + 1);
		System.out.print("\nlimit and skip: ");
		enterosInfinitosStream.skip(5).limit(2).forEach(System.out::print);

/**
 	-map()
 	The map() method creates a one-to-one mapping from the elements in the stream to the elements
	of the next step in the stream.
	
	<R> Stream<R> map(Function<? super T, ? extends R> mapper)
 */
		Stream<String> monosStream = Stream.of("12345","monkey", "gorilla", "bonobo","1234");
		System.out.print("\nmap: ");
		monosStream.map(String::length).forEach(System.out::print);
		
/**
 	-flatMap()
 	The flatMap() method takes each element in the stream and makes any elements it contains
	top-level elements in a single stream. 
	
	This is helpful when you want to remove empty elements from a stream 
		or you want to combine a stream of lists.
 	
 	<R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper)
 	
 	This gibberish basically says that it returns a Stream of the type that the function contains
	at a lower level.
 */
		List<String> zero = Arrays.asList();
		List<String> one = Arrays.asList("Bonobo");
		List<String> two = Arrays.asList("Mama Gorilla", "Baby Gorilla");
		Stream<List<String>> animals = Stream.of(zero, one, two);
		System.out.print("\nflatMap: ");
		animals.flatMap(l -> l.stream()).forEach(System.out::print);

/**
 	-sorted()
 	The sorted() method returns a stream with the elements sorted. Just like sorting arrays,
	Java uses natural ordering unless we specify a comparator.
 	
 	Stream<T> sorted()
	Stream<T> sorted(Comparator<? super T> comparator)
 */
		Stream<String> apellidosStream = Stream.of("zabaleta","ochoa","larios","alvarez");
		System.out.print("\nsorted: ");
		apellidosStream.sorted().forEach(System.out::print);
		
		Stream<String> apellidosInversosStream = Stream.of("zabaleta","ochoa","larios","alvarez");
		System.out.print("\nsorted(Comparator): ");
		apellidosInversosStream.sorted(Comparator.reverseOrder()).forEach(System.out::print);
		
/**
 	-peek()
 	It is useful for debugging because it allows us to perform a stream operation 
 		without actually changing the stream.
 		
 	Stream<T> peek(Consumer<? super T> action)
 */
		System.out.println("\npeek: ");
		Stream<String> theStream = Stream.of("black bear","gato", "brown bear", "grizzly");
		//System.out.println("original count: " + stream.count());
		//count method es una "Terminal Operation"
		Stream<String> streamFiltered = theStream.filter(s -> s.startsWith("g"));
		
		long count = streamFiltered
								//el peek es como el forEach, pero sin cambiar el stream
								.peek(System.out::print)
								.count();
		System.out.println(count);
		
/**
 	Putting Together the Pipeline
 	-----------------------------

 	Let’s say that we wanted to get the first two names alphabetically that are
	four characters long.
 		
 */
	//Before Streams
		List<String> list = Arrays.asList("Toby", "Anna", "Leroy", "Alex");
		List<String> filtered = new ArrayList<>();
		for (String name: list) if (name.length() == 4) filtered.add(name);
		Collections.sort(filtered);
		Iterator<String> iter = filtered.iterator();
		if (iter.hasNext()) System.out.println(iter.next());
		if (iter.hasNext()) System.out.println(iter.next());
	
	//Now With Streams
		List<String> listWithStream = Arrays.asList("Toby", "Anna", "Leroy", "Alex");
		listWithStream
			.stream()
			.filter(n -> n.length() == 4)
			.sorted()
			.limit(2)
			.forEach(System.out::println);
		
/**
 	In this example, you see all three parts of the pipeline.
 
				-------------------------------------
	Stream 	->	|	filter() ->	sorted() -> limit()	|	->	forEach()
				-------------------------------------
 
 	205
 
 */

		Stream<Integer> numerosStream = Stream.of(1,2,3,4);
		long numerosStreamCount = numerosStream.count();
		System.out.println(numerosStreamCount);
		//numerosStream.forEach(System.out::println);
		
		
	}
	
	public static void main(String[] args) {
		new TheStreams();
	}
}
