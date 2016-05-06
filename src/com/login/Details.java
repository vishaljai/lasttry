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

		try (PrintWriter pw = response.getWriter()) {

			ResultSet rs = s.executeQuery("select * from books where bookId='" + request.getParameter("bookId") + "'");
			ResultSet rs1 = s1.executeQuery("select review from addreview where bookId='" + request.getParameter("bookId") + "'");
			if (rs.next()) {

				pw.print(rs.getMetaData().getColumnName(1) + "\t" + rs.getMetaData().getColumnName(2) + "\t"
						+ rs.getMetaData().getColumnName(3) + "\t\t" + rs.getMetaData().getColumnName(4) + "\t\t"
						+ rs.getMetaData().getColumnName(5));
				//pw.println("\t"+rs1.getMetaData().getColumnName(1));
				do {
					if(rs1.next()){
					pw.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t" + rs.getString(4)
							+ "\t\t" + rs.getInt(5));
					pw.println("\t"+rs1.getString(1));}
				} while (rs.next());
			} else {
				pw.println("There is no data in database!!!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
