package org.roshan;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Random;

@EqualsAndHashCode
@Getter
@Setter
public class Course {
    private String courseId;
    private String courseName;
    private double credits;
    private Department department;
    private ArrayList<Assignment> assignments;
    private ArrayList<Student> registeredStudents;
    private ArrayList<Double> finalScores;

    private static int nextId = 1;

    public Course(String courseName, double credits, Department department) {
        this.courseId = "C" + "-" + department.getDepartmentId() + "-" + String.format("%02d", nextId++);
        this.courseName = courseName;
        this.credits = credits;
        this.department = department;
        this.assignments = new ArrayList<>();
        this.registeredStudents = new ArrayList<>();
    }

    public boolean isAssignmentWeightValid() {
        double sum = 0;
        double sumOfWeights = 100;

        for (Assignment assignment : assignments) {
            sum += assignment.getWeight();
        }

        return sum == sumOfWeights;
    }

    public boolean registerStudent(Student student) {
        if (registeredStudents.contains(student)) {
            return false;
        }

        registeredStudents.add(student);

        for (Assignment assignment : assignments) {
            assignment.getScores().add(null);

            finalScores.add(null);
        }

        return true;
    }

    public int[] calcStudentsAverage() {
        int numOfStudents = registeredStudents.size();
        int[] finalScoresArray = new int[numOfStudents];

        finalScores.clear();

        for (int i = 0; i < numOfStudents; i++) {
            double weightedTotal = 0.0;

            for (Assignment assignment : assignments) {
                Integer score = assignment.getScores().get(i);
                if (score != null) {
                    weightedTotal += score * (assignment.getWeight() / 100.0);
                }
            }

            int roundedScore = (int) Math.round(weightedTotal);

            finalScores.add((double) roundedScore);
            finalScoresArray[i] = roundedScore;
        }

        return finalScoresArray;
    }
}