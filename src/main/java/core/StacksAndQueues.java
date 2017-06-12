package core;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 
 * @author eighta
 
  Stacks ands Queues
  Pilas y Colas
  https://es.slideshare.net/RoverOportunity2012/java-pilas-ycolas
 
 
 Abstract data type
 
 Abstracción de Datos
 https://es.wikipedia.org/wiki/Tipo_de_dato_abstracto
 
 Stacks
 Como una pila de datos
 
 En una pila se hacen las siguientes operaciones:
 
 -Introducir (PUSH) apilar
 Adicionar un nuevo elemento a la pila
 
 -Quitar (POP) desapilar
 Se elimina el elemento que esta en la cima de la Pila
 
 -Quien (PEEK)
 Se consulta quien esta en la cima de la pila
 
 -Buscar (SEARCH)
 devuelve el indice de posicion en la pila de un elemento 
 
 
 Queues
 
 Cola tambien llamada fila
 FIFO: el primero en entrar sera el primero en salir
 
 */

public class StacksAndQueues {
	
	{
		
		//Para implementar Colas se puede utiliza un LinkedList
		System.out.println("[LinkedList]");
		Queue<String> cola = new LinkedList<>();
		
		cola.add("1ro");
		System.out.println("peek: " + cola.peek() );
		cola.add("2do");
		System.out.println("peek: " + cola.peek() );
		cola.add("3ro");
		System.out.println("peek: " + cola.peek() );
		
		while(!cola.isEmpty() )
			System.out.println(cola.remove());
		
		//La Clase Stack fue remplazada por la interface Deque y sus implementaciones
		
		//A linear collection that supports element insertion and removal at both ends. 
		//The name deque is short for "double ended queue" and is usually pronounced "deck". 
		
		System.out.println("[Deque]");
		Deque<String> pilaDeNombresDeque = new ArrayDeque<>();
		System.out.println(pilaDeNombresDeque.peek());
		
		pilaDeNombresDeque.push("1ro");
		pilaDeNombresDeque.push("2do");
		pilaDeNombresDeque.push("3ro");
		
		while(!pilaDeNombresDeque.isEmpty())
			System.out.println(pilaDeNombresDeque.pop());
		
		
		
		
		System.out.println("[Stack]");
		Stack<String> pilaDeNombres = new Stack<>();
		//The Stack class represents a last-in-first-out (LIFO) stack of objects
		
		System.out.println("pila: " + pilaDeNombres);
		
		String s1= "1ro en entrar";
		pilaDeNombres.push(s1);
		System.out.println("pila: " + pilaDeNombres);
		System.out.println("peek: " + pilaDeNombres.peek() );
		
		pilaDeNombres.push("2do en entrar");
		System.out.println("pila: " + pilaDeNombres);
		System.out.println("peek: " + pilaDeNombres.peek() );
		
		pilaDeNombres.push("3ro en entrar");
		System.out.println("pila: " + pilaDeNombres);
		System.out.println("peek: " + pilaDeNombres.peek() );
		
		String s4 = "4to en entrar";
		pilaDeNombres.push(s4);
		System.out.println("pila: " + pilaDeNombres);
		System.out.println("peek: " + pilaDeNombres.peek() );
		
		System.out.println("pila: " + pilaDeNombres);
		
		int searchIndex1 = pilaDeNombres.search(s1);
		System.out.println(s1 + ", searchIndex: " + searchIndex1);
		
		int searchIndex4 = pilaDeNombres.search(s4);
		System.out.println(s4 + ", searchIndex: " + searchIndex4);
		
		//Simplemente se recorre Object[] elementData;
		//No se realiza ninguna operacion de pilas
		System.out.print("simple loop: ");
		pilaDeNombres.forEach((s) -> System.out.print(" '" + s + "' "));

		System.out.println("\n[.pop() loop]");
		while(!pilaDeNombres.empty()){
			String nombre = pilaDeNombres.pop();
			System.out.println(nombre);
		}
		
		//System.out.println("2.");
		//pilaDeNombres.forEach(System.out::println);
		
	}
	
	public static void main(String[] args) {
		new StacksAndQueues();
	}
}
