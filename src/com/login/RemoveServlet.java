package com.login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RemoveServlet
 */
@WebServlet("/RemoveServlet")
public class RemoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RemoveServlet() {
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

		try {
			PreparedStatement ps = con.prepareStatement(
					"delete from books where bookId=" + Integer.parseInt(request.getParameter("bookId")));
			ps.executeUpdate();
			con.commit();
			response.sendRedirect("adminDashboard.html");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
