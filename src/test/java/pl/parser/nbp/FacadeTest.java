package pl.parser.nbp;

import org.junit.Before;
import org.junit.Test;
import pl.parser.nbp.model.Exchange;
import pl.parser.nbp.model.Storage;
import pl.parser.nbp.service.Calculator;
import pl.parser.nbp.service.ExchangeCalculator;
import pl.parser.nbp.service.ExchangeFacade;
import pl.parser.nbp.xml.ExchangeXMLParser;
import pl.parser.nbp.xml.XMLParser;

import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

/**
 * Holds tests for facade design pattern.
 */
public class FacadeTest {

    private ExchangeFacade facade = new ExchangeFacade();

    @Before
    public void init() {
        facade.boot();
    }

    @Test
    public void shouldCallCalculatorMethods() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        XMLParser parser = mock(ExchangeXMLParser.class);
        Calculator<Exchange> calculator = mock(ExchangeCalculator.class);
        Storage storage = new Storage();

        Field facadeParser = facade.getClass().getDeclaredField("parser");
        facadeParser.setAccessible(true);
        facadeParser.set(facade, parser);

        Field facadeCalculator = facade.getClass().getDeclaredField("calculator");
        facadeCalculator.setAccessible(true);
        facadeCalculator.set(facade, calculator);

        facade.compute("EUR", "2016-01-01", "2016-03-01");

        verify(calculator, times(1)).calculateAverage(storage.getExchanges());
        verify(calculator, times(1)).calculateStandardDeviation(storage.getExchanges());
    }
}
