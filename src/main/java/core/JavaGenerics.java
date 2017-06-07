package core;

import java.util.ArrayList;
import java.util.List;

import pojo.Robot;

/**
 	########
	GENERICS
	########
	Generics allows to write and use parameterized types
	
	They do show up frequently in the code you call, such as the Java Collections Framework.
	
	Generic Classes
	===============
	You can introduce generics into your own classes. The syntax for introducing a generic is to
	declare a formal type parameter in angle brackets
	
	see Crate class here
	
	Naming Conventions for Generics
	-------------------------------
	A type parameter can be named anything you want. The convention is to use single
	uppercase letters to make it obvious that they aren’t real class names. The following are
	common letters to use:
	
	E for an element
	K for a map key
	V for a map value
	N for a number
	T for a generic data type
	S , U , V , and so forth for multiple generic types
	
	Type Erasure
	------------
	Specifying a generic type allows the compiler to enforce proper use of the generic type.
	
	Behind the scenes, the compiler replaces all references to T in Crate with Object . In other
	words, after the code compiles, your generics are actually just Object types.
	
	The process of removing the generics syntax from your code is referred to as type erasure.
	
	Generic Interfaces
	==================
	Just like a class, an interface can declare a formal type parameter.
	
	What You Can't Do with Generic Types
	------------------------------------
	-Call the constructor. new T() is not allowed because at runtime it would be new Object().
	
	-Create an array of that static type. This one is the most annoying, but it makes sense
	because you’d be creating an array of Objects.
	
	-Call instanceof . This is not allowed because at runtime List<Integer> and
	List<String> look the same to Java thanks to type erasure.
	
	-Use a primitive type as a generic type parameter. This isn’t a big deal because you
	can use the wrapper class instead. If you want a type of int, just use Integer.
	
	-Create a static variable as a generic type parameter. This is not allowed because the
	type is linked to the instance of the class.
	
	Generic Methods
	===============
	PAGE 114

*/
public class JavaGenerics {

	//Generic Interface
	public interface Shippable<T> {
		void ship(T t);
	}
	
	//-Types of Implementations of Generic Interface
	//-1: The first is to specify the generic type in the class 
	public class ShippableRobotCrate implements Shippable<Robot>{
		@Override public void ship(Robot t) {}
	}
	//-2: The second way is to create a generic class
	public class ShippableAbstractCrate<U> implements Shippable<U> {
		public void ship(U t) { }
	}
	//-3: The final way is to not use generics at all
	//This is the old way of writing code. It generates a compiler warning 
	//about Shippable being a raw type, but it does compile.
	public class ShippableCrate implements Shippable {
		public void ship(Object t) { }
	}
	
	
	//Generic Class
	private class Crate<T> {
		private T contents;
		public T emptyCrate() {return contents;}
		public void packCrate(T contents) {this.contents = contents;}
	}
	
	//Generic Class with two generic parameters
	public class SizeLimitedCrate<T, U> {
		private T contents; private U sizeLimit;
		public SizeLimitedCrate(T contents, U sizeLimit) {
			this.contents = contents;
			this.sizeLimit = sizeLimit;
		} 
	}
	
	
	{
		
		Crate<String> crate = new Crate<String>();
		crate.emptyCrate();
		crate.packCrate("TheString");
		
		//java < 5
		List namesOldList = new ArrayList();
		
		//java 5
		List<String> namesListWithGenerics = new ArrayList<String>();

		//java 7
		/*
		Diamond Operator
		doesn’t contain the redundant type information
		 */
		List<String> namesListWithDiamond = new ArrayList<>();
		
	}
}


