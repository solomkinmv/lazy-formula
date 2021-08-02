public class SumOperator implements Operator {
    @Override
    public Expression apply(Expression expression1, Expression expression2) {
        return (context) -> expression1.calculate(context).add(expression2.calculate(context));
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
