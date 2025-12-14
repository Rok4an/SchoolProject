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

    public Student(String studentName, Gender gender, Address address, Department department) {
        this.studentName = studentName;
        this.gender = gender;
        this.address = address;
        this.department = department;
        this.registeredCourses = new ArrayList<>();
        this.studentId = String.format("S%06d", nextId++);
    }

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

    public boolean dropCourse(Course course) {
        if (!registeredCourses.contains(course)) {
            return false;
        }
        registeredCourses.remove(course);
        course.getRegisteredStudents().remove(this);
        return true;
    }


    public String toSimplifiedString() {
        return "Student{" +
                "studentId=" + studentId + '\'' +
                ", studentName=" + studentName + '\'' +
                ", departmentName=" + department.getDepartmentName() +
                '}';
    }

    public enum Gender {
        MALE, FEMALE
    }
}
