	private static void viewTA(String courseId) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
		 stmt=con.prepareStatement("SELECT STUDENT_ID from TA where COURSE_ID = ?");
		 stmt.setString(1, courseId);
	     rs = stmt.executeQuery();
	     System.out.println("Choose 0 to return to course: "+courseId);
	     
	     List<String> tas = new ArrayList<String>();
	     while(rs.next()){
	    	 tas.add(rs.getString("STUDENT_ID"));
		}
	     for(int i=1; i <= tas.size();i++){
			 System.out.println(i + ": " + tas.get(i-1));
		 }
	     System.out.println("Choose TA to remove or press 0 to return to course:" + courseId);
		 int choice  = sc.nextInt();
		 sc.nextLine();
		 if(choice == 0){
			 viewSpecificCourse(courseId);
		 }
		 else{
			 String taId = tas.get(choice-1);
			 DeleteTA(taId,courseId);
			 System.out.println("TA "+ taId+" removed from Course: "+courseId);
			 viewTA(courseId);
			 //viewSpecificCourse(courseId);
		 }
		}
		catch(Exception e){
			System.out.println("Encountered Error: "+e.getMessage());
			viewSpecificCourse(courseId);
		}
		
	}

	private static void DeleteTA(String taId, String courseId) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
		 stmt=con.prepareStatement("DELETE from TA where COURSE_ID = ? AND STUDENT_ID = ?");
		 stmt.setString(1, courseId);
		 stmt.setString(2,taId);
	     rs = stmt.executeQuery();
		}
		catch(Exception e){
			System.out.println("Encountered Error: "+e.getMessage());
			viewTA(courseId);
		}
		
	}
