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


------------------- Queries from program flow------------------------------

select s.STUDENT_ID 
from STUDENT s
where s.STUDENT_ID not in (select asu.STUDENT_ID from  ATTEMPT_SUBMISSION asu where asu.EXERCISE_ID=1);

select asu.STUDENT_ID
from ATTEMPT_SUBMISSION asu
where asu.EXERCISE_ID = 1
and asu.NUMBER_OF_ATTEMPTS = 1 and asu.POINTS = (select max(asui.POINTS) from ATTEMPT_SUBMISSION asui where asui.STUDENT_ID = asu.STUDENT_ID and asui.EXERCISE_ID = 1);

select asu.STUDENT_ID, asu.EXERCISE_ID
from ATTEMPT_SUBMISSION asu
where asu.NUMBER_OF_ATTEMPTS = 1 and asu.POINTS = (select max(asui.POINTS) from ATTEMPT_SUBMISSION asui where asui.STUDENT_ID = asu.STUDENT_ID and asui.EXERCISE_ID = asu.EXERCISE_ID);

select * 
from ATTEMPT_SUBMISSION asu
where asu.STUDENT_ID = ? and asu.EXERCISE_ID = ?;
---------------------------------------------------
select exstsc.Ex,exstsc.St,exstsc.score, ovrall.avg_scr from
(select es.e_id as Ex ,es.s_id as St,
(case es.sp when  'LatestAttempt' 
--then (select  asui.points from ATTEMPT_SUBMISSION asui where asui.EXERCISE_ID = es.e_id and asui.STUDENT_ID =  es.s_id  and rownum = 1 order by asui.NUMBER_OF_ATTEMPTS desc )
then latest.pts
when  'AverageScore' 
then avg(asu.points)
else
max(asu.points)
end) as score
from 
(select e.EXERCISE_ID as e_id, cs.STUDENT_ID as s_id, e.SCORING_POLICY as sp
from 
EXERCISE e, COURSE_STUDENT cs 
where e.COURSE_ID = cs.COURSE_ID) es
left join ATTEMPT_SUBMISSION asu on (asu.EXERCISE_ID = es.e_id and asu.STUDENT_ID = es.s_id)
left join (select asu2.EXERCISE_ID as e_id, asu2.STUDENT_ID as s_id, asu2.points as pts from ATTEMPT_SUBMISSION asu2 where asu2.NUMBER_OF_ATTEMPTS = (select max(asu3.NUMBER_OF_ATTEMPTS) from ATTEMPT_SUBMISSION asu3 where asu3.EXERCISE_ID = asu2.EXERCISE_ID and asu3.STUDENT_ID = asu2.STUDENT_ID)) latest on latest.e_id = es.e_id and latest.s_id = es.s_id
group by es.e_id,es.sp,es.s_id,latest.pts
order by es.e_id) exstsc
inner join ( select avg(et.score) as avg_scr ,et.St as stu from
 (select es.e_id as Ex ,es.s_id as St,
(case es.sp when  'LatestAttempt' 
--then (select  asui.points from ATTEMPT_SUBMISSION asui where asui.EXERCISE_ID = es.e_id and asui.STUDENT_ID =  es.s_id  and rownum = 1 order by asui.NUMBER_OF_ATTEMPTS desc )
then latest.pts
when  'AverageScore' 
then avg(asu.points)
else
max(asu.points)
end) as score
from 
(select e.EXERCISE_ID as e_id, cs.STUDENT_ID as s_id, e.SCORING_POLICY as sp
from 
EXERCISE e, COURSE_STUDENT cs 
where e.COURSE_ID = cs.COURSE_ID) es
left join ATTEMPT_SUBMISSION asu on (asu.EXERCISE_ID = es.e_id and asu.STUDENT_ID = es.s_id)
left join (select asu2.EXERCISE_ID as e_id, asu2.STUDENT_ID as s_id, asu2.points as pts from ATTEMPT_SUBMISSION asu2 where asu2.NUMBER_OF_ATTEMPTS = (select max(asu3.NUMBER_OF_ATTEMPTS) from ATTEMPT_SUBMISSION asu3 where asu3.EXERCISE_ID = asu2.EXERCISE_ID and asu3.STUDENT_ID = asu2.STUDENT_ID)) latest on latest.e_id = es.e_id and latest.s_id = es.s_id
group by es.e_id,es.sp,es.s_id,latest.pts
order by es.e_id) et group by et.St) ovrall
on ovrall.stu = exstsc.St;
-------------
select 
e.EXERCISE_ID as e_id , 
case when MAX(asu.points) is null then 0 else MAX(asu.points) end
, case when MIN(asu.points) is null then 0 else MIN(asu.points) end  
from Exercise e
left join ATTEMPT_SUBMISSION asu on e.EXERCISE_ID = asu.EXERCISE_ID
group by e.EXERCISE_ID;
-----------------



select stu_attempts.e_id, 
case when avg(stu_attempts.no_of_attmpt) is null 
then 0
else
avg(stu_attempts.no_of_attmpt)
end
from 
(select 
e.EXERCISE_ID as e_id , asu.STUDENT_ID as s_id, MAX(asu.NUMBER_OF_ATTEMPTS) as no_of_attmpt 
from Exercise e
left join ATTEMPT_SUBMISSION asu on e.EXERCISE_ID = asu.EXERCISE_ID
group by e.EXERCISE_ID, asu.STUDENT_ID) stu_attempts
group by stu_attempts.e_id;


