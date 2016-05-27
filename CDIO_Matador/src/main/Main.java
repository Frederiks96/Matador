package main;

import java.sql.SQLException;

import controller.Controller;
import desktop_resources.GUI;

public class Main {

	public static void main(String[] args) {
		Controller con;
		try {
			con = new Controller();
			con.run();
		} catch (SQLException s) {
			s.printStackTrace();
			GUI.close();
		}
	}
}
