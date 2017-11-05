package com.dbms.basiccheck;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TA {
	
	  public static ConnectionGetter cg = null;
	  public static Connection con = null;
	  public static Scanner sc = null;
	  public static String username = null;

		public static void homePage() {
			
		    PreparedStatement stmt = null;
			ResultSet rs = null;
			List<String> options = new ArrayList<String>();
			
			try{
				stmt=con.prepareStatement("SELECT col_name FROM MENU_OPTIONS where role = ? and menu_name = ? order by display_order");
				 stmt.setString(1, "T");
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
				//System.out.println("Enroll/Drop A Student");
				enrollDropOption();
				
			}
			}
			catch(Exception e){
				System.out.println("Encountered an Error: "+e.getMessage());
				homePage();
			}
		}

		private static void enrollDropOption() {
			
			System.out.println("select 0 to go to home page.");
			System.out.println("1: Enroll Student");
			System.out.println("2: Drop Student");
			int choice  = sc.nextInt();
			 sc.nextLine();
			 if(choice == 0){
				 homePage();
			 }
			 if(choice==1){
				 enrollStudent();
			 }
			 if(choice==2){
				 enrollDropStudent();
			 }
			 homePage(); 
			
		}

		private static void enrollStudent() {
			
			PreparedStatement stmt = null;
			ResultSet rs = null;
			List<String> courses = new ArrayList<String>();
			System.out.println("Select Course to enroll Student.");
			try{
				stmt=con.prepareStatement("SELECT COURSE_ID from TA where STUDENT_ID = ?");
				stmt.setString(1, username);
			   	rs = stmt.executeQuery();
			   	while(rs.next()){
			   		courses.add(rs.getString("COURSE_ID"));
			   	}
				 for(int i=1; i <= courses.size();i++){
					 System.out.println(i + ": " + courses.get(i-1));
				 }
				 System.out.println("select 0 to go to home page.");
				 int choice  = sc.nextInt();
				 sc.nextLine();
				 if(choice == 0){
					 homePage();
				 }
				 else{
					 String course = courses.get(choice-1);
					 DisplayStudents(course);
				 }
			}
			catch(Exception e){
				System.out.println("Encountered an Error: "+e.getMessage());
				enrollStudent();
			}
			
		}


		private static void DisplayStudents(String course) {
			
			PreparedStatement stmt = null;
			ResultSet rs = null;
			List<String> students = new ArrayList<String>();
			System.out.println("Select student to enroll/drop from :" + course);
			try{
				stmt=con.prepareStatement("SELECT STUDENT_ID from STUDENT ");
			   	rs = stmt.executeQuery();
			   	while(rs.next()){
			   		students.add(rs.getString("STUDENT_ID"));
			   	}
			   	System.out.println("select 0 to go to previous page.");
			   	for(int i=1; i <= students.size();i++){
					 System.out.println(i + ": " + students.get(i-1));
				 }
			   	int choice  = sc.nextInt();
				 sc.nextLine();
				 if(choice == 0){
					 homePage();
				 }
				 else{
					 String studentId = students.get(choice-1);
					 EnrollStudentToCourse(studentId,course);
					 DisplayStudents(course);
				 }
			}
			catch(Exception e){
				System.out.println("Encountered an Error: "+e.getMessage());
				DisplayStudents(course);
			}
			
		}

		private static void EnrollStudentToCourse(String studentId, String course) {
			PreparedStatement stmt = null;
			try{
				stmt = con.prepareStatement("INSERT into COURSE_STUDENT values (?,?)");
				stmt.setString(1, course);
				stmt.setString(2, studentId);
				stmt.executeUpdate();
				System.out.println("Enrolled "+studentId +" to "+course);
			}
			catch(Exception e){
				System.out.println(e.getMessage());
				DisplayStudents(course);
				
			}
					
		}

		private static void enrollDropStudent() {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			List<String> courses = new ArrayList<String>();
			System.out.println("Select Course to drop Student.");
			try{
				stmt=con.prepareStatement("SELECT COURSE_ID from TA where STUDENT_ID = ?");
				stmt.setString(1, username);
			   	rs = stmt.executeQuery();
			   	while(rs.next()){
			   		courses.add(rs.getString("COURSE_ID"));
			   	}
				 for(int i=1; i <= courses.size();i++){
					 System.out.println(i + ": " + courses.get(i-1));
				 }
				 System.out.println("select 0 to go to previous page.");
				 int choice  = sc.nextInt();
				 sc.nextLine();
				 if(choice == 0){
					 homePage();
				 }
				 else{
					 String course = courses.get(choice-1);
					 DisplayEnrolledStudents(course);
					 
				 }
			}
			catch(Exception e){
				System.out.println("Encountered an Error: "+e.getMessage());
				enrollDropStudent();
			}
			
		}

		private static void DisplayEnrolledStudents(String course) {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			List<String> students = new ArrayList<String>();
			System.out.println("Select student to enroll/drop from :" + course);
			try{
				stmt=con.prepareStatement("SELECT STUDENT_ID from COURSE_STUDENT where COURSE_ID = ?");
				stmt.setString(1, course);
			   	rs = stmt.executeQuery();
			   	while(rs.next()){
			   		students.add(rs.getString("STUDENT_ID"));
			   	}
			   	if(students.size()==0){
			   		System.out.println("No students enrolled");
			   		
			   		
			   	}
			   	System.out.println("select 0 to go to previous page.");
			   	for(int i=1; i <= students.size();i++){
					 System.out.println(i + ": " + students.get(i-1));
				 }
				 
				 int choice  = sc.nextInt();
				 sc.nextLine();
				 if(choice == 0){
					 homePage();
				 }
				 else{
					 String studentId = students.get(choice-1);
					 DropStudentFromCourse(studentId,course);
					 DisplayEnrolledStudents(course);
					 
				 }
			}
			catch(Exception e){
				System.out.println("Encountered an Error: "+e.getMessage());
				DisplayEnrolledStudents(course);
				
			}
			homePage();
			
		}

		private static void DropStudentFromCourse(String studentId, String course) {
			
			PreparedStatement stmt = null;
			try{
				stmt=con.prepareStatement("delete from COURSE_STUDENT where COURSE_ID = ? and STUDENT_ID= ?");
				stmt.setString(1, course);
				stmt.setString(2, studentId);
				stmt.executeUpdate();
				cg.closeStatement(stmt);
			   	System.out.println("Successfully dropped");
			}
			catch(Exception e){
				System.out.println("Encountered an Error: "+e.getMessage());
				DisplayEnrolledStudents(course);
				
			}
			
		}

		private static void viewAddCourses() {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			List<String> courses = new ArrayList<String>();
			try{
				stmt=con.prepareStatement("SELECT COURSE_ID from TA where STUDENT_ID = ?");
				 stmt.setString(1, username);
			     

			 rs = stmt.executeQuery();
			 while(rs.next()){
				 courses.add(rs.getString("COURSE_ID")); 
			 }
			 
			 }
			catch(Exception e){
			   System.out.println("Encountered an Error: "+e.getMessage());
			   homePage();
			   
			}
			
			System.out.println("Couses you are TA to: ");
			for(int i=1; i <= courses.size();i++){
			System.out.println(i + ": " + courses.get(i-1));	
			}
			System.out.println("Choose course to view the course");
			System.out.println("Press 0 to go back to previous menu");
			int choice  = sc.nextInt();
			 sc.nextLine();
			 if(choice == 0){
				 homePage();
			 }
			 else	{
				 viewSpecificCourse(courses.get(choice-1));
			 }
			
		}

		private static void viewSpecificCourse(String course) {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try{
				 stmt=con.prepareStatement("SELECT COURSE_NAME, START_DATE, END_DATE FROM COURSE where COURSE_ID = ?");
				 stmt.setString(1, course);
			     rs = stmt.executeQuery();
			     String courseName = "";
			     String stDate = "";
			     String endDate = "";
			     while(rs.next()){
			    	 courseName = rs.getString("COURSE_NAME");
			    	 stDate = rs.getString("START_DATE");
			    	 endDate = rs.getString("END_DATE");
			     }
			     System.out.println("Course Name: " + courseName);
			     System.out.println("Start Date: " + stDate);
			     System.out.println("End Date: " + endDate);
			     System.out.println("Choose 0 to return to homepage");
			     System.out.println("1: View Exercises");
			     int choice  = sc.nextInt();
				 sc.nextLine();
				 if(choice == 0){
					 homePage();
				 }
				 else if(choice == 0){
					 viewExercise(course);
				 }  
				
			}
			catch(Exception e){
				System.out.println(e.getMessage());
				viewAddCourses();
			}
			
		}
		
		private static void viewExercise(String courseId) {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try{
			stmt=con.prepareStatement("SELECT EXERCISE_ID, NAME, DEADLINE, TOTAL_QUESTIONS, RETRIES,START_DATE, END_DATE, POINTS, PENALTY, SCORING_POLICY, SC_MODE FROM EXERCISE where COURSE_ID = ?");
			 stmt.setString(1, courseId);
		     rs = stmt.executeQuery();
		     
		     while(rs.next()){
		    	 System.out.println("Exercise ID: " + rs.getString("EXERCISE_ID") + " Exercise Name: " + rs.getString("NAME") + " DEADLINE: " + rs.getString("DEADLINE") + " TOTAL_QUESTIONS: " + rs.getString("TOTAL_QUESTIONS") + " RETRIES: " + rs.getString("RETRIES") + " START DATE: " + rs.getString("START_DATE") + " END DATE: " + rs.getString("END_DATE") + " Points: " + rs.getString("POINTS") + " PENALTY: " + rs.getString("PENALTY") + " SCORING POLICY: " + rs.getString("SCORING_POLICY") + " MODE: " + rs.getString("SC_MODE"));

			}
		     boolean done = false;
		     while(!done){
		     System.out.println("Please Enter exercise Id for further details: ");
		     System.out.println("Press 0 to go to previous menu ");
		     String choice = sc.nextLine();
		     if(isNumber(choice)){
		    	 int no = Integer.parseInt(choice);
		    	 if(no == 0){
		    		 done = true;
		    		 viewSpecificCourse(courseId);
		    	 }
		    	 else{
		    		 viewSpecificExercise(no, courseId);
		    	 }
		    	 
		     }
		     else{
		    	 System.out.println("Invalid input, Please try again");
		     }

			}
			}
			catch(Exception e){
				System.out.println("Encountered an Error: "+e.getMessage());
				viewSpecificCourse(courseId);
			}	
		}
		
		private static void viewSpecificExercise(int no, String courseId) {
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try{
			stmt=con.prepareStatement("SELECT SC_MODE FROM EXERCISE where EXERCISE_ID = ?");
			 stmt.setInt(1, no);
		     rs = stmt.executeQuery();
		     String mode = "";
		     while(rs.next()){
		    	 mode = rs.getString("SC_MODE");
		     }
		     
		     cg.closeResult(rs);
		     cg.closeStatement(stmt);
		     if(mode.equals("ADAPTIVE")){
		    	 System.out.println("Questions generated during attempt!");
		     }
		     else{
		    	 stmt=con.prepareStatement("SELECT EQ.QUESTION_ID, Q.QUESTION_TEXT, Q.DIFFICULTY_LEVEL, Q.HINT, Q.EXPLANATION, Q.TOPIC_ID FROM EXERCISE_QUESTION EQ inner join QUESTION Q on EQ.QUESTION_ID = Q.QUESTION_ID where EQ.EXERCISE_ID = 1 ");
		    	 
		     }
		     
		     }
			catch(Exception e){
				System.out.println("Encountered an Error: "+e.getMessage());
				viewExercise(courseId);
			}
			
			
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
			PreparedStatement stmt = null;
			ResultSet rs = null;
			System.out.println("Press 0 to go back");
			String name = "";

			try{
				stmt=con.prepareStatement("SELECT name FROM STUDENT where STUDENT_ID = ?");
				stmt.setString(1, username);
				 
				 rs = stmt.executeQuery();
				 while(rs.next()){
					 name = rs.getString("name"); 
				 }
			    
			     }
			catch(Exception e){
			   System.out.println("Encountered an Error: "+e.getMessage());	
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
				 stmt.setString(1, "T");
			     stmt.setString(2, "Profile");
			     
			    rs = stmt.executeQuery();
			    while(rs.next()){
			    	lastOption = rs.getString("col_name");
			    }
			}
			catch(Exception e){
				System.out.println("Encountered an Error: "+e.getMessage());
				homePage();
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
