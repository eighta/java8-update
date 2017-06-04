package core;

public class UnaryOperators {

	{
		
		//#################################
		//Increment and Decrement Operators
		//#################################
		
		//have the higher order or precedence, as compared to binary operators
		
		/*
		If the operator is placed before the operand, referred to as the pre-increment operator
		and the pre-decrement operator, then the operator is applied first and the value return
		is the new value of the expression. 
		
		Alternatively, if the operator is placed after the operand,
		referred to as the post-increment operator and the post-decrement operator, then the original
		value of the expression is returned, with operator applied after the value is returned.
		*/
		
		int x = 3;
		int y = ++x * 5 / x-- + --x;
		
		/*
			int y = ++x * 5 / x-- + --x;
			
			-Como no hay parentesis todos los operadores tienen el mismo level
			-Unary operators tienen precedencia sobre los bynary operators
			-Toda la expresion se evalua de izquiera a derecha
			
			EXPRESSION							CURRENT X VALUE
			
			x incrementa y se toma el NEW value >>> 4
			int y = (4) * 5 / x-- + --x;		4
			
			x decrementa y se toma el OLD value	>>> 4
			int y = (4) * 5 / (4) + --x;		3
			
			x decrementa y se toma el NEW value	>>> 2
			int y = (4) * 5 / (4) + (2);		2
			
			opera la multiplicacion
			int y = 20 / 4 + 2;
			
			opera la division
			int y = 5 + 2;
			
			opera la suma
			int y = 7;
		
		*/
		System.out.println("x is " + x);
		System.out.println("y is " + y);
		
	}

	public static void main(String[] args) {
		new UnaryOperators();
	}
	
	
	
}
