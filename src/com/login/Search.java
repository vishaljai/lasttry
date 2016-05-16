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
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Search() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ConnectionDB cdb = new ConnectionDB();
		Connection con = null;

		try {
			con = cdb.getConnection();
			con.setAutoCommit(false);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement s = null;
		try {
			s = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try (PrintWriter pw = response.getWriter()) {

			ResultSet rs = s
					.executeQuery("select * from books where bookTitle='" + request.getParameter("bookTitle") + "'");
			if (rs.next()) {

				pw.println(rs.getMetaData().getColumnName(1) + "\t" + rs.getMetaData().getColumnName(2) + "\t"
						+ rs.getMetaData().getColumnName(3) + "\t" + rs.getMetaData().getColumnName(4) + "\t"
						+ rs.getMetaData().getColumnName(5));
				pw.println("<form method='get' action='AddReview'>");
				do {
					response.setContentType("text/html");
					pw.println("<h3 align='center'>Searched Results!!!</h3>");
					pw.println("<br><input type='radio' name='bookId' value='" + rs.getInt(1) + "'>" + rs.getInt(1) + "\t"
							+ rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t" + rs.getString(4) + "\t\t"
							+ rs.getInt(5));
				} while (rs.next());
			
				ResultSet rs1 = s
						.executeQuery("select * from books where bookTitle='" + request.getParameter("bookTitle") + "'");
				pw.println("<br><input type='submit' value='Add Review'>" + "\t\t");
				rs1.next();
				pw.println("</form>");
				pw.println("<form method='get' action='Details'>");
				pw.println("<input type='hidden' name='bookId' value='"+rs1.getInt(1)+"'>");
				pw.println("<input type='submit' value='View Details'><br><a href=loginPage.html>Logout</a>");
				pw.println("</form>");
			} else {
				pw.println("There is no data in database!!!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
