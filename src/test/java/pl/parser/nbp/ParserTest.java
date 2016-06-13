package pl.parser.nbp;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import pl.parser.nbp.model.Filter;
import pl.parser.nbp.model.Storage;
import pl.parser.nbp.xml.ExchangeHandler;
import pl.parser.nbp.xml.ExchangeXMLParser;
import pl.parser.nbp.xml.XMLParser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.*;
import static pl.parser.nbp.config.Constants.NBP.DATE_FORMAT;
import static pl.parser.nbp.config.Constants.NBP.PREFIX_PATH;

/**
 * Holds all tests which verify parser methods.
 */
public class ParserTest {

    private final XMLParser parser = new ExchangeXMLParser();
    private final Storage storage = new Storage();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private final String path = PREFIX_PATH + "c073z070413.xml";

    @Test
    public void shouldParseXMLFileAndSaveEntityInStorage() {
        Filter filter = new Filter("USD", LocalDate.parse("2007-04-12", formatter), LocalDate.parse("2007-04-14", formatter));
        DefaultHandler handler = new ExchangeHandler(storage, filter);
        parser.parse(path, handler);
        Assert.assertTrue(storage.getExchanges().size() == 1);
    }

    @Test
    public void shouldParseXMLFileAndSaveFilteredEntityInStorage() {
        Filter filter = new Filter("USD", LocalDate.parse("2007-04-12", formatter), LocalDate.parse("2007-04-14", formatter));
        DefaultHandler handler = new ExchangeHandler(storage, filter);
        parser.parse(path, handler);
        Assert.assertTrue(storage.getExchanges().stream().allMatch(exchange -> "USD".equals(exchange.getCode())));
    }

    @Test
    public void shouldParseXMLFileAndSaveNoEntityBecauseOfMissingCurrencyCode() {
        Filter filter = new Filter("XXX", LocalDate.parse("2007-04-12", formatter), LocalDate.parse("2007-04-14", formatter));
        DefaultHandler handler = new ExchangeHandler(storage, filter);
        parser.parse(path, handler);
        Assert.assertTrue(storage.getExchanges().size() == 0);
    }

    @Test
    public void shouldParseXMLFileAndSaveEntityEvenIfRangeDatesAreEqual() {
        Filter filter = new Filter("USD", LocalDate.parse("2007-04-13", formatter), LocalDate.parse("2007-04-13", formatter));
        DefaultHandler handler = new ExchangeHandler(storage, filter);
        parser.parse(path, handler);
        Assert.assertTrue(storage.getExchanges().size() == 1);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionBecauseOfIncorrectDateRange() {
        new Filter("USD", LocalDate.parse("2008-04-13", formatter), LocalDate.parse("2007-04-13", formatter));
    }

    @Test
    public void shouldCallStartDocumentFromHandlerExactlyOnce() throws SAXException {
        DefaultHandler handler = mock(ExchangeHandler.class);
        parser.parse(path, handler);

        verify(handler, times(1)).startDocument();
    }
}
