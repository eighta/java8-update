package ocp;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class OcpChapter4Test {

	{
		
		Predicate<? super String> p1 = s -> s.startsWith("G");
		//Predicate<? super String> p2 = String::startsWith("G");
		
		Stream<String> s1 = Stream.iterate("", s -> s + "1" );
		System.out.println(s1.limit(2).map( x -> x + "2"  ));
		
	}
	
	public static void main(String[] args) {
		new OcpChapter4Test();
	}
}
