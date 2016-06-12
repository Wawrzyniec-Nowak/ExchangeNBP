package pl.parser.nbp.service;

import pl.parser.nbp.model.Exchange;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wawek on 11.06.16.
 */
public final class ExchangeCalculator implements Calculator {

    private List<Exchange> exchanges;

    public ExchangeCalculator(List<Exchange> exchanges) {
        this.exchanges = exchanges;
    }

    public BigDecimal calculateAverage() {
        BigDecimal sum = exchanges.stream().map(Exchange::getBuyValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(new BigDecimal(exchanges.size()), BigDecimal.ROUND_DOWN);
    }

    public BigDecimal calculateStandardDeviation() {
        BigDecimal average = calculateAverage();
        BigDecimal temp = BigDecimal.ZERO;
        for(Exchange exchange : exchanges) {
            temp = temp.add(average.subtract(exchange.getSellValue()).multiply(average.subtract(exchange.getSellValue())));
        }
        return new BigDecimal(Math.sqrt(temp.divide(new BigDecimal(exchanges.size()), BigDecimal.ROUND_DOWN).doubleValue()));
    }
}
