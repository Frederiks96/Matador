///**
// * 
// */
//package test;
//
//import static org.junit.Assert.*;
//
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import boundary.SQL;
//import controller.Controller;
//import entity.Player;
//import entity.fields.Territory;
//
///**
// * @author Oliver
// *
// */
//public class ControllerTest {
//
//	Controller controller;
//	Player player;
//	Territory  territory;
//	SQL sql;
//	
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	
//	}
//
//	
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}
//	
//	
//	@Before
//	public void setUp() throws Exception {
//		player =  new Player("bob","","", sql);
//	}
//
//
//	@After
//	public void tearDown() throws Exception {
//	}
//
//	@Test
//	public void test() {
//		controller.countTotalWorth(player);
//		System.out.println(controller.countTotalWorth(player));
//	}
//
//}
