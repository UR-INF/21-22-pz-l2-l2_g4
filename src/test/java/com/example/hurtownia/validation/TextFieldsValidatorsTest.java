package com.example.hurtownia.validation;


import org.junit.Assert;
import org.junit.Test;

public class TextFieldsValidatorsTest {
    @Test
    public void testValidateEmail_ValidEmail_ReturnsTrue() {
        String email = "test@example.com";

        boolean isValid = TextFieldsValidators.validateEmail(email);

        Assert.assertTrue(isValid);
    }

    @Test
    public void testValidateEmail_InvalidEmail_ReturnsFalse() {
        String email = "invalid-email";

        boolean isValid = TextFieldsValidators.validateEmail(email);

        Assert.assertFalse(isValid);
    }

    @Test
    public void testValidatePhoneNumber_ValidPhoneNumber_ReturnsTrue() {
        String phoneNumber = "123456789";

        boolean isValid = TextFieldsValidators.validatePhoneNumber(phoneNumber);

        Assert.assertTrue(isValid);
    }

    @Test
    public void testValidatePhoneNumber_InvalidPhoneNumber_ReturnsFalse() {
        String phoneNumber = "12345";

        boolean isValid = TextFieldsValidators.validatePhoneNumber(phoneNumber);

        Assert.assertFalse(isValid);
    }

    @Test
    public void testValidateNip_ValidNip_ReturnsTrue() {
        String nip = "1234567890";

        boolean isValid = TextFieldsValidators.validateNip(nip);

        Assert.assertTrue(isValid);
    }

    @Test
    public void testValidateNip_InvalidNip_ReturnsFalse() {
        String nip = "12345";

        boolean isValid = TextFieldsValidators.validateNip(nip);

        Assert.assertFalse(isValid);
    }

    @Test
    public void testValidatePesel_ValidPesel_ReturnsTrue() {
        String pesel = "12345678901";

        boolean isValid = TextFieldsValidators.validatePesel(pesel);

        Assert.assertTrue(isValid);
    }

    @Test
    public void testValidatePesel_InvalidPesel_ReturnsFalse() {
        String pesel = "12345";

        boolean isValid = TextFieldsValidators.validatePesel(pesel);

        Assert.assertFalse(isValid);
    }

    @Test
    public void testValidateInteger_ValidInteger_ReturnsTrue() {
        String input = "123";

        boolean isValid = TextFieldsValidators.validateInteger(input);

        Assert.assertTrue(isValid);
    }

    @Test
    public void testValidateInteger_InvalidInteger_ReturnsFalse() {
        String input = "abc";

        boolean isValid = TextFieldsValidators.validateInteger(input);

        Assert.assertFalse(isValid);
    }

    @Test
    public void testValidateDouble_ValidDouble_ReturnsTrue() {
        String input = "123.45";

        boolean isValid = TextFieldsValidators.validateDouble(input);

        Assert.assertTrue(isValid);
    }

    @Test
    public void testValidateDouble_InvalidDouble_ReturnsFalse() {
        String input = "abc";

        boolean isValid = TextFieldsValidators.validateDouble(input);

        Assert.assertFalse(isValid);
    }

    @Test
    public void testValidatePostalCode_ValidPostalCode_ReturnsTrue() {
        String postalCode = "12-345";

        boolean isValid = TextFieldsValidators.validatePostalCode(postalCode);

        Assert.assertTrue(isValid);
    }

    @Test
    public void testValidatePostalCode_InvalidPostalCode_ReturnsFalse() {
        String postalCode = "12345";

        boolean isValid = TextFieldsValidators.validatePostalCode(postalCode);

        Assert.assertFalse(isValid);
    }
}

