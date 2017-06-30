package core;

import static java.time.temporal.ChronoUnit.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;


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
		such as todayâ€™s date is the 6th.

	Thatâ€™s right; the words day and date are often used as synonyms.

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
 	Contains just a timeâ€”no date and no time zone. 
 	A good example of LocalTime is midnight. It is midnight at the same time every day.
 */
		LocalTime midnight = LocalTime.of(0, 0);
		
/**
	-LocalDateTime
	Contains both a date and time but no time zone. A good example of
	LocalDateTime is "the stroke of midnight on New Yearâ€™s Eve."
 */
		LocalDateTime stroke = LocalDateTime.of(birthday, midnight);
		
/**
	-ZonedDateTime
	Contains a date, time, and time zone. 
	A good example of ZonedDateTime is "a conference call at 9:00 a.m. EST." 
	If you live in California, youâ€™ll have to get up really early 
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
		System.out.println("Specific ZonedDateTime: " + conference);
		
/**
	Obtain
 */
		System.out.println("LocalDate.now(): " + LocalDate.now());
		System.out.println("LocalTime.now(): " + LocalTime.now());
		System.out.println("LocalDateTime.now(): " + LocalDateTime.now());
		System.out.println("ZonedDateTime.now(): "  + ZonedDateTime.now());

/**
	The ZonedDateTime adds the time zone off-set and time zone. 
	New York is four time zones away from Greenwich Mean Time (GMT).
	
	Greenwich Mean Time is a time zone in Europe that is used as time zone zero when
	discussing offsets. 
	
	You might have also heard of Coordinated Universal Time, which is a time zone standard. 
	It is abbreviated as a UTC, as a compromise between the English and French names. 
	(Thats not a typo. UTC isnt actually the proper acronym in either language!) 
	UTC uses the same time zone zero as GMT.
	
	UTC
	2015–06–20T07:50+02:00[Europe/Paris]
	2015–06–20T06:50+05:30[Asia/Kolkata]
	
	GMT
	2015–06–20T07:50 GMT-04:00
	2015–06–20T04:50 GMT-07:00
 */
		
/**
 	US Date Format
 	In the United States, the month is written before the date (day).
 */
		//DATES
		LocalDate date1 = LocalDate.of(2015, Month.JANUARY, 20);
		LocalDate date2 = LocalDate.of(2015, 1, 20);
		System.out.println(date1.equals(date2));
		
		//TIMES
		LocalTime time1 = LocalTime.of(6, 15); // hour and minute
		LocalTime time2 = LocalTime.of(6, 15, 30); // + seconds
		LocalTime time3 = LocalTime.of(6, 15, 30, 200); // + nanoseconds
		
		//DATE-TIME
		LocalDateTime dateTime1 = LocalDateTime.of(2015, Month.JANUARY, 20, 6, 15, 30,1);
		LocalDateTime dateTime2 = LocalDateTime.of(date1, time1);
		
		//ZonedDateTime
		ZoneId zoneId = ZoneId.of("US/Eastern");
		
		ZonedDateTime zoned1 = ZonedDateTime.of(2015, 1, 20,6, 15, 30, 200, zoneId);
		ZonedDateTime zoned2 = ZonedDateTime.of(date2, time3, zoneId);
		ZonedDateTime zoned3 = ZonedDateTime.of(dateTime2, zoneId);

		ZoneId.getAvailableZoneIds().stream()
			.sorted().forEach(System.out::println);
		System.out.println(ZoneId.getAvailableZoneIds().size() );
		
/**
	Manipulating Dates and Times
	----------------------------
 */
		LocalTime time = LocalTime.now();
		time.plusNanos(1);
		time.plusSeconds(2);
		time.plusMinutes(3);
		time.plusHours(4);
		
		LocalDate date = LocalDate.of(2014, Month.JANUARY, 20);
		date.plusDays(2);
		date.plusWeeks(1);
		date.plusMonths(1);
		
		//This would bring us to February 29, 2014. 
		//However, 2014 is not a leap year. (2012 and 2016 are leap years.) 
		//Java is smart enough to realize that February 29, 2014, does not exist, 
		//and it gives us February 28, 2014, instead.
		
		date.plusYears(5);
		
		//from enum java.time.temporal.ChronoUnit
		date.plus(3,DAYS);
		
		LocalDateTime dateTime = LocalDateTime.now();
		dateTime.minusNanos(1);
		dateTime.minusSeconds(2);
		dateTime.minusMinutes(3);
		dateTime.minusHours(4);
		dateTime.minusDays(5);
		dateTime.minusWeeks(6);
		dateTime.minusMonths(7);
		dateTime.minusYears(8);

		
//EPOCH 1970-01-01
		LocalDate epochLocalDate = LocalDate.of(1970, Month.JANUARY, 10);
		System.out.println(epochLocalDate.toEpochDay() );
		
		LocalDate beforeEpochLocalDate = LocalDate.of(1969, Month.DECEMBER, 31);
		System.out.println(beforeEpochLocalDate.toEpochDay() );
		
