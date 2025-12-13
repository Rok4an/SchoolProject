package org.roshan;

import java.util.ArrayList;
import java.util.Random;

public class Assignment {
    private String assignmentId;
    private String assignmentName;
    private double weight;
    private ArrayList<Integer> scores;
    private static int nextId = 1;

    public Assignment(String assignmentName, double weight) {
        this.assignmentId = "A" + nextId++;
        this.assignmentName = assignmentName;
        this.weight = weight;
        this.scores = new ArrayList<>();
    }


    /**
     * Calculates the average score of this assignment.
     */
    public void calcAssignmentAvg() {
        double sum = 0;

        for (int score : scores) {
            sum += score;
        }

        double average = sum / scores.size();
    }

    /**
     * Generates random scores for all students in this assignment.
     * Scores are generated based on predefined ranges.
     * @param numStudents the number of students to generate scores for
     */
    public void generateRandomScore(int numStudents) {
        Random rand = new Random();
        scores.clear();

        for (int i = 0; i < numStudents; i++) {
            int randomCategory = rand.nextInt(11); // [0, 10]
            int score;

            if (randomCategory == 0) {
                score = rand.nextInt(60); // [0, 60)
            } else if (randomCategory == 1 || randomCategory == 2) {
                score = 60 + rand.nextInt(10); // [60, 70)
            } else if (randomCategory == 3 || randomCategory == 4) {
                score = 70 + rand.nextInt(10); // [70, 80)
            } else if (randomCategory <= 8) {
                score = 80 + rand.nextInt(10); // [80, 90)
            } else {
                score = 90 + rand.nextInt(11); // [90, 100]
            }

            scores.add(score);
        }
    }

    @Override
    public String toString() {
        return "Assignment ID: " + assignmentId +
                ", Name: " + assignmentName +
                ", Weight: " + weight;
    }
}
