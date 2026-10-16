package com.kai;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorTest {
    private final Calculator calculator = new Calculator();

    @Test
    void additionReturnsExpectedValue() {
        assertEquals(7.0, calculator.eval("3", "+", "4"));
    }

    @Test
    void subtractionReturnsExpectedValue() {
        assertEquals(3.0, calculator.eval("8", "-", "5"));
    }

    @Test
    void multiplicationReturnsExpectedValue() {
        assertEquals(12.0, calculator.eval("3", "*", "4"));
    }

    @Test
    void divisionReturnsExpectedValue() {
        assertEquals(2.5, calculator.eval("5", "/", "2"));
    }

    @Test
    void negativeValuesAreHandled() {
        assertEquals(-12.0, calculator.eval("-3", "*", "4"));
    }

    @Test
    void zeroAddendIsHandled() {
        assertEquals(5.0, calculator.eval("0", "+", "5"));
    }

    @Test
    void decimalValuesAreHandled() {
        assertEquals(0.3, calculator.eval("0.1", "+", "0.2"), 0.000001);
    }

    @Test
    void divisionByZeroThrowsArithmeticException() {
        assertThrows(ArithmeticException.class,
                () -> calculator.eval("9", "/", "0"));
    }

    @Test
    void blankInputThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.eval(" ", "+", "2"));
    }

    @Test
    void unsupportedOperatorThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.eval("2", "%", "1"));
    }
}
