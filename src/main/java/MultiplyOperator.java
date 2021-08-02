public class MultiplyOperator implements Operator {
    @Override
    public Expression apply(Expression expression1, Expression expression2) {
        return (context) -> expression1.calculate(context).multiply(expression2.calculate(context));
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
