package com.login;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class LogFilter
 */
@WebFilter("/LogFilter")
public class LogFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LogFilter() {
        // TODO Auto-generated constructor stub
    }
    
    boolean validate(int id, String pass) {

		Statement s = null;
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
			s = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ResultSet rs = s.executeQuery("select password from users where userId=" + id);
			if (rs.next())
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}
    
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
			
		int userId = Integer.parseInt(request.getParameter("userId"));
		String pass = request.getParameter("password");
		PrintWriter out = response.getWriter();
		

		if (userId == 0 && pass.equals("root"))
		{
			RequestDispatcher rd = request.getRequestDispatcher("adminDashboard.html");
			rd.forward(request, response);
		}
		boolean flag = validate(userId, pass);
		if(flag)
		{
			
			String user = request.getParameter("userId");
			File logFile = new File("D:/logFile.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(logFile,true));
			
			if(!logFile.exists())
			{
				logFile.createNewFile();
			}
			bw.append(user);
			bw.append("\t");
			bw.append(new Date().toString());
			bw.append(System.lineSeparator());
			bw.flush();
			bw.close();
			
			RequestDispatcher rd = request.getRequestDispatcher("userPage.html");
			rd.forward(request, response);
		}
		else
		{
			response.setContentType("text/html");
			out.println("!!!Invalid Username or Password!!!");
			out.close();
		}
		
	
		// pass the request along the filter chain
		
		//chain.doFilter(request, response);
		

	}
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
