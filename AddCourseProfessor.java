/*
* import java.util.Date;
* import java.text.DateFormat;
* import java.text.SimpleDateFormat;
*/

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
		 
		System.out.println("Couses you added: ");
		for(int i=1; i <= courses.size();i++){
		System.out.println(i + ": " + courses.get(i-1));	
		}
		
		System.out.println("Enter Course ID to view the course");
		int newOption = courses.size()+1;
		System.out.println("Press "+ newOption +" to add a new course");
		System.out.println("Press 0 to go back to previous menu");
		//String ip = sc.nextLine();
		//ip ="CSC 540";
		//String ip = sc.next();
		int selOption = sc.nextInt();
		sc.nextLine();
		if(selOption == 0){
			homePage();
		}
		else if(selOption == courses.size()+1){
			addCourse();
		}
		else if(selOption <= courses.size()){
			viewSpecificCourse(courses.get(selOption-1));
		}
		}
		catch(Exception e){
		   e.printStackTrace();	
		}
	}
	
	private static void addCourse() {
		PreparedStatement stmt = null;
		try{
			System.out.println("0. Go back to previous page");
			System.out.println("1. Create New Course");
			int opt = sc.nextInt();
			sc.nextLine();
			if(opt == 0){
				viewAddCourses();
			}
			else if(opt == 1){
			System.out.println("Enter unique Identifier for the Course: ");
			String courseId = sc.nextLine();
			System.out.println("Enter name for the course: ");
			String courseName = sc.nextLine();
			System.out.println("Enter Start Date (MM/dd/yyyy) for the Course :");
			String stDate = sc.nextLine();
			//Date startDate = null;
			java.sql.Date stDateSqlFormat = null;
			try{
				Date startDate = new SimpleDateFormat("MM/dd/yyyy").parse(stDate);
				stDateSqlFormat = new java.sql.Date(startDate.getTime());
			}
			catch(Exception e){
				System.out.println("Error parsing Date");
				stDateSqlFormat = null;
			}
			System.out.println("Enter End Date (MM/dd/yyyy) for the Course");
			String endDate = sc.nextLine();
			java.sql.Date endDateSqlFormat = null;
			try{
				Date enddate = new SimpleDateFormat("MM/dd/yyyy").parse(endDate);
				endDateSqlFormat = new java.sql.Date(enddate.getTime());
			}
			catch(Exception e){
				System.out.println("Error parsing End Date");
				endDateSqlFormat = null;
			}
			stmt=con.prepareStatement("INSERT INTO COURSE values (?,?,?,?,?)");
			 stmt.setString(1, courseId);
			 stmt.setString(2, courseName);
			 stmt.setDate(3, stDateSqlFormat);
			 stmt.setDate(4, endDateSqlFormat);
			 stmt.setString(5, username);
			 stmt.executeUpdate();
			}
		}
		catch(Exception e){
			System.out.println("Encountered Error: "+e.getMessage());
			viewAddCourses();
		}
		viewAddCourses();
		
	}	
		
