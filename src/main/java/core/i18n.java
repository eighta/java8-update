package core;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class i18n {

	{
/**
	Internationalization and Localization
	-------------------------------------
	Internationalization is the process of designing your program so it can be adapted. This
	involves placing strings in a property file and using classes like DateFormat so that the right
	format is used based on user preferences.

	Localization means actually supporting multiple locales. Oracle defines a locale as “a
	specific geographical, political, or cultural region.”
	You can think of a locale as being like a language and country pairing
	
	Since internationalization and localization are such long words, 
	they are often abbreviated as i18n and l10n
	
	Picking a Locale
	----------------
 */
		Locale locale = Locale.getDefault();
		System.out.println(locale);
		 
		System.out.println(Locale.GERMAN);
		System.out.println(Locale.GERMANY);
		
		System.out.println(new Locale("fr"));
		System.out.println(new Locale("hi", "IN"));
		
/**
 	There’s another way to create a Locale that is more fl exible. The builder design pattern lets
	you set all of the properties that you care about and then build it at the end. This means that
	you can specify the properties in any order.
 */
		Locale l1 = new Locale.Builder()
				.setLanguage("en")
				.setRegion("US")
				.build();
		System.out.println(l1);
		
		
		Locale l2 = new Locale.Builder()
		.setRegion("US")
		.setLanguage("en")
		.build();
		System.out.println(l2);
		
		//When testing a program, you might need to use a Locale other than the default for your computer.
		System.out.println(Locale.getDefault());
		Locale localeDefault = new Locale("fr");
		Locale.setDefault(localeDefault);
		System.out.println(Locale.getDefault());
		
/**
	Using a Resource Bundle
	-----------------------
	A resource bundle contains the local specific objects to be used by a program. 
	It is like a map with keys and values. 
	
	The resource bundle can be in a property fi le or in a Java class.
	
	A property file is a file in a specific format with key/value pairs.
 */
	
		Locale us = new Locale("en", "US");
		printProperties(us);
		
		Locale france = new Locale("fr", "FR");
		printProperties(france);
		
		Locale englishCanada = new Locale("en", "CA");
		System.out.println("=englishCanada=");
		printProperties(englishCanada);
		
		Locale frenchCanada = new Locale("fr", "CA");
		System.out.println("=frenchCanada=");
		printProperties(frenchCanada);
		
		ResourceBundle rb = ResourceBundle.getBundle("Zoo", locale);
		rb.keySet().forEach(System.out::println);
		
/**
	Properties Class
	---------------
	It is like a Map
	Properties has some additional features, including being able to pass a default.
 */
		
		Properties props = new Properties();
		rb.keySet().stream()
			.forEach(k -> props.put(k, rb.getString(k)));
		
		System.out.println(props.getProperty("notReallyAProperty"));
		System.out.println(props.getProperty("notReallyAProperty", "123"));

/**
	Creating a Java Class Resource Bundle
	-------------------------------------
	Most of the time, a property file resource bundle is enough to meet the program’s needs. It
	does have a limitation in that only String values are allowed. Java class resource bundles
	allow any Java type as the value. Keys are strings regardless.
	
	To implement a resource bundle in Java, you create a class with the same name that you
	would use for a property file.
	
	There are two main advantages of using a Java class instead of a property file for a
	resource bundle:
		-You can use a value type that is not a String.
		-You can create the values of the properties at runtime.
 */
		
		Locale spanish = new Locale("es","CO");
		System.out.println(spanish);
		//XXX OJO CON THIS
		Locale.setDefault(spanish);
		
		//TAKES DE DAFAULT LOCALE
		ResourceBundle rb_es = ResourceBundle.getBundle("core.Another");
		
		//SPECIFIC LOCALE
		//ResourceBundle rb_es = ResourceBundle.getBundle("core.Another",spanish);
		System.out.println(rb_es.getString("open"));
		System.out.println(rb_es.getObject("object"));
		
/**
	Determining Which Resource Bundle to Use
	----------------------------------------
	Picking a resource bundle for French in France with default locale US English
	
	1 Zoo_fr_FR.java
	2 Zoo_fr_FR.properties
	3 Zoo_fr.java
	4 Zoo_fr.properties
	5 Zoo_en_US.java
	6 Zoo_en_US.properties
	7 Zoo_en.java
	8 Zoo_en.properties
	9 Zoo.java
	10 Zoo.properties
	11 If still not found, throw MissingResourceException.

	Handling Variables Inside Resource Bundles
	------------------------------------------
	In real programs, it is common to substitute variables in the middle of a resource bundle
	string. The convention is to use a number inside brackets such as {0}. Although Java
	resource bundles don’t support this directly, the MessageFormat class does.
	
	For example, suppose that we had this property defined:
		helloByName=Hello, {0}
		
	In Java, we can read in the value normally. After that, we can run it through the
	MessageFormat class to substitute the parameters. As you might guess, the second
	parameter to format() is a varargs one. This means that you can pass many parameters.
 */
		ResourceBundle rsPlain = ResourceBundle.getBundle("plain");
		System.out.println(rsPlain.getString("helloByName"));

		String _2format = rsPlain.getString("helloByName");
		String formatted = MessageFormat.format(_2format, "Tammy");
		System.out.println(formatted);

/**
	Formatting Numbers
	------------------
	Resource bundles are great for content that doesn’t change. Text like a welcome greeting is
	pretty stable. When talking about dates and prices, the formatting varies and not just the
	text. Luckily, the java.text package has classes to save the day. The following sections
	cover how to format numbers, currency, and dates.
 */
		
		//Factory methods to get a NumberFormat
		
		//A general purpose formatter 
		NumberFormat.getInstance();
		NumberFormat.getInstance(locale);
		
		//Same as getInstance 
		NumberFormat.getNumberInstance();
		NumberFormat.getNumberInstance(locale);
		
		//For formatting monetary amounts
		NumberFormat.getCurrencyInstance();
		NumberFormat.getCurrencyInstance(locale);
		
		//For formatting percentages 
		NumberFormat.getPercentInstance();
		NumberFormat.getPercentInstance(locale);
		
		//Rounds decimal values before displaying (not on the exam)
		NumberFormat.getIntegerInstance();
		NumberFormat.getIntegerInstance(locale);
		
		//Once you have the NumberFormat instance, 
		//you can call format() to turn a number into a String 
		//and parse() to turn a String into a number.
		
		int attendeesPerYear = 3_200_000;
		int attendeesPerMonth = attendeesPerYear / 12;
		System.out.println(NumberFormat.getInstance(Locale.US).format(attendeesPerMonth));
		System.out.println(NumberFormat.getInstance(Locale.GERMANY).format(attendeesPerMonth));
		System.out.println(NumberFormat.getInstance(Locale.CANADA_FRENCH).format(attendeesPerMonth));
		
/**
	In the United States, we use commas to separate
	parts of large numbers. Germans use a dot for this function. French Canadians use neither.
 */
		//Format (NUMBER -> TEXT)
		double price = 48;
		NumberFormat usFormat = NumberFormat.getCurrencyInstance(Locale.US);
		System.out.println(usFormat.format(price));
		
		//Parsing (TEXT -> NUMBER)
		String text = "$48.00";
		Number number = null;
		try {number = usFormat.parse(text);} catch (ParseException e) {e.printStackTrace();}
		System.out.println(number);
		
/**
 	Formatting Dates and Times
 	--------------------------
 	Java provides a class called DateTimeFormatter to help us
	out. Unlike the LocalDateTime class, DateTimeFormatter can be used to format any type of
	date and/or time object. What changes is the format.
 */
		LocalDate date = LocalDate.of(2020, Month.JANUARY, 20);
		System.out.println(date.getDayOfWeek()); // MONDAY
		System.out.println(date.getMonth()); // JANUARY
		System.out.println(date.getYear()); // 2020
		System.out.println(date.getDayOfYear()); // 20
		
		//USING DateTimeFormatter
		//ISO is a standard for dates
		LocalTime time = LocalTime.of(11, 12, 34);
		LocalDateTime dateTime = LocalDateTime.of(date, time);
		System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
		System.out.println(time.format(DateTimeFormatter.ISO_LOCAL_TIME));
		System.out.println(dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		
		//there are some predefined formats that are more useful:
		DateTimeFormatter shortDateTime = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		System.out.println(shortDateTime.format(dateTime)); // 1/20/20
		System.out.println(shortDateTime.format(date)); // 1/20/20
		//throws an exception because a time cannot be formatted as a date.
		//System.out.println(shortDateTime.format(time));
		
		System.out.println(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(dateTime));
		System.out.println(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(dateTime));
		System.out.println(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(dateTime));
		
		//Locale
		System.out.println(Locale.getDefault());
		System.out.println(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(dateTime));
		
		System.out.println(Locale.GERMAN);
		System.out.println(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(Locale.GERMAN).format(dateTime));
		
		System.out.println(Locale.FRANCE);
		System.out.println(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(Locale.FRANCE).format(dateTime));
		
		System.out.println(new Locale("hi"));
		System.out.println(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(new Locale("hi")).format(dateTime));
		
		//predefined formats SHORT and MEDIUM
		DateTimeFormatter shortF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
		DateTimeFormatter mediumF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
		System.out.println(shortF.format(dateTime));
		System.out.println(mediumF.format(dateTime));
		
		//If you don’t want to use one of the predefined formats, you can create your own. 
		//For example, this code spells out the month:
		System.out.println("custom");
		DateTimeFormatter f = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mm").withLocale(Locale.FRANCE);
		System.out.println(dateTime.format(f)); // January 20, 2020, 11:12
		
		//Parsing a Date
		DateTimeFormatter dateFormatParse = DateTimeFormatter.ofPattern("MM dd yyyy");
		LocalDate localDateParser = LocalDate.parse("01 02 2015", dateFormatParse);
		System.out.println(localDateParser);
		//using default formmater
		LocalTime localTimeParser = LocalTime.parse("11:22");
		System.out.println(localTimeParser);
		
		
	}
	
	public static void printProperties(Locale locale) {
		ResourceBundle rb = ResourceBundle.getBundle("Zoo", locale);
		System.out.println(rb.getString("hello"));
		System.out.println(rb.getString("open"));
		
		System.out.println(rb.getString("colons"));
		System.out.println(rb.getString("space"));
		System.out.println(rb.getString("bla"));
		System.out.println(rb.getString("multiple"));
		System.out.println(rb.getString("scape"));
	}
	
	public static void main(String[] args) {
		new i18n();
	}
	
}
