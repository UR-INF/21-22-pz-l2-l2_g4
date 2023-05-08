package com.example.hurtownia.domain.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiscountConverterTest {

    static String code1;
    static String code2;
    static String codeDefault;
    static double code1Numeric;
    static double code2Numeric;
    static Double codeNumericDefault;
    static String code1Percentage;
    static String code2Percentage;
    static String codePercentageDefault;

    @BeforeEach
    void setUp() {
        code1 = "KOD1";
        code2 = "KOD2";
        codeDefault = "";
        code1Numeric = 0.1;
        code2Numeric = 0.2;
        codeNumericDefault = 0.0;
        code1Percentage = "-10%";
        code2Percentage = "-20%";
        codePercentageDefault = "nie udzielono";
    }

    @Test
    void fromNumericToPercentage() {
        assertThat(DiscountConverter.fromNumericToPercentage(code1Numeric)).isEqualTo(code1Percentage);
        assertThat(DiscountConverter.fromNumericToPercentage(code2Numeric)).isEqualTo(code2Percentage);
        assertThat(DiscountConverter.fromCodeToPercentage("anyOther")).isEqualTo(codePercentageDefault);
    }

    @Test
    void fromNumericToCode() {
        assertThat(DiscountConverter.fromNumericToCode(code1Numeric)).isEqualTo(code1);
        assertThat(DiscountConverter.fromNumericToCode(code2Numeric)).isEqualTo(code2);
        assertThat(DiscountConverter.fromNumericToCode(0.0)).isEqualTo(codeDefault);
    }

    @Test
    void fromPercentageToCode() {
        assertThat(DiscountConverter.fromPercentageToCode(code1Percentage)).isEqualTo(code1);
        assertThat(DiscountConverter.fromPercentageToCode(code2Percentage)).isEqualTo(code2);
        assertThat(DiscountConverter.fromPercentageToCode("anyOther")).isEqualTo(codeDefault);
    }

    @Test
    void fromPercentageToNumeric() {
        assertThat(DiscountConverter.fromPercentageToNumeric(code1Percentage)).isEqualTo(code1Numeric);
        assertThat(DiscountConverter.fromPercentageToNumeric(code2Percentage)).isEqualTo(code2Numeric);
        assertThat(DiscountConverter.fromPercentageToNumeric("anyOther")).isEqualTo(codeNumericDefault);
    }

    @Test
    void fromCodeToPercentage() {
        assertThat(DiscountConverter.fromCodeToPercentage(code1)).isEqualTo(code1Percentage);
        assertThat(DiscountConverter.fromCodeToPercentage(code2)).isEqualTo(code2Percentage);
        assertThat(DiscountConverter.fromCodeToPercentage("anyOther")).isEqualTo(codePercentageDefault);
    }

    @Test
    void fromCodeToNumeric() {
        assertThat(DiscountConverter.fromCodeToNumeric(code1)).isEqualTo(code1Numeric);
        assertThat(DiscountConverter.fromCodeToNumeric(code2)).isEqualTo(code2Numeric);
        assertThat(DiscountConverter.fromCodeToNumeric("anyOther")).isEqualTo(codeNumericDefault);
    }
}