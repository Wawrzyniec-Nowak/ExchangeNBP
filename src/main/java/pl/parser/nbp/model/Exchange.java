package pl.parser.nbp.model;

import java.math.BigDecimal;

/**
 * Created by wawek on 11.06.16.
 */
public final class Exchange {

    private BigDecimal buyValue;
    private BigDecimal sellValue;
    private String name;

    public Exchange(BigDecimal buyValue, BigDecimal sellValue, String name) {
        this.buyValue = buyValue;
        this.sellValue = sellValue;
        this.name = name;
    }

    public BigDecimal getBuyValue() {
        return buyValue;
    }

    public BigDecimal getSellValue() {
        return sellValue;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("Exchange{buyValue=%s, sellValue=%s, name='%s'}", buyValue, sellValue, name);
    }
}
