package ocp;

import java.util.ArrayDeque;

public class OcpChapter3Test {

	public interface CanClimb {
		public abstract void climb();
	}
	
	public static void main(String[] args) {
		
		//Practicando Pilas(Stacks) y Colas(Queues)
		//https://www.slideshare.net/RoverOportunity2012/java-pilas-ycolas

		//===================
		//PAGE 162 question 4
		//===================
		
		ArrayDeque<String> greetings = new ArrayDeque<String>();
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
	static void staticMethod(){int a = AMOUNT+1;}
}

abstract class ClaseAbstracta{}