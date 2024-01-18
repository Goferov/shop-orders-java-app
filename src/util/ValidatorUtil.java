package util;

import javax.swing.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class ValidatorUtil {
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

    public static boolean validatePrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        if (price.scale() > 2) {
            return false;
        }
        return true;
    }

    public static int validateWeight(String weightStr) {
        if (weightStr == null || weightStr.trim().isEmpty()) {
            return 0;
        }

        try {
            double weight = Double.parseDouble(weightStr.trim());

            if (weight <= 0) {
                return 1;
            }

            return 0;
        } catch (NumberFormatException e) {
            return 2;
        }
    }

    public static int validateDimensions(String width, String height, String depth) {
        String[] dimensions = {width, height, depth};
        long countProvided = java.util.Arrays.stream(dimensions).filter(d -> d != null && !d.trim().isEmpty()).count();
        if (countProvided > 0 && countProvided < dimensions.length) {
            return 1;
        }

        try {
            for (String dim : dimensions) {
                if (dim != null && !dim.trim().isEmpty()) {
                    double dimDouble = Double.parseDouble(dim.trim());
                    if (dimDouble < 0) {
                        return 0;
                    }
                }
            }
            return 3;
        } catch (NumberFormatException e) {
            return 2;
        }
    }
}
