insert into MENU_OPTIONS values ('P','Home','View Profile',1,1);
insert into MENU_OPTIONS values ('P','Home','View/Add Courses',2,1);
insert into MENU_OPTIONS values ('P','Home','Enroll/Drop A Student',3,1);
insert into MENU_OPTIONS values ('P','Home','Search/Add questions to Question Bank',4,1);
insert into MENU_OPTIONS values ('P','Home','Logout',5,1);
insert into MENU_OPTIONS values ('T','Home','View Profile',1,1);
insert into MENU_OPTIONS values ('T','Home','View/Add Courses',2,1);
insert into MENU_OPTIONS values ('T','Home','Enroll/Drop A Student',3,1);
insert into MENU_OPTIONS values ('T','Home','Logout',4,1);
insert into MENU_OPTIONS values ('S','Home','View/Edit Profile',1,1);
insert into MENU_OPTIONS values ('S','Home','View Courses',2,1);
insert into MENU_OPTIONS values ('S','Home','Logout',3,1);
insert into MENU_OPTIONS values ('S','Profile','Student ID',1,1);
insert into MENU_OPTIONS values ('T','Profile','Employee ID',1,1);
insert into MENU_OPTIONS values ('P','Profile','Employee ID',1,1);
insert into MENU_OPTIONS values ('P','View Course','Course Name',1,1);
insert into MENU_OPTIONS values ('P','View Course','Start Date',2,1);
insert into MENU_OPTIONS values ('P','View Course','End Date',3,1);
insert into MENU_OPTIONS values ('P','View Course','View Exercise',4,1);
insert into MENU_OPTIONS values ('P','View Course','ADD Exercise',5,0);
insert into MENU_OPTIONS values ('P','View Course','View TA',6,1);
insert into MENU_OPTIONS values ('P','View Course','Add TA',7,0);
insert into MENU_OPTIONS values ('P','View Course','Enroll/Drop Student',8,0);
insert into MENU_OPTIONS values ('P','View Course','View Report',9,0);

--viewSpecificExercise
insert into MENU_OPTIONS values ('P','viewSpecificExercise','DELETE QUESTION',2,0);
insert into MENU_OPTIONS values ('P','viewSpecificExercise','ADD QUESTION',1,0);



----------------------------------------------------------------------------------------------------------------------------------------------------------
insert into ROLE values ('tregan','s','tregan');
insert into ROLE values ('jmick','s','jmick');
insert into ROLE values ('mfisher','s','mfisher');
insert into ROLE values ('jander','s','jander');
insert into ROLE values ('jharla','s','jharla');
insert into ROLE values ('aneela','s','aneela');
insert into ROLE values ('mjones','s','mjones');
insert into ROLE values ('jmoyer','s','jmoye');
insert into ROLE values ('kogan','p','kogan');
insert into ROLE values ('rchirkova','p','rchirkova');
insert into ROLE values ('chealey','p','chealey');



----------------------------------------------------------------------------------------------------------------------------------------------------------
insert into STUDENT values ('tregan','Tom​ ​Regan',0);
insert into STUDENT values ('jmick','Jenelle​ ​Mick',1);
insert into STUDENT values ('mfisher','Michal​ ​Fisher',0);
insert into STUDENT values ('jander','Joseph​ ​Anderson',0);
insert into STUDENT values ('jharla','Jitendra​ ​Harlalka',1);
insert into STUDENT values ('aneela','Aishwarya​ ​Neelakantan',1);
insert into STUDENT values ('mjones','Mary​ ​Jones',1);
insert into STUDENT values ('jmoyer','James​ ​Moyer',1);



----------------------------------------------------------------------------------------------------------------------------------------------------------
insert into PROFESSOR values ('kogan','​ ​Kemafor​ ​Ogan');
insert into PROFESSOR values ('rchirkova','​ ​Rada​ ​Chirkova');
insert into PROFESSOR values ('chealey','​ ​Christipher​ ​Healey');



