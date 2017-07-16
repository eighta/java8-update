package core;

public class MiscClass {

	public static int factorial (int numero) {
		
	  if (numero==0)
	    return 1;
	  else
	    return numero * factorial(numero-1);
	}
	
	public static void main(String[] args) {
		System.out.println(String.class.getPackage().getSpecificationTitle());
		System.out.println(String.class.getPackage().getSpecificationVendor());
		System.out.println(String.class.getPackage().getImplementationTitle());
		System.out.println(String.class.getPackage().getImplementationVendor());
	}
}
