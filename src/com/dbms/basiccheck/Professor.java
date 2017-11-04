package com.dbms.basiccheck;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import oracle.jdbc.OracleTypes;

public class Professor {
	
	  public static ConnectionGetter cg = null;
	  public static Connection con = null;
	  public static Scanner sc = null;
	  public static String username = null;

	public static void homePage() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
	    PreparedStatement stmt = null;
		ResultSet rs = null;
		List<String> options = new ArrayList<String>();
		
		try{
			stmt=con.prepareStatement("SELECT col_name FROM MENU_OPTIONS where role = ? and menu_name = ? order by display_order");
			 stmt.setString(1, "P");
		     stmt.setString(2, "Home");

		 rs = stmt.executeQuery();
		 
		 while(rs.next()){
			 options.add(rs.getString("col_name"));
		 }
		 cg.closeStatement(stmt);
		 cg.closeResult(rs);
		for(int i=1; i <= options.size();i++){
			System.out.println(i+ ": "+ options.get(i-1));
		}
		
		int optionNo = sc.nextInt();
		sc.nextLine();
		String optionSelected = options.get(optionNo-1);
		if(optionSelected.equals("Logout") || optionSelected.equals("Log out")){
			Entry.startPage();
		}
		else if(optionSelected.equals("View Profile") || optionSelected.equals("View/Edit Profile")){
			//System.out.println("View Profile");
			viewProfile();
		}
		else if(optionSelected.equals("View/Add Courses")){
			//System.out.println("View/Add Courses");
			viewAddCourses();
		}
		else if(optionSelected.equals("Enroll/Drop A Student")){
			System.out.println("Enroll/Drop A Student");	
		}
		else if (optionSelected.equals("Search/Add questions to Question Bank")){
			System.out.println("Search/Add questions to Question Bank");
		}
        
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private static void viewAddCourses() {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<String> courses = new ArrayList<String>();
		try{
			stmt=con.prepareStatement("SELECT COURSE_ID from COURSE where PROFESSOR_ID = ?");
			 stmt.setString(1, username);
		     

		 rs = stmt.executeQuery();
		 while(rs.next()){
			 courses.add(rs.getString("COURSE_ID")); 
		 }
		 
		 }
		catch(Exception e){
		   e.printStackTrace();	
		}
		System.out.println("Couses you added: ");
		for(int i=1; i <= courses.size();i++){
		System.out.println(i + ": " + courses.get(i-1));	
		}
		
		boolean done = false;
		while(!done){
		System.out.println("Enter Course ID to view the course");
		System.out.println("Press 1 to add a new course");
		System.out.println("Press 0 to go back to previous menu");
		String ip = sc.nextLine();
		//ip ="CSC 540";
		//String ip = sc.next();
		if(isNumber(ip)){
			int no = Integer.parseInt(ip);
			if(no == 1){
				done = true;
				System.out.println("add course");
				//addCourse();
			}
			else if(no == 0){
				done = true;
				homePage();
			}
			
		}else{
			done = true;
			//System.out.println("view specific course");
			viewSpecificCourse(ip);
		}
		
	}
	}
	
	private static void viewSpecificCourse(String courseId) {
		// TODO Auto-generated method stub
		
		String getRelevantOptions = "{call SELECT_PROFESSOR_OPTIONS(?,?,?)}";
		CallableStatement callableStatement = null;
		ResultSet rs = null;
		try {
			callableStatement = con.prepareCall(getRelevantOptions);
			callableStatement.setString(1, courseId);
			callableStatement.setString(2, username);
			callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(3);
			List<String> options = new ArrayList<String>();
			while(rs.next()){
				
				System.out.println(rs.getString("col_name"));
				//options.add(rs.getString("col_name"));
			}
			//String d = "k";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private static void addCourse() {
		// TODO Auto-generated method stub
		
	}

	private static boolean isNumber(String x){
		try
	    {
	        Integer.parseInt(x);
	    }
	    catch(NumberFormatException ex)
	    {
	        return false;
	    }
	    return true;
	}

	private static void viewProfile() {

		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		System.out.println("Press 0 to go back");
		String name = "";

		try{
			stmt=con.prepareStatement("SELECT name FROM PROFESSOR where PROFESSOR_ID = ?");
			stmt.setString(1, username);
			 
			 rs = stmt.executeQuery();
			 while(rs.next()){
				 name = rs.getString("name"); 
			 }
		    
		     }
		catch(Exception e){
		   e.printStackTrace();	
		}
		cg.closeStatement(stmt);
		cg.closeResult(rs);
		String [] nameBreak = name.split("\\s");
		String fname = nameBreak[0];
		String lname = "";
		if(nameBreak.length > 1){
			lname = nameBreak[1];
		}
		System.out.println("First Name: " +  fname);
		System.out.println("Last Name: " +  lname);
		String lastOption = "";
		try{
			stmt=con.prepareStatement("SELECT col_name FROM MENU_OPTIONS where role = ? and menu_name = ? order by display_order");
			 stmt.setString(1, "P");
		     stmt.setString(2, "Profile");
		     
		    rs = stmt.executeQuery();
		    while(rs.next()){
		    	lastOption = rs.getString("col_name");
		    }
		}
		catch(Exception e){
			e.printStackTrace();
		}
		cg.closeStatement(stmt);
		cg.closeResult(rs);
		System.out.println(lastOption + ": " + username);
		int isZero = 1;
		while(isZero != 0){
			isZero = sc.nextInt();
			}
		homePage();
		
	}

}
