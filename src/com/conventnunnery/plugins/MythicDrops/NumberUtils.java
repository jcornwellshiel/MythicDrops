package com.conventnunnery.plugins.MythicDrops;

import java.util.Random;

public class NumberUtils {

	protected NumberUtils() {

	}

	/**
	 * Gets double from a string with a fallback double.
	 *
	 * @param string
	 * @param fallBack
	 * @return double from string
	 */
	public static double getDouble(String string, double fallBack) {
		double d;
		try {
			d = Double.parseDouble(string);
		} catch (Exception e) {
			d = fallBack;
		}
		return d;
	}

	/**
	 * Gets int from string with a fallback int.
	 *
	 * @param string
	 * @param fallBack
	 * @return int from string
	 */
	public static int getInt(String string, int fallBack) {
		int i;
		try {
			i = Integer.parseInt(string);
		} catch (Exception e) {
			i = fallBack;
		}
		return i;
	}

	/**
	 * Gets long from string with a fallback long
	 *
	 * @param string
	 * @param fallBack
	 * @return long from string
	 */
	public static long getLong(String string, long fallBack) {
		long i;
		try {
			i = getInt(string, (int) fallBack);
		} catch (Exception e) {
			i = fallBack;
		}
		return i;
	}

	/**
	 * Gets short from string with a fallback long
	 *
	 * @param string
	 * @param fallBack
	 * @return short from string
	 */
	public static short getShort(String string, short fallBack) {
		short s;
		try {
			s = (short) getInt(string, fallBack);
		} catch (Exception e) {
			s = fallBack;
		}
		return s;
	}

	/**
	 * Gets a random integer in a range
	 *
	 * @param i1
	 * @param i2
	 * @return random integer in a range
	 */
	public static int handleRange(int i1, int i2) {
		Random rand = new Random();
		int result;
		int range;
		int loc;
		if (Double.valueOf(i1) > Double.valueOf(i2)) {
			range = (int) ((Double.valueOf(i1) * 100) - (Double.valueOf(i2) * 100));
			loc = (int) (Double.valueOf(i2) * 100);
		} else {
			range = (int) ((Double.valueOf(i2) * 100) - (Double.valueOf(i1) * 100));
			loc = (int) (Double.valueOf(i1) * 100);
		}
		result = (loc + rand.nextInt(range + 1)) / 100;
		return result;
	}

	/**
	 * Gets a random int in a range from a string
	 *
	 * @param string
	 * @param splitter
	 * @param fallback
	 * @return random int in a string range
	 */
	public static int handleRange(String string, String splitter, int fallback) {
		Random rand = new Random();
		int result;
		int range;
		int loc;
		String[] splitString = string.split(splitter);
		if (splitString.length > 1) {
			if (getDouble(splitString[0], 0) > getDouble(splitString[1], 0)) {
				range = (int) ((getDouble(splitString[0], 0) * 100) - (getDouble(
						splitString[1], 0) * 100));
				loc = (int) (getDouble(splitString[1], 0) * 100);
			} else {
				range = (int) ((getDouble(splitString[1], 0) * 100) - (getDouble(
						splitString[0], 0) * 100));
				loc = (int) (getDouble(splitString[0], 0) * 100);
			}
			result = (loc + rand.nextInt(range + 1)) / 100;
			return result;
		}
		return fallback;
	}
}
