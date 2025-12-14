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

    public Department(String departmentName) {

        if (isDepartmentNameValid(departmentName)) {
            this.departmentId = String.format("D%02d", nextId++);
            this.departmentName = Util.toTitleCase(departmentName);
        } else {
            this.departmentId = null;
            this.departmentName = null;
        }
    }

    /**
     * Checks if a department name is valid.
     * A valid name contains only letters and spaces.
     * @param departmentName the name to check
     * @return true if valid, false otherwise
     */
    private static boolean isDepartmentNameValid(String departmentName) {
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

    public void setDepartmentName(String departmentName) {
        if (isDepartmentNameValid(departmentName)) {
            this.departmentName = Util.toTitleCase(departmentName);
        } else {
            this.departmentName = null;
            this.departmentId = null;
        }
    }
}
