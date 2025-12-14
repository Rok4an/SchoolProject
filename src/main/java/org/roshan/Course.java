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
                if (assignment.getScores().get(i) <= i) {
                    continue;
                }
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

    public boolean addAssignment(String assignmentName, double weight, int maxScore) {
        Assignment newAssignment = new Assignment(assignmentName, weight);
        assignments.add(newAssignment);
        for (int i = 0; i < registeredStudents.size(); i++) {
            newAssignment.getScores().add(null);
        }
        return true;
    }

    public void generateScores() {
        for (Assignment assignment : assignments) {
            assignment.generateRandomScore();
        }

        this.calcStudentsAverage();
    }

    public void displayScore() {
        System.out.println("Course: " + courseName + " (" + courseId + ")");
        if (registeredStudents.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }
        if (assignments.isEmpty()) {
            System.out.println("No assignments.");
            return;
        }

        // header
        System.out.printf("%-20s", "Student");
        for (Assignment a : assignments) {
            System.out.printf("%-15s", a.getAssignmentName());
        }
        System.out.printf("%-12s%n", "Final");

        // rows
        int[] avgs = calcStudentsAverage();
        for (int i = 0; i < registeredStudents.size(); i++) {
            Student s = registeredStudents.get(i);
            System.out.printf("%-20s", s.getStudentName());
            for (Assignment a : assignments) {
                Integer score = (a.getScores().size() > i) ? a.getScores().get(i) : null;
                String text = (score == null) ? "-" : String.valueOf(score);
                System.out.printf("%-15s", text);
            }
            System.out.printf("%-12d%n", avgs[i]);
        }

        // assignment averages row
        System.out.printf("%-20s", "Average");
        for (Assignment a : assignments) {
            double avg = a.calcAssignmentAvg();
            System.out.printf("%-15.1f", avg);
        }
        System.out.println();
    }

    public String toSimplifiedString() {
        return "Course ID: " + courseId +
                ", Name: " + courseName +
                ", Credits: " + credits +
                ", Department: " + department.getDepartmentName();
    }

    @Override
    public String toString() {
        String result = "Course ID: " + courseId +
                ", Name: " + courseName +
                ", Credits: " + credits +
                ", Department: " + department.getDepartmentName() +
                "\n";

        result += "Assignments: ";
        for (Assignment assign : assignments) {
            result += assign.getAssignmentName() + " (" + assign.getWeight() + "%), ";
        }
        result += "\n";

        result += "Students: ";
        for (Student student : registeredStudents) {
            result += student.getStudentId() + " - " + student.getStudentName() +
                    " (" + student.getDepartment().getDepartmentName() + "), ";
        }
        result += "\n";

        result += "Assignment weights valid: " + isAssignmentWeightValid();

        return result;
    }
}