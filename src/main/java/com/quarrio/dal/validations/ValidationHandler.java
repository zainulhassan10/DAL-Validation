package com.quarrio.dal.validations;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.commons.cli.CommandLine;
import org.json.JSONException;
import org.json.JSONObject;

import com.opencsv.CSVWriter;

/**
 * @author Zain ul Hassan
 *
 */
public class ValidationHandler {

	private static final String HTTPSURL = "https://dal.qca-";
	private static final String DALRESTURL = ".com/rest/dal?q=";
	private static final String UTF8 = "UTF-8";
	private static final String PASS = "Pass";
	private static final String FAIL = "Fail";
	private static final String ERROR = "Error";
	private static final String EXCEPTION = "Exception";

	private static String getJsonInnerObjectValue(JSONObject jsonObject, String objectKey, String innerKey) {
		String value = "";
		JSONObject jsonKeys = jsonObject.getJSONObject(objectKey);
		java.util.Iterator<?> keys = jsonKeys.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			if (jsonKeys.get(key) instanceof JSONObject) {
				JSONObject jsonInnerObject = jsonKeys.getJSONObject(key);
				value = jsonInnerObject.getString(innerKey);
			}
		}
		return value;
	}

	private static JSONObject getOldQuestionQueryData(String filePath) {
		System.out.println("Start getOldQuestionQueryData()");
		JSONObject oldListOf100Questions = new JSONObject();
		JSONObject jsonObject;
		try {
			jsonObject = ReadWriteFile.readDataFromJsonFile(filePath);
			oldListOf100Questions = jsonObject.getJSONObject("questionsList");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return oldListOf100Questions;
	}

	public static void processValidateQuestionsDemo(String serverName, String readFilePath, String writeFilePath)
			throws IOException {
		System.out.println("Start processValidateQuestions()");
		if (Utilities.isNotNullOrEmpty(serverName) && Utilities.isNotNullOrEmpty(readFilePath)
				&& Utilities.isNotNullOrEmpty(writeFilePath)) {
			CSVWriter csvWriter = ReadWriteFile.openCsvFile(writeFilePath);
			String dalUrl = HTTPSURL + serverName + DALRESTURL;
			JSONObject oldListOf100Questions = getOldQuestionQueryData(readFilePath);
			java.util.Iterator<?> questionskeys = oldListOf100Questions.keys();
			while (questionskeys.hasNext()) {
				String key = (String) questionskeys.next();
				System.out.println("key : " + key);
				if (oldListOf100Questions.get(key) instanceof JSONObject) {
					JSONObject questionAndQuery = oldListOf100Questions.getJSONObject(key);
					String old_question = questionAndQuery.getString("question");
					String old_query = questionAndQuery.getString("query");
					String dalQuestionUrl = dalUrl + URLEncoder.encode(old_question, UTF8);
					JSONObject response = DALClient.getQuestionResponseFromDAL(dalQuestionUrl);
					if (response.has("status")) {
						String hasException = response.getJSONObject("status").get("hasException").toString();
						if (hasException.equalsIgnoreCase("true")) {
							String exceptionStatus = getJsonInnerObjectValue(response, "status", "status");
							System.out.println("Old Question : " + old_question);
							System.out.println("Status : " + exceptionStatus);
							System.out.println("------------------------------------------");
							ReadWriteFile.writeData(csvWriter, old_question, old_query, exceptionStatus, ERROR);
						}
						if (hasException.equalsIgnoreCase("false")) {
							try {
								String new_query = response.getString("sql");
								System.out.println("new_query : " + new_query);
								boolean statusOfComparison = QueryParts.processValidateQueryByparts(old_query,
										new_query);
								System.out.println("Old Question : " + old_question);
								System.out.println("Status : " + statusOfComparison);
								System.out.println("------------------------------------------");
								if (statusOfComparison) {
									ReadWriteFile.writeData(csvWriter, old_question, old_query, new_query, PASS);
								} else {
									ReadWriteFile.writeData(csvWriter, old_question, old_query, new_query, FAIL);
								}
							} catch (JSONException ex) {
								ReadWriteFile.writeData(csvWriter, old_question, old_query, ex.getMessage(), EXCEPTION);
								ex.printStackTrace();
							}
						}
					}
				}
			}
			ReadWriteFile.closeFile(csvWriter);
		}
	}

	public static void processValidateQuestions(String args[]) throws IOException {
		System.out.println("Start processValidateQuestions()");
		CommandLine cmd = CommandLineArgument.getArrguments(args);
		String serverName = cmd.getOptionValue("servername");
		String writeFilePath = cmd.getOptionValue("fileoutput");
		String readFilePath = cmd.getOptionValue("fileinput");
		if (Utilities.isNotNullOrEmpty(serverName) && Utilities.isNotNullOrEmpty(readFilePath)
				&& Utilities.isNotNullOrEmpty(writeFilePath)) {
			CSVWriter csvWriter = ReadWriteFile.openCsvFile(writeFilePath);
			String dalUrl = HTTPSURL + serverName + DALRESTURL;
			JSONObject oldListOf100Questions = getOldQuestionQueryData(readFilePath);
			java.util.Iterator<?> questionskeys = oldListOf100Questions.keys();
			while (questionskeys.hasNext()) {
				String key = (String) questionskeys.next();
				System.out.println("key : " + key);
				if (oldListOf100Questions.get(key) instanceof JSONObject) {
					JSONObject questionAndQuery = oldListOf100Questions.getJSONObject(key);
					String old_question = questionAndQuery.getString("question");
					String old_query = questionAndQuery.getString("query");
					String dalQuestionUrl = dalUrl + URLEncoder.encode(old_question, UTF8);
					JSONObject response = DALClient.getQuestionResponseFromDAL(dalQuestionUrl);
					if (response.has("status")) {
						String hasException = response.getJSONObject("status").get("hasException").toString();
						if (hasException.equalsIgnoreCase("true")) {
							String exceptionStatus = getJsonInnerObjectValue(response, "status", "status");
							System.out.println("Old Question : " + old_question);
							System.out.println("Error Status : " + exceptionStatus);
							System.out.println("------------------------------------------");
							ReadWriteFile.writeData(csvWriter, old_question, old_query, exceptionStatus, ERROR);
						} else if (hasException.equalsIgnoreCase("false")) {
							try {
								String new_query = response.getString("sql");
								boolean statusOfComparison = QueryParts.processValidateQueryByparts(old_query,
										new_query);
								System.out.println("Old Question : " + old_question);
								System.out.println("Comparison Status : " + statusOfComparison);
								System.out.println("------------------------------------------");
								if (statusOfComparison) {
									ReadWriteFile.writeData(csvWriter, old_question, old_query, new_query, PASS);
								} else {
									ReadWriteFile.writeData(csvWriter, old_question, old_query, new_query, FAIL);
								}
							} catch (JSONException ex) {
								ReadWriteFile.writeData(csvWriter, old_question, old_query, ex.getMessage(), EXCEPTION);
								ex.printStackTrace();
							}
						}
					}
				}
			}
			ReadWriteFile.closeFile(csvWriter);
		}
	}

	public static void main(String args[]) throws IOException {
		// String readFilePath = "D:/dal-validation/test.json"; //
		// String writeFilePath = "D:/dal-validation/dumb.csv";
		// String serverName = "dev";
		// processValidateQuestionsDemo(serverName, readFilePath, writeFilePath);
		if (args.length > 0) {
			processValidateQuestions(args);
		} else {
			System.out.println("Please enter parameter server name , read file path and write file path.");
		}
	}
}
