# School System

## Author: Yi Wang 13613

This is a project for the final in introduction to programming.

This project mimics a school.

This code is a small replication of a model of a school system. It defines classes for addresses, departments, students, 
courses and assignments plus a utility class for formatting names. Each class has fields plus methods that 
match real world concepts - students belong to departments and have addresses, courses belong to departments 
but also contain assignments, assignments store scores for each registered student besides IDs are auto 
generated so each object is uniquely identifiable. When a student registers in a course, that course's student 
list and assignment score lists are updated - when scores are generated, assignments fill their score lists as 
well as courses compute weighted averages.

This model can be used as the backend for simple academic tools. As an example you can write a `main` method 
to create departments, students and courses, register students, generate random scores or print tables of 
marks and assignment averages. 