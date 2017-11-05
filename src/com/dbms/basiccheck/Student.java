package com.dbms.basiccheck;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Date;
import java.util.Collections;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class Student {
	
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
				 stmt.setString(1, "S");
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
			else if(optionSelected.equals("View Courses")){
				//System.out.println("View/Add Courses");
				viewAddCourses();
			}
			else if(optionSelected.equals("View Profile") || optionSelected.equals("View/Edit Profile")){
				//System.out.println("View Profile");
				viewProfile();
			}
		        
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}


		private static void viewProfile() {


			// TODO Auto-generated method stub
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
				 stmt.setString(1, "S");
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
		
        private static void viewAddCourses(){
        	PreparedStatement stmt = null;
			ResultSet rs = null;
			System.out.println("Press 0 to go back");
			System.out.println("The courses enrolled by you are:");
			try {
				stmt=con.prepareStatement("SELECT course_id FROM course_student WHERE student_id = ? ");
				stmt.setString(1, username);
				rs=stmt.executeQuery();
				int i=0;
				while (rs.next()) {
					System.out.println(++i + ". " +  rs.getString("course_id"));
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			cg.closeStatement(stmt);
			cg.closeResult(rs);
			System.out.println("Enter course id to view the course details:");
			String temp=sc.next();
			if (!isNumber(temp)) {
				System.out.println("choose one of the following options");
			    System.out.println("1. Current Open Homeworks");
			    System.out.println("2. Past Homeworks");
			    System.out.println("Press 0 to go back");
			    int temp1 = sc.nextInt();
			    if (temp1==1) {
			    	System.out.println("All open homeworks are:");
			    	try {
			    		stmt=con.prepareStatement("SELECT e.exercise_id FROM exercise e, course_student cs WHERE e.course_id=cs.course_id AND e.deadline > ? AND cs.student_id = ? ");
			    		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			    		stmt.setString(1, df.format(new Date()));
			    		stmt.setString(2, username);
			    		rs=stmt.executeQuery();
			    		int i=0;
			    		while (rs.next()) {
			    			System.out.println(++i + ". " + rs.getString("exercise_id"));
			    		}
			    	}
			    	catch(Exception e) {
						e.printStackTrace();
					}
			    	cg.closeStatement(stmt);
					cg.closeResult(rs);
					System.out.println("Enter Exercise ID to attempt or enter 0 to go back");
					String ex_id = sc.next();
					if (!isNumber(ex_id)) {
						try {
							stmt=con.prepareStatement("Select sc_mode FROM exercise WHERE exercise_id = ?");
							stmt.setString(1, ex_id);
							rs=stmt.executeQuery();
							while(rs.next()) {
								if(rs.getString("sc_mode").equals("standard")) {
									takeStandardTest(ex_id);
									viewAddCourses();
								}
								else if(rs.getString("sc_mode").equals("adaptive")) {
									takeAdaptiveTest(ex_id);
									viewAddCourses();
								}
							}
						}
						catch(Exception e){
							e.printStackTrace();
						}
						cg.closeStatement(stmt);
						cg.closeResult(rs);
					}
					else {
						if (Integer.parseInt(ex_id)==0)
							viewAddCourses();
						else {
							int isZero = 1;
							while(isZero != 0){
								System.out.println("Enter 0 to go back!!!");
								isZero = sc.nextInt();
								}
							homePage();
						}
					}
						
					
			    }
			    else if (temp1==2) {
			    	try {
			    		stmt=con.prepareStatement("SELECT e.start_date, e.end_date, e.sc_mode, (e.total_questions * e.points) AS total_points, e.scoring_policy, e.retries - max(a_s.number_of_attempts) AS available_attempts, max(a_s.number_of_attempts) AS attempts_by_student FROM exercise e, attempt_submission a_s WHERE a_s.exercise_id=e.exercise_id AND a_s.student_id = ?");
			    		stmt.setString(1, username);
			    		rs=stmt.executeQuery();
			    		while (rs.next()) {
			    			System.out.println(rs.getString("start_date")+"  "+rs.getString("end_date")+"  "+rs.getString("sc_mode")+"  "+rs.getString("total_points")+"  "+rs.getString("scoring_policy")+"  "+rs.getString("avaiable_attempts")+"  "+rs.getString("attempts_by_student"));
			    		}
			    	}
			    	catch(Exception e) {
						e.printStackTrace();
					}	
			    	cg.closeStatement(stmt);
					cg.closeResult(rs);
					System.out.println("Enter 0 to go back");
					int isZero = 1;
					while(isZero != 0){
						isZero = sc.nextInt();
						}
					viewAddCourses();
			    }
			    else{
			    	if (temp1==0)
			    	viewAddCourses();
			    	else {
						int isZero = 1;
						while(isZero != 0){
							System.out.println("Enter 0 to go back!!!");
							isZero = sc.nextInt();
							}
						viewAddCourses();
					}
			    }
			}

			else { 
				if (Integer.parseInt(temp)==0)
		    	homePage();
				else {
					int isZero = 1;
					while(isZero != 0){
						System.out.println("Enter 0 to go back!!!");
						isZero = sc.nextInt();
						}
					homePage();
				}
					
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
        
        public static void takeStandardTest(String ex_id) {
        	List<String> questionsList = new ArrayList<String>();
        	PreparedStatement stmt= null;
    		ResultSet rs=null;
        	try {
	    		stmt=con.prepareStatement("SELECT question_id FROM exercise_question WHERE exercise_id = ?");
	    		stmt.setString(1, ex_id);
	    		rs=stmt.executeQuery();
	    		while (rs.next()) {
	    			questionsList.add(rs.getString("question_id"));
	    		}
	    	}
	    	catch(Exception e) {
				e.printStackTrace();
			}	
        	cg.closeStatement(stmt);
			cg.closeResult(rs);
			Collections.shuffle(questionsList);
			for (int i=0; i<questionsList.size(); i++) {
				List<String> questionsTextList=new ArrayList<String>();
				List<String> answersCorrectList=new ArrayList<String>();
				List<String> answersWrongList=new ArrayList<String>();
				HashMap<Integer,String> h_options=new HashMap<>();
				String answerAttempt=null;
				String answer_result=null;
				try {
		    		stmt=con.prepareStatement("SELECT * FROM question_param_answer WHERE question_id = ?");
		    		stmt.setString(1, ex_id);
		    		rs=stmt.executeQuery();
		    		while (rs.next()) {
		    			questionsTextList.add(rs.getString("question"));
		    		}
		    	}
		    	catch(Exception e) {
					e.printStackTrace();
				}	
	        	cg.closeStatement(stmt);
				cg.closeResult(rs);				
				Collections.shuffle(questionsTextList);
				String questionText=questionsTextList.get(0);
				int index=0;
				System.out.println(++index + ". " + questionText);
				try {
		    		stmt=con.prepareStatement("SELECT answer FROM question_param_answer WHERE question = ? WHERE correct=1");
		    		stmt.setString(1, questionText);
		    		rs=stmt.executeQuery();
		    		while (rs.next()) {
		    			answersCorrectList.add(rs.getString("answer"));
		    		}
		    	}
		    	catch(Exception e) {
					e.printStackTrace();
				}
				cg.closeStatement(stmt);
				cg.closeResult(rs);
				try {
		    		stmt=con.prepareStatement("SELECT answer FROM question_param_answer WHERE question = ? AND correct=0");
		    		stmt.setString(1, questionText);
		    		rs=stmt.executeQuery();
		    		while (rs.next()) {
		    			answersWrongList.add(rs.getString("answer"));
		    		}
		    	}
		    	catch(Exception e) {
					e.printStackTrace();
				}
				cg.closeStatement(stmt);
				cg.closeResult(rs);
				Collections.shuffle(answersCorrectList);
				Collections.shuffle(answersWrongList);
				List<String> answerOptions=new ArrayList<String>();
				answerOptions.add(answersCorrectList.get(0));
				answerOptions.add(answersWrongList.get(0));
				answerOptions.add(answersWrongList.get(0));
				answerOptions.add(answersWrongList.get(0));
				
				Collections.shuffle(answerOptions);
				
				for (int j=0;j<answerOptions.size();j++) {
					System.out.println(j+1 + ". " + answerOptions.get(j));
					h_options.put(j+1, answerOptions.get(j));
				}
				int answerOption=sc.nextInt();
				answerAttempt = h_options.get(answerOption);
				try {
		    		stmt=con.prepareStatement("SELECT correct FROM question_param_answer WHERE question = ? AND answer = ?");
		    		stmt.setString(1, questionText);
		    		stmt.setString(2, answerAttempt);
		    		rs=stmt.executeQuery();
		    		while (rs.next()) {
		    			answer_result=rs.getString("correct");
		    		}
		    	}
		    	catch(Exception e) {
					e.printStackTrace();
				}
				cg.closeStatement(stmt);
				cg.closeResult(rs);
			}
				if (answer_result.equals("0")) {
					
					try {
						DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			    		stmt=con.prepareStatement("INSERT INTO attempt_submission (exercise_id,student_id,submission_time,number_of_attempts) VALUES (?,?,TO_DATE(?, 'MM/DD/YYYY'),?)");			    		
			    		stmt.setString(1, ex_id);
			    		stmt.setString(2, username);
			    		stmt.setString(3, df.format(new Date()));
			    		stmt.setString(4, );
			    		
			    		rs=stmt.executeQuery();
			    		while (rs.next()) {
			    			
			    		}
			    			
			    	}
			    	catch(Exception e) {
						e.printStackTrace();
					}
					cg.closeStatement(stmt);
					cg.closeResult(rs);
				}
				else if (answer_result == "1") {
					
				}
				
			     	
        }
        
        public static void takeAdaptiveTest(String ex_id) {
        	
        }

}

class SubmissionResult{
	int questionId;
	String question;
	String answer;
	int correct;
	public SubmissionResult(int questionId, String question, String answer, int correct) {
		this.questionId = questionId;
		this.question = question;
		this.answer = answer;
		this.correct = correct;
	}
}

