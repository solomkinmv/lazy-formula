import java.util.LinkedList;
import java.util.Map;

public class ExpressionParser {

    private static final Map<Character, Operator> OPERATORS = Map.of('+', new SumOperator(),
                                                                     '-', new MinusOperator(),
                                                                     '*', new MultiplyOperator(),
                                                                     '/', new DivideOperator());
    private final String text;
    private int index;

    public ExpressionParser(String text) {
        this.text = text;
    }

    Expression parse() {
        LinkedList<Expression> operands = new LinkedList<>();
        LinkedList<Operator> operators = new LinkedList<>();

        while (indexInBound()) {
            if (currChar() == ')') {
                index++;
                return foldOperandsAndOperators(operands, operators);
            }
            if (currentIsSimpleOperand()) {
                operands.push(new DefinedOperand(readNumber()));
                continue;
            }
            if (currentIsOperator()) {
                addOperator(operands, operators);
                continue;
            }
            if (currentIsCompoundOperand()) {
                index++;
                operands.push(parse());
                continue;
            }
            if (currentIsUndefinedOperand()) {
                operands.push(new VariableOperand(readVariable()));
                continue;
            }
        }

        return foldOperandsAndOperators(operands, operators);
    }

    private void addOperator(LinkedList<Expression> operands, LinkedList<Operator> operators) {
        var operator = parseOperator();
        while (!operators.isEmpty() && operators.peek().greaterOrEqual(operator)) {
            foldTwoOperands(operands, operators);
        }
        operators.push(operator);
    }

    private Expression foldOperandsAndOperators(LinkedList<Expression> operands, LinkedList<Operator> operators) {
        while (!operators.isEmpty()) {
            foldTwoOperands(operands, operators);
        }
        if (operands.size() > 1) throw new IllegalStateException("Left multiple operands after fold");
        return operands.pop();
    }

    private void foldTwoOperands(LinkedList<Expression> operands, LinkedList<Operator> operators) {
        var operand2 = operands.pop();
        operands.push(operators.pop().apply(operands.pop(), operand2));
    }

    private Operator parseOperator() {
        char ch = currChar();
        index++;
        var operator = OPERATORS.get(ch);
        if (operator == null) throw new IllegalStateException("Bad operator: " + ch);
        return operator;
    }

    boolean currentIsOperator() {
        return OPERATORS.containsKey(currChar());
    }

    boolean currentIsSimpleOperand() {
        return Character.isDigit(currChar());
    }

    boolean currentIsUndefinedOperand() {
        return Character.isAlphabetic(currChar());
    }

    boolean currentIsCompoundOperand() {
        return '(' == currChar();
    }

    // precondition: index at the first digit
    // sets `index` outside of the number
    int readNumber() {
        int result = 0;
        while (indexInBound() && currentIsSimpleOperand()) {
            result = result * 10 + (currChar() - '0');
            index++;
        }

        return result;
    }

    private boolean indexInBound() {
        skipWhitespaces();
        return index < text.length();
    }

    String readVariable() {
        StringBuilder result = new StringBuilder();
        while (indexInBound() && (currentIsSimpleOperand() || currentIsUndefinedOperand())) {
            result.append(currChar());
            index++;
        }
        return result.toString();
    }

    private void skipWhitespaces() {
        if (index < text.length() && Character.isWhitespace(currChar())) {
            index++;
            skipWhitespaces();
        }
    }

    private char currChar() {
        return text.charAt(index);
    }
}
