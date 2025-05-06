import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    Calculator calculator = new Calculator();

    // Basic operations
    @Test
    public void testAddition() {
        assertEquals(5.0, calculator.evaluateMathExpression("2 + 3"), 0.0001);
    }

    @Test
    public void testSubtraction() {
        assertEquals(1.0, calculator.evaluateMathExpression("4 - 3"), 0.0001);
    }

    @Test
    public void testMultiplication() {
        assertEquals(12.0, calculator.evaluateMathExpression("3 * 4"), 0.0001);
    }

    // Operator precedence
    @Test
    public void testPrecedence() {
        assertEquals(14.0, calculator.evaluateMathExpression("2 + 3 * 4"), 0.0001);
    }

    // Parentheses
    @Test
    public void testParentheses() {
        assertEquals(20.0, calculator.evaluateMathExpression("(2 + 3) * 4"), 0.0001);
    }

    // Negative numbers
    @Test
    public void testNegativeNumberAtStart() {
        assertEquals(-5.0, calculator.evaluateMathExpression("-5"), 0.0001);
    }

    @Test
    public void testNegativeNumberInExpression() {
        assertEquals(-1.0, calculator.evaluateMathExpression("2 + -3"), 0.0001);
    }

    @Test
    public void testNegativeMultiplication() {
        assertEquals(-6.0, calculator.evaluateMathExpression("-2 * 3"), 0.0001);
    }

    // Floating point
    @Test
    public void testFloatingPoint() {
        assertEquals(5.5, calculator.evaluateMathExpression("2.0 + 3.5"), 0.0001);
    }

    @Test
    public void testComplexExpression() {
        assertEquals(7.0, calculator.evaluateMathExpression("1 + 2 * (3)"), 0.0001);
    }
}
