package core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 	Collections
 	
 	A collection is a group of objects contained in a single object.
 	
 	The Java Collections Framework is a set of classes in java.util for storing collections.
 	
 	Common Collections Methods
 	==========================
 	
 	boolean add(E element)
 	boolean remove(Object object)
 	boolean isEmpty()
 	int size()
 	void clear()
 	boolean contains(Object object) >>> (This method calls equals() on each element)
 
 
 	########
 	Arreglos
 	########
 	int size = 4;
 	// empty array
 	String[] array = new String[size];
 	
 	// pre-poblado
 	String[] array = { "gerbil", "mouse" };
 	List<String> list = Arrays.asList(array);
 
 
 	#####################
 	List	extends Collection<E> extends Iterable<E>
 	#####################
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
 			
 	((Implementatations))
 	*ArrayList extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, Serializable
 	
 	*LinkedList	extends AbstractSequentialList<E> implements List<E>, Deque<E>, Cloneable, Serializable
 	Deque extends Queue<E>
 	-has additional methods to facilitate adding or removing from the beginning and/or end of the list
 	
 	*Vector was the only choice if you wanted a list in java 1.2; is thread-safe
 	
 	*Stack is a data structure where you add and remove elements from the top of the stack
 	-Stack extends Vector, its mean that is old, If you need a stack, use an ArrayDeque instead
 	
 	[[Methods]]
 	void add(int index, E element)
 	Adds element at index and moves the rest toward the end
 	
 	E set(int index, E e)
 	Replaces element at index and returns original
 	 
 	
 	#####################
 	Set		extends Collection<E> extends Iterable<E>
 	#####################
 	A set is a collection that does not allow duplicate entries.
 	
 	((Implementatations))
 	*HashSet stores its elements in a hash table. This means that it uses the hashCode()
	method of the objects to retrieve them more efficiently
	
	*TreeSet stores its elements in a sorted tree structure.
	
	[[Methods]]
	no news
 	
 	#####################
 	Queue	extends Collection<E> extends Iterable<E>
 	#####################
 	A queue is a collection that orders its elements in a specific order for processing.
 	You use a queue when elements are added and removed in a specific order. 
 	Queues are typically used for sorting elements prior to processing them.
 	
 	Unless stated otherwise, a queue is assumed to be FIFO (first-in, first-out). Some queue
	implementations change this to use a different order.
	
	The other common format is LIFO (last-in, first-out.)
	
	((Implementatations))
	*LinkedList In addition to being a list, it is a double-ended queue.
	A double-ended queue is different from a regular queue in that you can insert
	and remove elements from both the front and back of the queue.
	
	*ArrayDeque is a "pure" double-ended queue. It was introduced in Java 6, and it
	stores its elements in a resizable array.
	Deque (interface) is supposed to be pronounced “deck,” but many people,
	including the authors, say it wrong as “d-queue.”
 	
 	[[Methods]]
 	E element()
 	Returns next element or throws an exception if empty queue
 	
 	The offer / poll / peek methods are more common.
 	------------------------------------------------
 	boolean offer(E e)
 	Adds an element to the back of the queue and returns whether successful
 	
 	E poll()
 	Removes and returns next element or returns null if empty queue
 	
 	E peek()
 	Returns next element or returns null if empty queue
 	
 	Others
 	------
 	void push(E e)
 	Adds an element to the front of the queue
 	
 	E pop()
 	Removes and returns next element or throws an exception if empty queue
 	
 	When talking about LIFO (stack), people say push / poll / peek. 
 	When talking about FIFO (single-ended queue), people say offer / poll / peek.
 	
 	#####################
 	Map		NO TIENE SUPER INTERFACES
 	#####################
 	A map is a collection that maps keys to values, with no duplicate keys allowed.
	The elements in a map are key/value pairs.
 
 	((Implementatations))
 	*HashMap 
 	stores the keys in a hash table. This means that it uses the hashCode() method
	of the keys to retrieve their values more efficiently.
	
	*LinkedHashMap
	
	*TreeMap
	stores the keys in a sorted tree structure.
	The main benefit is that the keys are always in sorted order.
	
	*Hashtable
	is like Vector in that it is really old and thread-safe and that you won’t be
	expected to use it.
 	
 	[[Methods]]
 	PAGE 139
 
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