----------------------------------------------------------------------------------------------------------------------------------------------------------
insert into COURSE values ('CSC440','​Database Systems ',TO_DATE('08/27/2017', 'MM/DD/YYYY'),TO_DATE('12/12/2017', 'MM/DD/YYYY'), 'rchirkova');
insert into COURSE values ('CSC540','​Database Systems ',TO_DATE('09/27/2017', 'MM/DD/YYYY'),TO_DATE('12/10/2017', 'MM/DD/YYYY'), 'kogan');
insert into COURSE values ('CSC541','​Advanced Data Structures ',TO_DATE('09/25/2017', 'MM/DD/YYYY'),TO_DATE('12/06/2017', 'MM/DD/YYYY'), 'chealey');



----------------------------------------------------------------------------------------------------------------------------------------------------------
insert into COURSE_STUDENT values ('CSC440','​tregan');
insert into COURSE_STUDENT values ('CSC440','mfisher​');
insert into COURSE_STUDENT values ('CSC440','​jander');
insert into COURSE_STUDENT values ('CSC540','​aneela');
insert into COURSE_STUDENT values ('CSC540','​mjones');
insert into COURSE_STUDENT values ('CSC540','​jmick');
insert into COURSE_STUDENT values ('CSC541','aneela');
insert into COURSE_STUDENT values ('CSC541','​mjones');
insert into COURSE_STUDENT values ('CSC541','​jmick');



----------------------------------------------------------------------------------------------------------------------------------------------------------
insert into COURSE_TOPIC values ('CSC440','​04');
insert into COURSE_TOPIC values ('CSC440','​06');
insert into COURSE_TOPIC values ('CSC540','​01');
insert into COURSE_TOPIC values ('CSC540','​03');
insert into COURSE_TOPIC values ('CSC540','​04');
insert into COURSE_TOPIC values ('CSC540','​06');
insert into COURSE_TOPIC values ('CSC541','​04');
insert into COURSE_TOPIC values ('CSC541','​06');



----------------------------------------------------------------------------------------------------------------------------------------------------------
insert into TA values ('CSC440','​aneela');
insert into TA values ('CSC440','​​jmick');
insert into TA values ('CSC540','​jharla');
insert into TA values ('CSC541','jmoye');



----------------------------------------------------------------------------------------------------------------------------------------------------------
insert into TOPIC values ('01','ER​ ​Model');
insert into TOPIC values ('02','SQL');
insert into TOPIC values ('03','Storing​ ​Data:Disks​ ​and​ ​Files');
insert into TOPIC values ('04','Primary​ ​File​ ​Organization');
insert into TOPIC values ('05','Hashing​ ​Techniques');
insert into TOPIC values ('06','Binary​ ​Tree​ ​Structures');
insert into TOPIC values ('07','AVL​ ​Trees');
insert into TOPIC values ('08','Sequential​ ​File​ ​Organization');
insert into TOPIC values ('09','BinarySearch');
insert into TOPIC values ('10','Interpolation​ ​Search');



----------------------------------------------------------------------------------------------------------------------------------------------------------
insert into QUESTION values (1,'Question​ ​1?​',2,'Hint​ ​text​ ​Q1','detailed​ ​Explanation​ ​Q1','f','01');
insert into QUESTION values (2,'Question​ ​2​?',3,'Hint​ ​text​ ​Q2','detailed​ ​Explanation​ ​Q2','f','01');
insert into QUESTION values (3,'Question​ ​3​?',2,'Hint​ ​text​ ​Q3','detailed​ ​Explanation​ ​Q3','p','01');



----------------------------------------------------------------------------------------------------------------------------------------------------------
insert into QUESTION_BANK values ('CSC540',1);
insert into QUESTION_BANK values ('CSC540',2);
insert into QUESTION_BANK values ('CSC540',3);



