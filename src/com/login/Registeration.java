package com.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Registeration
 */
@WebServlet("/Registeration")
public class Registeration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registeration() {
        super();
        // TODO Auto-generated constructor stub
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try(PrintWriter pw = response.getWriter()) {
			PreparedStatement ps = con.prepareStatement("insert into users (userName,password) values(?,?)");
			ps.setString(1, request.getParameter("username"));
			ps.setString(2, request.getParameter("password"));
			ps.execute();
			con.commit();
			con.close();
			
			con = cdb.getConnection();
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery("select userId from users where userName='"+request.getParameter("username")+"'");
			response.setContentType("text/html");
			pw.println("<h3>!!!Registeration Successfull!!!</h3>");
			if(rs.next())
			pw.println("<h3>Your unique user ID is: </h3>"+rs.getInt(1));
			pw.println("<center>To go to Login Page: <a href='loginPage.html'>Click here</a></center>");
			
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
