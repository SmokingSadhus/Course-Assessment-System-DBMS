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
			 addQuestionToBank();
			//System.out.println("Search/Add questions to Question Bank");
		}
        
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private static void addQuestionToBank() {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<String> courses = new ArrayList<String>();
		System.out.println("Select course to view question bank.");
		try{
			stmt=con.prepareStatement("SELECT COURSE_ID from COURSE where PROFESSOR_ID = ?");
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
			 displayQuestions(course);
			 
		 }
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

	private static void displayQuestions(String course) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Integer> questions = new ArrayList<Integer>();
		//System.out.println("Select course to view question bank.");
		try{
			stmt=con.prepareStatement("select q.QUESTION_ID, q.QUESTION_TEXT, q.DIFFICULTY_LEVEL, q.TOPIC_ID from QUESTION_BANK qb inner join QUESTION q on qb.QUESTION_ID = q.QUESTION_ID where qb.COURSE_ID = ?");
			 stmt.setString(1, course);
		   	 rs = stmt.executeQuery();
		   	 int i =1;
		 while(rs.next()){
			 questions.add(rs.getInt("QUESTION_ID"));
			 System.out.println(i+" Question ID: " + rs.getString("QUESTION_ID") + " Question TEXT: " + rs.getString("QUESTION_TEXT") + " Difficulty Level: " + rs.getString("DIFFICULTY_LEVEL") + " Topic ID: " + rs.getString("TOPIC_ID"));
			 i++;
			  }
		 cg.closeResult(rs);
		 cg.closeStatement(stmt);
		 System.out.println("For deleting press 1");
		 System.out.println("For adding press 2");
		 System.out.println("Press 0 to go to previous screen.");
		 int choice = sc.nextInt();
		 sc.nextLine();
		 if(choice == 0){
			 addQuestionToBank();
		 }
		 else if(choice == 1){
			 System.out.println("Enter choice to delete");
			 int choice2 = sc.nextInt();
			 sc.nextLine();
			 int questionDelete = questions.get(choice2-1);
			 stmt = con.prepareStatement("delete from QUESTION_BANK where QUESTION_ID = ? and COURSE_ID = ?");
			 stmt.setInt(1, questionDelete);
			 stmt.setString(2, course);
		   	 stmt.executeUpdate();
		   	 cg.closeStatement(stmt);
		   	 System.out.println("Successfully deleted");
		   	displayQuestions(course);
			 
		 }
		 else if(choice == 2){
			 addNewQuestions(course);
		 }
		 
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

	private static void addNewQuestions(String course) {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet rs = null;
		CallableStatement callableStatement = null;
		try {
		System.out.println("Enter Question Type");
		String type = sc.nextLine();
		System.out.println("Enter Question Text");
		String text =  sc.nextLine();
		System.out.println("Enter topic");
		String topic = sc.nextLine();
		System.out.println("Enter difficulty level");
		int dl = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter Hint");
		String hint = sc.nextLine();
		System.out.println("Explanation");
		String exp = sc.nextLine();
		String retrieveQuestionId = "{call INSERT_QUESTION_AND_RETURN_ID(?,?,?,?,?,?,?)}";
		callableStatement = con.prepareCall(retrieveQuestionId);
		callableStatement.setString(1, type);
		callableStatement.setString(2, text);
		callableStatement.setString(3, topic);
		callableStatement.setInt(4, dl);
		callableStatement.setString(5, hint);
		callableStatement.setString(6, exp);
		callableStatement.registerOutParameter(7, OracleTypes.CURSOR);
		callableStatement.executeUpdate();
		rs = (ResultSet) callableStatement.getObject(7);
		int questionId = 0; 
		while(rs.next()){
			questionId = rs.getInt("ret_id");
		}
		cg.closeResult(rs);
		cg.closeStatement(callableStatement);
		stmt = con.prepareStatement("INSERT into QUESTION_BANK values (?,?)");
		stmt.setString(1, course);
		stmt.setInt(2, questionId);
		stmt.executeUpdate();
		cg.closeStatement(stmt);
		if(type.equals("P")){
            System.out.println("How many sets of parameters?");
            int no = sc.nextInt();
            sc.nextLine();
            String params = "";
            System.out.println("Enter parameters one-by-one as comma separated values");
            for(int i=1; i <= no; i++){
            	System.out.println("Enter Parameter set no: " + i);
            	params = sc.nextLine();
            	String qrp = combine(text,params);
            	System.out.println("Enter the number of correct answers: ");
            	int nc = sc.nextInt();
            	sc.nextLine();
            	for(int j=1; j <= nc;j++){
            		System.out.println("Enter correct answer no:" + j);
            		String ans = sc.nextLine();
            		stmt = con.prepareStatement("INSERT into QUESTION_PARAM_ANSWERS values (?,?,?,1)");
            		stmt.setInt(1, questionId);
            		stmt.setString(2, qrp);
            		stmt.setString(3, ans);
            		stmt.executeUpdate();
            		cg.closeStatement(stmt);
            	}
            	System.out.println("Enter the number of incorrect answers: ");
            	int nic = sc.nextInt();
            	sc.nextLine();
            	for(int j=1; j <= nic;j++){
            		System.out.println("Enter incorrect answer no:" + j);
            		String ans = sc.nextLine();
            		stmt = con.prepareStatement("INSERT into QUESTION_PARAM_ANSWERS values (?,?,?,0)");
            		stmt.setInt(1, questionId);
            		stmt.setString(2, qrp);
            		stmt.setString(3, ans);
            		stmt.executeUpdate();
            		cg.closeStatement(stmt);
            	}
            	
            }
            
            
		}
		else{
			System.out.println("Enter the number of correct answers: ");
        	int nc = sc.nextInt();
        	sc.nextLine();
        	for(int j=1; j <= nc;j++){
        		System.out.println("Enter correct answer no:" + j);
        		String ans = sc.nextLine();
        		stmt = con.prepareStatement("INSERT into QUESTION_PARAM_ANSWERS values (?,?,?,1)");
        		stmt.setInt(1, questionId);
        		stmt.setString(2, text);
        		stmt.setString(3, ans);
        		stmt.executeUpdate();
        		cg.closeStatement(stmt);
        	}
        	System.out.println("Enter the number of incorrect answers: ");
        	int nic = sc.nextInt();
        	sc.nextLine();
        	for(int j=1; j <= nic;j++){
        		System.out.println("Enter incorrect answer no:" + j);
        		String ans = sc.nextLine();
        		stmt = con.prepareStatement("INSERT into QUESTION_PARAM_ANSWERS values (?,?,?,0)");
        		stmt.setInt(1, questionId);
        		stmt.setString(2, text);
        		stmt.setString(3, ans);
        		stmt.executeUpdate();
        		cg.closeStatement(stmt);
        	}
		}
		
		//String q = sc.nextLine();
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private static String combine(String text, String params) {
		// TODO Auto-generated method stub
		String [] sArray = params.split(",");
		int ct = 0;
		while(text.indexOf("<?>") != -1){
            text = text.replace("<?>",sArray[ct]);
            ct++;
		}
		return text;
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
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			callableStatement = con.prepareCall(getRelevantOptions);
			callableStatement.setString(1, courseId);
			callableStatement.setString(2, username);
			callableStatement.registerOutParameter(3, OracleTypes.CURSOR);
			callableStatement.executeUpdate();
			rs = (ResultSet) callableStatement.getObject(3);
			List<String> options = new ArrayList<String>();
			while (rs.next()) {

				// System.out.println(rs.getString("col_name"));
				options.add(rs.getString("col_name"));

			}
			cg.closeResult(rs);
			cg.closeStatement(callableStatement);
			stmt=con.prepareStatement("SELECT COURSE_NAME, START_DATE, END_DATE FROM COURSE where COURSE_ID = ?");
			 stmt.setString(1, courseId);
		     rs = stmt.executeQuery();
		     String courseName = "";
		     String stDate = "";
		     String endDate = "";
		     while(rs.next()){
		    	 courseName = rs.getString("COURSE_NAME");
		    	 stDate = rs.getString("START_DATE");
		    	 endDate = rs.getString("END_DATE");
		     }
		     cg.closeResult(rs);
		     cg.closeStatement(stmt);
		     System.out.println("Course Name: " + courseName);
		     System.out.println("Start Date: " + stDate);
		     System.out.println("End Date: " + endDate);
			for(int i=4; i <= options.size();i++){
				System.out.println(i + ": " + options.get(i-1));
			}
			System.out.println("Press 0 to go to previous page");
			
			int selOption = sc.nextInt();
			sc.nextLine();
			if(selOption == 0){
				viewAddCourses();
			}
			else{
			String option = options.get(selOption-1);
			if(option.equals("View Exercise")){
				viewExercise(courseId);
			}
			else if (option.equals("ADD Exercise")){
				addExercise(courseId);
			}
			else if(option.equals("View TA")){
				viewTA(courseId);
			}
			else if(option.equals("Add TA")){
				addTA(courseId);
			}
			else if(option.equals("Enroll/Drop Student")){
				enrollDrop(courseId);
			}
			else if(option.equals("View Report")){
				viewReport(courseId);
			}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private static void viewReport(String courseId) {
		// TODO Auto-generated method stub
		
	}

	private static void enrollDrop(String courseId) {
		// TODO Auto-generated method stub
		
	}

	private static void addTA(String courseId) {
		// TODO Auto-generated method stub
		
	}

	private static void viewTA(String courseId) {
		// TODO Auto-generated method stub
		
	}

	private static void addExercise(String courseId) {
		// TODO Auto-generated method stub
		
		
	}

	private static void viewExercise(String courseId) {
		// TODO Auto-generated method stub
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
			e.printStackTrace();
		}
		
		
	}

	private static void viewSpecificExercise(int no, String courseId) {
		// TODO Auto-generated method stub
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
	    	 
	     }
	     else{
	    	 stmt=con.prepareStatement("SELECT EQ.QUESTION_ID, Q.QUESTION_TEXT, Q.DIFFICULTY_LEVEL, Q.HINT, Q.EXPLANATION, Q.TOPIC_ID FROM EXERCISE_QUESTION EQ inner join QUESTION Q on EQ.QUESTION_ID = Q.QUESTION_ID where EQ.EXERCISE_ID = 1 ");
	     }
	     
	     }
		catch(Exception e){
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
