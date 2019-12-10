package com.quarrio.dal.validations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zain ul Hassan
 *
 */
public class QueryParts {

	private static final String COMMA = ",";
	private static final String SELECT = "select";
	private static final String FROM = "from";
	private static final String WHERE = "where";
	private static final String GROUPBY = "group by";
	private static final String HAVING = "having";
	private static final String ORDERBY = "order by";
	private static final String SPACE = " ";
	private static final String EMPTYSPACE = "";

	public static String[] splitStr(String splitPattren, String queryPart) {
		if (Utilities.isNotNullOrEmpty(splitPattren) && Utilities.isNotNullOrEmpty(queryPart)) {
			String[] value = queryPart.split(splitPattren);
			return value;
		}
		return null;
	}

	public static List<String> getALLClause(String query) {
		HashMap<String, Integer> hm = findExistingClause(query);
		Map<String, Integer> map = Utilities.sortByValue(hm);
		List<String> result = new ArrayList<String>(map.keySet());
		return result;
	}

	public static List<String> listOfSQLClause() {
		List<String> clauseList = new ArrayList<String>();
		clauseList.add(SELECT);
		clauseList.add(FROM);
		clauseList.add(WHERE);
		clauseList.add(HAVING);
		clauseList.add(GROUPBY);
		clauseList.add(ORDERBY);
		return clauseList;
	}

	public static HashMap<String, Integer> findExistingClause(String query) {
		List<String> clauseList = listOfSQLClause();
		HashMap<String, Integer> sortedClauseHP = new HashMap<String, Integer>();
		for (int i = 0; i < clauseList.size(); i++) {
			String findValue = clauseList.get(i);
			List<Integer> result = new ArrayList<Integer>();
			int lastIndex = 0;
			while (lastIndex != -1) {
				lastIndex = query.indexOf(findValue, lastIndex);
				if (lastIndex != -1) {
					result.add(lastIndex);
					lastIndex += 1;
					sortedClauseHP.put(findValue, lastIndex);
				}
			}
		}
		return sortedClauseHP;
	}

