package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.ProviderNotFoundException;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.util.List;
import java.util.stream.Stream;

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
		
		Path _absolutePath = path.subpath(1,2).toAbsolutePath();
		System.out.println(">>>>>>>>>>>>>>>>>>_absolutePath: " + _absolutePath);
		
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
		System.out.println("<<<<resolve>>>");
/**		
 	Path path = Paths.get("---");
 	Path anotherPath = Paths.get("---");
	Path.resolve(Path anotherPath)

	JAVADOC
	Resolve the given path against this path. 
	If the other parameter is an absolute path then this method trivially returns other. 
	If other is an empty path then this method trivially returns this path. 
	Otherwise this method considers this path to be a directory 
		and resolves the given path against this path. 
		
	In the simplest case, the given path does not have a root component, 
		in which case this method joins the given path to this path 
		and returns a resulting path that ends with the given path. 
		
	Where the given path has a root component then resolution is highly implementation dependent and therefore unspecified.
	
	-NO VERIFICA SI LOS ARCHIVOS EN REALIDAD EXISTEN
	-CUANDO UN PATH SE FORMA CON .. o ., no se evaluan hasta que una operacion lo requiera, y se tratan como talS
	-SI EL PARAMETRO DADO ES UN PATH ABSOLUTO, ENTONCES QUEDA EL ABSOLUTO
*/
		System.out.println(Paths.get("/folder/../file.txt").resolve(Paths.get("./none.txt")) );
		//print: \folder\..\file.txt\.\none.txt
		System.out.println(Paths.get("./none.txt").resolve(Paths.get("/folder/../file.txt")) );
		//print: \folder\..\file.txt
		
		
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
exists, and thus it throws a checked IOException at runtime if the file cannot be located. 
It is also the only Path method to support the NOFOLLOW_LINKS option.
The toRealPath() method performs additional steps, such as removing redundant path elements. 
In other words, it implicitly calls normalize() on the resulting absolute path.
Let’s say that we have a file system in which we have a symbolic link 
from food.source to food.txt, as described in the following relationship:

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
			System.out.println("\nequal:" + realPathReference.equals(realPath) );
			System.out.println("==:" + (realPathReference == realPath) );
		
			
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
		System.out.println("\nequals: " + (_pathRelative.equals(_absolutPath)));
		System.out.println("==: " + (_pathRelative == _absolutPath));
/**		
	Interacting with Files
	----------------------
	Great! We now have access to a Path object, and we can find out a ton of information about it, 
	but what can we do with the file it references? For starters, many of the same
	operations available in java.io.File are available to java.nio.file.Path via a helper
	class called java.nio.file.Files, or Files for short. Unlike the methods in the Path and
	Paths class, most of the options within the Files class will throw an exception if the file to
	which the Path refers does not exist.
*/	
		
		/* THIS WORKs OK!!!
		Path source = Paths.get("c:/Yesta/content_disco_duro_500");
		OutputStream out = CustomFtpClient.ftpOutputStream();
		try {
			//http://www.codejava.net/java-se/networking/ftp/java-ftp-file-upload-tutorial-and-example
			long copy = Files.copy(source, out);
			System.out.println("copy: " + copy);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Files.delete(Paths.get("/vulture/feathers.txt"));
		Files.deleteIfExists(Paths.get("/pigeon"));
		
		*/
		
		
