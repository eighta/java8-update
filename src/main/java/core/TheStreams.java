package core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
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
	pero debe esperar la signal de inicio
	
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
 	We cant create an infinite list, though, which makes streams more powerful:
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
		you have seen that works with an infinite stream.
		
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
		cannot use a traditional for loop to run because they dont implement the Iterable interface.
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
 	The collect() method is a special type of reduction called a mutable reduction. 
 	It is more efficient than a regular reduction because we use the same mutable object while accumulating. 
 	Common mutable objects include StringBuilder and ArrayList. 
 	This is a really useful method, because it lets us get data out of streams and into another form. 
 	The method signatures are as follows:
	
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
	The limit() and skip() methods make a Stream smaller. 
	They could make a finite stream smaller, 
	or they could make a finite stream out of an infinite stream. 
	The method signatures are shown here:
	
	Stream<T> limit(int maxSize)
	Stream<T> skip(int n)
 */
		
		Stream<Integer> enterosInfinitosStream = Stream.iterate(1, n -> n + 1);
		System.out.print("\nlimit and skip: ");
		enterosInfinitosStream.skip(5).limit(2).forEach(System.out::print);

/**
 	-map()
 	The map() method creates a one-to-one mapping 
 		from the elements in the stream to the elements of the next step in the stream.
	
	<R> Stream<R> map(Function < ? super T, 	
								 ? extends R> mapper)
 */
		Stream<String> monosStream = Stream.of("12345","monkey", "gorilla", "bonobo","1234");
												//5		6		  7			 6		  4
		System.out.print("\nmap: ");
		Optional<Integer> resultadoInt = monosStream.map(String::length).reduce( (x,y) -> x+y );
		System.out.println("resultadoInt: " + resultadoInt);
		
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

 	Lets say that we wanted to get the first two names alphabetically that are
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
 */
		
/**
  	EXAMPLE1: 
 		
		Stream.generate( () -> "Elsa" )			//infinito
			.filter( n -> n.length() == 4 )		//filtro
			.sorted()							//espera a que tenga todos los elementos (infinito)
			.limit(2)
			.forEach(System.out::println);
			
	EXAMPLE2:
	
		Stream.generate(() -> "Elsa")			//infinito
			.filter(n -> n.length() == 4)		//filtra
			.limit(2)							//cuenta dos elementos
			.sorted()							//los dos anterioes elementos los ordena
			.forEach(System.out::println);		//los imprime
			
	EXAMPLE3:
		
		Stream.generate(() -> "Olaf Lazisson")	//infinito
			.filter(n -> n.length() == 4)		//filtra (queda inifinito porque le filtro no deja pasar a nadie)
			.limit(2)							
			.sorted()
			.forEach(System.out::println);
			
	EXAMPLE4:
	
		Stream.iterate(1, x -> x + 1)			//infinito
			.limit(5)							//limita a 5 elementos
			.filter(x -> x % 2 == 1)			//filtra impar
			.forEach(System.out::print);		//imprime
			
	EXAMPLE5:
	
		Stream.iterate(1, x -> x + 1)			//infinito
			.limit(5)							//limita a 5 elementos
			.peek(System.out::print)			//PEEK imprime el actual elemento
			.filter(x -> x % 2 == 1)			//filtra impar
			.forEach(System.out::print);		//imprime
			
	EXAMPLE6:
	
		Stream.iterate(1, x -> x + 1)			//infinito
			.filter(x -> x % 2 == 1)			//filtra impares
			.limit(5)							//limita a 5 elementos
			.forEach(System.out::print); 		//imprime
			
	EXAMPLE7:
	
		Stream.iterate(1, x -> x + 1)			//infinito
			.filter(x -> x % 2 == 1)			//filtra impares
			.peek(System.out::print)			//PEEK imprime el actual elemento
			.limit(5)							//limita a 5 elementos
			.forEach(System.out::print);		//imprime
	
	Working with Primitives
	-----------------------
	
	Suppose that we want to calculate the sum of numbers in a finite
*/
		Stream<Integer> sumaNumerosStream = Stream.of(1, 2, 3);
		System.out.println("sumaNumerosStream: " + sumaNumerosStream.reduce(0, (s, n) -> s + n));
		
		//otra forma
		sumaNumerosStream = Stream.of(1, 2, 3);
		IntStream numeroEnterosStream = sumaNumerosStream.mapToInt(x -> x);
		int suma = numeroEnterosStream.sum();
		System.out.println("suma: " + suma);

		//CALCULO DE PROMEDIO
		sumaNumerosStream = Stream.of(1, 2, 3);
		numeroEnterosStream = sumaNumerosStream.mapToInt(x -> x);
		OptionalDouble average = numeroEnterosStream.average();
		System.out.println("average: " + average.getAsDouble());
		
