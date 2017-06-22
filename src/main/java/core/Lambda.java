package core;

public class Lambda {

	private static final int IM_STATIC = 44;
	
	private int im_instance = 55;
	
	
	public void method(int parameterEffectivelyFinal){

		//parameterEffectivelyFinal = 2; GIVES ERROR
		
		//reference effectively final parameters
		doOperation( (a,b) -> parameterEffectivelyFinal );
		
		//reference effectively final local variables
		int localVariableEffectivelyFinal = 12;
		
		//localVariableEffectivelyFinal = 1; GIVES ERROR
		doOperation( (a,b) -> localVariableEffectivelyFinal );
		
	}
	
	{
		
		doNoReturn( e -> e.intValue() );
		doAndReturn( e -> e+1);
		
		//reference effectively final parameters
		method(62);
		
		//reference instance variables 
		doOperation( (a,b) -> im_instance );
		
		//reference static variables 
		doOperation( (a,b) -> IM_STATIC );
		
		//--
		System.out.print("1: ");
		doOperation( (a,b) -> a + b );		
		
		System.out.print("2: ");
		doOperation( (a,b) -> a * b );
		
		System.out.print("3: ");
		doOperation( (a,b) -> 2*a + 5*b );
	}
	
	
	public void doAndReturn(AndReturn ar){}
	public void doNoReturn(NoReturn nr){}
	
	public void doOperation(TasteMe tm){
		int a = 3;
		int b = 4;
		int c = tm.taste(a, b);
		System.out.println("taste: " + c);
	}
	
	public static void main(String[] args) {
		new Lambda();
	}
}

@FunctionalInterface
interface AndReturn {
	int method(Integer a);
}

@FunctionalInterface
interface NoReturn {
	void method(Integer a);
}

@FunctionalInterface
interface TasteMe {
	int taste(int a, int b);
}