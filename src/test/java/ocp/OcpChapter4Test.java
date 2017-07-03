package ocp;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.OptionalLong;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class OcpChapter4Test {

	{
		Stream<Double> streamDouble = Stream.<Double>generate(Math::random);
		//Stream<Double> streamDouble = Stream.empty();
		Map<Boolean, List<Double>> collectResultGenerate = streamDouble
			.limit(0)
			.collect(Collectors.groupingBy(b -> b >= 0.5));
		System.out.println("collectResultGenerate: " + collectResultGenerate);
		
		//XXX NOT WORKING
		//IntStream.range(10, 20).collect(BigInteger::new, (b,i) -> b.toString() , null);
		BigInteger intCollect = IntStream.rangeClosed(1, 5).collect(
				() -> new BigInteger("0"),
				//(b,i) -> b.add(new BigInteger(String.valueOf(i)) ), 
				(b,i) -> {	//System.out.println(b+":::"+i);
							b.add(new BigInteger(String.valueOf(i)) );
							},
				(w,z)-> System.out.println(w+":-:"+z) );
		
		System.out.println( "intCollect: " + intCollect);
		
		Stream<Integer> objIntStream = IntStream.range(4, 8).mapToObj(i->i);
		List<Integer> listaEnteros = objIntStream.collect( Collectors.toList() );
		System.out.println("listaEnteros: " + listaEnteros);
		
		
		IntStream intStreamRangeAbierto = IntStream.range(1, 6);
		Stream<String> fromIntStream2StringStream = 
				intStreamRangeAbierto.mapToObj( i -> String.valueOf(i));
		
		System.out.println();
		System.out.println(new Boolean("false") );
		BooleanSupplier booleanSupplier = () -> Boolean.TRUE;
		
		UnaryOperator<String> unaryOperator = s -> s+s;
		BinaryOperator<String> binaryOperator = String::concat;
		
		Stream<String> ss = Stream.of("a","b");
		String reduceString = ss.reduce("-",binaryOperator);
		System.out.println("reduceString: " + reduceString);
		
		Stream<Integer> si = Stream.of(1,2,3,4);
		Integer reduce = si.reduce(Integer.MIN_VALUE,Math::max);
		System.out.println(reduce);
		
		Function<String,Integer> f = s -> s.length();
		BiFunction<Double,Integer,String> bf = (d,i) -> String.valueOf(d) + String.valueOf(i);
		
		Stream<String> emptyStream = Stream.empty();
		Map<Boolean, List<String>> mapPartition = emptyStream.collect(Collectors.partitioningBy(s -> s.startsWith("c")));
		System.out.println(mapPartition);
		
		
		Stream<Integer> s = Stream.of(1);
		IntStream is = s.mapToInt(x -> x);
		DoubleStream ds = is.mapToDouble(x -> x);
		//Stream<Integer> s2 = ds.mapToInt(x -> x);
		
		
		Supplier<String> sup = String::new;;
		
		LongStream ls2 = LongStream.of(1, 2, 3);
		ls2.peek(System.out::println).count();
		
		LongStream ls = LongStream.of(1, 2, 3);
		OptionalLong opt = ls.map(n -> n * 10).filter(n -> n < 5).findFirst();
		
		IntStream is1 = IntStream.empty();
		int sum = is1.sum();
		System.out.println(sum);
		
		
		Predicate<? super String> p1 = z -> z.startsWith("G");
		//Predicate<? super String> p2 = String::startsWith("G");
		
		Stream<String> s1 = Stream.iterate("", w -> w + "1" );
		System.out.println(s1.limit(2).map( x -> x + "2"  ));
		
		//NOT USED
		String.valueOf(fromIntStream2StringStream);
		String.valueOf(booleanSupplier);
		String.valueOf(unaryOperator);
		String.valueOf(f);
		String.valueOf(bf);
		String.valueOf(ds);
		String.valueOf(sup);
		String.valueOf(opt);
		String.valueOf(p1);
	}
	
	public static void main(String[] args) {
		new OcpChapter4Test();
	}
}