/**
 	Creating Primitive Streams
 	--------------------------
 	
 	Here are three types of primitive streams:
	-IntStream: Used for the primitive types int, short, byte, and char
	-LongStream: Used for the primitive type long
	-DoubleStream: Used for the primitive types double and float

	Why doesn't each primitive type have its own primitive stream? 
		These three are the most common, so the API designers went with them.
 		
 		Stream, DoubleStream, IntStream , or LongStream
 */
		
		DoubleStream doubleEmptyStream = DoubleStream.empty();
		doubleEmptyStream.forEach(System.out::print);
		
		DoubleStream varargs = DoubleStream. of (1.0, 1.1, 1.2);
		varargs.forEach(System.out::print);
		
		DoubleStream randomDouble = DoubleStream.generate (Math::random);
		DoubleStream fractionsDouble = DoubleStream.iterate (.5, d -> d / 2);
		
		//IntStreams
		IntStream countInt = IntStream.iterate(1, n -> n+1).limit(5);
		countInt.forEach(System.out::println);
		
		//more simple
		//The range() method indicates that we want the numbers 1 to 6, not including the number 6.
		System.out.println("simple: ");
		IntStream range = IntStream.range(1, 6);
		range.forEach(System.out::println);
		
		//rangeClosed(1,5)
		IntStream rangeClosed = IntStream.rangeClosed(1, 5);
		rangeClosed.forEach(System.out::println);
		
/**
	Mapping methods between types of streams
	----------------------------------------
	
	|--------------------------------------------------------------------------------------------|
	|Source			2_Stream			2_DoubleStream			2_IntStream			2_LongStream |
	|--------------------------------------------------------------------------------------------|
	|Stream			map					mapToDouble				mapToInt			mapToLong	 |	 
	|
	|DoubleStream	mapToObj			map						mapToInt			mapToLong	 |
	|
	|IntStream		mapToObj			mapToDouble				map					mapToLong	 |
	|
	|LongStream		mapToObj			mapToDouble				mapToInt			map			 |
	|--------------------------------------------------------------------------------------------|
	
	Obviously, they have to be compatible types for this to work. Java requires a mapping
		function to be provided as a parameter, for example:
 */
		Stream<String> objStream = Stream.of("penguin", "fish");
		IntStream intStream = objStream.mapToInt(s -> s.length());
		
/**
	This function that takes an Object, which is a String in this case. 
	The function returns an int. 
	The function mappings are intuitive here. 
	They take the source type and return the target type. 
	
	In this example, the actual function type is ToIntFunction .


 	Function parameters when mapping between types of streams
 	---------------------------------------------------------
 	
 	|---------------------------------------------------------------------------------------------------|
	|Source			2_Stream			2_DoubleStream			2_IntStream			2_LongStream        |
	|---------------------------------------------------------------------------------------------------|
	|Stream			Function			ToDoubleFunction		ToIntFunction		ToLongFunction	 
	|
	|DoubleStream	DoubleFunction		DoubleUnaryOperator		DoubleToIntFunction	DoubleToLongFunction
	|
	|IntStream		IntFunction			IntToDoubleFunction		IntUnaryOperator	IntToLongFunction
	|
	|LongStream		LongFunction		LongToDoubleFunction	LongToIntFunction	LongUnaryOperator
	|----------------------------------------------------------------------------------------------------|
 	
 	You can also create a primitive stream from a Stream using 
 		flatMapToInt(),
		flatMapToDouble(), or 
		flatMapToLong(). 
		
	For example, 
		IntStream ints = list.stream().flatMapToInt(x -> IntStream.of(x));
		
		
	Using Optional with Primitive Streams
	-------------------------------------
	
	Now that you know about primitive streams, you can calculate the average in one line:
 */
		IntStream rangeClosedStream = IntStream.rangeClosed(1,10);
		OptionalDouble optional = rangeClosedStream.average();

