package org.roshan;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@ToString
public class Department {
    private String departmentId;
    private String departmentName;

    private static int nextId = 1;

    public static boolean isDepartmentNameValid(String departmentName) {
        if (departmentName == null || departmentName.isEmpty()) {
            return false;
        }

        for (char c : departmentName.toCharArray()) {
            if (!(c == ' ' || Character.isLetter(c))) {
                return false;
            }
        }
        return true;
    }

    public Department(String departmentName) {
        if (isDepartmentNameValid(departmentName)) {
            this.departmentName = departmentName;
            this.departmentId = String.format("D%02d", nextId++);
        } else  {
            this.departmentName = null;
            this.departmentId = null;
        }
    }
}
