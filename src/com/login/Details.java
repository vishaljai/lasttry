package com.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Details
 */
@WebServlet("/Details")
public class Details extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Details() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ConnectionDB cdb = new ConnectionDB();
		Connection con = null;
		PrintWriter pw = response.getWriter();

		try {
			con = cdb.getConnection();
			con.setAutoCommit(false);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement s = null;
		Statement s1 = null;
		try {
			s = con.createStatement();
			s1 = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			ResultSet rs = s.executeQuery("select * from books where bookId='" + request.getParameter("bookId") + "'");
			ResultSet rs1 = s1
					.executeQuery("select review from reviews where bookId='" + request.getParameter("bookId") + "'");
			response.setContentType("text/html");
			if (rs.next()) {

				pw.println(rs.getMetaData().getColumnName(2)
						+ "<span style='display:inline-block; width: 20px;'></span>"
						+ rs.getMetaData().getColumnName(3)
						+ "<span style='display:inline-block; width: 20px;'></span>"
						+ rs.getMetaData().getColumnName(4)
						+ "<span style='display:inline-block; width: 20px;'></span>"
						+ rs.getMetaData().getColumnName(5)
						+ "<span style='display:inline-block; width: 40px;'></span>"
						+ rs1.getMetaData().getColumnName(1) + "<br>");

				do {
					if (rs1.next()) {
						do {
							pw.println(rs.getString(2)
									+ "<span style='display:inline-block; width: 70px;'></span>"
									+ rs.getString(3)
									+ "<span style='display:inline-block; width: 70px;'></span>"
									+ rs.getString(4)
									+ "<span style='display:inline-block; width: 80px;'></span>"
									+ rs.getInt(5)
									+ "<span style='display:inline-block; width: 70px;'></span>"
									+ rs1.getString(1) + "<br>");
						} while (rs1.next());
					}

				} while (rs.next());
				pw.println("<a href=userPage.html>Go to Home</a><br>");
			} else {

				pw.println("<h3 align='center'>!!!There is no data in database!!!</h3>");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
