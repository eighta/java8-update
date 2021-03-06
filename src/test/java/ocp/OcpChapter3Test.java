package ocp;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

public class OcpChapter3Test {

	private class InnerClass<T>{
		T t;
		InnerClass(T t){
			this.t = t;
		}
		
		{
			String.valueOf(t);
		}
		
	}
	
	public static  void otroMetodoEstatico(){
		//System.out.println(u.getMessage());
	}
	
	private static <U extends Exception> void metodoEstatico(U u){
		System.out.println(u.getMessage());
	} 
	
	public interface CanClimb {
		public abstract void climb();
	}
	
	
	public static void showSize(List<?> list) {
		System.out.println(list.size());
		}
	
	
	public static void main(String[] args) {
		
		Queue<Integer> listaDeNumeros = new LinkedList<>();
		listaDeNumeros.add(10);
		listaDeNumeros.add(12);
		System.out.println(listaDeNumeros);
		listaDeNumeros.remove(1);
		System.out.println(listaDeNumeros);
		
		ArrayDeque<?> list = new ArrayDeque<String>();
		//showSize(list);
		
		ArrayList<? super Date> list2 = new ArrayList<Date>();
		List<?> list3 = new ArrayList<>();
		
		TreeSet<String> treeSet = new TreeSet<>();
		//List<Exception> list = new LinkedList<java.io.IOException>();
		Vector<? extends Number> list11 = new Vector<Integer>();
		
		OcpChapter3Test.metodoEstatico( new Exception("B exc"));
		
		OcpChapter3Test.<Exception>metodoEstatico( new Exception("B exc"));
		
		//OcpChapter3Test.metodoEstatico( new Trowable("B exc"));
		
		System.out.println("--");
		String [] data = new String [] {"b", "a"};
		Arrays.asList(data).forEach(System.out::print);
		Arrays.sort(data);
		Arrays.asList(data).forEach(System.out::print);
		System.out.println("--");
		
		Set<Number> setNumber = new HashSet<Number>();
		setNumber.add(1);
		setNumber.add(null);
		setNumber.add(new Integer("2"));
		System.out.println("numeros: " + setNumber);
		
		
		new OcpChapter3Test(). new InnerClass<String>("milton");
		//new OcpChapter3Test(). new InnerClass(new Object());
		
		//Set<Number> setNumber = new HashSet<Integer>();
		Set<? extends Number> setNumberNull = new HashSet<Integer>();
		setNumberNull.add(null);
		
		//HACIA ARRIBA
		Set<? super ClassCastException> super1 = new HashSet<Exception>();
		
		//HACIA ABAJO
		Set<? extends Exception> extends1 = new HashSet<ClassCastException>();
		
		
		
		Map<String,String> preferenciasColores = new TreeMap<>();
		
		preferenciasColores.put("Danny", "Verde");
		preferenciasColores.put("Sophie", "Rosado");
		preferenciasColores.put("Milton", "Azul");
		preferenciasColores.put("Angie", "Morado");
		
		System.out.println("Nombres TreeMap (ordenado por key)");
		preferenciasColores.forEach((k,v) -> System.out.println(k) );
		preferenciasColores.forEach((k,v) -> System.out.println(k) );
		
		//Practicando Pilas(Stacks) y Colas(Queues)
		//https://www.slideshare.net/RoverOportunity2012/java-pilas-ycolas

		//===================
		//PAGE 162 question 4
		//===================
		
		Deque<String> greetings = new ArrayDeque<String>();
		greetings.push("hello");
		greetings.push("hi");
		greetings.push("ola");
		
		greetings.forEach(System.out::println);
		
		/*
		Max max = new Max() {
			@Override
			public void doSomething() {
				System.out.println("DOING SOMETHING");
				
			}
		};
		
		max.walk();
		
		int a = UnaInterface.AMOUNT;
		*/
		
		//NOT USED
		String.valueOf(list);
		String.valueOf(list2);
		String.valueOf(list3);
		String.valueOf(treeSet);
		String.valueOf(list11);
		String.valueOf(super1);
		String.valueOf(extends1);
		
	}
	
}

interface InterfaceUno{
	static String VAR = "1";
	void doSomething();
	default void walk() { System.out.println("uno"); }}
interface InterfaceTwo{
	static String VAR = "2";
	void doSomething();
	default void walk_error() { System.out.println("dos"); }}

interface Max extends InterfaceUno, InterfaceTwo{
	@Override
	default void walk() { System.out.println("tres"); }
}

interface UnaInterface{
	int AMOUNT = 10;
	static void staticMethod(){int a = AMOUNT+1; String.valueOf(a);}
}

abstract class ClaseAbstracta{}