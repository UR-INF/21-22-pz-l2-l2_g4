package com.example.hurtownia.domain.product;

public class ProductState {

    public static String calculateState(int number, int maxNumber) {
        double ratio = (double) number / (double) maxNumber;
        if (ratio < 0.3) return "niski";
        else if (ratio < 0.7) return "umiarkowany";
        else return "wysoki";
    }
}
