package core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 	Collections
 	
 	A collection is a group of objects contained in a single object.
 	
 	The Java Collections Framework is a set of classes in java.util for storing collections.
 
 
 	########
 	Arreglos
 	########
 	int size = 4;
 	// empty array
 	String[] array = new String[size];
 	
 	// pre-poblado
 	String[] array = { "gerbil", "mouse" };
 	List<String> list = Arrays.asList(array);
 
 
 	####
 	List	extends Collection<E> extends Iterable<E>
 	####
 	A list is an ordered collection of elements that allows duplicate entries.
 	Elements in a list can be accessed by an int index.
 	
 	ArrayList 
 		-cannot contain primitives
 		
 	java.util.Arrays$ArrayList
 		-representacion de un Arreglo en forma de List
 		-esta implementacion posee las mismas caracteristicas que un arreglo
 			Una ves definido el tamaño inicial del arreglo, esta no puede cambiar 
 			de igual forma esta caracteristica se conserva en este ArrayList
 			y al intentar adicionar o remover items lanza un:
 			UnsupportedOperationException
 	
 	###
 	Set		extends Collection<E> extends Iterable<E>
 	###
 	A set is a collection that does not allow duplicate entries.
 	
 	#####
 	Queue	extends Collection<E> extends Iterable<E>
 	#####
 	A queue is a collection that orders its elements in a specific order for processing.
 	
 	###
 	Map		NO TIENE SUPER INTERFACES
 	###
 	A map is a collection that maps keys to values, with no duplicate keys allowed.
	The elements in a map are key/value pairs.
 
 	Common Collections Methods
 	==========================
 	PAGE 125
 
 */

public class JavaCollections {

	{
		
		
	}
	
	public static void main(String[] args) {
		
		//Arreglo to List
		String[] array = { "gerbil", "mouse" };
	 	List<String> listInvariable = Arrays.asList(array);
	 	
	 	//List to Arreglo
	 	String[] array2 = (String[]) listInvariable.toArray();
	 	
	 	//Sorting
	 	Integer[] numbers = {6,9,1,8};
	 	Arrays.sort(numbers);
	 	List<Integer> numbersAsList = Arrays.asList(numbers);
	 	numbersAsList.forEach(System.out::println);
	 	
	 	List<Integer> list4search = Arrays.asList(9,7,5,3);
	 	
	 	//Searching
	 	//binary search assumes the input is sorted
	 	int sixIndex = Arrays.binarySearch(numbers, 6);
	 	System.out.println(sixIndex);
	 	
	 	int threeIndex = Arrays.binarySearch(numbers, 3);
	 	System.out.println(threeIndex);
	 	/*
	 	 [3 >>> -2]
	 	 INDEX:				0		1		2		3
	 	 ARRAY:		[		1,		6,		8,		9]
	 	  
	 	 La logica es: se intenta buscar el valor 3, pero este no es encontrado por eso retorna un valor negativo
	 	 y el valor -2 resulta de lo siguiente, el valor a buscar 3, segun el arreglo debió haber estado despues de 1,
	 	 y el 1 se encuentra en en el index 0, entonces se supone que el 3 debio estar en el index 1, como no es encontrado
	 	 pasa a ser -1 pero, finalmente a este -1 se le resta -1 para finalmente resultando -2
	 	 */
	 	
	 	Collections.sort(list4search);
	 	System.out.println(Collections.binarySearch(list4search, 3)); // 0
	 	
	 	//System.out.println(numbersAsList);
	 	
	 	
//	 	System.out.println(listInvariable.getClass());
//	 	System.out.println((new ArrayList<Integer>()).getClass() );
		
	}
	
}
