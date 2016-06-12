package pl.parser.nbp.service;

import pl.parser.nbp.model.Exchange;

import java.math.BigDecimal;
import java.util.List;

/**
 * Implementation of the exchange calculator which stores methods related with arithmetic operations on exchanges.
 */
public final class ExchangeCalculator implements Calculator {

    private List<Exchange> exchanges;

    /**
     * Creates calculator object.
     * @param exchanges The list of the entities basing on which the arithmetic operations should be done.
     */
    public ExchangeCalculator(List<Exchange> exchanges) {
        this.exchanges = exchanges;
    }

    /**
     * Calculates an average value from the values of the buy exchange
     * basing on a list of the exchanges passed in constructor.
     * @return The average value of the buy exchange.
     */
    @Override
    public BigDecimal calculateAverage() {
        BigDecimal sum = exchanges.stream().map(Exchange::getBuyValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(new BigDecimal(exchanges.size()), BigDecimal.ROUND_DOWN);
    }

    /**
     * Calculates a standard deviation value from the values of the sell exchange
     * basing on a list of the exchanges passed in constructor.
     * @return The standard deviation value of the sell exchange.
     */
    @Override
    public BigDecimal calculateStandardDeviation() {
        BigDecimal average = calculateAverage();
        BigDecimal temp = BigDecimal.ZERO;
        for(Exchange exchange : exchanges) {
            temp = temp.add(average.subtract(exchange.getSellValue()).multiply(average.subtract(exchange.getSellValue())));
        }
        return new BigDecimal(Math.sqrt(temp.divide(new BigDecimal(exchanges.size()), BigDecimal.ROUND_DOWN).doubleValue()));
    }
}
