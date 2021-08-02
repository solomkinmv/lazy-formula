import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

public interface Expression {
    default BigDecimal calculate() {
        return calculate(Collections.emptyMap());
    }

    BigDecimal calculate(Map<String, BigDecimal> context);
}
