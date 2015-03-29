package ru.messageBoard.test;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import ru.messageBoard.model.util.DateFormat;

public class TestDateFormat {

	int year = 2015 - 1900;
	int month = 2;
	int date = 29;
	int hrs = 2;
	int min = 20;
	int sec = 20;
	String s = "2015.03.29 02:20:20";

	/**
	 * @param year
	 *            the year minus 1900.
	 * @param month
	 *            the month between 0-11.
	 * @param date
	 *            the day of the month between 1-31.
	 * @param hrs
	 *            the hours between 0-23.
	 * @param min
	 *            the minutes between 0-59.
	 * @param sec
	 *            the seconds between 0-59.
	 */

	@SuppressWarnings("deprecation")
	@Test
	public void testDateFormat() {

		Date d = new Date(year, month, date, hrs, min, sec);
		System.out.println(DateFormat.format(d));
		assertTrue(DateFormat.format(d).equals(s));
	}
}
