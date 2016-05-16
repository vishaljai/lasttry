package com.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

	Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");

		// creating connection object
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cybagelibrary", "root", "root");
		return con;
	}

}
