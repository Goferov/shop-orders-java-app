package util;

import java.util.regex.Pattern;

public class ValidatorUtil {
    public static boolean validateId(String id) {
        try {
            Integer.parseInt(id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validateNIP(String nip) {
        if (nip == null || nip.isEmpty()) {
            return true;
        }
        String nipPattern = "\\d{3}-\\d{2}-\\d{2}-\\d{2}|\\d{10}";
        return Pattern.matches(nipPattern, nip);
    }

    public static boolean validatePostalCode(String postalCode) {
        String postalCodePattern = "\\d{2}-\\d{3}";
        return Pattern.matches(postalCodePattern, postalCode);
    }

    public static boolean validateTextField(String text) {
        return text != null && !text.trim().isEmpty();
    }

    public static boolean validateIntRange(int number, int min, int max) {
        return number >= min && number <= max;
    }
}