----------------------------------------------------------------------------------------------------------------------------------------------------------
insert into QUESTION_PARAM_ANSWERS values ('1','Question​ ​1?​', 'Correct Ans​ ​1',1, '');
insert into QUESTION_PARAM_ANSWERS values ('1','Question​ ​1?​', 'Correct Ans​ ​2',1, '');
insert into QUESTION_PARAM_ANSWERS values ('1','Question​ ​1?​', 'Incorrect Ans 3',0, 'short explanation 3');
insert into QUESTION_PARAM_ANSWERS values ('1','Question​ ​1?​', 'Incorrect Ans 4',0, 'short explanation 4');
insert into QUESTION_PARAM_ANSWERS values ('1','Question​ ​1?​', 'Incorrect Ans 5',0, 'short explanation 5');
insert into QUESTION_PARAM_ANSWERS values ('1','Question​ ​1?​', 'Incorrect Ans 6',0, 'short explanation 6');
insert into QUESTION_PARAM_ANSWERS values ('2','Question​ ​2?​', 'Correct Ans 1',1, '');
insert into QUESTION_PARAM_ANSWERS values ('2','Question​ ​2?​', 'Correct Ans 2',1, '');
insert into QUESTION_PARAM_ANSWERS values ('2','Question​ ​2?​', 'Correct Ans 3',1, '');
insert into QUESTION_PARAM_ANSWERS values ('2','Question​ ​2?​', 'Incorrect Ans 4',0, 'short explanation 4');
insert into QUESTION_PARAM_ANSWERS values ('2','Question​ ​2?​', 'Incorrect Ans 5',0, 'short explanation 5');
insert into QUESTION_PARAM_ANSWERS values ('2','Question​ ​2?​', 'Incorrect Ans 6',0, 'short explanation 6');

insert into QUESTION_PARAM_ANSWERS values ('3','Consider​ ​a​ ​disk​ ​with​ ​a​ ​512 bytes,​ 2000,​ 50,​ 5,​ 10msec. What​ ​is​ ​the​ ​capacity​ ​of​ ​a​ ​track​ ​in​ ​bytes? ​', 'Correct Ans 1v1',1, '');
insert into QUESTION_PARAM_ANSWERS values ('3','Consider​ ​a​ ​disk​ ​with​ ​a​ ​512 bytes,​ 2000,​ 50,​ 5,​ 10msec. What​ ​is​ ​the​ ​capacity​ ​of​ ​a​ ​track​ ​in​ ​bytes?​', 'Correct Ans 2v1',1, '');
insert into QUESTION_PARAM_ANSWERS values ('3','Consider​ ​a​ ​disk​ ​with​ ​a​ ​512 bytes,​ 2000,​ 50,​ 5,​ 10msec. What​ ​is​ ​the​ ​capacity​ ​of​ ​a​ ​track​ ​in​ ​bytes?​', 'Correct Ans 3v1',1, '');
insert into QUESTION_PARAM_ANSWERS values ('3','Consider​ ​a​ ​disk​ ​with​ ​a​ ​512 bytes,​ 2000,​ 50,​ 5,​ 10msec. What​ ​is​ ​the​ ​capacity​ ​of​ ​a​ ​track​ ​in​ ​bytes?​', 'Incorrect Ans 4v1',0, 'short explanation 4');
insert into QUESTION_PARAM_ANSWERS values ('3','Consider​ ​a​ ​disk​ ​with​ ​a​ ​512 bytes,​ 2000,​ 50,​ 5,​ 10msec. What​ ​is​ ​the​ ​capacity​ ​of​ ​a​ ​track​ ​in​ ​bytes?​', 'Incorrect Ans 5v1',0, 'short explanation 5');
insert into QUESTION_PARAM_ANSWERS values ('3','Consider​ ​a​ ​disk​ ​with​ ​a​ ​512 bytes,​ 2000,​ 50,​ 5,​ 10msec. What​ ​is​ ​the​ ​capacity​ ​of​ ​a​ ​track​ ​in​ ​bytes?​', 'Incorrect Ans 6v1',0, 'short explanation 6');
insert into QUESTION_PARAM_ANSWERS values ('3','Consider​ ​a​ ​disk​ ​with​ ​a​ ​512 bytes,​ 2000,​ 50,​ 5,​ 10msec. What​ ​is​ ​the​ ​capacity​ ​of​ ​a​ ​track​ ​in​ ​bytes?', 'Incorrect Ans 7v1',0, 'short explanation 7');
insert into QUESTION_PARAM_ANSWERS values ('3','Consider​ ​a​ ​disk​ ​with​ ​a​ ​512 bytes,​ 2000,​ 50,​ 5,​ 10msec. What​ ​is​ ​the​ ​capacity​ ​of​ ​a​ ​track​ ​in​ ​bytes?​', 'Incorrect Ans 8v1',0, 'short explanation 8');

