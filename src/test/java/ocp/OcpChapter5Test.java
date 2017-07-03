package ocp;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class OcpChapter5Test {

	public static void main(String[] args) {
		
		Locale locale1 = new Locale("zz");
		ResourceBundle rb1 = ResourceBundle.getBundle("Zoo",locale1);
		System.out.println(rb1.getString("open"));
		
		
		LocalDate date2 = LocalDate.now();
		System.out.println(DateTimeFormatter.ISO_DATE.format(date2));
		System.out.println(DateTimeFormatter.ISO_LOCAL_DATE.format(date2));
		
		LocalDate.parse("2017-07-02",DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDate date1 = LocalDate.parse("2018-04-01", DateTimeFormatter.ISO_LOCAL_DATE);
		
		//exception
		//LocalDate dateWrong = LocalDate.of(2018, Month.APRIL, 40);
		
		//XXX OJO OPERACIONES SOBRE FECHAS SON INMUTABLES
		LocalDate date30 = LocalDate.of(2018, Month.APRIL, 30);
		System.out.println(date30);
		date30.plusDays(2);
		date30.plusYears(3);
		System.out.println(date30);
		
		LocalDateTime dateTime = LocalDateTime.of(2015, 5, 10, 11, 22, 33);
		Period p = Period.of(1, 2, 3);
		
		@SuppressWarnings("static-access")
		Period p2k = Period.ofDays(1).ofYears(2);
		System.out.println(p2k);
		
		LocalDateTime d11 = LocalDateTime.of(2015, 5, 10, 11, 22, 33);
		DateTimeFormatter f11 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
		System.out.println(f11.format(d11));
		
		
		LocalDate date13 = LocalDate.of(2016, Month.MARCH, 13);
		LocalTime time13 = LocalTime.of(1, 30);
		ZoneId zone13 = ZoneId.of("US/Eastern");
		ZonedDateTime dateTime1_13 = ZonedDateTime.of(date13, time13, zone13);
		ZonedDateTime dateTime2_13 = dateTime1_13.plus(1, ChronoUnit.HOURS);
		
		System.out.println(dateTime1_13);
		System.out.println(dateTime2_13);
		
		long hours_13 = ChronoUnit.HOURS.between(dateTime1_13, dateTime2_13);
		System.out.println(hours_13);
		int clock1_13 = dateTime1_13.getHour();
		int clock2_13 = dateTime2_13.getHour();
		System.out.println(clock1_13);
		System.out.println(clock2_13);
		
		String m1_15 = Duration.of(1, ChronoUnit.MINUTES).toString();
		String m2_15 = Duration.ofMinutes(1).toString();
		String s_15 = Duration.of(60, ChronoUnit.SECONDS).toString();
		
		System.out.println(m1_15);
		System.out.println(m2_15);
		System.out.println(s_15);
		
		System.out.println(m1_15 == m2_15);
		
		String d_15 = Duration.ofDays(3).toString();
		String p_15 = Period.ofDays(1).toString();
		System.out.println(d_15);
		System.out.println(p_15);
		
		Instant ofEpochSecond_16 = Instant.ofEpochSecond(0);
		ZonedDateTime atZone_16 = ofEpochSecond_16.atZone(ZoneId.systemDefault());
		System.out.println(atZone_16);
		
		LocalTime t16 = LocalTime.now();
		LocalDate d16 = LocalDate.now();
		LocalDateTime dt16 = LocalDateTime.now();
		ZonedDateTime zdt16 = ZonedDateTime.now();
		
		
		//Manipulando el Offset
		OffsetDateTime odt = OffsetDateTime.now();
		System.out.println(odt);
		ZoneOffset offset = odt.getOffset();
		System.out.println(offset);
		
		ZoneOffset offset2 = zdt16.getOffset();
		System.out.println(offset.equals(offset2));

		//Convert 2 Instant
		dt16.toInstant(offset2);
		zdt16.toInstant();
		
		//Review 1
		System.out.println("rv_1");
		ZoneId zi_rv1 = ZoneId.of("US/Eastern");
		ZonedDateTime zdt_rv1 = ZonedDateTime.of(2016, 3, 13, 1, 0, 0, 0, zi_rv1);
		System.out.println(zdt_rv1);
		
		for(int i=0;i<5;i++){
			zdt_rv1 = zdt_rv1.plusMinutes(15);
			System.out.println(zdt_rv1);
		}
		
		//Review 2
		System.out.println("rv_2");
		ZonedDateTime zdt_rv2 = ZonedDateTime.of(2016, 11, 6, 1, 0, 0, 0, zi_rv1);
		System.out.println(zdt_rv2);
		
		for(int i=0;i<5;i++){
			zdt_rv2 = zdt_rv2.plusMinutes(15);
			System.out.println(zdt_rv2);
		}
		//Review 3
		System.out.println("rv_3");
		System.out.println(ZoneId.systemDefault());

		LocalDate ld_rv3 = LocalDate.of(2016, Month.MARCH, 13);
		LocalTime lt_rv3 = LocalTime.of(1, 0);
		LocalDateTime ldt_rv3 = LocalDateTime.of(ld_rv3, lt_rv3);
		System.out.println(ldt_rv3);
		ZonedDateTime zdt_rv3 = ZonedDateTime.of(ld_rv3,lt_rv3, zi_rv1);
		System.out.println(zdt_rv3);
		
		//Review 4
		System.out.println("rv_4");
		ZonedDateTime zdt_rv4_a = ZonedDateTime.of(2016, 3, 13, 1, 30, 0, 0, zi_rv1);
		ZonedDateTime zdt_rv4_b = zdt_rv4_a.plus(1, ChronoUnit.HOURS);
		System.out.println(zdt_rv4_a);
		System.out.println(zdt_rv4_b);
		
		long rv_4_hours = ChronoUnit.HOURS.between(zdt_rv4_a, zdt_rv4_b);
		System.out.println(rv_4_hours);
		int clock1_rv4 = zdt_rv4_a.getHour();
		System.out.println(clock1_rv4);
		int clock2_rv4 = zdt_rv4_b.getHour();
		System.out.println(clock2_rv4);
		
		//Review 5
		ZoneId zone_rv5 = ZoneId.of("US/Eastern");
		LocalDate date_rv5 = LocalDate.of(2017, 2, 29);
		LocalTime time1_rv5 = LocalTime.of(2, 15);
		ZonedDateTime a_rv5 = ZonedDateTime.of(date_rv5, time1_rv5, zone_rv5);
		
		//Review 6
		Properties props_rv_6 = new Properties();
		props_rv_6.getOrDefault("", "default");
		props_rv_6.getProperty("", "default");
		//XXX OJO EL metodo "get" solito no viene enversion sobrecargarga
		//En cambio el getProperty si
		
		//NOT USED
		String.valueOf(date1);
		String.valueOf(dateTime);
		String.valueOf(p);
		String.valueOf(t16);
		String.valueOf(d16);
		String.valueOf(a_rv5);
	}
	
	
}
