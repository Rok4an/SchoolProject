package org.roshan;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Department {
    private String departmentId;
    private String departmentName;
    private static int nextId = 0;

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

}
