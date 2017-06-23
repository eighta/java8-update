package core;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateAndTime {

	{
/**
	Working with Dates and Times
	----------------------------
	
	You need an import to work with the date and time classes. 
	Most of them are in the java.time package.
	
	Day vs Date
	------------
	In American English, the word date is used to represent two different concepts. 
	Sometimes, it is the month/day/year combination when something happened, 
		such as January1, 2000. 
	Sometimes, it is the day of the month, 
		such as today’s date is the 6th.

	That’s right; the words day and date are often used as synonyms.

	Creating Dates and Times
	------------------------
	
	In the real world, we usually talk about dates and time zones 
	as if the other person is located near us. 
	
	For example, if you say to me "I'll call you at 11:00 on Tuesday morning,"
	we assume that 11:00 means the same thing to both of us. 
	
	But if I live in New York and you live in California, 
	we need to be more specific. California is three hours earlier than
	New York because the states are in different time zones. 
	
	You would instead say "I'll call you at 11:00 EST (eastern standard time) on Tuesday morning."
	
	Java gives you four choices:
	
	-LocalDate
	Contains just a date, no time and no time zone. 
	A good example of LocalDate is your birthday this year. 
 */
		LocalDate birthday = LocalDate.of(1980, 1, 1);

/**
 	-LocalTime
 	Contains just a time—no date and no time zone. 
 	A good example of LocalTime is midnight. It is midnight at the same time every day.
 */
		LocalTime midnight = LocalTime.of(0, 0);
		
/**
	-LocalDateTime
	Contains both a date and time but no time zone. A good example of
	LocalDateTime is "the stroke of midnight on New Year’s Eve."
 */
		LocalDateTime stroke = LocalDateTime.of(birthday, midnight);
		
/**
	-ZonedDateTime
	Contains a date, time, and time zone. 
	A good example of ZonedDateTime is "a conference call at 9:00 a.m. EST." 
	If you live in California, you’ll have to get up really early 
	since the call is at 6:00 a.m. local time!
 */
		int year = 2000;
		int month = 1;
		int dayOfMonth = 31;
		int hour = 15;
		int minute = 30;
		int second = 0;
		int nanoOfSecond = 10;
		ZoneId zone = ZoneId.systemDefault();
		ZonedDateTime conference = ZonedDateTime.of(
				year, month, dayOfMonth, hour, minute, second, nanoOfSecond, zone);
		System.out.println(conference);
		
/**
	Obtain
 */
		System.out.println(LocalDate.now());
		System.out.println(LocalTime.now());
		System.out.println(LocalDateTime.now());
		System.out.println(ZonedDateTime.now());

/**
	The ZonedDateTime adds the time zone off-set and time zone. 
	New York is four time zones away from Greenwich Mean Time (GMT).
	Greenwich Mean Time is a time zone in Europe that is used as time zone zero when
	discussing offsets. You might have also heard of Coordinated Universal Time, which is
	a time zone standard. It is abbreviated as a UTC, as a compromise between the English
	and French names. (That’s not a typo. UTC isn’t actually the proper acronym in either
	language!) UTC uses the same time zone zero as GMT.
	
	PAGE 236

 */
		
/**
 	Date Format
 	In the United States, the month is written before the date.
 */
		
	}
	
	public static void main(String[] args) {
		new DateAndTime();
	}
}
