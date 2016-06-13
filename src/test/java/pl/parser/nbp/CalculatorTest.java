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

    @Before
    public void init() {
        calculator = new ExchangeCalculator();
    }

    @Test
    public void shouldCalculateAverageEqualsToTwoPointThree() {
        List<Exchange> exchanges = new ArrayList<>();
        Exchange e1 = new Exchange(new BigDecimal("1.6"), new BigDecimal("2.1"), "EUR");
        Exchange e2 = new Exchange(new BigDecimal("2.2"), new BigDecimal("2.3"), "EUR");
        Exchange e3 = new Exchange(new BigDecimal("3.1"), new BigDecimal("2.5"), "EUR");
        exchanges.add(e1);
        exchanges.add(e2);
        exchanges.add(e3);

        BigDecimal average = calculator.calculateAverage(exchanges);
        Assert.assertEquals(new BigDecimal("2.3"), average);
    }

    @Test
    public void shouldCalculateStandardDeviationEqualsToZeroPointTwo() {
        List<Exchange> exchanges = new ArrayList<>();
        Exchange e1 = new Exchange(new BigDecimal("1.1"), new BigDecimal("2.1"), "EUR");
        Exchange e2 = new Exchange(new BigDecimal("2.0"), new BigDecimal("2.3"), "EUR");
        Exchange e3 = new Exchange(new BigDecimal("1.5"), new BigDecimal("2.5"), "EUR");
        exchanges.add(e1);
        exchanges.add(e2);
        exchanges.add(e3);

        BigDecimal standardDeviation = calculator.calculateStandardDeviation(exchanges);
        Assert.assertEquals(new BigDecimal("0.2"), standardDeviation.round(new MathContext(1)));
    }

    @Test
    public void shouldReturnZeroAsAverageBecauseOfEmptyList() {
        BigDecimal average = calculator.calculateAverage(new ArrayList<>());
        Assert.assertSame(BigDecimal.ZERO, average);
    }

    @Test
    public void shouldReturnZeroAsStandardDeviationBecauseOfEmptyList() {
        BigDecimal standardDeviation = calculator.calculateStandardDeviation(new ArrayList<>());
        Assert.assertSame(BigDecimal.ZERO, standardDeviation);
    }
}