/**
 	The return type is not the Optional you have become accustomed to using. 
 		It is a new type called OptionalDouble. 
 	
 	Why do we have a separate type, you might wonder? 
 	Why not just use Optional<Double>? 
 	
 	The difference is that 
 		OptionalDouble is for a primitive and
		Optional<Double> is for the Double wrapper class. 
		
	Working with the primitive optional class looks similar 
		to working with the Optional class itself:

 	optional.ifPresent(System.out::println);
	System.out.println(optional.getAsDouble());
	System.out.println(optional.orElseGet(() -> Double.NaN));

 	The only noticeable difference is that we called getAsDouble() rather than get().
	This makes it clear that we are working with a primitive. 
	
	Also, orElseGet() takes a DoubleSupplier instead of a Supplier.
	
	Optional types for primitives
	-----------------------------
	
	|-----------------------------------------------------------------|
	|				OptionalDouble		OptionalInt		OptionalLong  |
	|-----------------------------------------------------------------|
	|Getting		getAsDouble()		getAsInt()		getAsLong()	  |
	|
	|orElseGet()	DoubleSupplier		IntSupplier		LongSupploer  |
	|
	|max()			OptionalDouble		OptionalInt		OptionalLong  |
	|
	|sum()			double				int				long		  |
	|
	|avg()			OptionalDouble		OptionalDouble	OptionalDouble|
	|-----------------------------------------------------------------|
	
	
	Summarizing Statistics
	----------------------
	You have learned enough to be able to get the maximum value from a stream of int primitives.
	If the stream is empty, we want to throw an exception:
 */
		IntStream ints = IntStream.empty();
		OptionalInt optionaIntMax = ints.max();
		optionaIntMax.orElse(99);
		//optionaIntMax.orElseThrow(RuntimeException::new);
		
/**
 		PAGE 210 - 213
 		InterfacesFunctionales con los Streams primitivos 
 		
 	Working with Advanced Stream Pipeline Concepts
 	----------------------------------------------
 	
 	>Linking Streams to the Underlying Data
 	---------------------------------------
 */
		List<String> cats = new ArrayList<>();
		cats.add("Annie");
		cats.add("Ripley");
		Stream<String> catsStream = cats.stream();
		cats.add("KC");
		System.out.println("cats: " + catsStream.count());
/**
 	Remember that streams are lazily evaluated
 	An object is created  "cats.stream()" that knows where to look for the data when it is needed.
 	The stream pipeline runs first, looking at the source and seeing three elements.

 	Chaining Optionals
 	------------------
 	Suppose that you are given an Optional<Integer> and asked to print the value, but
	only if it is a three-digit number.
 */
		Optional<Integer> optionalInteger = Optional.of(81);
		if (optionalInteger.isPresent()) { // outer if
			Integer num = optionalInteger.get();
			String string = "" + num;
			if (string.length() == 3) // inner if
				System.out.println(string);
		}
/**
 	It works, but it contains nested if statements. Thats extra complexity. 
 	Lets try this again with functional programming:
 */
		optionalInteger
			.map(n -> "" + n) 				// part 1
			.filter(s -> s.length() == 3) 	// part 2
			.ifPresent(System.out::println);// part 3

/**
	Now suppose that we wanted to get an Optional<Integer> representing the length of
	the String contained in another Optional.
 */
		//Optional<String> optionalString = Optional.<String>empty();
		Optional<String> optionalString = Optional.<String>of("sophie");
		Optional<Integer> result = optionalString.map(String::length);
		result.ifPresent(System.out::println);
		
/**
 	What if we had a helper method that did the logic of calculating something for us and it
	had the signature static Optional<Integer> calculator(String s)? 
	Using map doesn't work:
	
	// DOES NOT COMPILE
	Optional<Integer> result = optional.map(ChainingOptionals::calculator); 
 */
		Optional<String> anotherOptionalString = Optional.empty();
		Optional<Integer> resultAnotherOptional = anotherOptionalString.map(TheStreams::calculate);
		
		Optional<Optional<Integer>> resultAnotherOptionalOptional = 
				anotherOptionalString.map(TheStreams::calculateOptional);
		
		//definitive solution
		//avoid double Optional
		Optional<Integer> flatResult = anotherOptionalString.flatMap(TheStreams::calculateOptional);
	
