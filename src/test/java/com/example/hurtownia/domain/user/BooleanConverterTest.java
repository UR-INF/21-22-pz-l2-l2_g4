package com.example.hurtownia.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class BooleanConverterTest {

    static String no;
    static String yes;

    @BeforeEach
    void setUp() {
        no = "Nie";
        yes = "Tak";
    }

    @Test
    void convertToString() {
        assertThat(BooleanConverter.convertToString(true)).isEqualTo(yes);
        assertThat(BooleanConverter.convertToString(false)).isEqualTo(no);
    }

    @Test
    void convertToBoolean() {
        assertThat(BooleanConverter.convertToBoolean(yes)).isEqualTo(true);
        assertThat(BooleanConverter.convertToBoolean(no)).isEqualTo(false);
    }
}