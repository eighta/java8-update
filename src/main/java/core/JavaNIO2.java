package core;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.ProviderNotFoundException;
import java.nio.file.spi.FileSystemProvider;

public class JavaNIO2 {

	{
/**
	java.nio version 2 API, or NIO.2 for short, to interact with files. 
	NIO.2 is an acronym that stands for the second version of the 
	Nonblocking Input/Output API, and it is sometimes referred to as the “New I/O.”
		
	Introducing NIO.2
	-----------------
	In Java, file I/O has undergone a number of revisions over the years. 
	
	The first version of file I/O available in Java was the java.io API.
	
	Java introduced a replacement for java.io streams in Java 1.4 called 
	Non-blocking I/O, or NIO for short. The NIO API introduced the concepts of buffers and channels in
	place of java.io streams. The basic idea is that you load the data from a file channel into
	a temporary buffer that, unlike byte streams, can be read forward and backward without
	blocking on the underlying resource.
	
	Java 7 introduced the NIO.2 API. While the NIO API was intended as a replacement for
	java.io streams, the NIO.2 API is actually a replacement for the java.io.File class and
	related interactions that we discussed in "JavaIO.java"
	
	The goal of the NIO.2 API implementation is to provide a more intuitive, 
	more featurerich API for working with files.
	
	Introducing Path
	----------------
	The java.nio.file.Path interface, or Path interface for short, is the primary entry point
	for working with the NIO.2 API. A Path object represents a hierarchical path on the
	storage system to a file or directory. In this manner, Path is a direct replacement for the
	legacy java.io.File class, and conceptually it contains many of the same properties. 
	For example, both File and Path objects may refer to a file or a directory. 
	Both also may refer to an absolute path or relative path within the file system. 
	As we did in "JavaIO.java" and continue to do in this chapter, for simplicity’s sake, 
	we often refer to a directory reference as a file record since it is stored in the file system with similar properties.
	
	Unlike the File class, the Path interface contains support for symbolic links. A symbolic
	link is a special file within an operating system that serves as a reference or pointer to another
	file or directory. In general, symbolic links are transparent to the user, as the operating system
	takes care of resolving the reference to the actual file. The NIO.2 API includes full support
	for creating, detecting, and navigating symbolic links within the file system.
	
	Creating Instances with Factory and Helper Classes
	--------------------------------------------------
	The NIO.2 API makes good use of the factory pattern as discussed in Chapter 2, “Design
	Patterns and Principles.” Remember that a factory class can be implemented using static
	methods to create instances of another class. For example, you can create an instance of a
	Path interface using a static method available in the Paths factory class. 
	Note the "s" at the end of the Paths class to distinguish it from the Path interface.
	
	NIO.2 also includes helper classes such as java.nio.file.Files, whose primary purpose
	is to operate on instances of Path objects. Helper or utility classes are similar to factory classes
	in that they are often composed primarily of static methods that operate on a particular
	class. They differ in that helper classes are focused on manipulating or creating new objects
	from existing instances, whereas factory classes are focused primarily on object creation.
	You should become comfortable with this paradigm, if you are not already, as most
	of your interactions with the NIO.2 API will require accessing at least two classes: 
	an interface and a factory or helper class.
	
	FileSystems ->(create)-> FileSystem
	FileSystem and Paths ->(create)-> Path
	Files ->(uses)-> Path
	Path ->(convert to/from)-> File or URI
	
	Creating Paths
	--------------
	Since Path is an interface, you need a factory class to create instances of one. The NIO.2
	API provides a number of classes and methods that you can use to create Path objects,
	
	Using the Paths Class
	---------------------
	The simplest and most straightforward way to obtain a Path object is using the
	java.nio.files.Paths factory class, or Paths for short. To obtain a reference to a file or
	directory, you would call the static method Paths.getPath(String) method,
	
 */
		Path path1 = Paths.get("pandas/cuddly.png");	
		System.out.println(path1);
		
		Path path1s = Paths.get("pandas","cuddly.png");
		Path path2s = Paths.get("c:","zooinfo","November","employees.txt");
		System.out.println(path2s);
		Path path3s = Paths.get("/","home","zoodirector");
		
/**
 	Another way to construct a Path using the Paths class is with a URI value. A uniform
	resource identifier (URI) is a string of characters that identify a resource. It begins with
	a schema that indicates the resource type, followed by a path value. Examples of schema
	values include file://, http://, https://, and ftp://. The java.net.URI class is used to
	create and manage URI values.
 */
		
		//LOCAL FILE
		URI uri = null;
		try {
			
			uri = new URI("file://pandas/cuddly.png");
			Path path1URI = Paths.get(uri);
			System.out.println(path1URI);
			
			//Exception in thread "main" java.lang.IllegalArgumentException: URI path component is empty
			//URI _uri = new URI("file://pandas.png");
			//Path file1 = Paths.get(_uri);
			
			
			
		} catch (URISyntaxException e) {
			System.out.println("exception...");
			e.printStackTrace();
		}
		
		
		
