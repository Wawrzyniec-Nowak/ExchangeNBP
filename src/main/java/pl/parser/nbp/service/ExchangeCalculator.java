package pl.parser.nbp.service;

import pl.parser.nbp.model.Exchange;

import java.math.BigDecimal;
import java.util.List;

/**
 * Implementation of the exchange calculator which stores methods related with arithmetic operations on exchanges.
 */
public class ExchangeCalculator implements Calculator<Exchange> {

    /**
     * Calculates an average value from the values of the buy exchange
     * basing on a list of the exchanges passed in constructor.
     * @return The average value of the buy exchange.
     */
    @Override
    public BigDecimal calculateAverage(List<Exchange> exchanges) {
        if (exchanges.size() > 0) {
            BigDecimal sum = exchanges
                    .stream()
                    .map(Exchange::getBuyValue)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            return sum.divide(new BigDecimal(exchanges.size()), BigDecimal.ROUND_HALF_DOWN);
        } else {
            return BigDecimal.ZERO;
        }
    }

    /**
     * Calculates a standard deviation value from the values of the sell exchange
     * basing on a list of the exchanges passed in constructor.
     * @return The standard deviation value of the sell exchange.
     */
    @Override
    public BigDecimal calculateStandardDeviation(List<Exchange> exchanges) {
        if (exchanges.size() > 0) {
            BigDecimal sum = exchanges
                    .stream()
                    .map(Exchange::getSellValue)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal average = sum.divide(new BigDecimal(exchanges.size()), BigDecimal.ROUND_HALF_DOWN);

            sum = BigDecimal.ZERO;
            for (Exchange exchange : exchanges) {
                BigDecimal difference = average.subtract(exchange.getSellValue());
                BigDecimal power = difference.multiply(difference);
                sum = sum.add(power);
            }
            BigDecimal underRoot = sum.divide(new BigDecimal(exchanges.size()), BigDecimal.ROUND_HALF_DOWN);
            return new BigDecimal(Math.sqrt(underRoot.doubleValue()));
        } else {
            return BigDecimal.ZERO;
        }
    }
}
