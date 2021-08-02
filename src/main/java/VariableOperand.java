import java.math.BigDecimal;
import java.util.Map;

public class VariableOperand implements Expression {

    private final String variableName;

    public VariableOperand(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public BigDecimal calculate(Map<String, BigDecimal> context) {
        var value = context.get(variableName);
        if (value == null) {
            throw new IllegalStateException(String.format("Can't resolve variable %s from context %s", variableName, context));
        }
        return value;
    }

}
