package pl.parser.nbp;

import org.junit.Assert;
import org.junit.Test;
import pl.parser.nbp.xml.XMLBuySellExchangeFileFinder;
import pl.parser.nbp.xml.XMLExchangeFileFinder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static pl.parser.nbp.config.Constants.NBP.DATE_FORMAT;

/**
 * Holds all tests which verify file finder methods.
 */
public class FileFinderTest {

    private XMLExchangeFileFinder fileFinder;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @Test
    public void shouldGetXMLFilesFrom20160101To20160301() {
        fileFinder
                = new XMLBuySellExchangeFileFinder(LocalDate.parse("2016-01-01", formatter), LocalDate.parse("2016-03-01", formatter));
        List<String> xmlFiles = fileFinder.findXMLFiles();
        Assert.assertTrue(xmlFiles.size() > 0);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionBecauseOfDateRange() {
        fileFinder
                = new XMLBuySellExchangeFileFinder(LocalDate.parse("2016-01-01", formatter), LocalDate.parse("2015-03-01", formatter));
        fileFinder.findXMLFiles();
    }

    @Test
    public void shouldReturnEmptyListBecauseOfFutureDateRange() {
        LocalDate nextYear = LocalDate.now().plusYears(1);
        LocalDate twoYearsAfter = LocalDate.now().plusYears(2);
        fileFinder
                = new XMLBuySellExchangeFileFinder(nextYear, twoYearsAfter);
        List<String> xmlFiles = fileFinder.findXMLFiles();
        Assert.assertTrue(xmlFiles.size() == 0);
    }
}
