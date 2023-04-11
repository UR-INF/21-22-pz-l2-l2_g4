package com.example.hurtownia.domain.order;

/**
 * Konwerter rabatu pomiędzy wartością liczbową, procentową i kodem.
 */
public class DiscountConverter {

    public static String fromNumericToPercentage(double value){
        switch (String.valueOf(value)) {
            case "0.1":
                return "-10%";
            case "0.2":
                return "-20%";
            default:
                return "nie udzielono";
        }
    }

    public static String fromNumericToCode(double value){
        switch (String.valueOf(value)) {
            case "0.1":
                return "KOD1";
            case "0.2":
                return "KOD2";
            default:
                return "";
        }
    }

    public static String fromPercentageToCode(String value){
        switch (value) {
            case "-10%":
                return "KOD1";
            case "-20%":
                return "KOD2";
            default:
                return "";
        }
    }

    public static Double fromPercentageToNumeric(String value){
        switch (value) {
            case "-10%":
                return 0.1;
            case "-20%":
                return 0.2;
            default:
                return 0.0;
        }
    }

    public static String fromCodeToPercentage(String value){
        switch (value) {
            case "KOD1":
                return "-10%";
            case "KOD2":
                return "-20%";
            default:
                return "nie udzielono";
        }
    }

    public static Double fromCodeToNumeric(String value){
        switch (value) {
            case "KOD1":
                return 0.1;
            case "KOD2":
                return 0.2;
            default:
                return 0.0;
        }
    }
}
