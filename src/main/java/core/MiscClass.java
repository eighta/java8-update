package core;

public class MiscClass {

	public static int factorial (int numero) {
		
	  if (numero==0)
	    return 1;
	  else
	    return numero * factorial(numero-1);
	}
}
