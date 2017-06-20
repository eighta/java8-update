package core;

public class Payu {
/**
 	Stack y Heap
 	------------
 	Dos zonas diferentes de memoria que utiliza la JVM (Java Virtual Machine) de Java.
 	
 	>>>Stack 
 	se utiliza para almacenar variables locales, variables de referencia, 
 	parámetros y valores de retorno, resultados parciales. 
 	También se utiliza para llevar el control de la invocación y retorno de los métodos.
 	
 	Cada thread en la JVM tiene asignado un stack privado desde el momento de su creación.
 	
 	>>>Heap
 	almacena objetos y sus variables de instancia. Es un espacio de memoria dinámica 
 	que se crea al inicio de la máquina virtual y es único.
 	
 	El administrador de este espacio de memoria dinámica es el sistema de administración 
 	de almacenamiento automático o más conocido como Garbage Collector (Recolector de Basura).
 	
 	Acoplamiento y Cohesión
 	-----------------------
 	Uno de los objetivos más importantes del diseño orientado a objetos es conseguir 
 	una alta cohesión entre clases y un bajo acoplamiento.

	>>>¿Qué es la cohesión?
	La medida que indica si una clase tiene una función bien definida dentro del sistema. 
	El objetivo es enfocar de la forma más precisa posible el propósito de la clase. 
	Cuanto más enfoquemos el propósito de la clase, mayor será su cohesión.
	
	Una prueba fácil de cohesión consiste en examinar una clase 
	y decidir si todo su contenido está directamente relacionado con el nombre de la clase 
	y descrito por el mismo.

	Una alta cohesión hace más fácil:
	-Entender qué hace una clase o método
	-Usar nombres descriptivos
	-Reutilizar clases o métodos
 
 
 	>>>¿Qué es el acoplamiento?
	El acoplamiento entre clases es una medida de la interconexión o dependencia entre esas clases.
	
	El acoplamiento fuerte significa que las clases relacionadas necesitan saber 
	detalles internos unas de otras, los cambios se propagan por el sistema 
	y el sistema es posiblemente más difícil de entender.

	Por ello deberemos siempre intentar que nuestras clases tengan un acoplamiento bajo. 
	Cuantas menos cosas conozca la clase A sobre la clase B, menor será su acoplamiento.

	Lo ideal es conseguir que la clase A sólo conozca de la clase B lo necesario para 
	que pueda hacer uso de los métodos de la clase B, pero no conozca nada acerca de 
	cómo estos métodos o sus atributos están implementados.

	Los atributos de una clase deberán ser privados y la única forma de acceder a ellos 
	debe ser a través de los métodos getter y setter.

	Un bajo acoplamiento permite:
	-Entender una clase sin leer otras
	-Cambiar una clase sin afectar a otras
	-Mejora la mantenibilidad del código
 */
	
	
}
