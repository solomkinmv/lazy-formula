import java.util.Map;

public interface Operator {

    Expression apply(Expression expression1, Expression expression2);

    int getOrder();

    default boolean greaterOrEqual(Operator o) {
        return getOrder() - o.getOrder() >= 0;
    }
}
