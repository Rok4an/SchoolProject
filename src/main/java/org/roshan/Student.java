package org.roshan;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@EqualsAndHashCode
@Getter
@Setter
public class Student {
    private String studentId;
    private String studentName;
    private Gender gender;
    private Address address;
    private Department department;
    private ArrayList<Course> registeredCourses;

    private static int nextId = 1;

    public Student(String studentName, Gender gender, Address address,
                   Department department) {
        this.studentId = String.format("%06d", nextId++);
        this.studentName = Util.toTitleCase(studentName);
        this.gender = gender;
        this.address = address;
        this.department = department;
        this.registeredCourses = new ArrayList<>();
    }

    /**
     * Registers this student in the given course and updates both sides.
     * @param course the course to register in
     * @return true if registration succeeded, false if already registered or failed
     */
    public boolean registerCourse(Course course) {
        if (registeredCourses.contains(course)) {
            return false;
        }
        if (course.registerStudent(this)) {
            registeredCourses.add(course);
            return true;
        }
        return false;
    }

    /**
     * Drops this student from the given course and removes related scores.
     * @param course the course to drop
     * @return true if the course was dropped, false if it was not registered
     */
    public boolean dropCourse(Course course) {
        if (!registeredCourses.contains(course)) {
            return false;
        }

        int idx = course.getRegisteredStudents().indexOf(this);
        if (idx >= 0) {
            course.getRegisteredStudents().remove(idx);
            for (Assignment a : course.getAssignments()) {
                if (a.getScores().size() > idx) {
                    a.getScores().remove(idx);
                }
            }
            if (course.getFinalScores() != null && course.getFinalScores().size() > idx) {
                course.getFinalScores().remove(idx);
            }
        }

        registeredCourses.remove(course);
        return true;
    }

    /**
     * Returns a short string with only id, name, and department name.
     * @return simplified student description
     */
    public String toSimplifiedString() {
        return "Student ID: " + studentId +
                ", Name: " + studentName +
                ", Department: " + department.getDepartmentName();
    }

    @Override
    public String toString() {
        String result = "Student ID: " + studentId +
                ", Name: " + studentName +
                ", Gender: " + gender +
                ", Address: " + address +
                ", Department: " + department.getDepartmentName() +
                "\nRegistered courses: ";

        if (registeredCourses.isEmpty()) {
            result += "None";
        } else {
            for (int i = 0; i < registeredCourses.size(); i++) {
                Course course = registeredCourses.get(i);
                result += course.getCourseId() + " - " +
                        course.getCourseName() + " (" +
                        course.getDepartment().getDepartmentName() + ")";
                if (i < registeredCourses.size() - 1) {
                    result += ", ";
                }
            }
        }

        return result;
    }

    public enum Gender {
        MALE, FEMALE
    }
}
