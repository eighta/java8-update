package core;

import java.util.ArrayList;
import java.util.List;

public class JavaGenericsExamples {

	{
		//Por herencia, es posible adicionar todos los tipos que son heredados
		List<Inner> innerList = new ArrayList<>();
		innerList.add(new Inner());
		innerList.add(new InMiddle());
		innerList.add(new Outer());
		
		//Cuando se utiliza Unbounded wildcard (?) o upper bound (? extends)
		//la coleccion becomes logically immutable, es decir no se pueden añadir elementos
		@SuppressWarnings("unused")
		List<? extends Inner> innerExtendsList = new ArrayList<>();
		//COMPILATION ERROR innerExtendsList.add(new Inner());
		//COMPILATION ERROR innerExtendsList.add(new InMiddle());
		//COMPILATION ERROR innerExtendsList.add(new Outer());
		
		//Wildcard with a lower bound (? super Type)
		//With a lower bound, we are telling Java that the list will be a 
		//list of some objects that are a superclass of Type (Inner)
		//XXX SIMILAR A COMO SI NO SE COLOCARA EL BOUND
		List<? super Inner> innerSuperList = new ArrayList<>();
		innerSuperList.add(new Inner());
		innerSuperList.add(new InMiddle());
		innerSuperList.add(new Outer());		
	}
	
	public <T extends Inner> List<T> genericMethod(){
		//Funciona Correctamente
		//return new ArrayList<>();
		
		//En los tres casos el compilador dice que se debe castear a List<T>, es decir la T generica!
		//ERROR return new ArrayList<Inner>();
		//ERROR return new ArrayList<InMiddle>();
		//ERROR return new ArrayList<Outer>();
		
		return null;
	}
	
	public List<Inner> method(){
		List<Inner> listaRetornada = new ArrayList<>();
		listaRetornada.add(new InMiddle ());
		return listaRetornada;
	}
}

class Inner{}
class InMiddle extends Inner{}
class Outer extends InMiddle{}

//Clases Genericas
abstract class GenericClazz<T>{
	public abstract List<T> genericMethod();
} 

class ConcreteClass
extends GenericClazz<Inner>{

	@Override
	public List<Inner> genericMethod() {
		
		//OK return new ArrayList<Inner>();
		
		List<Inner> list = new ArrayList<>();
		list.add(new Inner());
		list.add(new InMiddle());
		list.add(new Outer());		
		return list;
	}
	
}

//Using multiple bounds
//We can use the syntax <T extends X & Y & Z> to define a generic class whose type parameter can be sub types of multiple types. 
//Here, X, Y, Z can be classes and interfaces. Remember if there is a class, the class must be the first in the list.
class MultipleBounds <T extends Number & Runnable >{}