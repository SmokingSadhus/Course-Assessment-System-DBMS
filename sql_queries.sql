----------------------------------------------INSTRUCTOR-------------------------------------------------------------------------
--- 1)
SELECT p.PROFESSOR_ID, p.NAME, p.PASSWORD, p.ADDRESS, c.COURSE_ID, c.COURSE_NAME
FROM PROFESSOR p, COURSE c
WHERE p.PROFESSOR_ID=c.PROFESSOR_ID;

--- 2)
--a)
CREATE VIEW COURSE_VIEW
AS SELECT *
FROM COURSE
WHERE c.COURSE_ID='shit';

SELECT * FROM COURSE_VIEW;

SELECT *
FROM COURSE
WHERE c.PROFESSOR_ID='shit';

--b)
---------------------- Done by the prof initially-----------------------------------------
INSERT INTO COURSE
VALUES ('courese_id','course_name','start_date','end_date','professor_id');

INSERT INTO TA
VALUES ('course_id','student_id');

INSERT INTO COURSE_STUDENT
VALUES ('course_id','student_id');  ----Enroll student

INSERT INTO COURSE_TOPIC
VALUES ('course_id','topic_id'); ------ make sure topic and course exist



---------------------- TAs are added for an existing course (wehther a student or the course exists needs to be checked)--------

INSERT INTO TA
VALUES ('course_id','student_id');

--- 3) make sure that specific student exists and the course exists
INSERT INTO COURSE_STUDENT
VALUES ('course_id','student_id');

DELETE FROM COURSE_STUDENT
WHERE STUDENT_ID = 'shit';

--- 4)
CREATE VIEW STUDENT_WITHOUT_PASSWORD
AS SELECT s.STUDENT_ID, s.NAME, s.STUDENT_LEVEL, s.ADDRESS
FROM STUDENT s;

SELECT * FROM STUDENT_WITHOUT_PASSWORD;

--- 5)
-------------same as 2b)

--- 6)
--a)
SELECT *
FROM EXERCISE
WHERE EXERCISE_ID = 'shit' and COURSE_ID = 'shit'

--b)
INSERT INTO EXERCISE
VALUES ('exercise_id','course_id','name','deadline','total_questions','retries','start_date','end_date','points','penalty','scoring_policy','sc_mode')
      --- students shouldn't be allowed to view after deadline
	  
--- 7)
-- a) 
SELECT *
FROM QUESTION_BANK qb
WHERE qb.QUESTION_ID = 'shit' OR qb.COURSE_ID IN (SELECT ct.COURSE_ID 
FROM COURSE_TOPIC ct
WHERE ct.TOPIC_ID = 'shit');

--b)
INSERT INTO QUESTION_BANK
VALUES ('course_id','question_id');

INSERT INTO QUESTION
VALUES ('question_id','question_text','difficulty_level','hint','explanation','topic_id');

--- 8) --- for every exercise created
DELETE FROM EXERCISE_QUESTION
WHERE QUESTION_ID = 'shit'

INSERT INTO EXERCISE_QUESTION
VALUES ('exercise_id','question_id')

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

----- TA

--- 1) profile
SELECT s.STUDENT_ID, s.NAME, s.STUDENT_LEVEL, s.ADDRESS, TA.COURSE_ID, e.EXERCISE_ID
FROM TA, STUDENT s, EXERCISE e
WHERE TA.STUDENT_ID = s.STUDENT_ID AND TA.STUDENT_ID='shit' AND e.COURSE_ID = TA.COURSE_ID;

--- 2) 
INSERT INTO COURSE_STUDENT
VALUES ('course_id','student_id')

DELETE FROM COURSE_STUDENT
WHERE STUDENT_ID = 'shit'

--- 3)
SELECT * FROM STUDENT_WITHOUT_PASSWORD;

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

----- Student
--- 1) profile
SELECT STUDENT_ID, NAME
FROM STUDENT

--- 2) Courses
-- Current hw

SELECT EXERCISE_ID
FROM EXERCISE
WHERE DEADLINE < 'current date/time';

--past hws
SELECT e.EXERCISE_ID, e.START_DATE, e.END_DATE, e.SC_MODE, e.POINTS * e.TOTAL_QURSTIONS as TOTAL_POINTS, e.SCORING_POLICY, a_s.POINTS, e.RETRIES - max(a_s.NUMBER_OF_ATTEMPTS) AS AVAILABLE_ATTEMPTS, max(a_s.NUMBER_OF_ATTEMPTS) AS ATTEMPTS_TAKEN, q.QUESTION_TEXT, sr.GIVEN_ANSWER, a.ANSWER, qpa.CORRECT, e.POINTS
FROM EXERCISE e, ATTEMPT_SUBMISSION a_s, QUESTION_PARAM_ANSWER qpa, QUESTION q, EXERCISE_QUESTION eq, SUBMISSION_RESULT sr, ANSWER a
WHERE e.deadline > 'current date/time' AND a_s.EXERCISE_ID = e.EXERCISE_ID AND e.EXERCISE_ID = eq.EXERCISE_ID AND eq.QUESTION_ID = q.QUESTION_ID AND a_s.ATTEMPT_ID = sr.ATTEMPT_ID AND q.QUESTION_ID = qpa.QUESTION_ID AND qpa.ANSWER_ID = a.ID





























