package ocp;

import java.util.OptionalLong;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class OcpChapter4Test {

	{
		
		Stream<Integer> s = Stream.of(1);
		IntStream is = s.mapToInt(x -> x);
		DoubleStream ds = s.mapToDouble(x -> x);
		Stream<Integer> s2 = ds.mapToInt(x -> x);
		
		
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
		
	}
	
	public static void main(String[] args) {
		new OcpChapter4Test();
	}
}
