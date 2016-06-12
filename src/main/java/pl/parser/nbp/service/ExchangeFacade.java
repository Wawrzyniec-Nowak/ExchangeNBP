package pl.parser.nbp.service;

import pl.parser.nbp.model.Exchange;
import pl.parser.nbp.model.Filter;
import pl.parser.nbp.xml.ExchangeXMLParser;
import pl.parser.nbp.xml.XMLParser;
import pl.parser.nbp.xml.XMLBuySellExchangeFileFinder;
import pl.parser.nbp.xml.XMLExchangeFileFinder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static pl.parser.nbp.config.Constants.NBP.DATE_FORMAT;
import static pl.parser.nbp.config.Constants.NBP.PREFIX_PATH;

/**
 * Facade design pattern which covers all actions needed to compute desired output.
 */
public class ExchangeFacade {

    private static ExchangeFacade instance;
    private BigDecimal average;
    private BigDecimal standardDeviation;

    /**
     * Creates the instance of the class. This project limits the number of instanced to one.
     * @return The created facade object.
     */
    public static ExchangeFacade getInstance() {
        if (instance == null) {
            instance = new ExchangeFacade();
        }
        return instance;
    }

    private ExchangeFacade() {
    }

    /**
     * Hides all operations which are required to calculate average and standard deviation values.
     * Receive arguments passed from user input then looks for xml files which stores expected exchanges,
     * then parses these files and calculate output basing on parsed entities.
     * @param currencyName Name of the currency for which the output should be calculated.
     * @param publicationFrom Lower date of a range for which the output should be calculated.
     * @param publicationTo Higher date of a range for which the output should be calculated.
     */
    public void compute(String currencyName, String publicationFrom, String publicationTo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate publicationDateFrom = LocalDate.parse(publicationFrom, formatter);
        LocalDate publicationDateTo = LocalDate.parse(publicationTo, formatter);

        // downloading
        XMLExchangeFileFinder fileFinder = new XMLBuySellExchangeFileFinder(publicationDateFrom, publicationDateTo);
        List<String> xmlFileNames = fileFinder.findXMLFiles();

        // parsing
        List<Exchange> filteredExchanges = new ArrayList<>();
        XMLParser<Exchange> parser =
                new ExchangeXMLParser(new Filter(currencyName, publicationDateFrom, publicationDateTo));
        xmlFileNames.forEach(xmlFile -> filteredExchanges.addAll(parser.parse(PREFIX_PATH + xmlFile + ".xml")));

        // calculating
        Calculator calculator = new ExchangeCalculator(filteredExchanges);
        this.average = calculator.calculateAverage();
        this.standardDeviation = calculator.calculateStandardDeviation();
    }

    /**
     * Prints the calculated average and standard deviation values to the standard output.
     */
    public void printCalculation() {
        System.out.println("Average: " + average + "\nStandard deviation: " + standardDeviation);
    }
}
