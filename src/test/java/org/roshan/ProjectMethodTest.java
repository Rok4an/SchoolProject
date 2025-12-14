package org.roshan;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ProjectMethodsTest {

    //Util.toTitleCase

    @Test
    @DisplayName("toTitleCase(\"john doe\") -> \"John Doe\"")
    void testToTitleCase1() {
        String input = "john doe";
        String expected = "John Doe";
        String actual = Util.toTitleCase(input);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("toTitleCase(\"cOmPuTeR sCiEnCe\") -> \"Computer Science\"")
    void testToTitleCase2() {
        String input = "cOmPuTeR sCiEnCe";
        String expected = "Computer Science";
        String actual = Util.toTitleCase(input);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("toTitleCase long string still works")
    void testToTitleCase3() {
        String input = "Hi my name is John Doe";
        String expected = "Hi My Name Is John Doe";
        String actual = Util.toTitleCase(input);
        assertEquals(expected, actual);
    }

    //Assignment.calcAssignmentAvg

    @Test
    @DisplayName("calcAssignmentAvg ignores null scores")
    void testCalcAssignmentAvg1() {
        Assignment assignment = new Assignment("Test", 20);
        assignment.getScores().add(80);
        assignment.getScores().add(null);
        assignment.getScores().add(100);

        double expected = 90.0; // (80 + 100) / 2
        double actual = assignment.calcAssignmentAvg();
        assertEquals(expected, actual, 0.0001);
    }

    @Test
    @DisplayName("calcAssignmentAvg with all scores at upper limit 100")
    void testCalcAssignmentAvg2() {
        Assignment assignment = new Assignment("MaxTest", 20);
        assignment.getScores().add(100);
        assignment.getScores().add(100);
        assignment.getScores().add(100);

        double expected = 100.0;
        double actual = assignment.calcAssignmentAvg();
        assertEquals(expected, actual, 0.0001);
    }

    @Test
    @DisplayName("calcAssignmentAvg with zeros and positives")
    void testCalcAssignmentAvg3() {
        Assignment assignment = new Assignment("MixTest", 20);
        assignment.getScores().add(0);
        assignment.getScores().add(50);
        assignment.getScores().add(100);

        double expected = (0 + 50 + 100) / 3.0;
        double actual = assignment.calcAssignmentAvg();
        assertEquals(expected, actual, 0.0001);
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
        assertEquals(expectedSize, actualSize);

        for (Integer score : assignment.getScores()) {
            assertNotNull(score);
            assertTrue(score >= 0 && score <= 100);
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
        assertEquals(expectedSize, actualSize);

        for (Integer score : assignment.getScores()) {
            assertNotNull(score);
            assertTrue(score >= 0 && score <= 100);
        }
    }

    @Test
    @DisplayName("generateRandomScore works for single student")
    void testGenerateRandomScore3() {
        Assignment assignment = new Assignment("Single", 20);
        assignment.getScores().add(null);

        assignment.generateRandomScore();

        assertEquals(1, assignment.getScores().size());
        Integer score = assignment.getScores().get(0);
        assertNotNull(score);
        assertTrue(score >= 0 && score <= 100);
    }

    //Course.isAssignmentWeightValid

    @Test
    @DisplayName("isAssignmentWeightValid -> true when weights sum to 100")
    void testIsAssignmentWeightValid1() {
        Department department = new Department("Math");
        Course course = new Course("Algebra", 3.0, department);

        course.addAssignment("A1", 40, 100);
        course.addAssignment("A2", 60, 100);

        boolean expected = true;
        boolean actual = course.isAssignmentWeightValid();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("isAssignmentWeightValid -> false when weights != 100")
    void testIsAssignmentWeightValid2() {
        Department department = new Department("Math");
        Course course = new Course("Algebra", 3.0, department);

        course.addAssignment("A1", 30, 100);
        course.addAssignment("A2", 60, 100);

        boolean expected = false;
        boolean actual = course.isAssignmentWeightValid();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("isAssignmentWeightValid with many assignments that sum to 100")
    void testIsAssignmentWeightValid3() {
        Department department = new Department("Math");
        Course course = new Course("Algebra", 3.0, department);

        course.addAssignment("A1", 25, 100);
        course.addAssignment("A2", 25, 100);
        course.addAssignment("A3", 25, 100);
        course.addAssignment("A4", 25, 100);

        assertTrue(course.isAssignmentWeightValid());
    }

    //Course.calcStudentsAverage

    @Test
    @DisplayName("calcStudentsAverage one student, two assignments 50/50")
    void testCalcStudentsAverage1() {
        Department department = new Department("Math");
        Course course = new Course("Algebra", 3.0, department);

        course.addAssignment("A1", 50, 100);
        course.addAssignment("A2", 50, 100);

        Address addr = new Address(1, "Main", "Montreal", Address.Province.QC, "A1B2C3");
        Student student = new Student("john doe", Student.Gender.MALE, addr, department);

        boolean registered = student.registerCourse(course);
        assertTrue(registered);

        course.getAssignments().get(0).getScores().set(0, 80);
        course.getAssignments().get(1).getScores().set(0, 90);

        int[] actual = course.calcStudentsAverage();
        int expectedLen = 1;
        assertEquals(expectedLen, actual.length);

        int expectedScore = 85;
        assertEquals(expectedScore, actual[0]);
    }

    @Test
    @DisplayName("calcStudentsAverage many students (upper bound list size)")
    void testCalcStudentsAverage2() {
        Department department = new Department("Math");
        Course course = new Course("Algebra", 3.0, department);

        course.addAssignment("A1", 100, 100);

        for (int i = 0; i < 50; i++) {
            Address addr = new Address(1, "Main", "Montreal", Address.Province.QC, "A1B2C3");
            Student student = new Student("student " + i, Student.Gender.MALE, addr, department);
            student.registerCourse(course);
            course.getAssignments().get(0).getScores().set(i, 100);
        }

        int[] actual = course.calcStudentsAverage();
        assertEquals(50, actual.length);
        for (int score : actual) {
            assertEquals(100, score);
        }
    }

    @Test
    @DisplayName("calcStudentsAverage with all zero scores")
    void testCalcStudentsAverage3() {
        Department department = new Department("Math");
        Course course = new Course("Algebra", 3.0, department);

        course.addAssignment("A1", 50, 100);
        course.addAssignment("A2", 50, 100);

        Address addr = new Address(1, "Main", "Montreal", Address.Province.QC, "A1B2C3");
        Student student = new Student("john doe", Student.Gender.MALE, addr, department);

        student.registerCourse(course);

        course.getAssignments().get(0).getScores().set(0, 0);
        course.getAssignments().get(1).getScores().set(0, 0);

        int[] actual = course.calcStudentsAverage();
        assertEquals(1, actual.length);
        assertEquals(0, actual[0]);
    }

    //Student.registerCourse

    @Test
    @DisplayName("registerCourse adds course to student and student to course")
    void testRegisterCourse1() {
        Department department = new Department("Math");
        Course course = new Course("Algebra", 3.0, department);
        course.addAssignment("A1", 100, 100);

        Address addr = new Address(1, "Main", "Montreal", Address.Province.QC, "A1B2C3");
        Student student = new Student("john doe", Student.Gender.MALE, addr, department);

        boolean result = student.registerCourse(course);
        assertTrue(result);
        assertTrue(student.getRegisteredCourses().contains(course));
        assertTrue(course.getRegisteredStudents().contains(student));

        int expectedSlots = 1;
        int actualSlots = course.getAssignments().get(0).getScores().size();
        assertEquals(expectedSlots, actualSlots);
    }

    @Test
    @DisplayName("registerCourse returns false when course already registered")
    void testRegisterCourse2() {
        Department department = new Department("Math");
        Course course = new Course("Algebra", 3.0, department);
        Address addr = new Address(1, "Main", "Montreal", Address.Province.QC, "A1B2C3");
        Student student = new Student("john doe", Student.Gender.MALE, addr, department);

        boolean first = student.registerCourse(course);
        boolean second = student.registerCourse(course);

        assertTrue(first);
        assertFalse(second);
    }

    @Test
    @DisplayName("registerCourse works for multiple different courses")
    void testRegisterCourse3() {
        Department department = new Department("Math");
        Course course1 = new Course("Algebra", 3.0, department);
        Course course2 = new Course("Calculus", 3.0, department);

        Address addr = new Address(1, "Main", "Montreal", Address.Province.QC, "A1B2C3");
        Student student = new Student("john doe", Student.Gender.MALE, addr, department);

        assertTrue(student.registerCourse(course1));
        assertTrue(student.registerCourse(course2));

        assertTrue(student.getRegisteredCourses().contains(course1));
        assertTrue(student.getRegisteredCourses().contains(course2));
    }

    //Student.dropCourse

    @Test
    @DisplayName("dropCourse removes course and cleans scores")
    void testDropCourse1() {
        Department department = new Department("Math");
        Course course = new Course("Algebra", 3.0, department);
        course.addAssignment("A1", 100, 100);

        Address addr = new Address(1, "Main", "Montreal", Address.Province.QC, "A1B2C3");
        Student student = new Student("john doe", Student.Gender.MALE, addr, department);

        student.registerCourse(course);
        boolean result = student.dropCourse(course);

        assertTrue(result);
        assertFalse(student.getRegisteredCourses().contains(course));
        assertFalse(course.getRegisteredStudents().contains(student));

        int expectedSize = 0;
        int actualSize = course.getAssignments().get(0).getScores().size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    @DisplayName("dropCourse returns false when course not registered")
    void testDropCourse2() {
        Department department = new Department("Math");
        Course course = new Course("Algebra", 3.0, department);
        Address addr = new Address(1, "Main", "Montreal", Address.Province.QC, "A1B2C3");
        Student student = new Student("john doe", Student.Gender.MALE, addr, department);

        boolean result = student.dropCourse(course);
        assertFalse(result);
    }

    @Test
    @DisplayName("dropCourse works when student has multiple courses")
    void testDropCourse3() {
        Department department = new Department("Math");
        Course course1 = new Course("Algebra", 3.0, department);
        Course course2 = new Course("Calculus", 3.0, department);
        course1.addAssignment("A1", 100, 100);
        course2.addAssignment("B1", 100, 100);

        Address addr = new Address(1, "Main", "Montreal", Address.Province.QC, "A1B2C3");
        Student student = new Student("john doe", Student.Gender.MALE, addr, department);

        student.registerCourse(course1);
        student.registerCourse(course2);

        boolean dropped = student.dropCourse(course1);
        assertTrue(dropped);
        assertFalse(student.getRegisteredCourses().contains(course1));
        assertTrue(student.getRegisteredCourses().contains(course2));
    }
}
