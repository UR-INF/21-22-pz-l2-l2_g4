package com.example.hurtownia.domain.user;

/**
 * Konwerter wartości logicznej na wartość tekstową.
 */
public class BooleanConverter {

    /**
     * Konwertuje wartość logiczną na tekstową.
     *
     * @param value
     * @return
     */
    public static String convertToString(boolean value) {
        if (value) return "Tak";
        else return "Nie";
    }

    /**
     * Konwertuje wartość tekstową na logiczną.
     *
     * @param value
     * @return
     */
    public static boolean convertToBoolean(String value) {
        return value.equals("Tak");
    }
}