	public static ClauseValue validateByQuery(String query) {
		ClauseValue cv = new ClauseValue();
		query = query.replace("\\", "");
		query = query.replace("\"", "");
		query = query.toLowerCase();
//		System.out.println("Actual Query : " + query);
		if (Utilities.isNotNullOrEmpty(query)) {
			List<String> result = new ArrayList<String>();
			result = getALLClause(query);
//			System.out.println("Clause exist in query size : " + result.size() + " ===== " + result);
			if (Utilities.checkIndexExist(1, result) && Utilities.isNotNullOrEmpty(result.get(1))
					&& Utilities.isNotNullOrEmpty(query)) {
				String[] firstValue = query.split(result.get(1));
//				System.out.println("PartOne : " + firstValue[0]);
				List<String> subPartOfQuery = new ArrayList<String>();
				String newValue = Utilities.removeWordFromLine(firstValue[0], SELECT);
//				System.out.println("PartOne : " + newValue);
				// subPartOfQuery = queryFurtherInParts(firstValue[0], "", COMMA);
				subPartOfQuery = queryFurtherInParts(newValue, COMMA);
				cv.setFirstPart(subPartOfQuery);
				if (Utilities.checkStringArrayIndexExist(1, firstValue)) {
					query = firstValue[1];
				}
			}
			if (Utilities.checkIndexExist(2, result) && Utilities.isNotNullOrEmpty(result.get(2))
					&& Utilities.isNotNullOrEmpty(query)) {
				String[] secondValue = query.split(result.get(2));
//				System.out.println("PartTwo : " + secondValue[0]);
				List<String> subPartOfQuery = new ArrayList<String>();
				// subPartOfQuery = queryFurtherInParts(secondValue[0], "", COMMA);
				subPartOfQuery = queryFurtherInParts(secondValue[0], COMMA);
				cv.setSecondPart(subPartOfQuery);
				if (Utilities.checkStringArrayIndexExist(1, secondValue)) {
					query = secondValue[1];
				}
			}
			if (Utilities.checkIndexExist(3, result) && Utilities.isNotNullOrEmpty(result.get(3))
					&& Utilities.isNotNullOrEmpty(query)) {
				String[] thirdValue = query.split(result.get(3));
//				System.out.println("PartThree : " + thirdValue[0]);
				List<String> subPartOfQuery = new ArrayList<String>();
				// subPartOfQuery = queryFurtherInParts(thirdValue[0], "", COMMA);
				subPartOfQuery = queryFurtherInParts(thirdValue[0], COMMA);
				cv.setThirdPart(subPartOfQuery);
				if (Utilities.checkStringArrayIndexExist(1, thirdValue)) {
					query = thirdValue[1];
				}
			}
			if (Utilities.checkIndexExist(4, result) && Utilities.isNotNullOrEmpty(result.get(4))
					&& Utilities.isNotNullOrEmpty(query)) {
				String[] fourthValue = query.split(result.get(4));
//				System.out.println("PartFour : " + fourthValue[0]);
				List<String> subPartOfQuery = new ArrayList<String>();
				// subPartOfQuery = queryFurtherInParts(fourthValue[0], "", COMMA);
				subPartOfQuery = queryFurtherInParts(fourthValue[0], COMMA);
				cv.setFourthPart(subPartOfQuery);
				if (Utilities.checkStringArrayIndexExist(1, fourthValue)) {
					query = fourthValue[1];
				}
			}
			if (Utilities.checkIndexExist(5, result) && Utilities.isNotNullOrEmpty(result.get(5))
					&& Utilities.isNotNullOrEmpty(query)) {
				String[] fifthValue = query.split(result.get(5));
//				System.out.println("PartFive : " + fifthValue[0]);
				List<String> subPartOfQuery = new ArrayList<String>();
				// subPartOfQuery = queryFurtherInParts(fifthValue[0], "", COMMA);
				subPartOfQuery = queryFurtherInParts(fifthValue[0], COMMA);
				cv.setFifthPart(subPartOfQuery);
				if (Utilities.checkStringArrayIndexExist(1, fifthValue)) {
					query = fifthValue[1];
				}
			}
			if (Utilities.isNotNullOrEmpty(query)) {
//				System.out.println("LastPart : " + query);
				cv.setLastPart(query);
			}
		}
		return cv;
	}

	// public static List<String> queryFurtherInParts(String line, String
	// replaceWord, String splitPattren) {
	public static List<String> queryFurtherInParts(String line, String splitPattren) {
		List<String> querySubPartsList = new ArrayList<String>();
		// String newValue = Utilities.removeWordFromLine(line, replaceWord);
		// if (Utilities.isNotNullOrEmpty(newValue) &&
		// Utilities.isNotNullOrEmpty(splitPattren)) {
		String[] value = splitStr(splitPattren, line);
		if (value != null) {
			querySubPartsList = Arrays.asList(value);
		}
		// }
		return querySubPartsList;
	}

	public static void innerQueryValidation(ClauseValue cv) {
		List<String> innerQuery = cv.getFirstPart();
		System.out.println("old innerQuery : " + innerQuery);

	}

	public static List<String> getCleanList(List<String> queryList) {
		List<String> cleanList = new ArrayList<String>();
		for (String s : queryList) {
			cleanList.add(s.replace(SPACE, EMPTYSPACE));
		}
		return cleanList;
	}

	public static boolean compareTwoQueries(List<String> firstList, List<String> secondList) {
		boolean indicator = true;
		if (firstList != null && secondList != null) {
			List<String> cleanFirstList = new ArrayList<String>();
			cleanFirstList = getCleanList(firstList);
			List<String> cleanSecondList = new ArrayList<String>();
			cleanSecondList = getCleanList(secondList);
			for (String s : cleanSecondList) {
				if (!cleanFirstList.contains(s)) {
					indicator = false;
				}
			}
		}
		return indicator;
	}

	public static boolean comapreTwoString(String firstValue, String secondValue) {
		boolean indicator = false;
		if (Utilities.isNotNullOrEmpty(firstValue) && Utilities.isNotNullOrEmpty(secondValue)) {
			if (firstValue.equals(secondValue)) {
				indicator = true;
			}
		}
		return indicator;
	}

