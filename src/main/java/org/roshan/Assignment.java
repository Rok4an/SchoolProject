package org.roshan;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Random;

@Getter
@Setter
public class Assignment {
    private String assignmentId;
    private String assignmentName;
    private double weight;
    private ArrayList<Integer> scores;

    private static int nextId = 1;

    public Assignment(String assignmentName, double weight) {
        this.assignmentId = String.format("%05d",  nextId++);
        this.assignmentName = assignmentName;
        this.weight = weight;
        this.scores = new ArrayList<>();
    }

    /**
     * Calculates the average score of this assignment.
     */
    public double calcAssignmentAvg() {
        double sum = 0;
        for (Integer score : scores) {
            if (score != null) {
                sum += score;
            }
        }
        return scores.size() > 0 ? sum / scores.size() : 0;
    }


    /**
     * Generates random scores for all students in this assignment.
     * Scores are generated based on predefined ranges.
     */
    public void generateRandomScore() {
        Random rand = new Random();

        for (int i = 0; i < scores.size(); i++) {
            int randomCategory = rand.nextInt(11);  // 0–10 inclusive [web:2]
            int randomScore;

            if (randomCategory == 0) {
                randomScore = rand.nextInt(0, 60);

            } else if (randomCategory == 1 || randomCategory == 2) {
                randomScore = rand.nextInt(60, 70);

            } else if (randomCategory == 3 || randomCategory == 4) {
                randomScore = rand.nextInt(70, 80);

            } else if (randomCategory <= 8) {
                randomScore = rand.nextInt(80, 90);

            } else {
                randomScore = rand.nextInt(90, 101);
            }

            scores.set(i, randomScore);
        }
    }


    @Override
    public String toString() {
        return "Assignment ID: " + assignmentId +
                ", Name: " + assignmentName +
                ", Weight: " + weight;
    }
}
