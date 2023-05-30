package com.example.hurtownia.validation;

/**
 * Klasa obsługująca walidację w aplikacji
 */
public class TextFieldsValidators {
    /**
     * Funkcja służąca do walidacji emailu
     *
     * @param email
     * @return true jeśli email poprawny, w przeciwnym wypadku false
     */
    public static boolean validateEmail(String email) {
        return email.matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b");
    }

    /**
     * Funckja służy do walidacji numeru telefonu
     *
     * @param phoneNumber
     * @return true jeśli numer poprawny,w przeciwnym wypadku false
     */
    public static boolean validatePhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{9}");
    }

    /**
     * Funkcja służy do walidowania adresu nip
     * @param phoneNumber
     * @return true, jeśli nip poprawny, w przeciwnym wypadku false
     */
    public static boolean validateNip(String phoneNumber) {
        return phoneNumber.matches("\\d{10}");
    }

    /**
     * Funkcja służy do walidowania numeru pese
     * @param phoneNumber
     * @return true, jeśli numer poprawny, w przeciwnym wypadku false
     */
    public static boolean validatePesel(String phoneNumber) {
        return phoneNumber.matches("\\d{11}");
    }

    /**
     * Funkcja do walidacji liczb typu int
     * @param input
     * @return wartość logiczna założeń funkcji
     */
    public static boolean validateInteger(String input) {
        try {
            return Integer.parseInt(input) >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Funkcja waliduje liczby typu double
     * @param input
     * @return wartość logiczna jeśli liczba typu double, wieksza od 0
     */
    public static boolean validateDouble(String input) {
        try {
            return Double.parseDouble(input) >= 0.0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Funckja waliduje kod pocztowy
     * @param postalCode
     * @return wartość typu boolean, w zależności czy kod jest poprawny
     */
    public static boolean validatePostalCode(String postalCode) {
        String postalCodePattern = "\\d{2}-\\d{3}";

        return postalCode.matches(postalCodePattern);
    }

}
