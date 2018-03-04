package core;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;

public class JavaIO {
	{
/**
	Understanding Files and Directories
	-----------------------------------
	
	what a file is and what a directory is within a file system
	
	Conceptualizing the File System
	-------------------------------
	*** A file is record within a file system that stores user and system data. Files are organized
	using directories. 
	*** A directory is a record within a file system that contains files as well
	as other directories.
	*** Finally, the root directory is the topmost directory in the file system, from
	which all files and directories inherit.
	
	In order to interact with files, we need to connect to the file system.
	The file system is in charge of reading and writing data within a computer.

	Different operating systems use different file systems to manage their data. 
	For example, Windows-based systems use a different file system than Unix-based ones.
	
	A path is a String representation of a fi le or directory within a file system.
	Each fi le system defines its own path separator character that is used between directory entries.

	Introducing the File Class
	--------------------------
	The java.io.File class is used to read information about existing 
	files and directories, list the contents of a directory, and create/delete 
	files and directories.
	
	An instance of a File class represents the pathname of a particular file or directory on the file system. 
	The File class cannot read or write data within a fi le, although it can be passed as a reference 
	to many stream classes to read or write data
	
	Creating a File Object
	----------------------
	A File object often is initialized with String containing either an absolute or relative path
	to the file or directory within the file system. The absolute path of a file or directory is the
	full path from the root directory to the file or directory, including all subdirectories that
	contain the file or directory. Alternatively, the relative path of a file or directory is the path
	from the current working directory to file or directory.
	
	For example, the following is an absolute path to the zoo.txt file:
		/home/smith/data/zoo.txt
	
	The following is a relative path to the same file, 
	assuming the user’s current directory was set to /home/smith.
		data/zoo.txt
	
	Different operating systems vary in their format of path names. 
	For example, Unixbased systems use the forward slash / for paths, 
	whereas Windows-based systems use the backslash \ character. 
	That said, many programming languages and file systems support both types of slashes 
	when writing path statements. 
	For convenience, Java offers two options to retrieve the local separator character: 
	a system property and a static variable defined in the File class. 
	
	Both of the following examples will output the separator character:
	
 */
		System.out.println(
				"System.getProperty(\"file.separator\"): " +
				System.getProperty("file.separator"));	
		
		System.out.println(
				"java.io.File.separator: " + 
				java.io.File.separator);
		

//determines if the path it references exists within the file system:
		
		//File file = new File("/home/smith/data/zoo.txt");
		File parent = new File("C:\\Yesta");
		System.out.println(parent.exists());
		
		File child = new File(parent,"dev/dualshock4.txt");
		System.out.println(child.exists());
		
//Working with a File Object
		
		child.exists();
		child.getName();
		child.getAbsolutePath();
		child.isDirectory();
		child.isFile();
		child.length();
		child.lastModified();
		
		child.delete();
		//Deletes the file or directory. If this pathname denotes a directory,
		//then the directory must be empty in order to be deleted.
		
