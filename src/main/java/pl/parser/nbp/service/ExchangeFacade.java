package pl.parser.nbp.service;

import pl.parser.nbp.model.Exchange;
import pl.parser.nbp.model.Filter;
import pl.parser.nbp.xml.ExchangeParser;
import pl.parser.nbp.xml.XMLDownloader;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static pl.parser.nbp.config.Constants.NBP.DATE_FORMAT;
import static pl.parser.nbp.config.Constants.NBP.PREFIX_PATH;

/**
 * Created by wawek on 11.06.16.
 */
public class ExchangeFacade {

    private static ExchangeFacade instance;
    private BigDecimal average;
    private BigDecimal standardDeviation;

    public static ExchangeFacade getInstance() {
        if (instance == null) {
            instance = new ExchangeFacade();
        }
        return instance;
    }

    private ExchangeFacade() {
    }

    public void startComputing(String currencyName, String publicationFrom, String publicationTo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate publicationDateFrom = LocalDate.parse(publicationFrom, formatter);
        LocalDate publicationDateTo = LocalDate.parse(publicationTo, formatter);

        // downloading
        XMLDownloader downloader = new XMLDownloader(publicationDateFrom, publicationDateTo);
        List<String> xmlFileNames = downloader.getXMLFileNamesForPeriod();

        // parsing
        ExchangeParser parser =
                new ExchangeParser(new Filter(currencyName, publicationDateFrom, publicationDateTo), new ArrayList<>());
        xmlFileNames.forEach(xmlFile -> parser.parse(PREFIX_PATH + xmlFile + ".xml"));

        List<Exchange> filteredExchanges = parser.getExchanges();

        // calculating
        Calculator calculator = new ExchangeCalculator(filteredExchanges);
        this.average = calculator.calculateAverage();
        this.standardDeviation = calculator.calculateStandardDeviation();
    }

    public void printCalculation() {
        System.out.println("Average: " + average + "\nStandard deviation: " + standardDeviation);
    }
}