/**
	Working with Periods
 */
		Period.ofDays(1);
		Period.ofWeeks(2);
		Period.ofMonths(3);
		Period.ofYears(4);
		
		int years = 1, months= 2, days=3;
		Period periodYearMonthDay = Period.of(years, months, days);
		
		//El LocalTime no soporta sumar (operar) un period (ya que su minima unidad es un dia) 
		//time.plus(Period.ofDays(1));
		//java.time.temporal.UnsupportedTemporalTypeException: Unsupported unit: Days
		
		date.plus(periodYearMonthDay);
		
		System.out.println(Period.of(1, 2, 3));
		System.out.println(Period.ofWeeks(2));
		System.out.println(Period.of(1,13,33));
		System.out.println(Period.ofWeeks(5));
		
		//java.time.temporal.UnsupportedTemporalTypeException: Unit must not have an estimated duration
		//Duration.of(1, YEARS);
		Duration.of(360, DAYS);
		

/**
 	Working with Durations
 	
 	Period is a day or more of time. There is also
	Duration, which is intended for smaller units of time.
	
	For Duration, you can specify the number of 
		days, hours, minutes, seconds, or nanoseconds.
 */
		
		Duration daily = Duration.ofDays(1);
		Duration hourly = Duration.ofHours(1);
		Duration everyMinute = Duration.ofMinutes(1);
		Duration everyTenSeconds = Duration.ofSeconds(10);
		Duration everyMilli = Duration.ofMillis(1);
		Duration everyNano = Duration.ofNanos(1);
		
		//from enum java.time.temporal.ChronoUnit
		Duration.of(3, SECONDS);
		
		time.plus(everyMilli);
		//date.plus(everyMilli);
		//java.time.temporal.UnsupportedTemporalTypeException: Unsupported unit: Nanos
		dateTime.plus(everyMilli);
		System.out.println(everyTenSeconds);
		
/**
 	ChronoUnit for Differences
 	
 	ChronoUnit is a great way to determine how far apart two Temporal values are. 
 	Temporal includes LocalDate, LocalTime, and so on.
 */
		
		LocalTime one = LocalTime.of(5, 15);
		LocalTime two = LocalTime.of(6, 30);
		LocalDate odate = LocalDate.of(2016, 1, 20);
		System.out.println(ChronoUnit.HOURS.between(one, two)); 
		System.out.println(ChronoUnit.MINUTES.between(one, two)); 
		
		//java.time.DateTimeException
		//System.out.println(ChronoUnit.MINUTES.between(one, odate));
		
/**
 	Working with Instants
 	
 	The Instant class represents a specific moment in time in the GMT time zone.
 */
		Instant now = Instant.now();
		System.out.println(now);
		
		// do something time consuming
		//MiscClass.factorial(18_000);
		
		Instant later = Instant.now();
		
		Duration duration = Duration.between(now, later);
		System.out.println(duration);
		
		//If you have a ZonedDateTime, you can turn it into an Instant:
		LocalDate idate = LocalDate.of(2015, 5, 25);
		LocalTime itime = LocalTime.now();
		ZoneId izone = ZoneId.of("America/Bogota");
		ZonedDateTime izonedDateTime = ZonedDateTime.of(idate, itime, izone);
		Instant instant = izonedDateTime.toInstant(); // 2015–05–25T15:55:00Z
		System.out.println(izonedDateTime);
		System.out.println(instant);
		
		//
		Instant instant1 = Instant.ofEpochSecond(0);
		System.out.println(instant1);
		
		instant.plus(1, NANOS);
		instant.plus(1, MILLIS);
		instant.plus(1, SECONDS);
		instant.plus(1, MINUTES);
		instant.plus(1, HOURS);
		Instant nextDay = instant.plus(1, DAYS);
		//instant.plus(1,WEEKS);	Exception
		//instant.plus(1,MONTHS);	Exception
		
		System.out.println(nextDay);
		
/**
	Accounting for Daylight Savings Time
	
	Some countries observe daylight savings time. This is where the clocks are adjusted by an
	hour twice a year to make better use of the sunlight. Not all countries participate, and
	those that do use different weekends for the change.
	In the United States, we move the clocks an hour ahead in March and move them an
	hour back in November.
 */
		
		LocalDate ddate = LocalDate.of(2016, Month.MARCH, 13);
		LocalTime dtime = LocalTime.of(1, 30);
		ZoneId dzone = ZoneId.of("US/Eastern");
		ZonedDateTime ddateTime = ZonedDateTime.of(ddate, dtime, dzone);
		System.out.println(ddateTime);
		ddateTime = ddateTime.plusHours(1);
		System.out.println(ddateTime);
		
		
	}
	
	public static void main(String[] args) {
		new DateAndTime();
	}
}
