package com.kai;

import java.io.PrintStream;

/** Command-line adapter for the calculator from Labs 3 and 4. */
public final class CalculatorCli {
    private CalculatorCli() {
    }

    public static void main(String[] args) {
        System.exit(run(args, System.out, System.err));
    }

    static int run(String[] args, PrintStream out, PrintStream err) {
        Calculator calculator = new Calculator();
        if (args.length == 0) {
            out.println("Java Calculator - four-operation demonstration");
            for (String operator : new String[]{"+", "-", "*", "/"}) {
                out.println("12 " + operator + " 3 = " + calculator.eval("12", operator, "3"));
            }
            return 0;
        }
        if (args.length != 3) {
            err.println("Usage: java -jar calculator.jar <number> <operator> <number>");
            err.println("Example: java -jar calculator.jar 12 / 3");
            return 2;
        }
        try {
            double result = calculator.eval(args[0], args[1], args[2]);
            out.println(args[0] + " " + args[1] + " " + args[2] + " = " + result);
            return 0;
        } catch (IllegalArgumentException | ArithmeticException exception) {
            err.println("Calculation error: " + exception.getMessage());
            return 1;
        }
    }
}
