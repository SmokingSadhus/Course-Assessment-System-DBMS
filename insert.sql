select * from student
select * from topic
select * from question

select * from course

insert into topic values ('T1','Topic Number one')

insert into question (question_text, difficulty_level,hint,explanation,topic_id) values ('Q1',5,'h','e','T1')

insert into question (question_text, difficulty_level,hint,explanation,topic_id) values ('Q2',8,'h','e','T1')


insert into student values ('amedhek','Abhinav',0)

insert into course (course_id, course_name)values('CSC540','DBMS',)


create or replace FUNCTION checkGrad(student_id IN VARCHAR)
   RETURN Number
   IS is_grad Number;

   BEGIN

	  SELECT IS_GRAD
      INTO is_grad
      FROM student s where s.STUDENT_ID = student_id and rownum = 1;

      RETURN(is_grad);

    END checkGrad;

drop function checkIsGrad
drop function myFunction
drop trigger check_is_grad

if ((select is_grad from student s where s.STUDENT_ID = NEW.student_id and rownum = 1) >= 1)
then 
insert into tp values(1);
END if;


create table tp
(id number
);

insert into ROLE values ('sjha4','S','sjha');
insert into ROLE values ('sjha5','P','sjha');
insert into ROLE values ('sjha6','T','sjha');
insert into ROLE values ('P1','P','sjha');
insert into ROLE values ('P2','P','sjha');

SELECT ROLE FROM ROLE where user_id = 'sjha4'

SELECT * FROM ROLE where user_id = 'sjha4'

---------select * from MENU_OPTIONS-----Menu options -----------------------------------------
insert into MENU_OPTIONS values ('P','Home','View Profile',1,0);
insert into MENU_OPTIONS values ('P','Home','View/Add Courses',2,0);
insert into MENU_OPTIONS values ('P','Home','Enroll/Drop A Student',3,0);
insert into MENU_OPTIONS values ('P','Home','Search/Add questions to Question Bank',4,0);
insert into MENU_OPTIONS values ('P','Home','Logout',5,0);
insert into MENU_OPTIONS values ('T','Home','View Profile',1,0);
insert into MENU_OPTIONS values ('T','Home','View/Add Courses',2,0);
insert into MENU_OPTIONS values ('T','Home','Enroll/Drop A Student',3,0);
insert into MENU_OPTIONS values ('T','Home','Logout',4,0);
insert into MENU_OPTIONS values ('S','Home','View/Edit Profile',1,0);
insert into MENU_OPTIONS values ('S','Home','View Courses',2,0);
insert into MENU_OPTIONS values ('S','Home','Logout',3,0);
insert into MENU_OPTIONS values ('S','Profile','Student ID',1,0);
insert into MENU_OPTIONS values ('T','Profile','Employee ID',1,0);
insert into MENU_OPTIONS values ('P','Profile','Employee ID',1,0);

insert into student values ('sjha4','SameerStud Jha',1);

insert into professor values ('sjha5', 'SameerProf Jha');
insert into professor values ('P1', 'Prof1');
insert into professor values ('P2', 'Prof2');

insert into COURSE values ('CSC 540','DBMS',TO_DATE('09/07/2017', 'MM/DD/YYYY'), TO_DATE('09/08/2017', 'MM/DD/YYYY'),'P1');
insert into COURSE values ('CSC 541','ADS',TO_DATE('09/07/2017', 'MM/DD/YYYY'), TO_DATE('09/08/2017', 'MM/DD/YYYY'),'P1');
insert into COURSE values ('CSC 542','ALDA',TO_DATE('09/07/2017', 'MM/DD/YYYY'), TO_DATE('09/08/2017', 'MM/DD/YYYY'),'P2');

---select * from COURSE
--select * from professor
--select * from student
SELECT col_name FROM MENU_OPTIONS where role = ? and menu_name = ? order by display_order

select * from MENU_OPTIONS



CREATE PROCEDURE SELECT_OPTIONS_PROFESSOR_4(prc out sys_refcursor) 
As
begin
   --if ((select PROFESSOR_ID from course where course_id = c_id) <> p_id)
   --then
  open prc for select col_name from MENU_OPTIONS;

end;

exec SELECT_OPTIONS_PROFESSOR_4
