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
}
