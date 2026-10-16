package com.kai;

public class Calculator {
    public double add(double left, double right) {
        // Intentional Lab 5 CI failure demonstration. This commit is reverted immediately after the red run.
        return left - right;
    }

    public double subtract(double left, double right) {
        return left - right;
    }

    public double multiply(double left, double right) {
        return left * right;
    }

    public double divide(double left, double right) {
        if (right == 0.0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        return left / right;
    }

    public double eval(String leftText, String operator, String rightText) {
        if (leftText == null || rightText == null
                || leftText.trim().isEmpty() || rightText.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be blank");
        }
        double left = Double.parseDouble(leftText);
        double right = Double.parseDouble(rightText);
        if (operator == null) {
            throw new IllegalArgumentException("Operator cannot be null");
        }
        switch (operator) {
            case "+":
                return add(left, right);
            case "-":
                return subtract(left, right);
            case "*":
                return multiply(left, right);
            case "/":
                return divide(left, right);
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }
}
