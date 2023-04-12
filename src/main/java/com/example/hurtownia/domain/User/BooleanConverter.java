package com.example.hurtownia.domain.user;

/**
 * Konwerter wartości logicznej na wartość tekstową.
 */
public class BooleanConverter {

    public static String convertToString(boolean value) {
        if (value) return "Tak";
        else return "Nie";
    }

    public static boolean convertToBoolean(String value) {
        return value.equals("Tak");
    }
}
