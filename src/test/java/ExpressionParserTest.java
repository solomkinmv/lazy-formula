import org.junit.jupiter.api.Assertions;import org.junit.jupiter.api.Test;import java.math.BigDecimal;import java.util.Collections;import java.util.Map;import static org.junit.jupiter.api.Assertions.assertEquals;class ExpressionParserTest {    @Test    void parsesDefinedOperandExpression_GivenSimpleNumber() {        calculatesFormula("42", BigDecimal.valueOf(42));    }    @Test    void parsesSimpleSumExpression() {        calculatesFormula("1+1", BigDecimal.valueOf(2));    }    @Test    void parsesMultipleOperatorExpression() {        calculatesFormula("1+2+3", BigDecimal.valueOf(6));    }    @Test    void parsesSimpleParenthesesPriorityExpression() {        calculatesFormula("1-(3+1)", BigDecimal.valueOf(-3));    }    @Test    void parsesParenthesesPriorityExpression_GivenParenthesesInTheScopeOfOtherOps() {        calculatesFormula("1-(3+1)+5", BigDecimal.valueOf(2));        calculatesFormula("1-(3+1)+(5-1)", BigDecimal.valueOf(1));        calculatesFormula("1-(3+1)+(5-1)-(3+7)", BigDecimal.valueOf(-9));        calculatesFormula("((1+2)-(4)+5)", BigDecimal.valueOf(4));        calculatesFormula("1-(3+1)+(5-1)-(3+7)+((1+2)-(4)+5)", BigDecimal.valueOf(-5));    }    @Test    void parsesVariableOperandExpression() {        String variable = "asdf2";        BigDecimal value = BigDecimal.valueOf(173);        calculatesFormula(variable, value, Map.of(variable, value));        value = BigDecimal.ZERO;        calculatesFormula(variable, value, Map.of(variable, value));    }    @Test    void parsesComplexExpressionWithMultipleVariables() {        calculatesFormula("1-(3+1)+(5-1)-(Asf2V+7)+((1+asdf2a)-(4)+5)",                          BigDecimal.valueOf(-5),                          Map.of("Asf2V", BigDecimal.valueOf(3),                                 "asdf2a", BigDecimal.valueOf(2)));        calculatesFormula("1-(3+1)+(5-1)-(Asf2V+7)+((1+asdf2a)-(4)+5)",                          BigDecimal.valueOf(-54),                          Map.of("Asf2V", BigDecimal.valueOf(3),                                 "asdf2a", BigDecimal.valueOf(-47)));        calculatesFormula("1-(3+1)+(5-1)-(Asf2V+7)+((1+asdf2a)-(4)+5)",                          BigDecimal.valueOf(-11),                          Map.of("Asf2V", BigDecimal.valueOf(13),                                 "asdf2a", BigDecimal.valueOf(6)));    }    @Test    void parsesFormulaWithWhitespaces() {        calculatesFormula(" 1  -  ( 3 + 1   )+(   5-1) -(  Asf2V+7  ) +( (1+asdf2a    )- (4 )+5 )  ",                          BigDecimal.valueOf(-5),                          Map.of("Asf2V", BigDecimal.valueOf(3),                                 "asdf2a", BigDecimal.valueOf(2)));    }    @Test    void parsesExpressionWithDifferentOrderOperators() {        calculatesFormula("1+2+3*4/2-1", BigDecimal.valueOf(8));    }    @Test    void parsesExpressionsFromReadme() {        calculatesFormula("(a + b) * 20 - c",                          BigDecimal.valueOf(327),                          Map.of("a", BigDecimal.valueOf(3),                                 "b", BigDecimal.valueOf(14),                                 "c", BigDecimal.valueOf(13)));        calculatesFormula("12 - 5 * 6 / (a - 12 * (b + 5))",                          BigDecimal.valueOf(11.5),                          Map.of("a", BigDecimal.valueOf(84),                                 "b", BigDecimal.valueOf(-3)));    }    @Test    void failsToCalculateIfNoContext() {        Assertions.assertThrows(IllegalStateException.class,                                () -> new ExpressionParser("a32fsdf").parse().calculate(Collections.emptyMap()));    }    @Test    void parsesNumber() {        ExpressionParser parser = new ExpressionParser("4212");        int number = parser.readNumber();        assertEquals(4212, number);    }    @Test    void parsesVariable() {        ExpressionParser parser = new ExpressionParser("a32fsdf");        String variableName = parser.readVariable();        assertEquals("a32fsdf", variableName);    }    private void calculatesFormula(String exp, BigDecimal result) {        calculatesFormula(exp, result, Collections.emptyMap());    }    private void calculatesFormula(String exp, BigDecimal result, Map<String, BigDecimal> context) {        //when        BigDecimal calculationResult = new ExpressionParser(exp).parse().calculate(context);        //then        assertEquals(result, calculationResult);    }}