package com.quarrio.dal.validations;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * @author Zain ul Hassan
 *
 */
public class DALClient {

	public static JSONObject getQuestionResponseFromDAL(String url) throws UnsupportedEncodingException {
		System.out.println("Start getQuestionResponseFromDAL()");
		JSONObject jsonDalResponse = new JSONObject();
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpPost = new HttpGet(url);
		try {
			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String dalResponse = EntityUtils.toString(entity);
			jsonDalResponse = new JSONObject(dalResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonDalResponse;
	}
}