package pl.parser.nbp.model;

import java.math.BigDecimal;

/**
 * Model of the NBP exchange entity. Stores information about buy exchange and sell exchange.
 */
public final class Exchange {

    private final BigDecimal buyValue;
    private final BigDecimal sellValue;
    private final String code;

    /**
     * Creates object filling its fields.
     * @param buyValue The buy value of the exchange.
     * @param sellValue The sell value of the exchange.
     * @param code The currency code.
     */
    public Exchange(BigDecimal buyValue, BigDecimal sellValue, String code) {
        this.buyValue = buyValue;
        this.sellValue = sellValue;
        this.code = code;
    }

    public BigDecimal getBuyValue() {
        return buyValue;
    }

    public BigDecimal getSellValue() {
        return sellValue;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.format("Exchange{buyValue=%s, sellValue=%s, code='%s'}", buyValue, sellValue, code);
    }
}
