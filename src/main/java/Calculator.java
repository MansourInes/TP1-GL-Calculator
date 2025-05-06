import java.util.*;

public class Calculator {

    public double evaluateMathExpression(String expression) {
        List<String> tokens = parseExpressionToTokens(expression);
        List<String> postfixTokens = convertToPostfixNotation(tokens);
        return computePostfixExpression(postfixTokens);
    }

    // === TOKENIZATION ===

    private List<String> parseExpressionToTokens(String expression) {
        List<String> tokens = new ArrayList<>();
        int index = 0;

        while (index < expression.length()) {
            char currentChar = expression.charAt(index);

            if (Character.isWhitespace(currentChar)) {
                index++;
                continue;
            }

            if (isParenthesis(currentChar)) {
                tokens.add(String.valueOf(currentChar));
                index++;
            } else if (isOperator(currentChar)) {
                index = handleOperator(expression, tokens, index);
            } else if (Character.isDigit(currentChar) || currentChar == '.') {
                index = extractNumber(expression, tokens, index);
            } else {
                throw new IllegalArgumentException("Invalid character in expression: " + currentChar);
            }
        }

        return tokens;
    }

    private boolean isParenthesis(char c) {
        return c == '(' || c == ')';
    }

    private boolean isOperator(char c) {
        return "+-*".indexOf(c) >= 0;
    }

    private int handleOperator(String expr, List<String> tokens, int index) {
        char op = expr.charAt(index);
        if (op == '-' && (tokens.isEmpty() || "+-*/(".contains(tokens.get(tokens.size() - 1)))) {
            return extractNegativeNumber(expr, tokens, index);
        } else {
            tokens.add(String.valueOf(op));
            return index + 1;
        }
    }

    private int extractNegativeNumber(String expr, List<String> tokens, int index) {
        int start = index;
        index++; // skip '-'
        while (index < expr.length() && (Character.isDigit(expr.charAt(index)) || expr.charAt(index) == '.')) {
            index++;
        }
        tokens.add(expr.substring(start, index));
        return index;
    }

    private int extractNumber(String expr, List<String> tokens, int index) {
        int start = index;
        while (index < expr.length() &&
                (Character.isDigit(expr.charAt(index)) || expr.charAt(index) == '.')) {
            index++;
        }
        tokens.add(expr.substring(start, index));
        return index;
    }

    // === CONVERT TO POSTFIX ===

    private List<String> convertToPostfixNotation(List<String> infixTokens) {
        List<String> outputQueue = new ArrayList<>();
        Deque<String> operatorStack = new ArrayDeque<>();

        for (String token : infixTokens) {
            if (isNumber(token)) {
                outputQueue.add(token);
            } else if (isOperator(token)) {
                popOperatorsWithHigherPrecedence(token, operatorStack, outputQueue);
                operatorStack.push(token);
            } else if ("(".equals(token)) {
                operatorStack.push(token);
            } else if (")".equals(token)) {
                popUntilOpeningParenthesis(operatorStack, outputQueue);
            }
        }

        while (!operatorStack.isEmpty()) {
            outputQueue.add(operatorStack.pop());
        }

        return outputQueue;
    }

    private boolean isNumber(String token) {
        return token.matches("-?\\d+(\\.\\d+)?");
    }

    private void popOperatorsWithHigherPrecedence(String currentOp, Deque<String> stack, List<String> output) {
        while (!stack.isEmpty() && !"(".equals(stack.peek()) &&
                getOperatorPrecedence(stack.peek()) >= getOperatorPrecedence(currentOp)) {
            output.add(stack.pop());
        }
    }

    private void popUntilOpeningParenthesis(Deque<String> stack, List<String> output) {
        while (!stack.isEmpty() && !"(".equals(stack.peek())) {
            output.add(stack.pop());
        }
        if (!stack.isEmpty() && "(".equals(stack.peek())) {
            stack.pop(); // discard '('
        }
    }

    private int getOperatorPrecedence(String operator) {
        switch (operator) {
            case "+": case "-":
                return 1;
            case "*":
                return 2;
            default:
                return 0;
        }
    }


    private boolean isOperator(String token) {
        return "+-*".contains(token);
    }

    // === EVALUATE POSTFIX ===

    private double computePostfixExpression(List<String> postfixTokens) {
        Deque<Double> evaluationStack = new ArrayDeque<>();

        for (String token : postfixTokens) {
            if (isNumber(token)) {
                evaluationStack.push(Double.parseDouble(token));
            } else {
                double rightOperand = evaluationStack.pop();
                double leftOperand = evaluationStack.pop();
                double result = applyOperator(token, leftOperand, rightOperand);
                evaluationStack.push(result);
            }
        }

        return evaluationStack.pop();
    }

    private double applyOperator(String operator, double left, double right) {
        switch (operator) {
            case "+":
                return left + right;
            case "-":
                return left - right;
            case "*":
                return left * right;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }

}
