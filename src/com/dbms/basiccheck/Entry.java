package com.dbms.basiccheck;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Entry {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to our application.");
		System.out.println("Please enter your username.");
		String username = scanner.next();
		System.out.println("Please enter your Password.");
		String password = scanner.next();
		System.out.println("User Name: " + username + " Password: "+ password);
		
		ConnectionGetter cg = new ConnectionGetter();
		Connection con  = cg.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String role = "";
		try{
		 stmt = con.createStatement();
		 String x = "SELECT ROLE FROM ROLE where user_id = '" + username + "'";
		 rs = stmt.executeQuery(x);
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
		
		
	}
	
	
	
	
}
