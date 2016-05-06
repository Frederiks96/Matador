package main;

import java.sql.SQLException;

import controller.Controller;

public class Main {

	public static void main(String[] args) throws SQLException {
		Controller con;
		try {
			con = new Controller();
			con.run();
		} catch (SQLException e) {
			
		}
	}
}
