package com.quarrio.dal.validations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONObject;

import com.opencsv.CSVWriter;

/**
 * @author Zain ul Hassan
 *
 */
public class ReadWriteFile {

	private static final String QUESTION = "Questions";
	private static final String STATUS = "Status";
	private static final String OLDQUERY = "Old Queries";
	private static final String NEWQUERY = "New Queries";

	public static JSONObject readDataFromJsonFile(String filePath) throws IOException {
		System.out.println("Start readDataFromJsonFile()");
		JSONObject jsonObject = new JSONObject();
		try {
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String read;
			while ((read = reader.readLine()) != null) {
				builder.append(read);
			}
			reader.close();
			jsonObject = new JSONObject(builder.toString());
			reader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jsonObject;
	}

	public static CSVWriter openCsvFile(String filePath) {
		// System.out.println("Start writeDataToCsvFile()");
		CSVWriter csvWriter = null;
		try {
			FileWriter writer = new FileWriter(filePath);
			csvWriter = new CSVWriter(writer);
			String[] header = { QUESTION, OLDQUERY, NEWQUERY, STATUS };
			csvWriter.writeNext(header);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return csvWriter;
	}

	public static void writeData(CSVWriter csvWriter, String oldQuestion, String oldQuery, String newQuery,
			String status) {
		// System.out.println("Start writeData()");
		try {
			String[] data = { oldQuestion, oldQuery, newQuery, status };
			csvWriter.writeNext(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void closeFile(CSVWriter csvWriter) {
		 System.out.println("Start  closeFile()");
		try {
			csvWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
