package core;

import java.util.Random;
import java.util.stream.IntStream;

public class PayuOrdenamientoPorSeleccion {

	public static void main(String[] args) {
		
		//Randoms
		Random r = new Random();
		
		IntStream.generate( () -> r.nextInt(10) )
					.limit(10)
					.sorted()
					.forEach(System.out::println);
	}
}
