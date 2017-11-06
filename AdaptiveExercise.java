public static void takeAdaptiveTest(int ex_id) {         //////////////add course_id and retries as args
        	List<SubmissionResult> srAttributes= new ArrayList<SubmissionResult>();
        	List<Integer> questionList = new ArrayList<>();
        	List<String> correctness=new ArrayList<>();
			HashMap<Integer,List<String>> DQ=new HashMap<>();
        	int retriesAllowed=3;          ////////////////////remove
        	String topic_id=null;
        	int attempt_id=0;
    		int attempt_number=0;
    		int total_points=0;
    		int correct_points=0;
    		int wrong_points=0;
    		String course_id=null; 			////////////////////remove
    		int total_questions=0;
    		PreparedStatement stmt= null;
    		ResultSet rs=null;
    		boolean topic_exists=false;
    		try {
	    		stmt=con.prepareStatement("SELECT total_questions FROM exercise WHERE exercise_id=?");
	    		stmt.setInt(1, ex_id);
	    		rs=stmt.executeQuery();
	    		while (rs.next()) {
	    			total_questions=rs.getInt("total_questions");
	    		}
	    	}
	    	catch(Exception e) {
	    		System.out.println("Encountered an Error: "+e.getMessage());
	    		//takeAdaptiveTest(ex_id,retriesAllowed);
			}
			cg.closeStatement(stmt);
			cg.closeResult(rs);
    		try {
	    		stmt=con.prepareStatement("SELECT count(number_of_attempts) AS number_of_attempts FROM attempt_submission WHERE exercise_id = ?");
	    		stmt.setInt(1, ex_id);
	    		rs=stmt.executeQuery();
	    		while (rs.next()) {
	    			attempt_number = rs.getInt("number_of_attempts"); 
	    		}
	    	}
	    	catch(Exception e) {
	    		System.out.println("Encountered an Error: "+e.getMessage());
	    		//takeAdaptiveTest(ex_id,retriesAllowed);
			}	
        	cg.closeStatement(stmt);
			cg.closeResult(rs);
			if (attempt_number >= retriesAllowed && retriesAllowed != -1) {
				System.out.println("No more attempts left!!!");
				return;
			}
			try {
	    		stmt=con.prepareStatement("SELECT topic_id, count(1) AS topic_exists FROM adaptive_exercise_topic WHERE exercise_id = ?");
	    		stmt.setInt(1, ex_id);
	    		rs=stmt.executeQuery();
	    		while (rs.next()) {
	    			int temp = rs.getInt("topic_exists");
	    			topic_id = rs.getString("topic_id");
	    			if (temp==1) topic_exists=true;
	    		}
	    	}
	    	catch(Exception e) {
	    		System.out.println("Encountered an Error: "+e.getMessage());
	    		//takeAdaptiveTest(ex_id,retriesAllowed);
			}
			cg.closeStatement(stmt);
			cg.closeResult(rs);
			if(topic_exists) {
				try {
		    		stmt=con.prepareStatement("SELECT q.question_id FROM question q, adaptive_exercise_topic aet  WHERE q.topic_id=aet.topic_id and aet.topic_id = ?");
		    		stmt.setString(1, topic_id);
		    		rs=stmt.executeQuery();
		    		while (rs.next()) {
		    			questionList.add(rs.getInt("question_id"));
		    		}
		    	}
		    	catch(Exception e) {
		    		System.out.println("Encountered an Error: "+e.getMessage());
		    		//takeAdaptiveTest(ex_id,retriesAllowed);
				}
				cg.closeStatement(stmt);
				cg.closeResult(rs);				
			}
			else {
				try {
		    		stmt=con.prepareStatement("SELECT q.question_id FROM question q, question_bank qb WHERE q.question_id=qb.question_id and qb.course_id = ?");
		    		stmt.setString(1, course_id);
		    		rs=stmt.executeQuery();
		    		while (rs.next()) {
		    			questionList.add(rs.getInt("question_id"));
		    		}
		    	}
		    	catch(Exception e) {
		    		System.out.println("Encountered an Error: "+e.getMessage());
		    		//takeAdaptiveTest(ex_id,retriesAllowed);
				}
				cg.closeStatement(stmt);
				cg.closeResult(rs);	
			}
			for (int i=0; i<questionList.size(); i++) {
				try {
		    		stmt=con.prepareStatement("SELECT question,difficulty_level FROM question WHERE question_id = ?");
		    		stmt.setInt(1, questionList.get(i));
		    		rs=stmt.executeQuery();
		    		while (rs.next()) {
		    			if (DQ.containsKey(rs.getInt("difficulty_level"))) {
		    				List<String> l = DQ.get(rs.getInt("difficulty_level"));
		    				Collections.shuffle(l);
		    				l.add(rs.getString("question"));
		    				DQ.put(rs.getInt("difficulty_level"),l);
		    			}
		    			else {
		    				List<String> l = new ArrayList<String>();
		    				l.add(rs.getString("question"));
		    				DQ.put(rs.getInt("difficulty_level"),l );
		    			}
		    		}
		    	}
		    	catch(Exception e) {
		    		System.out.println("Encountered an Error: "+e.getMessage());
		    		//takeAdaptiveTest(ex_id,retriesAllowed);
				}
				cg.closeStatement(stmt);
				cg.closeResult(rs);
			}
			int index=0;
			int temp_difficulty=3;
			
			for (int j=0; j<total_questions;j++) {
				int q_id=0;
				String answerAttempt=null;
				List<String> temp=new ArrayList<>();
				List<String> answersCorrectList=new ArrayList<>();
				List<String> answersWrongList=new ArrayList<>();
				temp= DQ.get(temp_difficulty);
				String answer_result=null;
				System.out.println(++index + ". " + temp.get(0));
				try {
			   		stmt=con.prepareStatement("SELECT answer FROM question_param_answer WHERE question = ? WHERE correct=1");
			   		stmt.setString(1, temp.get(0));
			   		
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
			   		stmt.setString(1, temp.get(0));
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
				HashMap<Integer,String> h_options=new HashMap<>();
				answerOptions.add(answersCorrectList.get(0));
     			answerOptions.add(answersWrongList.get(0));
				answerOptions.add(answersWrongList.get(0));
				answerOptions.add(answersWrongList.get(0));
					
				Collections.shuffle(answerOptions);
					
				for (int k=0;k<answerOptions.size();k++) {
					System.out.println(k+1 + ". " + answerOptions.get(j));
					h_options.put(j+1, answerOptions.get(j));
				}
					
				int answerOption=sc.nextInt();
				answerAttempt = h_options.get(answerOption);
				try {
		    		stmt=con.prepareStatement("SELECT correct FROM question_param_answer WHERE question = ? AND answer = ?");
		    		stmt.setString(1, temp.get(0));
		    		stmt.setString(2, answerAttempt);
		    		rs=stmt.executeQuery();
		    		while (rs.next()) {
		    			answer_result=rs.getString("correct");
		    			if (answer_result.equals("0") && temp_difficulty!=1)
		    				temp_difficulty--;
		    			else if (answer_result.equals("1") && temp_difficulty!=6)
		    				temp_difficulty++;
		    			correctness.add(answer_result);
		    		}
		    	}
		    	catch(Exception e) {
					e.printStackTrace();
				}
				try {
		    		stmt=con.prepareStatement("SELECT question_id FROM question_param_answer WHERE question = ?");
		    		stmt.setString(1, temp.get(0));
		    		rs=stmt.executeQuery();
		    		while (rs.next()) {
		    			q_id=rs.getInt("question_id");
		    		}
		    	}
		    	catch(Exception e) {
					e.printStackTrace();
				}
				SubmissionResult values=new SubmissionResult(q_id,temp.get(0),answerAttempt,Integer.parseInt(answer_result));
				srAttributes.add(values);
				
				cg.closeStatement(stmt);
				cg.closeResult(rs);
			}
			attempt_number++;
			try {
	    		stmt=con.prepareStatement("SELECT points,penalty FROM exercise WHERE exercise_id=?");			    		
	    		stmt.setInt(1, ex_id);
	    		rs=stmt.executeQuery();
	    		while (rs.next()) {
	    			correct_points=Integer.parseInt(rs.getString("points"));
	    			wrong_points=Integer.parseInt(rs.getString("penalty"));
	    		}
	    			
	    	}
	    	catch(Exception e) {
				e.printStackTrace();
			}

			for (int i=0;i<correctness.size();i++) {
				if(correctness.get(i).equals("0")) total_points-=wrong_points;
				else if(correctness.get(i).equals("1")) total_points+=correct_points;
			}	
			
			CallableStatement callableStatement = null;
			try {
				DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
				String retrieveAttemptId = "{call INSERT_AS_AND_RETURN_ID(?,?,?,?,?,?)}";
				callableStatement = con.prepareCall(retrieveAttemptId);
				callableStatement.setInt(1,ex_id);
				callableStatement.setString(2, username);
				callableStatement.setString(3, df.format(new Date()));
				callableStatement.setInt(4, total_points);
				callableStatement.setInt(5, attempt_number);
				callableStatement.registerOutParameter(6, OracleTypes.CURSOR);
				callableStatement.executeUpdate();
				rs = (ResultSet) callableStatement.getObject(6);
				
				while(rs.next()){
					attempt_id = rs.getInt("ret_id");
				}
				cg.closeResult(rs);
				cg.closeStatement(callableStatement);
			}

	    	catch(Exception e) {
				e.printStackTrace();
			}
				
			for (int i=0; i<srAttributes.size();i++) {
				SubmissionResult attribute=srAttributes.get(i);
				try {
		    		stmt=con.prepareStatement("INSERT INTO submission_result (attempt_id,question_id,question,answer,correct) VALUES (?,?,TO_DATE(?, 'MM/DD/YYYY'),?,?)");			    		
		    		stmt.setInt(1, attempt_id );
		    		stmt.setInt(2, attribute.questionId);
		    		stmt.setString(3, attribute.question);
		    		stmt.setString(4,attribute.answer);
		    		stmt.setInt(5, attribute.correct);
		    		rs=stmt.executeQuery();
				}

		    	catch(Exception e) {
					e.printStackTrace();
				}
				cg.closeStatement(stmt);
				cg.closeResult(rs);
			}	
				
				
			
			
			
        	
        }
