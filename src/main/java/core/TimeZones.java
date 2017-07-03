package core;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class TimeZones {

	{
/**
 	The Java TimeZone class is a class that represents time zones, 
 	and is helpful when doing calendar arithmetics across time zones. 
 	The java.util.TimeZone class is used in conjunction with the java.util.Calendar class.		
 */
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		System.out.println(timeZone);
		
		//Creating a TimeZone Instance
		
		TimeZone timeZoneDefault = TimeZone.getDefault();
		TimeZone timeZoneCopenhagen = TimeZone.getTimeZone("Europe/Copenhagen");
		System.out.println(timeZoneCopenhagen);
		
		Arrays.asList(TimeZone.getAvailableIDs() ).stream().forEach(System.out::println);
		System.out.println(TimeZone.getAvailableIDs().length);
		
		//depreciado
		@SuppressWarnings("deprecation")
		Date jan = new Date(2015, Calendar.JANUARY, 1);
		
		//NOT USED
		String.valueOf(timeZoneDefault);
		String.valueOf(jan);
	}
	
	
	public static void main(String[] args) {
		new TimeZones();
	}
}
