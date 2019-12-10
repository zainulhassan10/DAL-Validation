package com.quarrio.dal.validations;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Zain ul Hassan
 *
 */
@WebServlet("/validate")
public class ValidationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		java.io.PrintWriter out = response.getWriter();
		// sn means server name i.e: dev , staging , production , live
		String serverName = request.getParameter("sn");
		System.out.println("Server Name : " + serverName);
		out.println("Server Name : " + serverName);
		String readFilePath = "D:/dal-validation/oldQuestionsQueries.json";
		String writeFilePath = "D:/dal-validation/comparisonData.csv";
//		ValidationHandler.processValidateQuestions(serverName, readFilePath, writeFilePath);
	}
}
