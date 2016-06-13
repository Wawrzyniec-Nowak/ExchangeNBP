package pl.parser.nbp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.parser.nbp.model.Exchange;
import pl.parser.nbp.service.Calculator;
import pl.parser.nbp.service.ExchangeCalculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds all test required to verify calculator methods.
 */
public class CalculatorTest {

    private Calculator<Exchange> calculator;
    private List<Exchange> exchanges;

    @Before
    public void init() {
        calculator = new ExchangeCalculator();
        exchanges = new ArrayList<>();
        Exchange e1 = new Exchange(new BigDecimal("1.6"), new BigDecimal("2.1"));
        Exchange e2 = new Exchange(new BigDecimal("2.2"), new BigDecimal("2.3"));
        Exchange e3 = new Exchange(new BigDecimal("3.1"), new BigDecimal("2.5"));
        exchanges.add(e1);
        exchanges.add(e2);
        exchanges.add(e3);
    }

    @Test
    public void shouldCalculateAverageEqualsToTwoPointThree() {
        BigDecimal average = calculator.calculateAverage(exchanges);
        Assert.assertEquals(new BigDecimal("2.3"), average);
    }

    @Test
    public void shouldCalculateStandardDeviationEqualsToZeroPointTwo() {
        BigDecimal standardDeviation = calculator.calculateStandardDeviation(exchanges);
        Assert.assertEquals(new BigDecimal("0.2"), standardDeviation.round(new MathContext(1)));
    }
}
