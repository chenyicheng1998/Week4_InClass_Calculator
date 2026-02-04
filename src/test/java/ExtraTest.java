
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ExtraTest extends AbstractParent {

    private static Calculator calculator = new Calculator();
    private final double DELTA = 0.001;

    @BeforeAll
    public static void testPowerOn() {
        System.out.println("@BeforeAll Power ON (before the first test)");
        calculator.powerOn();
    }

    @AfterAll
    public static void testPowerOff() {
        System.out.println("@AfterAll Power OFF (all tests executed).");
        calculator.powerOff();
        calculator = null;
    }

    @BeforeEach
    public void testReset() {
        System.out.println("  Reset calculator.");
        calculator.reset();
        assertEquals(0, calculator.getResult(), DELTA, "Reset failed");
    }

    // Parameterized tests for square operations
    @ParameterizedTest(name = "Square of {0} should be {1}")
    @CsvSource({ "2, 4", "4, 16", "5, 25", "0, 0", "10, 100" })
    public void testSquareParameterized(int number, int expected) {
        calculator.square(number);
        assertEquals(expected, calculator.getResult(), DELTA, "Squaring number " + number + " is incorrect");
    }

    // Parameterized tests for square root operations
    @ParameterizedTest(name = "Square root of {0} should be approximately {1}")
    @CsvSource({ "4, 2", "9, 3", "16, 4", "25, 5", "0, 0" })
    public void testSquareRootParameterized(int number, double expected) {
        calculator.squareRoot(number);
        assertEquals(expected, calculator.getResult(), DELTA, "Square root of " + number + " is incorrect");
    }

    @Test
    public void testSquareRoot2() {
        calculator.squareRoot(2);
        assertEquals(Math.sqrt(2), calculator.getResult(), DELTA, "Square root of 2 is incorrect");
    }

    @Test
    @DisplayName("Test negative square root")
    public void testSquareRootNegative() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> calculator.squareRoot(-4));
        assertEquals("Cannot calculate square root of negative number", exception.getMessage());
    }

    @Test
    @DisplayName("Test square root of large number")
    public void testSquareRootLargeNumber() {
        calculator.squareRoot(100);
        assertEquals(10, calculator.getResult(), DELTA, "Square root of 100 is incorrect");
    }
}
