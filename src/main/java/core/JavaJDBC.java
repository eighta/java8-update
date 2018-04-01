package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JavaJDBC {

	{
/**
JDBC stands for Java Database Connectivity. 

Introducing Relational Databases
and SQL
Data is information. A piece of data is one fact, such as your first name. A database is an
organized collection of data. In the real world, a file cabinet is a type of database. It has file
folders, each of which contains pieces of paper. The file folders are organized in some way,
often alphabetically. Each piece of paper is like a piece of data. Similarly, the folders on your
computer are like a database. The folders provide organization, and each file is a piece of data.
A relational database is a database that is organized into tables, which consist of rows
and columns. You can think of a table as a spreadsheet. There are two main ways to access
a relational database from Java:
■■ Java Database Connectivity Language (JDBC): Accesses data as rows and columns.
JDBC is the API covered in this chapter.
■■ Java Persistence API (JPA): Accesses data through Java objects using a concept called
object-relational mapping (ORM). The idea is that you don’t have to write as much
code, and you get your data in Java objects. JPA is not on the exam, and therefore it is
not covered in this chapter.
■■ A relational database is accessed through Structured Query Language (SQL).
■■ In addition to relational databases, there is another type of database called a NoSQL
database. This is for databases that store their data in a format other than tables.
NoSQL is out of scope for the exam as well.
■■ In the following sections, we introduce a small relational database that we will be
using for the examples in this chapter and present the SQL to access it. We will also
cover some vocabulary that you need to know.
		
In all of the other chapters of this book, you need to write code and try lots of examples.
This chapter is different. It’s still nice to try out the examples, but you can probably get
the JDBC questions correct on the exam from just reading this chapter and mastering the
review questions.
Luckily, Java comes with an embedded database called JavaDB. JavaDB is a
version of the open source Derby database that comes automatically with the JDK
(http://db.apache.org/derby). To practice for the exam, Derby is sufficient.
		
	Introducing the Interfaces of JDBC
	----------------------------------
	For the exam you need to know four key interfaces of JDBC.
	
	As you know, interfaces need a concrete class to implement them in order to be useful. 
	These concrete classes come from the JDBC driver. Each database has a different
	JAR file with these classes. For example, PostgreSQL’s JAR is called something like
	postgresql-9.4–1201.jdbc4.jar. MySql’s JAR is called something like mysql-connectorjava-
	5.1.36.jar. The exact name depends on the version of the driver JAR.
	
	This driver JAR contains an implementation of these key interfaces along with a number
	of others. The key is that the provided implementations know how to communicate with a
	database. There are different types of drivers; luckily, you don’t need to know about this for
	the exam
	
	Figure 10.2 shows the four key interfaces that you need to know. It also shows that the
	implementation is provided by an imaginary Foo driver JAR. They cleverly stick the name
	Foo in all classes.
	
	You’ve probably noticed that we didn’t tell you what the implementing classes are called
	in any real database. The main point is that you shouldn’t know. With JDBC, you use
	only the interfaces in your code and never the implementation classes directly. In fact, they
	might not even be public classes.
	
	Interfaces in the JDK	Implementation in the driver
	Driver		>>>			FooDriver
	Connection	>>>			FooConnection
	Statement	>>>			FooStatement
	ResultSet	>>>			FooResultSet

	What do these four interfaces do? On a very high level, we have the following:
		Driver: Knows how to get a connection to the database
		Connection: Knows how to communicate with the database
		Statement: Knows how to run the SQL
		ResultSet: Knows what was returned by a SELECT query
	All database classes are in the package java.sql, so we will omit the imports going forward.
	
 */
		
		String url = "jdbc:sqlite::memory:";
		try(Connection conn = DriverManager.getConnection(url,"username","password");){
			System.out.println(conn.getClass());
			createDatabase(conn);
		
			try (	Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery("select name from animal") ) {
			
				while (rs.next())
					System.out.println(rs.getString(1));
			}
/**
 	Connecting to a Database
 	------------------------
	The first step in doing anything with a database is connecting to it. First we will show you
	how to build the JDBC URL. Then we will show you how the exam wants you to get a
	Connection to the database.
	
	Building a JDBC URL
	-------------------
	To access a website, you need to know the URL of the website. To access your email, you
	need to know your username and password. JDBC is no different. In order to access a
	database, you need to know this information about it.
	
	Unlike web URLs, a JDBC URL has a variety of formats. They have three parts in
	common, as shows:
	
		jdbc:postgres://localhost:5432/zoo
	
	jdbc					protocol
	:						colon separator
	postgres				Product/Vendor Name
	//localhost:5432/zoo	Database Specific Connection Details

	The third part typically contains the location and the name of the database. The syntax
	varies. You need to know about the three main parts. You don’t need to memorize the
	vendor-specific part. Phew! You’ve already seen one such URL:

	Other examples are shown here:
		jdbc:postgresql://localhost/zoo
		jdbc:oracle:thin:@123.123.123.123:1521:zoo
		jdbc:mysql://localhost:3306/zoo?profileSQL=true
		
	You can see that each of these begins with jdbc, followed by a colon, and then followed
	by the vendor/product name. After that it varies. Notice how all of them include the location
	of the database, which are localhost, 123.123.123.123:1521, and localhost:3306, respectively.
	Also notice that the port is optional when using the default. Finally, notice that all of
	them include the name of the database, which is zoo.
	
	Getting a Database Connection
	-----------------------------
	
	There are two main ways to get a Connection: DriverManager or DataSource.
	DriverManager is the one covered on the exam. Do not use a DriverManager in code
	someone is paying you to write. A DataSource is a factory, and it has more features than
	DriverManager. For example, it can pool connections or store the database connection info
	outside the application.
	
	The DriverManager class is in the JDK, as it is an API that comes with Java. It uses the
	factory pattern, which means that you call a static method to get a Connection.

	Connection conn = DriverManager.getConnection("jdbc:derby:zoo");
	
	
	The command line tells Java where to find the driver JAR. 
	It also includes the current directory so that Java can find TestConnect itself!
	
	Unless the exam specifies a command line, you can assume that the correct JDBC driver
	JAR is in the classpath. The exam creators explicitly ask about the driver JAR if they want
	you to think about it.
	The nice thing about a factory is that it takes care of the logic of creating a class for you.
	You don’t need to know the name of the class that implements Connection, and you don’t
	need to know how it is created. You are probably a bit curious, though.
	The DriverManager class looks through the classpath for JARs that contain a Driver.
	DriverManager knows that a JAR is a driver because it contains a file called java.sql.Driver
	in the directory META-INF/services. In other words, a driver might contain this information:
	
	META-INF
	−service
	―java.sql.Driver
	
	com
	−wiley
	―MyDriver.class
	
	Inside the java.sql.Driver file is one line. It is the fully qualified package name of the
	Driver implementation class. Remember those four key interfaces? Driver is the first one.
	DriverManager then looks through any drivers it can find to see if they can handle the
	JDBC URL. If so, it creates a Connection using that Driver. If not, it gives up and throws a
	SQLException.

	DataSource
	----------
	In real applications, you should use a DataSource rather than DriverManager to get a
	Connection. For one thing, there’s no reason why you should have to know the database
	password. It’s far better if the database team or another team can set up a data source
	that you can reference. Another reason is that a DataSource maintains a connection
	pool so that you can keep reusing the same connection rather than needing to get a new
	one each time. Even the JavaDoc says DataSource is preferred over DriverManager.


	You might see Class.forName() used in older code before getting a Connection. 
	It looked like this:
 */
			
			try {
				if(false)
					Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

/**
	Class.forName() loads a class. This lets DriverManager use a Driver, even if the JAR
	doesn’t have a META-INF/service/java.sql.Driver file. There’s no harm in including
	Class.forName(), even if the newer driver does have this file.
	When Class.forName() is used, the error about an invalid class occurs on that line and
	throws a ClassNotFoundException:
	
	Having META-INF/service/java.sql.Driver inside the JAR became mandatory with
	JDBC 4.0 in Java 6. Before that, some drivers included it and some didn’t. Table 10.1 sums
	up the current state of affairs.
	
	Obtaining a Statement
	---------------------
	In order to run SQL, you need to tell a Statement about it. 
	Getting a Statement from a Connection is easy:
		Statement stmt = conn.createStatement();
		
	As you will remember, Statement is one of the four core interfaces on the exam. It represents
	a SQL statement that you want to run using the Connection.
	That’s the simple signature. 
	
	There’s another one that you need to know for the exam:
	Statement stmt = conn.createStatement(
						ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
						
	This signature takes two parameters. The first is the ResultSet type, and the other is
	the ResultSet concurrency mode. You have to know all of the choices for these parameters
	and the order in which they are specified. Let’s look at the choices for these parameters.
	
	Choosing a ResultSet Type
	-------------------------
	By default, a ResultSet is in TYPE_FORWARD_ONLY mode. This is what you need most of the
	time. You can go through the data once in the order in which it was retrieved.
	Two other modes that you can request when creating a Statement are 
		TYPE_SCROLL_INSENSITIVE and TYPE_SCROLL_SENSITIVE . 
		
	Both allow you to go through the data in any order. 
	You can go both forward and backward. You can even go to a specific spot in the data. 
	Think of this like scrolling in a browser. You can scroll up and down. 
	You can go to a specific spot in the result.
	
	The difference between TYPE_SCROLL_INSENSITIVE and TYPE_SCROLL_SENSITIVE is what
	happens when data changes in the actual database while you are busy scrolling. 
	With TYPE_SCROLL_INSENSITIVE , you have a static view of what the ResultSet looked like when you
	did the query. 
	
	If the data changed in the table, you will see it as it was when you did the query. 
	With TYPE_SCROLL_SENSITIVE , you would see the latest data when scrolling through
	the ResultSet.
	
	We say “would” because most databases and database drivers don’t actually support the
	TYPE_SCROLL_SENSITIVE mode. That’s right. You have to learn something for the exam that
	you are almost guaranteed never to use in practice.
	If the type you request isn’t available, the driver can “helpfully” downgrade to one that
	is. This means that if you ask for TYPE_SCROLL_SENSITIVE , you will likely get a Statement
	that is TYPE_SCROLL_INSENSITIVE . Isn’t that great? If you’d wanted insensitive, you’d have
	asked for that in the fi rst place!
	
	Choosing a ResultSet Concurrency Mode
	-------------------------------------
	By default, a ResultSet is in CONCUR_READ_ONLY mode. This is what you need most
	of the time. It means that you can’t update the result set. Most of the time, you will
	use INSERT , UPDATE , or DELETE SQL statements to change the database rather than a
	ResultSet.
	
	There is one other mode that you can request when creating a Statement.
	Unsurprisingly, it lets you modify the database through the ResultSet. 
	It is called CONCUR_UPDATABLE.
	
	You have to know read-only mode in detail for the exam. For updatable,
	you only have to know the name and that it is not universally
	supported.

*/
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_FORWARD_ONLY,					
					//ResultSet.TYPE_SCROLL_SENSITIVE OR ResultSet.TYPE_SCROLL_INSENSITIVE
					//java.sql.SQLException: SQLite only supports TYPE_FORWARD_ONLY cursors
					
					ResultSet.CONCUR_READ_ONLY
					//Rejava.sql.SQLException: SQLite only supports CONCUR_READ_ONLY cursorssultSet.CONCUR_UPDATABLE
					//
					);
			
/**
	Executing a Statement
	---------------------
 */
			
			//INSERT UPDATE DELETE
			int result = stmt.executeUpdate("");
			
			//SELECT
			String sql = "select * from species";
			ResultSet rs = stmt.executeQuery(sql);
			
			//AND BOTH (INSERT UPDATE DELETE SELECT)
			sql = "update species set name = '' where name = 'None'";
			boolean isResultSet = stmt.execute(sql);
			if (isResultSet) {
				rs = stmt.getResultSet();
				System.out.println("ran a query");
			} else {
				result = stmt.getUpdateCount();
				System.out.println("ran an update");
			}
			
/**
	PreparedStatement
	-----------------
	On the exam, only Statement is covered. In real life, you should not use Statement directly. 
	You should use a subclass called PreparedStatement. This subclass has three
	advantages: performance, security, and readability.
	
 */
			String sqlSELECT = "select * from animal where name = 'a'";
			result = stmt.executeUpdate(sqlSELECT);
			System.out.println("result executeUpdate ON SELECT: " + result);
			//MISTERIOSAMENTE CON LA BD SELECCIONADA NO OCURRE UNA EXCEPTION
			
			//rs = stmt.executeQuery(sql);
			//java.sql.SQLException: query does not return ResultSet

/**
	Getting Data from a ResultSet
	-----------------------------
			
			ResultSet rs = stmt.executeQuery("select id, name from species");
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
			}
			
	A ResultSet has a cursor, which points to the current location in the data.
			
	basic pages 524-529		
			
	Scrolling ResultSet
	-------------------
	A scrollable ResultSet allows you to position the cursor at any row. In this section, we will
	show you the options for doing so.
	You’ve already learned the next() method. There’s also a previous() method, which
	does the opposite. It moves backward one row and returns true if pointing to a valid row
	of data.		
	
	There are also methods to start at the beginning and end of the ResultSet. 
	The first() and last() methods return a boolean for whether they were successful at finding a row.
	
	The beforeFirst() and afterLast() methods have a return type of void, since it is always
	possible to get to a spot that doesn’t have data. Figure 10.5 shows these methods. You can
	see that beforeFirst() and afterLast() don’t point to rows in the ResultSet.
	
	<<absolute(int) METHOD>> (uses negatives)
	Another method that you need to know is absolute(). It takes the row number to
	which you want to move the cursor as a parameter. A positive number moves the cursor to
	that numbered row. Zero moves the cursor to a location immediately before the first row.
	
	A negative number means to start counting from the end of the ResultSet rather than
	from the beginning. Figure
	
	<<relative(int) METHOD>> (uses negatives)
	Finally, there is a relative() method that moves forward or backward the requested
	number of rows. It returns a boolean if the cursor is pointing to a row with data.
		
	Closing Database Resources
	--------------------------
	As you saw in Chapter 8, “IO,” and Chapter 9, “NIO.2,” it is important to close resources
	when you are finished with them. This is true for JDBC as well. JDBC resources, such as
	a Connection, are expensive to create. Not closing them creates a resource leak that will
	eventually slow down your program.
	
	Dealing with Exceptions
	-----------------------
	SQLException e
	
	System.out.println(e.getMessage());
	System.out.println(e.getSQLState());
	System.out.println(e.getErrorCode());
	
	
 */
			
			
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	
	public static void main(String[] args) {
		new JavaJDBC();
	}
	
	private static void createDatabase(Connection conn) throws SQLException {
		
		try (Statement stmt = conn.createStatement()) {

			stmt.executeUpdate("CREATE TABLE species ("
				+ "id INTEGER PRIMARY KEY, "
				+ "name VARCHAR(255), "
				+ "num_acres DECIMAL)");
			stmt.executeUpdate("CREATE TABLE animal ("
				+ "id INTEGER PRIMARY KEY, "
				+ "species_id integer, "
				+ "name VARCHAR(255), "
				+ "date_born TIMESTAMP)");
			
			stmt.executeUpdate("INSERT INTO species VALUES (1, 'African Elephant', 7.5)");
			stmt.executeUpdate("INSERT INTO species VALUES (2, 'Zebra', 1.2)");
			stmt.executeUpdate("INSERT INTO animal VALUES (1, 1, 'Elsa', '2001−05−06 02:15')");
			stmt.executeUpdate("INSERT INTO animal VALUES (2, 2, 'Zelda', '2002−08−15 09:12')");
			stmt.executeUpdate("INSERT INTO animal VALUES (3, 1, 'Ester', '2002−09−09 10:36')");
			stmt.executeUpdate("INSERT INTO animal VALUES (4, 1, 'Eddie', '2010−06−08 01:24')");
			stmt.executeUpdate("INSERT INTO animal VALUES (5, 2, 'Zoe', '2005−11−12 03:44')");
			
		}
	}
}
