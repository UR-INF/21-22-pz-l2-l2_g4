package com.example.hurtownia.domain.order;

/**
 * Konwerter rabatu pomiędzy wartością liczbową, procentową i kodem rabatowym.
 */
public class DiscountConverter {

    /**
     * Konwertuje wartość liczbową na wartość procentową.
     *
     * @param value
     * @return procent rabatu
     */
    public static String fromNumericToPercentage(double value) {
        return switch (String.valueOf(value)) {
            case "0.1" -> "-10%";
            case "0.2" -> "-20%";
            default -> "nie udzielono";
        };
    }

    /**
     * Konwertuje wartość liczbową na kod.
     *
     * @param value
     * @return kod rabatu
     */
    public static String fromNumericToCode(double value) {
        return switch (String.valueOf(value)) {
            case "0.1" -> "KOD1";
            case "0.2" -> "KOD2";
            default -> "";
        };
    }

    /**
     * Konwertuje wartość procentową na kod.
     *
     * @param value
     * @return kod rabatu
     */
    public static String fromPercentageToCode(String value) {
        return switch (value) {
            case "-10%" -> "KOD1";
            case "-20%" -> "KOD2";
            default -> "";
        };
    }

    /**
     * Konwertuje wartość procentową na wartość liczbową.
     *
     * @param value
     * @return wartość liczbowa kodu
     */
    public static Double fromPercentageToNumeric(String value) {
        return switch (value) {
            case "-10%" -> 0.1;
            case "-20%" -> 0.2;
            default -> 0.0;
        };
    }

    /**
     * Konwertuje wartość kod rabatu na wartość procentową.
     *
     * @param value
     * @return procent rabatu
     */
    public static String fromCodeToPercentage(String value) {
        return switch (value) {
            case "KOD1" -> "-10%";
            case "KOD2" -> "-20%";
            default -> "nie udzielono";
        };
    }

    /**
     * Konwertuje kod rabatu na wartość liczbową.
     *
     * @param value
     * @return wartość liczbowa kodu
     */
    public static Double fromCodeToNumeric(String value) {
        return switch (value) {
            case "KOD1" -> 0.1;
            case "KOD2" -> 0.2;
            default -> 0.0;
        };
    }
}