/**
 	Checked Exceptions and Functional Interfaces
 	--------------------------------------------
 	
 	TheStreams.create().stream().count();
 	
 	Nothing new here. The create() method throws a checked exception. The calling
	method handles or declares it.
	
	page 215-216
	
	Collecting Results
	------------------

	>>>Calculates the average for our three core primitive types
	averagingDouble(ToDoubleFunction f) :Double
	averagingInt(ToIntFunction f) :Double
	averagingLong(ToLongFunction f) :Double
	
	>>>Counts number of elements
	counting() :Long
	
	>>>Creates a map grouping by the specified function with the optional type 
		and optional downstream collector
	groupingBy(Function f) :Map<K, List<T>>
	groupingBy(Function f, Collector dc) :Map<K, List<T>>
	groupingBy(Function f, Supplier s, Collector dc) :Map<K, List<T>>
	
	>>>Creates a single String using cs (,) as a delimiter between elements 
		IF one is specified
	joining() :String
	joining(CharSequence cs) :String
	
	>>>Finds the largest/smallest elements
	maxBy(Comparator c) :Optional<T>
	minBy(Comparator c) :Optional<T>

	>>>Adds another level of collectors
	mapping(Function f, Collector dc) :Collector
	
	>>>Creates a map grouping by the specified predicate with the
		optional further downstream collector
	partitioningBy(Predicate p) :Map<Boolean, List<T>>
	partitioningBy(Predicate p, Collector dc) :Map<Boolean, List<T>>
	
	>>>Calculates average, min, max, and so on
	summarizingDouble(ToDoubleFunction f) :DoubleSummaryStatistics
	summarizingInt(ToIntFunction f) :IntSummaryStatistics
	summarizingLong(ToLongFunction f) :LongSummaryStatistics
	
	>>>Calculates the sum for our three core primitive types
	summingDouble(ToDoubleFunction f) :Double
	summingInt(ToIntFunction f) :Integer
	summingLong(ToLongFunction f) :Long
	
	>>>Creates an arbitrary type of list or set
	toList() :List
	toSet() :Set
	
	>>>Creates a Collection of the specified type
	toCollection(Supplier s) :Collection
	
	>>>Creates a map using functions to map the keys, values, 
		an optional merge function, and an optional type
	toMap(Function k, Function v) :Map
	toMap(Function k, Function v, BinaryOperator m) :Map
	toMap(Function k, Function v, BinaryOperator m, Supplier s) :Map
	
	Collecting Using Basic Collectors
	---------------------------------
 */
		String[] arrayAnimals = (String[]) Arrays.asList("lions", "tigers", "bears").toArray();
		Stream<String> ohMy = Stream.of(arrayAnimals);
		Collector<CharSequence, ?, String> joiningCollector = Collectors.joining(", ");
		String resultJoined = ohMy.collect(joiningCollector);
		System.out.println(resultJoined); // lions, tigers, bears
	
		//What is the average length of the three animal names?
		ohMy = Stream.of(arrayAnimals);
		Collector<String, ?, Double> averagingIntCollector = Collectors.averagingInt(String::length);
		Double avgResult = ohMy.collect(averagingIntCollector);
		System.out.println(avgResult);

/**
 	You can express yourself using a Stream and then convert to a Collection at the end, 
 		for example:
 */
		ohMy = Stream.of("lions", "tigers", "bears");
		Set<String> collectResult = ohMy
									.filter(s -> s.startsWith("t"))
									.collect(Collectors.toCollection(TreeSet::new));
		
		System.out.println(collectResult); // [tigers]
		
/**
 	Collecting into Maps 
 	--------------------
 */
		//create a map from a stream:
		ohMy = Stream.of("lions", "tigers", "bears");
	
		//When creating a map, you need to specify two functions. 
		//The first function tells the collector how to create the key.
		//The second function tells the collector how to create the value.
		Collector<String, ?, Map<String, Integer>> mapCollector 
				= Collectors.toMap(s -> s, String::length);
		
		Map<String, Integer> mapResult = ohMy.collect(mapCollector);
		System.out.println(mapResult);
		
