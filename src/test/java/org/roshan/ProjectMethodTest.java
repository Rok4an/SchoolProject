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
}