insert into QUESTION_PARAM_ANSWERS values ('3','Consider​ ​a​ ​disk​ ​with​ ​a​ ​256 bytes,​ 1000,​ 100,​ 10,​ 20msec. What​ ​is​ ​the​ ​capacity​ ​of​ ​a​ ​track​ ​in​ ​bytes? ​', 'Correct Ans 1v2',1, '');
insert into QUESTION_PARAM_ANSWERS values ('3','Consider​ ​a​ ​disk​ ​with​ ​a​ ​256 bytes,​ 1000,​ 100,​ 10,​ 20msec. What​ ​is​ ​the​ ​capacity​ ​of​ ​a​ ​track​ ​in​ ​bytes?', 'Correct Ans 2v2',1, '');
insert into QUESTION_PARAM_ANSWERS values ('3','Consider​ ​a​ ​disk​ ​with​ ​a​ ​256 bytes,​ 1000,​ 100,​ 10,​ 20msec. What​ ​is​ ​the​ ​capacity​ ​of​ ​a​ ​track​ ​in​ ​bytes?', 'Correct Ans 3v2',1, '');
insert into QUESTION_PARAM_ANSWERS values ('3','Consider​ ​a​ ​disk​ ​with​ ​a​ ​256 bytes,​ 1000,​ 100,​ 10,​ 20msec. What​ ​is​ ​the​ ​capacity​ ​of​ ​a​ ​track​ ​in​ ​bytes?', 'Incorrect Ans 4v1',0, 'short explanation 4');
insert into QUESTION_PARAM_ANSWERS values ('3','Consider​ ​a​ ​disk​ ​with​ ​a​ ​256 bytes,​ 1000,​ 100,​ 10,​ 20msec. What​ ​is​ ​the​ ​capacity​ ​of​ ​a​ ​track​ ​in​ ​bytes?', 'Incorrect Ans 5v1',0, 'short explanation 5');
insert into QUESTION_PARAM_ANSWERS values ('3','Consider​ ​a​ ​disk​ ​with​ ​a​ ​256 bytes,​ 1000,​ 100,​ 10,​ 20msec. What​ ​is​ ​the​ ​capacity​ ​of​ ​a​ ​track​ ​in​ ​bytes?', 'Incorrect Ans 6v1',0, 'short explanation 6');
insert into QUESTION_PARAM_ANSWERS values ('3','Consider​ ​a​ ​disk​ ​with​ ​a​ ​256 bytes,​ 1000,​ 100,​ 10,​ 20msec. What​ ​is​ ​the​ ​capacity​ ​of​ ​a​ ​track​ ​in​ ​bytes?​', 'Incorrect Ans 7v1',0, 'short explanation 7');
insert into QUESTION_PARAM_ANSWERS values ('3','Consider​ ​a​ ​disk​ ​with​ ​a​ ​256 bytes,​ 1000,​ 100,​ 10,​ 20msec. What​ ​is​ ​the​ ​capacity​ ​of​ ​a​ ​track​ ​in​ ​bytes?', 'Incorrect Ans 8v1',0, 'short explanation 8');



