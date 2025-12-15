package org.roshan;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProjectMethodsTest {

    private static Department dept = new Department("Math");
    private static Address address = new Address(1, "Main", "Montreal", Address.Province.QC, "A1B2C3");
    private static Student s1 = new Student("john doe", Student.Gender.MALE, address, dept);
    private static Student s2 = new Student("john moe", Student.Gender.MALE, address, dept);

    //Util.toTitleCase

    @Test
    @DisplayName("toTitleCase(\"john doe\") -> \"John Doe\"")
    void testToTitleCase1() {
        String input = "john doe";
        String expected = "John Doe";
        String actual = Util.toTitleCase(input);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("toTitleCase(\"cOmPuTeR sCiEnCe\") -> \"Computer Science\"")
    void testToTitleCase2() {
        String input = "cOmPuTeR sCiEnCe";
        String expected = "Computer Science";
        String actual = Util.toTitleCase(input);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("toTitleCase long string still works")
    void testToTitleCase3() {
        String input = "very lonnnnnnnnng string";
        String expected = "Very Lonnnnnnnnng String";
        String actual = Util.toTitleCase(input);
        Assertions.assertEquals(expected, actual);
    }

    //Assignment.calcAssignmentAvg

    @Test
    @DisplayName("calcAssignmentAvg ignores null scores")
    void testCalcAssignmentAvg1() {
        Assignment assignment = new Assignment("Test", 20);
        assignment.getScores().add(80);
        assignment.getScores().add(null);
        assignment.getScores().add(100);

        double expected = 90.0;
        double actual = assignment.calcAssignmentAvg();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("calcAssignmentAvg with all scores at upper limit 100")
    void testCalcAssignmentAvg2() {
        Assignment assignment = new Assignment("Final Test", 20);
        assignment.getScores().add(100);
        assignment.getScores().add(100);
        assignment.getScores().add(100);

        double expected = 100.0;
        double actual = assignment.calcAssignmentAvg();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("calcAssignmentAvg with zeros and positives")
    void testCalcAssignmentAvg3() {
        Assignment assignment = new Assignment("Mid Term Test", 20);
        assignment.getScores().add(0);
        assignment.getScores().add(50);
        assignment.getScores().add(100);

        double expected = (0 + 50 + 100) / 3.0;
        double actual = assignment.calcAssignmentAvg();
        Assertions.assertEquals(expected, actual);
    }

    //Assignment.generateRandomScore

    @Test
    @DisplayName("generateRandomScore sets all scores between 0 and 100")
    void testGenerateRandomScore1() {
        Assignment assignment = new Assignment("Test", 20);
        assignment.getScores().add(null);
        assignment.getScores().add(null);
        assignment.getScores().add(null);

        assignment.generateRandomScore();

        int expectedSize = 3;
        int actualSize = assignment.getScores().size();
        Assertions.assertEquals(expectedSize, actualSize);

        for (Integer score : assignment.getScores()) {
            boolean inRange = (score != null && score >= 0 && score <= 100);
            Assertions.assertTrue(inRange);
        }
    }

    @Test
    @DisplayName("generateRandomScore works for many students (upper bound list size)")
    void testGenerateRandomScore2() {
        Assignment assignment = new Assignment("BigClass", 20);
        for (int i = 0; i < 100; i++) {
            assignment.getScores().add(null);
        }

        assignment.generateRandomScore();

        int expectedSize = 100;
        int actualSize = assignment.getScores().size();
        Assertions.assertEquals(expectedSize, actualSize);

        for (Integer score : assignment.getScores()) {
            boolean inRange = (score != null && score >= 0 && score <= 100);
            Assertions.assertTrue(inRange);
        }
    }

    @Test
    @DisplayName("generateRandomScore works for single student")
    void testGenerateRandomScore3() {
        Assignment assignment = new Assignment("Single", 20);
        assignment.getScores().add(null);

        assignment.generateRandomScore();

        int expectedSize = 1;
        int actualSize = assignment.getScores().size();
        Assertions.assertEquals(expectedSize, actualSize);

        Integer score = assignment.getScores().getFirst();
        boolean inRange = (score != null && score >= 0 && score <= 100);
        Assertions.assertTrue(inRange);
    }

    //Course.isAssignmentWeightValid

    @Test
    @DisplayName("isAssignmentWeightValid -> true when weights sum to 100")
    void testIsAssignmentWeightValid1() {
        Course course = new Course("Algebra", 3.0, dept);

        course.addAssignment("A1", 40, 100);
        course.addAssignment("A2", 60, 100);

        boolean expected = true;
        boolean actual = course.isAssignmentWeightValid();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("isAssignmentWeightValid -> false when weights != 100")
    void testIsAssignmentWeightValid2() {
        Course course = new Course("Algebra", 3.0, dept);

        course.addAssignment("A1", 30, 100);
        course.addAssignment("A2", 60, 100);

        boolean expected = false;
        boolean actual = course.isAssignmentWeightValid();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("isAssignmentWeightValid with many assignments that sum to 100")
    void testIsAssignmentWeightValid3() {
        Course course = new Course("Algebra", 3.0, dept);

        course.addAssignment("A1", 25, 100);
        course.addAssignment("A2", 25, 100);
        course.addAssignment("A3", 25, 100);
        course.addAssignment("A4", 25, 100);

        boolean expected = true;
        boolean actual = course.isAssignmentWeightValid();
        Assertions.assertEquals(expected, actual);
    }

    //Course.calcStudentsAverage

    @Test
    @DisplayName("calcStudentsAverage one student, two assignments 50/50")
    void testCalcStudentsAverage1() {
        Course course = new Course("Algebra", 3.0, dept);

        course.addAssignment("A1", 50, 100);
        course.addAssignment("A2", 50, 100);

        boolean registered = s1.registerCourse(course);
        Assertions.assertTrue(registered);

        course.getAssignments().get(0).getScores().set(0, 80);
        course.getAssignments().get(1).getScores().set(0, 90);

        int[] actual = course.calcStudentsAverage();
        int expectedLen = 1;
        Assertions.assertEquals(expectedLen, actual.length);

        int expectedScore = 85;
        Assertions.assertEquals(expectedScore, actual[0]);
    }

    @Test
    @DisplayName("calcStudentsAverage many students (upper bound list size)")
    void testCalcStudentsAverage2() {
        Course course = new Course("Algebra", 3.0, dept);

        course.addAssignment("A1", 100, 100);

        for (int i = 0; i < 50; i++) {
            Student student = new Student("student " + i, Student.Gender.MALE, address, dept);
            student.registerCourse(course);
            course.getAssignments().getFirst().getScores().set(i, 100);
        }

        int[] actual = course.calcStudentsAverage();
        int expectedLen = 50;
        Assertions.assertEquals(expectedLen, actual.length);

        for (int score : actual) {
            Assertions.assertEquals(100, score);
        }
    }

    @Test
    @DisplayName("calcStudentsAverage with all zero scores")
    void testCalcStudentsAverage3() {
        Course course = new Course("Algebra", 3.0, dept);

        course.addAssignment("A1", 50, 100);
        course.addAssignment("A2", 50, 100);

        s1.registerCourse(course);

        course.getAssignments().get(0).getScores().set(0, 0);
        course.getAssignments().get(1).getScores().set(0, 0);

        int[] actual = course.calcStudentsAverage();
        int expectedLen = 1;
        Assertions.assertEquals(expectedLen, actual.length);
        int expectedScore = 0;
        Assertions.assertEquals(expectedScore, actual[0]);
    }

    @Test
    @DisplayName("calcStudentsAverage with two students with different averages")
    void testCalcStudentsAverage4() {
        Course course = new Course("Algebra", 3.0, dept);

        course.addAssignment("A1", 50, 100);
        course.addAssignment("A2", 50, 100);

        s1.registerCourse(course);
        s2.registerCourse(course);

        course.getAssignments().get(0).getScores().set(0, 80);
        course.getAssignments().get(1).getScores().set(0, 90);

        course.getAssignments().get(0).getScores().set(1, 60);
        course.getAssignments().get(1).getScores().set(1, 100);

        int[] actual = course.calcStudentsAverage();

        int expectedLen = 2;
        Assertions.assertEquals(expectedLen, actual.length);

        int expectedScoreS1 = 85;
        int expectedScoreS2 = 80;
        Assertions.assertEquals(expectedScoreS1, actual[0]);
        Assertions.assertEquals(expectedScoreS2, actual[1]);
    }

    //addAssignment

    @Test
    @DisplayName("addAssignment increases assignments size")
    void addAssignment1() {
        Course course = new Course("Prog4", 3.0, dept);

        boolean result = course.addAssignment("A1", 50, 100);
        Assertions.assertTrue(result);
        Assertions.assertEquals(1, course.getAssignments().size());
    }

    @Test
    @DisplayName("addAssignment creates score slots for existing students")
    void addAssignment2() {
        Course course = new Course("Prog5", 3.0, dept);
        s1.registerCourse(course);
        s2.registerCourse(course);

        course.addAssignment("A1", 100, 100);

        Assignment assignment1 = course.getAssignments().getFirst();
        int expectedSlots = 2;
        int actualSlots = assignment1.getScores().size();
        Assertions.assertEquals(expectedSlots, actualSlots);
    }

    @Test
    @DisplayName("addAssignment can be called multiple times")
    void addAssignment3() {
        Course course = new Course("Prog6", 3.0, dept);

        course.addAssignment("A1", 30, 100);
        course.addAssignment("A2", 30, 100);
        course.addAssignment("A3", 40, 100);

        int expectedSize = 3;
        int actualSize = course.getAssignments().size();
        Assertions.assertEquals(expectedSize, actualSize);
    }

    @Test
    @DisplayName("addAssignment returns false for duplicate name")
    void addAssignment4() {
        Course course = new Course("Prog7", 3.0, dept);

        boolean first = course.addAssignment("A1", 50, 100);
        boolean second = course.addAssignment("A1", 50, 100);

        Assertions.assertTrue(first);
        Assertions.assertFalse(second);
        Assertions.assertEquals(1, course.getAssignments().size());
    }

    //Student.registerCourse

    @Test
    @DisplayName("registerCourse adds course to student and student to course")
    void testRegisterCourse1() {
        Course course = new Course("Algebra", 3.0, dept);
        course.addAssignment("A1", 100, 100);

        boolean result = s1.registerCourse(course);
        Assertions.assertTrue(result);
        Assertions.assertTrue(s1.getRegisteredCourses().contains(course));
        Assertions.assertTrue(course.getRegisteredStudents().contains(s1));

        int expectedSlots = 1;
        int actualSlots = course.getAssignments().getFirst().getScores().size();
        Assertions.assertEquals(expectedSlots, actualSlots);
    }

    @Test
    @DisplayName("registerCourse returns false when course already registered")
    void testRegisterCourse2() {
        Course course = new Course("Algebra", 3.0, dept);

        boolean first = s1.registerCourse(course);
        boolean second = s1.registerCourse(course);

        Assertions.assertTrue(first);
        Assertions.assertFalse(second);
    }

    @Test
    @DisplayName("registerCourse works for multiple different courses")
    void testRegisterCourse3() {
        Course course1 = new Course("Algebra", 3.0, dept);
        Course course2 = new Course("Calculus", 3.0, dept);

        boolean r1 = s1.registerCourse(course1);
        boolean r2 = s1.registerCourse(course2);

        Assertions.assertTrue(r1);
        Assertions.assertTrue(r2);
        Assertions.assertTrue(s1.getRegisteredCourses().contains(course1));
        Assertions.assertTrue(s1.getRegisteredCourses().contains(course2));
    }

    //Student.dropCourse

    @Test
    @DisplayName("dropCourse removes course and cleans scores")
    void testDropCourse1() {
        Course course = new Course("Algebra", 3.0, dept);
        course.addAssignment("A1", 100, 100);

        s1.registerCourse(course);
        boolean result = s1.dropCourse(course);

        Assertions.assertTrue(result);
        Assertions.assertFalse(s1.getRegisteredCourses().contains(course));
        Assertions.assertFalse(course.getRegisteredStudents().contains(s1));

        int expectedSize = 0;
        int actualSize = course.getAssignments().getFirst().getScores().size();
        Assertions.assertEquals(expectedSize, actualSize);
    }

    @Test
    @DisplayName("dropCourse returns false when course not registered")
    void testDropCourse2() {
        Course course = new Course("Algebra", 3.0, dept);

        boolean result = s1.dropCourse(course);
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("dropCourse works when student has multiple courses")
    void testDropCourse3() {
        Course course1 = new Course("Algebra", 3.0, dept);
        Course course2 = new Course("Calculus", 3.0, dept);
        course1.addAssignment("A1", 100, 100);
        course2.addAssignment("B1", 100, 100);

        s1.registerCourse(course1);
        s1.registerCourse(course2);

        boolean dropped = s1.dropCourse(course1);
        Assertions.assertTrue(dropped);
        Assertions.assertFalse(s1.getRegisteredCourses().contains(course1));
        Assertions.assertTrue(s1.getRegisteredCourses().contains(course2));
    }
}