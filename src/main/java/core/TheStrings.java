package core;

public class TheStrings {

	{
/**
	Reviewing the String class
	--------------------------
	
	the String class is final and String objects are immutable
	
	This allows Java to optimize by storing string literals in the string pool.
	
	This also means that you can compare string literals with == .
	However, it is still a good idea to compare with equals(), 
	because String objects created via a constructor or a
	method call will not always match when using comparison with == .
 */
		String s1 = "bunny";
		String s2 = "bunny";
		String s3 = new String("bunny");
		
		//prints true because the s1 and s2 references point to the same literal in the string pool.
		System.out.println(s1 == s2);
		
		System.out.println(s1 == s3);
		System.out.println(s1.equals(s3));
/**
	Since String is such a fundamental class, Java allows using the + operator to combine
	them, which is called concatenation.
	
	Concatenation is a big word, but it just means creating a new String 
	with the values from both original strings.
 */
		String s4 = "1" + 2 + 3;
		String s5 = 1 + 2 + "3";
		System.out.println(s4);
		System.out.println(s5);
		
		//Methods commons
		String s = "abcde ";
		s.trim().length();
		s.charAt(4);
		s.indexOf('e');
		s.indexOf("de");
		s.substring(2, 4).toUpperCase();
		s.replace('a', '1');
		s.contains("DE");
		s.startsWith("a");
		
/**
	Since String is immutable, it is inefficient for when you are updating the value in a
	loop. StringBuilder is better for that scenario. A StringBuilder is mutable, which means
	that it can change value and increase in capacity. If multiple threads are updating the same
	object, you should use StringBuffer rather than StringBuilder .		
 */
		StringBuilder b = new StringBuilder();
		b.append(12345).append('-');
		System.out.println(b.length());
		System.out.println(b.indexOf("-"));
		System.out.println(b.charAt(2));
		
		//reverse the StringBuilder and return a reference to the same object.
		StringBuilder b2 = b.reverse();
		System.out.println(b);
		System.out.println(b == b2);
		
		StringBuilder si = new StringBuilder("abcde");
		si.insert(1, '-');
		System.out.println(si);
		
		si.delete(3, 4);
		System.out.println(si);
		
		
	}
	
	public static void main(String[] args) {
		new TheStrings();
	}
	
}