		//HTTP
		try {
			URI uri2 = new URI("http://www.wiley.com");
			System.out.println(uri2);
			
			FileSystemProvider.installedProviders()
				//.forEach(System.out::println);
				.forEach(provider->{
					System.out.println(provider.getScheme());
				});
			
			//Exception in thread "main" java.nio.file.FileSystemNotFoundException: Provider "http" not installed
			//Path path4URIHTTP = Paths.get(uri2);
			//System.out.println(path4URIHTTP);
			
			//Path path5 = Paths.get(
			//		new URI("ftp://username:password@ftp.the-ftp-server.com"));
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		
		Path path3 = FileSystems.getDefault().getPath("/home/zoodirector");
		System.out.println(path3);
		
		try {
			FileSystem fileSystem = FileSystems.getFileSystem(
					new URI("http://www.selikoff.net"));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}catch (ProviderNotFoundException e) {
			//e.printStackTrace();
		}
		
		//Working with Legacy File Instances
		File file = new File("pandas/cuddly.png");
		Path path = file.toPath();
					path.toFile();
					
		//Interacting with Paths and Files
					path.toUri();		
		
/**
	Viewing the Path with toString(), getNameCount(), and getName()
	---------------------------------------------------------------
	The Path interface contains three methods to retrieve basic information about the
	path representative. The first method, toString(), returns a String representation of
	the entire path. In fact, it is the only method in the Path interface to return a String.
	Most of the other methods that we will discuss in this section return a new Path
	object.
	
	The second and third methods, getNameCount() and getName(int), are often
	used in conjunction to retrieve the number of elements in the path and a reference to
	each element, respectively. For greater compatibility with other NIO.2 methods, the
	getName(int) method returns the component of the Path as a new Path object rather
	than a String.
	The following sample code uses these methods to retrieve path data:
*/
		path = Paths.get("c:/Yesta/Java8/Preparacion.txt");
		System.out.println("basicPath: " + path.toString());

		for(int i=0; i<path.getNameCount(); i++) {
			System.out.println(" Element "+i+" is: "+path.getName(i));
		}
/**
	 For the exam, you should be aware that the getName(int) method is zero-indexed, 
	 with the file system root excluded from the path components.
	 
	Accessing Path Components with getFileName(), getParent(), and getRoot()
	-------------------------------------------------------------------------
	The Path interface contains numerous methods for retrieving specific subelements of a Path
	object, returned as Path objects themselves. The first method, getFileName(), returns a Path
	instance representing the filename, which is the farthest element from the root. Like most
	methods in the Path interface, getFileName() returns a new Path instance rather than a String.
	
	The next method, getParent(), returns a Path instance representing the parent path or
	null if there is no such parent. If the instance of the Path object is relative, this method will
	stop at the top-level element defined in the Path object. In other words, it will not traverse
	outside the working directory to the file system root.
	The last method, getRoot(), returns the root element for the Path object or null if the
	Path object is relative.
 */
		System.out.println("Filename is: "+path.getFileName());
		System.out.println("Root is: "+path.getRoot());
		Path currentParent = path;
		while((currentParent = currentParent.getParent()) != null) {
		System.out.println(" Current parent is: "+currentParent);
		}
/**
	Checking Path Type with isAbsolute() and toAbsolutePath()
	---------------------------------------------------------
	The Path interface contains two methods for assisting with relative and absolute
	paths. The first method, isAbsolute(), returns true if the path the object references
	is absolute and false if the path the object references is relative. As discussed earlier
	in this chapter, whether a path is absolute or relative is often file system dependent,
	although we, like the exam writers, adopt common conventions for simplicity
	throughout the book.
	
	The second method, toAbsolutePath(), converts a relative Path object to an absolute
	Path object by joining it to the current working directory. If the Path object is already
	absolute, then the method just returns an equivalent copy of it.
	The following code snippet shows usage of both of these methods:		
 */
		System.out.println("Path is Absolute? "+path.isAbsolute());
		System.out.println("Absolute Path: "+path.toAbsolutePath());
		
		path = Paths.get("relative/file.txt");
		System.out.println(path.toAbsolutePath()== path);

/**
	Creating a New Path with subpath()
	----------------------------------
	The method subpath(int,int) returns a relative subpath of the Path object, referenced
	by an inclusive start index and an exclusive end index. It is useful for constructing a new
	relative path from a particular parent path element to another parent path element, as
	shown in the following example:
	
 */
		path = Paths.get("/mammal/carnivore/raccoon.image");
		System.out.println("Path is: "+path);
		System.out.println("Subpath from 0 to 3 is: "+path.subpath(0,3));
		System.out.println("Subpath from 1 to 3 is: "+path.subpath(1,3));
		System.out.println("Subpath from 1 to 2 is: "+path.subpath(1,2));
		//System.out.println("Subpath from 0 to 0 is: "+path.subpath(0,0));
		//The example throws an exception since the start and end indexes are
		//the same, leading to an empty path value.

/**
	Using Path Symbols
	------------------
	Many file systems support paths that contain relative path information in the form of path
	symbols. For example, you might want a path that refers to the parent directory, regardless
	of what the current directory is. In this scenario, the double period value .. can be used to
	reference the parent directory. In addit
	
	For example, the path value ../bear.txt refers to a file named bear.txt in the parent
	of the current directory. Likewise, the path value ./penguin.txt refers to a file named
	penguin.txt in the current directory. These symbols can also be combined for greater
	effect. For example, ../../lion.data refers to a file lion.data that is two directories up
	from the current working directory.
 */
		path = Paths.get("..");
		System.out.println(path.toAbsolutePath());
		
	//Deriving a Path with relativize()
		
		path1 = Paths.get("parent1/fish.txt");
		Path path2 = Paths.get("grandParent2/parent2/birds.txt");
		
		System.out.println(path1);
		System.out.println(path1.relativize(path2));
		
		System.out.println(path2);
		System.out.println(path2.relativize(path1));
		
	//Joining Path Objects with resolve()
		Path root = Paths.get("c:/");
		Path relativePath = Paths.get("Yesta/Java8/Preparacion.txt");
		System.out.println( root.resolve(relativePath) );
		System.out.println( relativePath.resolve(root) );
		
		//Exception NO SE PUEDEN realatize tipos diferentes de Path (Absoluto,Relativo)
		//System.out.println( root.relativize(relativePath) );
		//System.out.println( relativePath.relativize(root) );
		
	//Cleaning Up a Path with normalize() [NO SE ENTIENDE, NO FUNCIONA MUY BIEN EN WINDOWS page470]
		Path pathNormal1 = Paths.get("e:/data");
		Path pathNormal2 = Paths.get("e:/user/home");
		
		System.out.println(pathNormal1.relativize(pathNormal2));
		System.out.println(pathNormal1.relativize(pathNormal2).normalize());
		
		System.out.println(pathNormal2.relativize(pathNormal1));
		System.out.println(pathNormal2.relativize(pathNormal1).normalize());
		
/**
	Checking for File Existence with toRealPath()
	---------------------------------------------
	The toRealPath(Path) method takes a Path object that may or may not point to an
existing file within the file system, and it returns a reference to a real path within the file
system. It is similar to the toAbsolutePath() method in that it can convert a relative path
to an absolute path, except that it also verifies that the file referenced by the path actually
exists, and thus it throws a checked IOException at runtime if the file cannot be located. It
is also the only Path method to support the NOFOLLOW_LINKS option.
The toRealPath() method performs additional steps, such as removing redundant path
elements. In other words, it implicitly calls normalize() on the resulting absolute path.
Let’s say that we have a file system in which we have a symbolic link from food.source
to food.txt, as described in the following relationship:
	/zebra/food.source → /horse/food.txt

		
 */
		Path path4 = Paths.get("/zebra/food.source");
		System.out.println("root: " + path4.getRoot());
		System.out.println("root in Windows: " + Paths.get("c:\\Yesta\\file.txt").getRoot());
		
		
		Path realPathReference, realPath=null;
		try {
			//Path realPath = path4.toRealPath();
			//System.out.println(realPath.getRoot());
			//java.nio.file.NoSuchFileException: C:\zebra\food.source
			//TRANSFORMACION AUTOMATICA DE: "/" a "C:"
			realPathReference = Paths.get("C:\\Yesta\\content_disco_duro_500");
			realPath = realPathReference.toRealPath();
			System.out.print("realPathReference and realPath are equals: ");
			System.out.println(realPathReference == realPath);
		
			
			Path currentWorkingDirectory = Paths.get(".").toRealPath();
			System.out.println("currentWorkingDirectory: " + currentWorkingDirectory);
			
			//we have to catch IOException, since unlike the toAbsolutePath() method,
			//the toRealPath() method interacts with the file system to check if the path is valid.
		} catch (IOException e) {
			e.printStackTrace();
		}

		Path absolutePath = realPath.toAbsolutePath();
		System.out.print("realPath and absolutePath are equals: ");
		System.out.println(realPath == absolutePath);
		
		Path _pathRelative = Paths.get("bla/bla.relative");
		Path _absolutPath = _pathRelative.toAbsolutePath();
		System.out.print("pathRelative and absolutPath are equals: ");
		System.out.println(_pathRelative == _absolutPath);
/**		
	Interacting with Files
	----------------------
	Great! We now have access to a Path object, and we can fi nd out a ton of information
	about it, but what can we do with the fi le it references? For starters, many of the same
	operations available in java.io.File are available to java.nio.file.Path via a helper
	class called java.nio.file.Files , or Files for short. Unlike the methods in the Path and
	Paths class, most of the options within the Files class will throw an exception if the fi le to
	which the Path refers does not exist.
*/	
		
		Path source = null;
		OutputStream out = null;
		try {
			Files.copy(source, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		new JavaNIO2();
	}
}
