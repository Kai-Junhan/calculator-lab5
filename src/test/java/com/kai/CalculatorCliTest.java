package com.kai;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorCliTest {
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errors = new ByteArrayOutputStream();

    private int run(String... args) {
        return CalculatorCli.run(args, new PrintStream(output), new PrintStream(errors));
    }

    @Test
    void defaultDemoShowsAllFourOperations() {
        assertEquals(0, run());
        String text = output.toString(StandardCharsets.UTF_8);
        assertTrue(text.contains("12 + 3 = 15.0"));
        assertTrue(text.contains("12 - 3 = 9.0"));
        assertTrue(text.contains("12 * 3 = 36.0"));
        assertTrue(text.contains("12 / 3 = 4.0"));
        assertEquals(0, errors.size());
    }

    @Test
    void argumentsProduceResult() {
        assertEquals(0, run("-3", "*", "4"));
        assertEquals("-3 * 4 = -12.0" + System.lineSeparator(), output.toString(StandardCharsets.UTF_8));
    }

    @Test
    void wrongArgumentCountReturnsUsageError() {
        assertEquals(2, run("12"));
        assertTrue(errors.toString(StandardCharsets.UTF_8).contains("Usage:"));
    }

    @Test
    void divisionByZeroReturnsCalculationError() {
        assertEquals(1, run("9", "/", "0"));
        assertTrue(errors.toString(StandardCharsets.UTF_8).contains("Division by zero"));
        assertEquals(0, output.size());
    }

    @Test
    void invalidNumberReturnsCalculationError() {
        assertEquals(1, run("abc", "+", "2"));
        assertTrue(errors.toString(StandardCharsets.UTF_8).contains("Calculation error:"));
    }
}
