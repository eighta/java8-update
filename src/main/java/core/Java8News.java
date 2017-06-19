package core;

public class Java8News {

	private interface InnerInterface{
		default void metodoDefecto(){
			System.out.println("Calling a Default method");
		}
		void methodDeInstancia();
		static void metodoEstatico(){
			System.out.println("Calling a static method");
		}
	} 
	
	
	{
		InnerInterface innerInterfaceAnnonymous = new InnerInterface(){
			@Override
			public void methodDeInstancia() {
				System.out.println("Calling a instance method");
			}};
		
		
/**
 	Default Methods and Multiple Inheritance
 	----------------------------------------
 
 
 */
		
		innerInterfaceAnnonymous.metodoDefecto();
		
		
		
/**
	 Static Interface Methods
	 ------------------------	 
	A static method defined in an interface is not inherited 
		in any classes that implement the interface.
*/
		
		innerInterfaceAnnonymous.methodDeInstancia();
		
		//NO ES POSIBLE LLAMAR EL METODO ESTATICO DESDE UNA INSTANCIA
		//innerInterfaceAnnonymous.metodoEstatico();
		
		//SE LLAMA DIRECTAMENTE DESDE SU DEFINICION
		InnerInterface.metodoEstatico();
		
	}
	
	public static void main(String[] args) {
		new Java8News();
	}
	
	
}
