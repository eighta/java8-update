package core;

public class Payu {
/**
 	Stack y Heap
 	------------
 	Dos zonas diferentes de memoria que utiliza la JVM (Java Virtual Machine) de Java.
 	
 	>>>Stack 
 	se utiliza para almacenar variables locales, variables de referencia, 
 	par�metros y valores de retorno, resultados parciales. 
 	Tambi�n se utiliza para llevar el control de la invocaci�n y retorno de los m�todos.
 	
 	Cada thread en la JVM tiene asignado un stack privado desde el momento de su creaci�n.
 	
 	>>>Heap
 	almacena objetos y sus variables de instancia. Es un espacio de memoria din�mica 
 	que se crea al inicio de la m�quina virtual y es �nico.
 	
 	El administrador de este espacio de memoria din�mica es el sistema de administraci�n 
 	de almacenamiento autom�tico o m�s conocido como Garbage Collector (Recolector de Basura).
 	
 	Acoplamiento y Cohesi�n
 	-----------------------
 	Uno de los objetivos m�s importantes del dise�o orientado a objetos es conseguir 
 	una alta cohesi�n entre clases y un bajo acoplamiento.

	>>>�Qu� es la cohesi�n?
	La medida que indica si una clase tiene una funci�n bien definida dentro del sistema. 
	El objetivo es enfocar de la forma m�s precisa posible el prop�sito de la clase. 
	Cuanto m�s enfoquemos el prop�sito de la clase, mayor ser� su cohesi�n.
	
	Una prueba f�cil de cohesi�n consiste en examinar una clase 
	y decidir si todo su contenido est� directamente relacionado con el nombre de la clase 
	y descrito por el mismo.

	Una alta cohesi�n hace m�s f�cil:
	-Entender qu� hace una clase o m�todo
	-Usar nombres descriptivos
	-Reutilizar clases o m�todos
 
 
 	>>>�Qu� es el acoplamiento?
	El acoplamiento entre clases es una medida de la interconexi�n o dependencia entre esas clases.
	
	El acoplamiento fuerte significa que las clases relacionadas necesitan saber 
	detalles internos unas de otras, los cambios se propagan por el sistema 
	y el sistema es posiblemente m�s dif�cil de entender.

	Por ello deberemos siempre intentar que nuestras clases tengan un acoplamiento bajo. 
	Cuantas menos cosas conozca la clase A sobre la clase B, menor ser� su acoplamiento.

	Lo ideal es conseguir que la clase A s�lo conozca de la clase B lo necesario para 
	que pueda hacer uso de los m�todos de la clase B, pero no conozca nada acerca de 
	c�mo estos m�todos o sus atributos est�n implementados.

	Los atributos de una clase deber�n ser privados y la �nica forma de acceder a ellos 
	debe ser a trav�s de los m�todos getter y setter.

	Un bajo acoplamiento permite:
	-Entender una clase sin leer otras
	-Cambiar una clase sin afectar a otras
	-Mejora la mantenibilidad del c�digo
 */
	
	
}
