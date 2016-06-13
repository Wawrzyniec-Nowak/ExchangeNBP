package pl.parser.nbp.model;

import java.math.BigDecimal;

/**
 * Model of the NBP exchange entity. Stores information about buy exchange and sell exchange.
 */
public final class Exchange {

    private final BigDecimal buyValue;
    private final BigDecimal sellValue;

    /**
     * Creates object filling its fields.
     * @param buyValue The buy value of the exchange.
     * @param sellValue The sell value of the exchange.
     */
    public Exchange(BigDecimal buyValue, BigDecimal sellValue) {
        this.buyValue = buyValue;
        this.sellValue = sellValue;
    }

    public BigDecimal getBuyValue() {
        return buyValue;
    }

    public BigDecimal getSellValue() {
        return sellValue;
    }

    @Override
    public String toString() {
        return String.format("Exchange{buyValue=%s, sellValue=%s}", buyValue, sellValue);
    }
}