/**
	Las otras dos metodos:
	-toMap(Function k, Function v, BinaryOperator m) :Map
	
	Utilizado en la circunstancia en que Dos key sean IDENTICAS (equals)
	el tercer parametro indica como resolver el value, dado los dos elementos
	por ello el BinaryOperator
	
	-toMap(Function k, Function v, BinaryOperator m, Supplier s) :Map
	
	Y esta tercera forma, recibe como cuarto parametro el Supplier, 
	lo cual indica el tipo (clase) de implementacion del Map
	ejemplo: TreeMap::new 
	
	Collecting Using Grouping, Partitioning, and Mapping
	----------------------------------------------------
	Now suppose that we want to get groups of names by their length. 
	We can do that by saying that we want to group by length:
 */
		ohMy = Stream.of("lions", "tigers", "bears");
		Map<Integer, List<String>> mapGroupingBy = 
				ohMy.collect(
						Collectors.groupingBy(String::length));
		System.out.println(mapGroupingBy);
/**
	The groupingBy() collector tells collect() that it should group all of the elements of
	the stream into lists, organizing them by the function provided. 
	This makes the keys in the map the function value and the values the function results.
	
	Suppose that we dont want a List as the value in the map and prefer a Set instead.
 */
		ohMy = Stream.of("lions", "tigers", "bears");
		
		//Collector<String, ?, Map<Integer, Set<Object>>> groupingByUsingSet 
		// = Collectors.groupingBy(String::length, Collectors.toSet() );
		
		Map<Integer, Set<String>> mapSet = ohMy.collect(
					Collectors.groupingBy(String::length, Collectors.toSet()));
		System.out.println(mapSet);
		
/**
 	We can even change the type of Map returned through yet another parameter:
 */
		ohMy = Stream.of("lions", "tigers", "bears");
		TreeMap<Integer, Set<String>> mapCustomSet = ohMy.collect(
		Collectors.groupingBy(String::length, TreeMap::new, Collectors.toSet()));
		System.out.println(mapCustomSet); // {5=[lions, bears], 6=[tigers]}

/**
 	Partitioning
 	------------
 	Partitioning is a special case of grouping. With partitioning, there are only two possible
	groups: true and false. Partitioning is like splitting a list into two parts
 	
 */
		ohMy = Stream.of("lions", "tigers", "bears");
		Map<Boolean, List<String>> map = ohMy.collect(
		Collectors.partitioningBy(s -> s.length() <= 5));
		System.out.println(map); // {false=[tigers], true=[lions, bears]}
		
/**
	-counting() collector
 */	
		ohMy = Stream.of("lions", "tigers", "bears");
		Map<Integer, Long> mapCounting = ohMy.collect(
				Collectors.groupingBy(String::length, Collectors.counting()));
		System.out.println(mapCounting); // {5=2, 6=1}
		
/**
	-mapping() collector (XXX TOPIC HARD XXX)
	Finally, there is a mapping() collector that lets us go down a level and add another
	collector. Suppose that we wanted to get the first letter of the first animal alphabetically of
	each length. Why? Perhaps for random sampling. The examples on this part of the exam
	are fairly contrived as well. We would write the following:
 */
		ohMy = Stream.of("lions", "tigers", "bears");
		
		Map<Integer, Optional<Character>> mapCustomGrouping = ohMy.collect(
				Collectors.groupingBy(
						String::length,
						Collectors.mapping(s -> s.charAt(0),
						Collectors.minBy( Comparator.naturalOrder() ) ) ) );
		System.out.println(mapCustomGrouping); // {5=Optional[b], 6=Optional[t]}
		
/**
 	Comparing it to the previous example, you can see that we replaced counting() with mapping(). 
 		It so happens that mapping() takes two parameters: 
 		the function for the value and how to group it further.
 		
 		
 	-reducing() collector
 	There is one more collector called reducing(). You dont need to know it for the exam.
	It is a general reduction in case all of the previous collectors dont meet your needs.
 */
	}
	
	private static List<String> create() throws IOException {throw new IOException();}
	
	public static Optional<Integer> calculateOptional (String source){
		return Optional.ofNullable(source).map(s -> s.length()); }
	public static Integer calculate (String s){return s.length();	}
	
	public static void main(String[] args) {
		new TheStreams();
	}
}
