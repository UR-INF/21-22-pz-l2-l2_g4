package com.example.hurtownia.domain.product;

/**
 * Zawiera metodę do obliczenia stanu magazynowego.
 */
public class ProductState {

    /**
     * Oblicza stan produktu w magazynie.
     *
     * @param number aktualna liczba
     * @param maxNumber maksymalna liczba możliwa do przechowania w magazynie
     * @return stan produktu
     */
    public static String calculateState(int number, int maxNumber) {
        double ratio = (double) number / (double) maxNumber;
        if (ratio < 0.3) return "niski";
        else if (ratio < 0.7) return "umiarkowany";
        else return "wysoki";
    }
}
