package com.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class AddReview
 */
@WebServlet("/AddReview")
public class AddReview extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddReview() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PrintWriter pw = response.getWriter();
    	response.setContentType("text/html");
		pw.println("<h3 align='center'>!!!Your review is valuable for us!!!</h3>"
	+"<br>"
	+"<br>"
	+"<form method='post' action='#'>"
		+"<table align='center'>"
			+"<tr>"
		+"<input type='hidden' name='bookId' value='"+request.getParameter("bookId")+"'"
				+"<td><textarea rows='4' cols='50'"
						+"placeholder='Enter User Id' name='review'></textarea></td>"
			+"</tr>"
			+"<tr>"
			+"<td><input type='submit' value='Submit'></td>"
		+"</tr>");
    }
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		ConnectionDB cdb = new ConnectionDB();
		Connection con = null;

		try {
			con = cdb.getConnection();
			con.setAutoCommit(false);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		try {
			PreparedStatement ps = con.prepareStatement("insert into review(review,bookId) values(?,?)");
			ps.setString(1, request.getParameter("review"));
			ps.setInt(2, Integer.parseInt(request.getParameter("bookId")));
			ps.execute();
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect("Details?bookId="+request.getParameter("bookId"));
	}

}