----------------------------------------------------------------------------------------------------------------------------------------------------------
insert into EXERCISE (exercise_id,course_id,name,deadline,total_questions,retries,start_date,end_date,points,penalty,scoring_policy,sc_mode) values (1,'CSC540','homework 1',TO_DATE('09/19/2017', 'MM/DD/YYYY'),3,2,TO_DATE('08/12/2017', 'MM/DD/YYYY'),TO_DATE('09/07/2017', 'MM/DD/YYYY'),3,1,'LatestAttempt','STANDARD');
insert into EXERCISE (exercise_id,course_id,name,deadline,total_questions,retries,start_date,end_date,points,penalty,scoring_policy,sc_mode) values (2,'CSC540','homework 2',TO_DATE('10/10/2017', 'MM/DD/YYYY'),3,1,TO_DATE('09/21/2017', 'MM/DD/YYYY'),TO_DATE('10/10/2017', 'MM/DD/YYYY'),4,1,'AverageScore','ADAPTIVE');
insert into EXERCISE (exercise_id,course_id,name,deadline,total_questions,retries,start_date,end_date,points,penalty,scoring_policy,sc_mode) values (3,'CSC540','homework 3',TO_DATE('10/30/2017', 'MM/DD/YYYY'),3,-1,TO_DATE('10/12/2017', 'MM/DD/YYYY'),TO_DATE('10/30/2017', 'MM/DD/YYYY'),4,0,'AverageScore','STANDARD');



----------------------------------------------------------------------------------------------------------------------------------------------------------
insert into EXERCISE_QUESTION values (1,1);
insert into EXERCISE_QUESTION values (1,2);
insert into EXERCISE_QUESTION values (1,3);
insert into EXERCISE_QUESTION values (3,1);
insert into EXERCISE_QUESTION values (3,2);
insert into EXERCISE_QUESTION values (3,3);



----------------------------------------------------------------------------------------------------------------------------------------------------------
insert into ADAPTIVE_EXERCISE_TOPIC(2,1);



----------------------------------------------------------------------------------------------------------------------------------------------------------
insert into ATTEMPT_SUBMISSION(attempt_id,exercise_id,student_id,points,number_of_attempts) values(1,1,'mjones',5,1);
insert into ATTEMPT_SUBMISSION(attempt_id,exercise_id,student_id,points,number_of_attempts) values(2,1,'mjones',9,2);
insert into ATTEMPT_SUBMISSION(attempt_id,exercise_id,student_id,points,number_of_attempts) values(3,1,'jmick',9,1);
insert into ATTEMPT_SUBMISSION(attempt_id,exercise_id,student_id,points,number_of_attempts) values(4,2,'aneela',7,1);
insert into ATTEMPT_SUBMISSION(attempt_id,exercise_id,student_id,points,number_of_attempts) values(5,2,'aneela',12,2);
insert into ATTEMPT_SUBMISSION(attempt_id,exercise_id,student_id,points,number_of_attempts) values(6,2,'mjones',3,1);
insert into ATTEMPT_SUBMISSION(attempt_id,exercise_id,student_id,points,number_of_attempts) values(7,2,'mjones',7,2);
insert into ATTEMPT_SUBMISSION(attempt_id,exercise_id,student_id,points,number_of_attempts) values(8,3,'aneela',8,1);
insert into ATTEMPT_SUBMISSION(attempt_id,exercise_id,student_id,points,number_of_attempts) values(9,3,'aneela',4,2);
insert into ATTEMPT_SUBMISSION(attempt_id,exercise_id,student_id,points,number_of_attempts) values(10,3,'aneela',12,3);
insert into ATTEMPT_SUBMISSION(attempt_id,exercise_id,student_id,points,number_of_attempts) values(11,3,'mjones',8,1);
insert into ATTEMPT_SUBMISSION(attempt_id,exercise_id,student_id,points,number_of_attempts) values(12,3,'mjones',12,2);