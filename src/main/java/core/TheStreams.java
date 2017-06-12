package core;

/**
 
	A stream in Java is a sequence of data. 
	A stream pipeline is the operations that run on a stream to produce a result.
 
 	Think of a stream pipeline as an assembly line in a factory.
 
 	Finite streams have a limit
 	Other assembly lines essentially run forever, like one for food production
 	
 	Many things can happen in the assembly line stations along the way. 
 	In programming, these are called stream operations.
 	
 	Just like with the assembly line, operations occur in a pipeline. 
 	Someone has to start and end the work, and there can be any number of stations
	in between.
	
	There are three parts to a stream pipeline:
	-Source: Where the stream comes from.
	-Intermediate operations: Transforms the stream into another one.
		Since streams use lazy evaluation, the intermediate operations 
		do not run until the terminal operation runs.
	-Terminal operation: Actually produces a result.
	
	
 	
 */


public class TheStreams {

}
