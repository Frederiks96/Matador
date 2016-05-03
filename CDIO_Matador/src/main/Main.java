package main;

import java.io.IOException;
import java.sql.SQLException;

import controller.Controller;

public class Main {

	public static void main(String[] args) throws SQLException, IOException {
		Controller con = new Controller();
		con.run();
	
	}
}