		child.renameTo(parent);
		child.mkdir();
		child.mkdirs();
		child.getParent();
		child.listFiles();
/**
	Introducing Streams
	-------------------
	I/O refers to the nature of how data is accessed, either by
	reading the data from a resource (input), or writing the data to a resource (output).
	
	Note that the I/O streams that we discuss in here are data streams and
	completely unrelated to the new "Stream" API "Functional Programming"
	
	Stream Fundamentals
	-------------------
	The contents of a fi le may be accessed or written via a stream, 
	which is a list of data elements presented sequentially.
	
	Streams should be conceptually thought of as a long, nearly never-ending
	“stream of water” with data presented one “wave” at a time.
	
	NOTA:
	It may be helpful to visualize a stream as being so large that all of the
	data contained in it could not possibly fit into memory. For example, a 1
	terabyte file could not be stored entirely in memory by most computer
	systems (at the time this book is being written). The file can still be read
	and written by a program with very little memory, since the stream allows
	the application to focus on only a small portion of the overall stream at any
	given time.
	
	A File can be very long that The stream is so large that once we start reading it, 
	we have no idea where the beginning or the end is. We just have a pointer to our 
	current position in the stream and read data one block at a time.
	
	Each type of stream segments data into a “wave” or “block” in a particular way. 
	For example, some stream classes read or write data as individual byte values. 
	Other stream classes read or write individual characters or strings of characters. 
	On top of that, some stream classes read or write groups of bytes or characters at a time, 
	specifically those with the word Buffered in their name.
	
	All Java Streams Use Bytes
	--------------------------
	Although the java.io API is full of streams that handle characters, strings, groups of
	bytes, and so on, nearly all are built on top of reading or writing an individual byte or
	an array of bytes at a time. The reasoning behind more high-order streams is for convenience
	as well as performance.
	
	Although streams are commonly used with file I/O, they are more generally used to
	handle reading/writing of a sequential data source. For example, you might construct a Java
	application that submits data to a website using an input stream and reads the result via an
	output stream.
	In fact, you have been using streams since your first “Hello World” program! Java provides
	three built-in streams, System.in, System.err, and System.out, the last of which we have
	been using to output data to the screen
	
	Stream Nomenclature
	-------------------
	The java.io API provides numerous classes for creating, accessing, and manipulating streams
	- so many that it tends to overwhelm most new Java developers. 
	Stay calm! We will review the major differences between each stream class 
	and show you how to distinguish between them. 
	
	Even if you do come across a particular stream on the exam that you do not recognize, 
	often the name of the stream gives you enough information to understand exactly what it does.

	Byte Streams vs. Character Streams
	----------------------------------
	The java.io API defines two sets of classes for reading and writing streams: 
		those with Stream in their name and 
		those with Reader/Writer in their name. 
		
	For example, the java.io API defines both 
		a FileInputStream class as well as a 
		FileReader class, 
		
	both of which define a stream that reads a file. 
	The difference between the two classes is based on how the stream is read or written.
	
	Differences between Streams and Readers/Writers
	-----------------------------------------------
	1. The stream classes are used for inputting and outputting all types of binary or byte data.
	
	2. The reader and writer classes are used for inputting and outputting 
		only character and String data.
		
	It is important to remember that even though readers/writers do not contain the word Stream 
	in their class name, they are still in fact streams! 
	The use of Reader/Writer in the name is just to distinguish them from byte streams. 
	
	Why Use Character Streams?
	--------------------------
	Since the byte stream classes can be used to input and output all types of binary data,
	including strings, it naturally follows that you can write all of your code to use the byte
	stream classes, never really needing the character stream classes.
	There are advantages, though, to using the reader/writer classes, as they are specifically
	focused on managing character and string data. For example, you can use a Writer class
	to output a String value to a file without necessarily having to worry about the underlying
	byte encoding of the file.
	For this reason, the character stream classes are sometimes referred to as convenience
	classes for working with text data.
	
	The java.io API is structured such that all of the stream classes have the word
	InputStream or OutputStream in their name, while all Reader/Writer classes have either
	Reader or Writer in their name.
	
	Input and Output
	----------------
	Most Input stream classes have a corresponding Output class and vice versa. 
	For example, the FileOutputStream class writes data that can be read by a FileInputStream. 
	If you understand the features of a particular Input or Output stream class, you should naturally
	know what its complementary class does.
	
	It follows, then, that most Reader classes have a corresponding Writer class. 
	For example, the FileWriter class writes data that can be read by a FileReader.
	
	There are exceptions to this rule. For the exam, you should know that PrintWriter has
	no accompanying PrintReader class. Likewise, the PrintStream class has no corresponding
	InputStream class. We will discuss these classes later this chapter.
	
	Low-Level vs. High-Level Streams
	--------------------------------
	Another way that you can familiarize yourself with the java.io API is by segmenting
	streams into low-level and high-level streams.
	
	A low-level stream connects directly with the source of the data, such as a file, an array,
	or a String. Low-level streams process the raw data or resource and are accessed in a
	direct and unfiltered manner. For example, a FileInputStream is a class that reads file data
	one byte at a time.
	
	Alternatively, a high-level stream is built on top of another stream using wrapping.
	Wrapping is the process by which an instance is passed to the constructor of another
	class and operations on the resulting instance are filtered and applied to the original
	instance. For example, take a look at the FileWriter and BufferedWriter objects in the
	following sample code:
	
 */
		//Reader
		String file = "C:\\Yesta\\allFiles_2018-03-04.txt";
		try{
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			System.out.println(bufferedReader.readLine());
			
		}catch(Exception e) {e.printStackTrace();}
/*	
	In this example, FileReader is the low-level stream reader, whereas BufferedReader is the
	high-level stream that takes a FileReader as input. Many operations on the high-level stream
	pass through as operations to the underlying low-level stream, such as read() or close().

	Other operations override or add new functionality to the low-level stream methods. 
	The highlevel stream adds new methods, such as readLine(), as well as performance enhancements for
	reading and filtering the low-level data.

	High-level streams can take other high-level streams as input. For example, although the
	following code might seem a little odd at first, the style of wrapping a stream is quite common
	in practice:
*/	
		//InputStream
		try{
			ObjectInputStream objectStream = 
					new ObjectInputStream(
						new BufferedInputStream(
							new FileInputStream(file)));
			
			System.out.println(objectStream.readObject());
			
		}catch(Exception e) {
			//e.printStackTrace();
			//SE LANZA EXCEPTION PORQUE EL ObjectInputStream SOLO LEE OBJETOS
			//QUE FUERON ESCRITOS UTILIZANDO ObjectOutputStream
		}
		
/**		
	In this example, FileInputStream is the low-level stream that interacts directly with
	the file, which is wrapped by a high-level BufferedInputStream to improve performance.
	Finally, the entire object is wrapped by a high-level ObjectInputStream, which allows us to
	filter the data as Java objects.
	
	Use Buffered Streams When Working with Files
	--------------------------------------------
	As briefly mentioned, Buffered classes read or write data in groups, rather than a single
	byte or character at a time. The performance gain from using a Buffered class to access
	a low-level file stream cannot be overstated. Unless you are doing something very specialized
	in your application, you should always wrap a file stream with a Buffered class in
	practice.

	The reason that Buffered streams tend to perform so well in practice is that file systems
	are geared for sequential disk access. The more sequential bytes you read at a time, the
	fewer round-trips between the Java process and the file system, improving the access of
	your application. For example, accessing 16 sequential bytes is a lot faster than accessing
	16 bytes spread across the hard drive.
	
	Stream Base Classes
	-------------------
	The java.io library defines four abstract classes that are the parents of all stream classes
	defined within the API: InputStream, OutputStream, Reader, and Writer. For convenience,
	the authors of the Java API include the name of the abstract parent class as the suffix of
	the child class. For example, ObjectInputStream ends with InputStream, meaning it has
	InputStream as an inherited parent class. Although most stream classes in java.io follow
	this pattern, PrintStream, which is an OutputStream, does not.
	
	Common Stream Operations
	------------------------
	
	***Closing the Stream
	Since streams are considered resources, it is imperative that they be closed after they
	are used lest they lead to resource leaks.
	
	In a file system, failing to close a file properly could leave it locked by the operating
	system such that no other processes could read/write to it until the program is terminated.
	
	***Flushing the Stream
	When data is written to an OutputStream, the underlying operating system does not
	necessarily guarantee that the data will make it to the file immediately. In many operating
	systems, the data may be cached in memory, with a write occurring only after a temporary
	cache is filled or after some amount of time has passed.
	If the data is cached in memory and the application terminates unexpectedly, the data
	would be lost, because it was never written to the file system. To address this, Java provides
	a flush() method, which requests that all accumulated data be written immediately to disk.
	The flush() method helps reduce the amount of data lost if the application terminates
	unexpectedly. It is not without cost, though. Each time it is used, it may cause a noticeable
	delay in the application, especially for large files. Unless the data that you are writing
	is extremely critical, the flush() method should only be used intermittently.
	
	***Marking the Stream
	The InputStream and Reader classes include mark(int) and reset() methods to move
	the stream back to an earlier position. Before calling either of these methods, you should
	call the markSupported() method, which returns true only if mark() is supported. Not
	all java.io input stream classes support this operation, and trying to call mark(int)
	or reset() on a class that does not support these operations will throw an exception at
	runtime.
	
	Once you’ve verified that the stream can support these operations, you can call
	mark(int) with a read-ahead limit value. You can then read as many bytes as you want up
	to the limit value. If at any point you want to go back to the earlier position where you last
	called mark(), then you just call reset() and the stream will “revert” to an earlier state. In
	practice, it’s not actually putting the data back into the stream but storing the data that was
	already read into memory for you to read again. Therefore, you should not call the mark()
	operation with too large a value as this could take up a lot of memory.
	
	***Skipping over Data
	The InputStream and Reader classes also include a skip(long) method, which as you
	might expect skips over a certain number of bytes. It returns a long value, which indicates
	the number of bytes that were actually skipped. If the return value is zero or negative, such
	as if the end of the stream was reached, no bytes were skipped.
	
	Working with Streams
	--------------------
	
	------------------------------------------------------
	[[[The FileInputStream and FileOutputStream Classes]]]
	------------------------------------------------------
	
	The first stream classes that we are going to discuss in detail are the most basic file stream
	classes, FileInputStream and FileOutputStream. They are used to read bytes from a file or
	write bytes to a file, respectively. These classes include constructors that take a File object
	or String, representing a path to the file.
	
	The data in a FileInputStream object is commonly accessed by successive calls to the
	read() method until a value of -1 is returned, indicating that the end of the stream—in this
	case the end of the file—has been reached. Although less common, you can also choose to
	stop reading the stream early just by exiting the loop, such as if some search String is found.
	
	When reading a single value of a FileInputStream instance, the read() method 
	returns a primitive int value rather than a byte value. 
	It does this so that it has an additional value available to be returned, specifically -1, 
	when the end of the file is reached. If the class did return a byte instead of an int,
	there would be no way to know whether the end of the file had been reached
	based on the value returned from the read() method, since the file could
	contain all possible byte values as data. 
	For compatibility, the FileOutputStream also uses int instead of byte 
	for writing a single byte to a file.
	
	The FileInputStream class also contains overloaded versions of the read() method,
	which take a pointer to a byte array where the data is written.
	The method returns an integer value indicating how many bytes can be read into the byte array.
*/
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			int intReaded = fileInputStream.read();
			System.out.println(intReaded);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
/**
 	--------------------------------------------------------------
	[[[The BufferedInputStream and BufferedOutputStream Classes]]]
	--------------------------------------------------------------
	page 422
	
	[[[The FileReader and FileWriter classes]]]
	The FileReader and FileWriter classes, along with their associated buffer classes, are
	among the most convenient classes in the java.io API, in part because reading and writing
	text data are among the most common ways that developers interact with files.
	Like the FileInputStream and FileOutputStream classes, the FileReader and
	FileWriter classes contain read() and write() methods, respectively. These methods
	read/write char values instead of byte values; although similar to what you saw with
	streams, the API actually uses an int value to hold the data so that -1 can be returned if
	the end of the file is detected. The FileReader and FileWriter classes contain other methods
	that you saw in the stream classes, including close() and flush(), the usage of which
	is the same.
	
	Comparing the Two Copy Applications
	PAGE 426
	...We would also have to write code to detect and process the character encoding. The
	character encoding determines how characters are encoded and stored in bytes and later read
	back or decoded as characters. Although this may sound simple, Java supports a wide variety
	of character encodings, ranging from ones that may use one byte for Latin characters, UTF-8
	and ASCII for example, to using two or more bytes per character, such as UTF-16 .
	
	Character encoding in Java
	--------------------------
	In Java, the character encoding can be specifi ed using the Charset class by passing a
	name value to the static Charset.forName() method, such as in the following examples:
		Charset usAsciiCharset = Charset.forName("US-ASCII");
		Charset utf8Charset = Charset.forName("UTF-8");
		Charset utf16Charset = Charset.forName("UTF-16");
	Java supports numerous character encodings, each specifi ed by a different standard name value.
	
	The key point here is that although you can use InputStream / OutputStream instead
	of Reader / Writer to read and write text files, it is inappropriate to do so. 
	Recall that the character stream classes were created for convenience, and you should certainly take
	advantage of them when working with text data.
	
	----------------------------------------------------------
	[[[The ObjectInputStream and ObjectOutputStream Classes]]]
	----------------------------------------------------------
	Throughout this book, we have been managing our data model using classes, so it makes sense
	that we would want to write these objects to disk. The process of converting an in-memory
	object to a stored data format is referred to as serialization , with the reciprocal process of
	converting stored data into an object, which is known as deserialization . In this section, we
	will show you how Java provides built-in mechanisms for serializing and deserializing streams
	of objects directly to and from disk, respectively.
	
	Although understanding serialization is important for using
	ObjectInputStream and ObjectOutputStream , we should mention that
	Oracle has a long history of adding and removing serialization from the list
	of exam objectives. Please check the latest list of objectives prior to taking
	the exam to determine if it is present.
	
	***The Serializable Interface
	
		
 */
		
		
		
	}
	
	public static void main(String[] args) {
		new JavaIO();
	}

}
