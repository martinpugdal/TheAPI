package dk.martinersej.theapi.utils;

public class MathUtils {

    /**
     * Returns the minimum of two numbers.
     *
     * @param a The first number.
     * @param b The second number.
     * @return The minimum of the two numbers.
     */
    public static int max(int a, int b) {
        return Math.max(a, b);
    }

    /**
     * Returns the factorial of a number.
     *
     * @param n The number.
     * @return The factorial of the number.
     */
    public static int factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Number must be non-negative.");
        }
        return n == 0 ? 1 : n * factorial(n - 1);
    }

    /**
     * Returns the power of a number.
     *
     * @param base     The base.
     * @param exponent The exponent.
     * @return The power of the number.
     */
    public static double power(double base, int exponent) {
        return Math.pow(base, exponent);
    }

    /**
     * Checks if a number is prime.
     *
     * @param n The number.
     * @return True if the number is prime, false otherwise.
     */
    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the nth Fibonacci number.
     *
     * @param n The index of the Fibonacci number.
     * @return The nth Fibonacci number.
     */
    public static int fibonacci(int n) {
        return n <= 1 ? n : fibonacci(n - 1) + fibonacci(n - 2); // Recursive implementation
    }
}

