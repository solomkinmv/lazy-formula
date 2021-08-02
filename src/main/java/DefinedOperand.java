import java.math.BigDecimal;
import java.util.Map;

public class DefinedOperand implements Expression {

    private BigDecimal value;

    public DefinedOperand(BigDecimal value) {
        this.value = value;
    }

    public DefinedOperand(int value) {
        this.value = BigDecimal.valueOf(value);
    }

    @Override
    public BigDecimal calculate(Map<String, BigDecimal> context) {
        return value;
    }

}
