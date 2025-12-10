package org.roshan;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Address {
    private Integer streetNo;
    private String street;
    private String city;
    private Province province;
    private String postalCode;

    /**
     * Checks if the postal code is a valid one
     * @param postalCode the postal code itself
     * @return true if the postal code is actually a postal code if not it returns false
     */
    public static boolean isPostalCodeValid(String postalCode) {
        if (postalCode == null || postalCode.length() != 6) {
            return false;
        }

        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String nums = "123456789";

        for (int i = 0; i < postalCode.length(); i++) {
            char c = postalCode.toUpperCase().charAt(i);
            if (i % 2 == 0) {
                if (!letters.contains(String.valueOf(c))) {
                    return false;
                }
            } else {
                if (!nums.contains(String.valueOf(c))) {
                    return false;
                }
            }
        }
        return true;
    }

    public Address(Integer streetNo, String street, String city, Province province, String postalCode) {
        if (isPostalCodeValid(postalCode)) {
            this.streetNo = streetNo;
            this.street = street;
            this.city = city;
            this.province = province;
            this.postalCode = postalCode.toUpperCase();
        } else {
            this.streetNo = null;
            this.street = null;
            this.city = null;
            this.province = null;
            this.postalCode = null;
        }
    }

    public enum Province {
        ON, QC, MB, BC, AB, NB, NS, PE, NL
    }

}
