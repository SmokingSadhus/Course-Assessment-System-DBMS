select
s.Name
from Student s, COURSE_STUDENT cs
where cs.student_id = s.student_id
and cs.course_id = 'CSC540'
and ((select count(attempt_id) from ATTEMPT_SUBMISSION asu where asu.student_id = cs.student_id and asu.exercise_id =1) <> 0)
and ((select count(attempt_id) from ATTEMPT_SUBMISSION asu where asu.student_id = cs.student_id and asu.exercise_id =2) = 0);

select 
unique(s.name) 
from ATTEMPT_SUBMISSION asu1, ATTEMPT_SUBMISSION asu2, Student s
where (asu1.exercise_id = asu2.exercise_id and asu1.student_id = asu2.student_id and asu1.number_of_attempts = 1 and asu2.number_of_attempts = 2 and asu1.points < asu2.points)
and asu1.student_id = s.student_id;

select
c.course_id, c.course_name,count(cs.STUDENT_ID)
from COURSE c, COURSE_STUDENT cs
where c.COURSE_ID = cs.COURSE_ID
group by c.COURSE_ID,c.COURSE_NAME;

---- Nulls not considered-----------------
select
s.STUDENT_ID, s.Name, e.NAME, asu.NUMBER_OF_ATTEMPTS as Attempt_Number, asu.POINTS, asu.SCORE, asu.SUBMISSION_TIME
from  COURSE_STUDENT cs, Student s, ATTEMPT_SUBMISSION asu, EXERCISE e
where cs.student_id = s.student_id
and cs.course_id = 'CSC540'
and cs.STUDENT_ID = asu.STUDENT_ID
and asu.EXERCISE_ID = e.EXERCISE_ID
order by e.NAME, s.STUDENT_ID, asu.NUMBER_OF_ATTEMPTS;
--------------------------------------------

select student_exercise.s_id, student_exercise.s_name, student_exercise.e_name, asu.NUMBER_OF_ATTEMPTS as Attempt_Number, asu.POINTS, asu.SCORE, asu.SUBMISSION_TIME  from
(select
cs.STUDENT_ID as s_id, s.Name as s_name, e.NAME as e_name, e.EXERCISE_ID as e_id
from  Exercise e, Course_Student cs, Student s
where s.student_id = cs.student_id
and e.course_id = 'CSC540'
and cs.course_id = 'CSC540') student_exercise
left join ATTEMPT_SUBMISSION asu on (student_exercise.s_id = asu.STUDENT_ID and student_exercise.e_id = asu.EXERCISE_ID)
order by student_exercise.e_name, student_exercise.s_id, asu.NUMBER_OF_ATTEMPTS; 
