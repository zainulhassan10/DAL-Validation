package com.quarrio.dal.validations;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Zain ul Hassan
 *
 */
public class Utilities {

	private static final String SPACE = " ";
	private static final String EMPTYSTRING = "";

	public static boolean isNotNullOrEmpty(String value) {
		return value != null && !value.isEmpty() && !value.equalsIgnoreCase("null");
	}

	public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
		// Create a list from elements of HashMap
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(hm.entrySet());

		// Sort the list
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		// put data from sorted list to hashmap
		HashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> aa : list) {
			sortedMap.put(aa.getKey(), aa.getValue());
		}
		return sortedMap;
	}

	public static String removeWordFromLine(String line, String replaceWord) {
		if (isNotNullOrEmpty(line)) {
			if (line.contains(replaceWord)) {
				String tempWord = replaceWord + SPACE;
				line = line.replaceAll(tempWord, EMPTYSTRING);
				return line;
			}
		}
		return null;
	}

	public static boolean checkIndexExist(int index, List<String> list) {
		return index >= 0 && index < list.size();
	}

	public static boolean checkStringArrayIndexExist(int index, String[] strArray) {
		return index >= 0 && index < strArray.length;
	}

	public static int countWordInString(String value, String pattren) {
		int i = 0;
		Pattern p = Pattern.compile(pattren);
		Matcher m = p.matcher(value);
		while (m.find()) {
			i++;
		}
		return i;
	}

}
