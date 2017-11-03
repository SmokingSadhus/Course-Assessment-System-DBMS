package com.dbms.basiccheck;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Entry {
	
	  static Connection con = null;
	  static Scanner sc = null;
	
	private static void getConnection(){
    	ConnectionGetter cg = new ConnectionGetter();
		 con  = cg.getConnection();
		
    }
  
	public static void main(String[] args) {
		 sc = new Scanner(System.in);
		 getConnection();
	      startPage();
	      sc.close();
	      try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void startPage() {
		// TODO Auto-generated method stub
		
		System.out.println("Start Menu");
		System.out.println("1. Login");
		System.out.println("2. Exit");
		int no = sc.nextInt();
		if(no == 2){
			
			return; 
		}
		else{
			loginPage();
		}
		
		
	}

	private static void loginPage() {
		// TODO Auto-generated method stub
		
		System.out.println("UserName: ");
		String username = sc.next();
		System.out.println("Password: ");
		String password = sc.next();
		
		//Statement stmt = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String role = "";
		try{
			 stmt=con.prepareStatement("SELECT ROLE FROM ROLE where user_id = ? and PASSWORD = ?");
			 stmt.setString(1, username);
			 stmt.setString(2, password);

		 rs = stmt.executeQuery();
		 while(rs.next()){
			 
			  role = rs.getString("ROLE");
			 
		 }
		}
		catch(Exception e){
			e.printStackTrace();
			role = "Error";
		}
		
		new ConnectionGetter().closeResult(rs);
		new ConnectionGetter().closeStatement(stmt);
		if(role.equals("Error")|| role.equals("")){
			System.out.println("Invalid Credentials, try again.");
			startPage();
		}
		else{
			homePage(role);
		}
		
		
		
	}

	private static void homePage(String role) {
		// TODO Auto-generated method stub
	    PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			stmt=con.prepareStatement("SELECT col_name FROM ROLE where user_id = ? and PASSWORD = ?");
			 //stmt.setString(1, username);
			// stmt.setString(2, password);

		 rs = stmt.executeQuery();

		}
		catch(Exception e){
			
		}
		
	}

	/*public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to our application.");
		System.out.println("Please enter your username.");
		String username = scanner.next();
		System.out.println("Please enter your Password.");
		String password = scanner.next();
		System.out.println("User Name: " + username + " Password: "+ password);
		
		ConnectionGetter cg = new ConnectionGetter();
		Connection con  = cg.getConnection();
		//Statement stmt = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String role = "";
		try{
			 stmt=con.prepareStatement("SELECT ROLE FROM ROLE where user_id = ? and PASSWORD = ?");
			 stmt.setString(1, username);
			 stmt.setString(2, password);
			 
		 //stmt = con.createStatement();
		 //String x = "SELECT ROLE FROM ROLE where user_id = '" + username + "'";
		 //String x = "SELECT ROLE FROM ROLE";
		 rs = stmt.executeQuery();
		 while(rs.next()){
			 
			  role = rs.getString("ROLE");
			 
		 }
		}
		catch(Exception e){
			e.printStackTrace();
			role = "Error";
		}
		
		cg.closeResult(rs);
		cg.closeStatement(stmt);
		cg.closeConnection(con);
		
		System.out.println("Your role is " + role);
		
		if(role.equals("Error")|| role.equals("")){
			System.out.println("You are not in our system");
		}
		else{
			
		}
		
	
	}*/	
	
	
	
	
}