/**
Reading and Writing File Data with newBufferedReader() and newBufferedWriter()
------------------------------------------------------------------------------

The NIO.2 API includes methods for reading and writing file contents using 
java.io streams. In this manner, the NIO.2 API bridges information about streams, 

The first method, Files.newBufferedReader(Path,Charset), reads the file specified
at the Path location using a java.io.BufferedReader object. It also requires a Charset
value to determine what character encoding to use to read the file. You may remember
that we briefly discussed character encoding and Charset in Chapter 8. 
For this chapter, you just need to know that characters can be encoded in bytes in a variety of ways. 
It may also be useful to know that Charset.defaultCharset() can be used to get the default Charset for the JVM.

We now present an example of this method:
*/
		
		path = Paths.get("C:\\Yesta\\content_disco_duro_500");
		System.out.println("real: " + path.toAbsolutePath());
		
		try {
			long size = Files.size(path);
			System.out.println("size: " + size);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
			
		Path __path = Paths.get("C:\\Yesta\\content_disco_duro_500");
		try (BufferedReader reader = Files.newBufferedReader(__path, Charset.forName("ISO-8859-1"))) {
			// Read from the stream
			String currentLine = null;
			while((currentLine = reader.readLine()) != null)
				//System.out.println(currentLine);
				currentLine.trim();
				
		} catch (IOException e) {
			// 	Handle file I/O exception...
			e.printStackTrace();
		}
			
		
		
/**
Understanding File Attributes
-----------------------------
The Files class also provides numerous methods accessing file and
directory metadata, referred to as file attributes. Put simply, metadata is data that describes
other data. In this context, file metadata is data about the file or directory record within
the file system and not the contents of the file.

 */
		System.out.println("Files.isDirectory: " + Files.isDirectory(__path));
		System.out.println("Files.isRegularFile: " + Files.isRegularFile(__path));
		System.out.println("Files.isSymbolicLink: " + Files.isSymbolicLink(__path));
		System.out.println("Files.isReadable: " + Files.isReadable(__path) );
		System.out.println("Files.isExecutable: " + Files.isExecutable(__path) );
		try {
			System.out.println("Files.isHidden: " + Files.isHidden(__path) );
			FileTime lastModifiedTime = Files.getLastModifiedTime(__path);
			System.out.println("lastModifiedTime: " + lastModifiedTime);
			UserPrincipal owner = Files.getOwner(__path);
			System.out.println("owner name: "+ owner.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		FileSystem fs = FileSystems.getDefault();
		UserPrincipalLookupService userPrincipalLookupService = fs.getUserPrincipalLookupService();
		try {
			UserPrincipal eightaUser = userPrincipalLookupService.lookupPrincipalByName("eighta");
			System.out.println("eightaUser: " + eightaUser);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
/**
Improving Access with Views
---------------------------
Up until now, we have been accessing individual file attributes with single method calls.
While this is functionally correct, there are often costs associated with accessing the
file that make it far more efficient to retrieve all file metadata attributes in a single call.
Furthermore, some attributes are file system specific and cannot be easily generalized for all
file systems.
The NIO.2 API addresses both of these concerns by allowing you to construct views
for various file systems in a single method call. A view is a group of related attributes for a
particular file system type. A file may support multiple views, allowing you to retrieve and
update various sets of information about the file.
If you need to read multiple attributes of a file or directory at a time, the performance
advantage of using a view may be substantial. Although more attributes are read than in
a single method call, there are fewer round-trips between Java and the operating system,
whereas reading the same attributes with the previously described single method calls would
require many such trips. In practice, the number of trips between Java and the operating
system is more important in determining performance than the number of attributes read.

That’s not to say that the single method calls we just finished discussing do not have
their applications. If you only need to read exactly one file attribute, then there is little or
no performance difference. They also tend to be more convenient to use given their concise
nature

Understanding Views
To request a view, you need to provide both a path to the file or a directory whose information
you want to read, as well as a class object, which tells the NIO.2 API method which
type of view you would like returned.
The Files API includes two sets of methods of analogous classes for accessing view
information. The first method, Files.readAttributes(), returns a read-only view of the
file attributes. The second method, Files.getFileAttributeView(), returns the underlying
attribute view, and it provides a direct resource for modifying file information.
Both of these methods can throw a checked IOException, such as when the view class
type is unsupported. For example, trying to read Windows-based attributes within a Linux
file system may throw an UnsupportedOperationException.
Table 9.4 lists the commonly used attributes and view classes; note that the first
row is required knowledge for the exam. The DOS and POSIX classes are useful for
reading and modifying operating system–specific properties. They also both inherit
from their respective attribute and view classes. For example, PosixFileAttributes
inherits from BasicFileAttributes, just as DosFileAttributeView inherits from
BasicFileAttributeView, meaning that all of the operations available on the parent class
are available in the respective subclasses.


BasicFileAttributes BasicFileAttributeView Basic set of attributes supported by
all file systems

DosFileAttributes DosFileAttributeView Attributes supported by DOS/
Windows-based systems

PosixFileAttributes PosixFileAttributeView Attributes supported by POSIX
systems, such as UNIX, Linux, Mac,
and so on
 */
		
		try {
			
			//Files.readAttributes(__path, String.class);
			BasicFileAttributes readAttributes = Files.readAttributes(__path, BasicFileAttributes.class);
			
			boolean directory = readAttributes.isDirectory();
			System.out.println("directory: " + directory);
			
			
			Object fileKey = readAttributes.fileKey();
			System.out.println("fileKey:" + fileKey);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		BasicFileAttributeView fileAttributeView = Files.getFileAttributeView(__path, BasicFileAttributeView.class);
		try {
			fileAttributeView.setTimes(null, null, null);
		} catch (IOException e) {
			e.printStackTrace();
		}

/**
Presenting the New Stream Methods
---------------------------------
Prior to Java 8, the techniques used to perform complex file operations in NIO.2, such as
searching for a file within a directory tree, were a tad verbose and often required you to
define an entire class to perform a simple task. When Java 8 was released, new methods
that rely on streams were added to the NIO.2 specification that allow you to perform many
of these complex operations with a single line of code.
Conceptualizing Directory Walking
Before delving into the new NIO.2 stream methods, let’s review some basic concepts about
file systems. When we originally described a directory in Chapter 8, we mentioned that it
was organized in a hierarchical manner. For example, a directory can contain files and other
directories, which can in turn contain other files and directories. Every record in a file system
has exactly one parent, with the exception of the root directory, which sits atop everything.
This is commonly visualized as a tree with a single root node and many branches and
leaves, as shown in Figure 9.2. Notice that this tree is conceptually equivalent to Figure 8.1.


A common task in a file system is to iterate over the descendants of a particular file path,
either recording information about them or, more commonly, filtering them for a specific
set of files. For example, you may want to search a folder and print a list of all of the .java
files. Furthermore, file systems store file records in a hierarchical manner. Generally speaking,
if you want to search for a file, you have to start with a parent directory, read its child
elements, then read their children, and so on.
Walking or traversing a directory is the process by which you start with a parent
directory and iterate over all of its descendants until some condition is met or there are no
more elements over which to iterate. The starting path is usually a relevant directory to the
application; after all, it would be time consuming to search the entire file system if your
application uses only a single directory!
Selecting a Search Strategy
There are two common strategies associated with walking a directory tree: a depth-first
search and a breadth-first search. A depth-first search traverses the structure from the root
to an arbitrary leaf and then navigates back up toward the root, traversing fully down any
paths it skipped along the way. The search depth is the distance from the root to current
node. For performance reasons, some processes have a maximum search depth that is used
to limit how many levels deep the search goes before stopping.
Alternatively, a breadth-first search starts at the root and processes all elements of each
particular depth, or distance from the root, before proceeding to the next depth level. The results
are ordered by depth, with all nodes at depth 1 read before all nodes at depth 2, and so on.
For the exam, you don’t have to understand the details of each search strategy that Java
employs; you just need to be aware that the Streams API uses depth-first searching with a
default maximum depth of Integer.MAX_VALUE.

Walking a Directory
As presented in Chapter 4, Java 8 includes a new Streams API for performing complex
operations in a single line of code using functional programming and lambda expressions.
The first newly added NIO.2 stream-based method that we will cover is one used to
traverse a directory. The Files.walk(path) method returns a Stream<Path> object that
traverses the directory in a depth-first, lazy manner.

By lazy, we mean the set of elements is built and read while the directory is being
traversed. For example, until a specific subdirectory is reached, its child elements are not
loaded. This performance enhancement allows the process to be run on directories with a
large number of descendants in a reasonable manner.

... (EXAMPLE ABOVE(
 */
		
		Path directory = Paths.get("c:\\Yesta");
		System.out.println("dir: " +  Files.isDirectory(directory));
		
		try {
			DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(directory);
			
			/*
			 While browsing the NIO.2 API, you may come across the method Files.newDirectoryStream()
along with the generic object it returns, DirectoryStream<Path>. The method behaves quite
similarly to Files.walk(), except the DirectoryStream<Path> object that it returns does not
inherit from the java.util.stream.Stream class. In other words, despite its name, it is not
actually a stream as described in Chapter 4, and therefore none of the useful stream operations
can be applied.*/
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
		
		
		
		
		
		try {
			//FileAttribute<?> attrs = null;
			//Files.createDirectories(directory/*, attrs*/ );
			//Files.createDirectory(directory);
			if(false) throw new IOException();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Path path500 = Paths.get("C:\\Yesta\\content_disco_duro_500");

//======================
//Class: Path (instance)
//======================

/**METHOD. path.toRealPath(), devuelve el path real del path
-return: Path real
-VALIDA EXISTENCIA DEL ARCHIVO (java.nio.file.NoSuchFileException)
 */
		
		try {
			Path realPathDots = Paths.get("..").toRealPath();
			System.out.println("realPathDots: " + realPathDots);
			
			Path parent = realPathDots.getParent();
			System.out.println("parent: " + parent);
			
			Path absolutePath2 = parent.toAbsolutePath();
			System.out.println("absolutePath2: " + absolutePath2);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
/**METHOD: path.normalize(), elimina los elementos redundates del Path 
-return: Path normalizado
 */
		
		//Path _pathN1 = Paths.get("/path1/../path2");
		//System.out.println("normalize: " + _pathN1.normalize());
		//convert to: \path2
		
		Path _pathN1 = Paths.get("./file.txt");
		System.out.println("normalize: " + _pathN1.normalize());
		//convert to: file.txt
		
		
		
		
//============
//Class: Files
//============
		
/**METHOD: Files.lines(Path instance), DEVUELVE (Stream) LAS LINEAS (CONTENIDO) DE UN ARCHIVO ESPECIFICO
-return: Stream<String>
-is LAZY
-VERIFICA EXISTENCIA DEL ARCHIVO
-TENER CUIDADO CUANDO SE RECORRE, POR AQUELLO DEL Charset
-WARNING ResourceLeak (se esta leyendo un archivo) (BaseStream)

*/
		
		try {
			Stream<String> linesStream = Files.lines(path500, Charset.forName("ISO-8859-1"));
			//linesStream.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
/**METHOD: Files.readAllLines(Path instance), DEVUELVE (List) LAS LINEAS (CONTENIDO) DE UN ARCHIVO ESPECIFICO
-return: List<String>
-is EAGER
-VERIFICA EXISTENCIA DEL ARCHIVO
-TENER CUIDADO CUANDO SE RECORRE, POR AQUELLO DEL Charset

 */
		try {
			List<String> readAllLines = Files.readAllLines(path500, Charset.forName("ISO-8859-1"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
/**METHOD: Files.list(Path instance), DEVUELVE (Stream) LOS ELEMENTOS (Path instance) del directorio especificado
-return: Stream<Path>
-is LAZY 
-VERIFICA EXISTENCIA DEL DIRECTORIO 
-VERIFICA SI EL PATH ES UN DIRECTORIO	
 */
		try {
			Stream<Path> list = Files.list(directory);
			//Stream<Path> list = Files.list(Paths.get("lala"));
			//is analogous to java.io.File.listFiles()
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
/**METHOD: Files.move(Path source, Path target, CopyOption ... options), mueve un archivo (o renombra)
-return: Path target
-VERIFICA EXISTENCIA DEL source
-ByDefault (NO COPY OPTION), VERIFICA EXISTENCIA DEL target (si ya existe un Path con el nombre del target)
--Si se coloca la opcion: StandardCopyOption.ATOMIC_MOVE, se reemplaza el archivo target en caso de existir 
-RENOMBRA UN DIRECTIO (NO IMPORTANDO SI ESTA VACIO solo si el movimiento es el mismo FileStore)
-SI EL source AND target apuntan al mismo nombre, no realiza nada

 */
		
		try {
			Path targetPathMove = Files.move(Paths.get("C:\\Yesta\\Java8\\Preparacion.txt"), Paths.get("C:\\Yesta\\Java8\\PreparacionRENAME.txt")
					,StandardCopyOption.ATOMIC_MOVE, LinkOption.NOFOLLOW_LINKS
					);
			
			targetPathMove = Files.move(Paths.get("C:\\Yesta\\Java8\\PreparacionRENAME.txt"), Paths.get("C:\\Yesta\\Java8\\Preparacion.txt")
					,StandardCopyOption.ATOMIC_MOVE, LinkOption.NOFOLLOW_LINKS
					);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
/**METHOD: Files.copy(...), copia los bytes desde un archivo origen al destino (EXISTEN TRES VERSIONES DEL METODO) 
Files.copy(Path source, OutputStream out)
Files.copy(InputStream in, Path target, CopyOption... options)
Files.copy(Path source, Path target, CopyOption... options)

-return: long cantidad de bytes leidos/escritos
-VERIFICA EXISTENCIA DEL source
-VERIFICA EXISTENCIA DEL target (SI EXISTE LANZA java.nio.file.FileAlreadyExistsException)
 		
 */
		try {
			Files.copy(
					Paths.get("C:\\Yesta\\Java8\\Preparacion.txt"),
					//Paths.get("C:\\Yesta\\Java8\\Persona.class") );
					Paths.get("C:\\Yesta\\Java8\\Preparacion.txt" + System.currentTimeMillis() ),
					StandardCopyOption.COPY_ATTRIBUTES
					);
		} catch (IOException e) {
			e.printStackTrace();
		}
/**METHOD: Files.isSameFile(Path instance1, Path instance2), verica si los paths apuntan al mismo archivo fisico		
-return: boolean si son el mismo archivo o no
-VERIFICA EXISTENCIA DEL pathInstance1
-VERIFICA EXISTENCIA DEL pathInstance2
*/
		
		try {
			boolean sameFile = Files.isSameFile(
					Paths.get("C:\\Yesta\\Java8\\Preparacion.txt"), 
					Paths.get("C:\\Yesta\\Java8\\Preparacion.txt"));
			System.out.println("sameFile: " + sameFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

/**METHOD: Files.find(Path directory, int maxDepth, BiPridicate<Path,BasicFileAttributes> matcher), retorna un Stream para la busqueda 		
-return: Stream<Path>
-IS LAZY
-WARNING ResourceLeak (se esta leyendo el arbol de directorios)
-VERIFICA EXISTENCIA DEL directory or File
*/		
		
		try {
			Stream<Path> findStream = Files.find(Paths.get("C:\\Yesta\\Java8\\Preparacion.txt"), 0, (pathF,attributeF)->attributeF.isRegularFile() );
			System.out.println("findFirst: " + findStream.findFirst().get() );
		}catch(IOException e) {
			e.printStackTrace();
		}
		
/**METHOD: Files.walk(Path directory, int maxDept, FileVisitOption ... options), traverse depth firts, devuelve un Stream para recorrer el Arbol de directorio
-return: Stream<Path>
-IS LAZY
-VERIFICA EXISTENCIA DEL directory or File
-WARNING ResourceLeak (se esta leyendo el arbol de directorios) 		
 */
		try {
			Stream<Path> walk = Files.walk(Paths.get("C:\\Yesta\\Java8\\Preparacion.txt"));
			walk.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			Paths.get("..").toRealPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		System.out.println(new File("/lizard/././actions/../walking.txt").toPath() );
		
		Path normalize = Paths.get(".").normalize();
		System.out.println("normalize:" + normalize);
		System.out.println(normalize.getNameCount());
		
		
	}
	
	public static void main(String[] args) {
		new JavaNIO2();
	}
}
