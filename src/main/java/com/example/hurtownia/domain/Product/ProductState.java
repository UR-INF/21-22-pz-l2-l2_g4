package com.example.hurtownia.domain.product;

public class ProductState {

    public static String calculateState(int number, int maxNumber){
        double ratio = (double)number/(double)maxNumber;
        if (ratio < 30) return "niski";
        else if (ratio < 70) return "umiarkowany";
        else return "wysoki";
    }
}