	public static boolean processValidateQueryByparts(String oldQuery, String urlQuery) {
		ClauseValue oldClauseValue = validateByQuery(oldQuery);
		ClauseValue urlClauseValue = validateByQuery(urlQuery);
		boolean returnValue = checkComparison(oldClauseValue, urlClauseValue);
		return returnValue;
	}

	public static boolean checkComparison(ClauseValue oldClauseValue, ClauseValue urlClauseValue) {
		boolean returnValue = false;
		boolean firstPartIndicator1 = compareTwoQueries(oldClauseValue.getFirstPart(), urlClauseValue.getFirstPart());
		boolean secondPartIndicator1 = compareTwoQueries(oldClauseValue.getSecondPart(),
				urlClauseValue.getSecondPart());
		boolean thirdPartIndicator1 = compareTwoQueries(oldClauseValue.getThirdPart(), urlClauseValue.getThirdPart());
		boolean fourthPartIndicator1 = compareTwoQueries(oldClauseValue.getFourthPart(),
				urlClauseValue.getFourthPart());
		boolean fifthPartIndicator1 = compareTwoQueries(oldClauseValue.getFifthPart(), urlClauseValue.getFifthPart());
		boolean lastPartIndicator1 = comapreTwoString(oldClauseValue.getLastPart(), urlClauseValue.getLastPart());
		// System.out.println("firstPartIndicator1 : " + firstPartIndicator1);
		// System.out.println("secondPartIndicator1 : " + secondPartIndicator1);
		// System.out.println("thirdPartIndicator1 : " + thirdPartIndicator1);
		// System.out.println("fourthPartIndicator1 : " + fourthPartIndicator1);
		// System.out.println("fifthPartIndicator1 : " + fifthPartIndicator1);
		// System.out.println("lastPartIndicator1 : " + lastPartIndicator1);

		boolean firstPartIndicator2 = compareTwoQueries(urlClauseValue.getFirstPart(), oldClauseValue.getFirstPart());
		boolean secondPartIndicator2 = compareTwoQueries(urlClauseValue.getSecondPart(),
				oldClauseValue.getSecondPart());
		boolean thirdPartIndicator2 = compareTwoQueries(urlClauseValue.getThirdPart(), oldClauseValue.getThirdPart());
		boolean fourthPartIndicator2 = compareTwoQueries(urlClauseValue.getFourthPart(),
				oldClauseValue.getFourthPart());
		boolean fifthPartIndicator2 = compareTwoQueries(urlClauseValue.getFifthPart(), oldClauseValue.getFifthPart());
		boolean lastPartIndicator2 = comapreTwoString(urlClauseValue.getLastPart(), oldClauseValue.getLastPart());
		// System.out.println("firstPartIndicator2 : " + firstPartIndicator2);
		// System.out.println("secondPartIndicator2 : " + secondPartIndicator2);
		// System.out.println("thirdPartIndicator2 : " + thirdPartIndicator2);
		// System.out.println("fourthPartIndicator2 : " + fourthPartIndicator2);
		// System.out.println("fifthPartIndicator2 : " + fifthPartIndicator2);
		// System.out.println("lastPartIndicator2 : " + lastPartIndicator2);

		if (firstPartIndicator1 && secondPartIndicator1 && thirdPartIndicator1 && fourthPartIndicator1
				&& fifthPartIndicator1 && lastPartIndicator1 && firstPartIndicator2 && secondPartIndicator2
				&& thirdPartIndicator2 && fourthPartIndicator2 && fifthPartIndicator2 && lastPartIndicator2) {
			returnValue = true;
		}
		return returnValue;
	}

//	public static void main(String args[]) {
//		QueryParts qp = new QueryParts();
//		String oldQuery = "select \"User\".\"Name\", SUM(\"Opportunity\".\"Amount\") from \"User\", \"Opportunity\" where \"User\".\"Id\"=\"Opportunity\".\"OwnerId\" group by \"User\".\"Name\" order by SUM(\"Opportunity\".\"Amount\") DESC nulls last limit 4";
//		boolean returnValue = qp.processValidateQueryByparts(oldQuery, "");
//		System.out.println("Ans : " + returnValue);
//	}
}
