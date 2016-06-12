package pl.parser.nbp.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import pl.parser.nbp.model.Exchange;
import pl.parser.nbp.model.Filter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static pl.parser.nbp.config.Constants.NBP.*;

/**
 * Implementation of the SAX handler which parses the XML file and fetches Exchange entities from it.
 */
public class ExchangeHandler extends DefaultHandler {

    private List<Exchange> exchanges;
    private Filter filter;

    private String publicationDate;
    private String currencyName;
    private String exchangeBuyValue;
    private String exchangeSellValue;

    private boolean publicationDateFlag;
    private boolean currencyNameFlag;
    private boolean exchangeBuyValueFlag;
    private boolean exchangeSellValueFlag;

    /**
     * Creates handler object and initializes the list of exchange objects.
     */
    public ExchangeHandler() {
        this.exchanges = new ArrayList<>();
    }

    /**
     * Sets the filter value.
     * @param filter
     */
    public void filter(Filter filter) {
        this.filter = filter;
    }

    /**
     * Sets the boolean flags basing on the xml tag.
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase(PUBLICATION_DATE)) {
            publicationDateFlag = true;
        } else if (qName.equalsIgnoreCase(CURRENCY_CODE)) {
            currencyNameFlag = true;
        } else if (qName.equalsIgnoreCase(BUY_EXCHANGE)) {
            exchangeBuyValueFlag = true;
        } else if (qName.equalsIgnoreCase(SELL_EXCHANGE)) {
            exchangeSellValueFlag = true;
        }
    }

    /**
     * Creates Exchange object and stores it in the list. Only entities which are valid for specified filter
     * are passed to the list.
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase(POSITION)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            if (publicationDate != null) {
                LocalDate publicationLocalDate = LocalDate.parse(publicationDate, formatter);
                // filter entities during fetching in order to save memory
                if (filter.getCurrencyName().equals(currencyName) &&
                        ((publicationLocalDate.isBefore(filter.getDateTo()) &&
                          publicationLocalDate.isAfter(filter.getDateFrom())) ||
                        publicationLocalDate.isEqual(filter.getDateTo()) ||
                        publicationLocalDate.isEqual(filter.getDateFrom()))
                    ) {

                    Exchange exchange = new Exchange(
                            new BigDecimal(exchangeBuyValue.replaceAll(",", ".")),
                            new BigDecimal(exchangeSellValue.replaceAll(",", "."))
                    );
                    exchanges.add(exchange);
                }
            }
        }
    }

    /**
     * Implementation of the default method which is responsible for retrieving value placed between
     * tags in XML file.
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (publicationDateFlag) {
            publicationDate = new String(ch, start, length);
            publicationDateFlag = false;
        }
        if (currencyNameFlag) {
            currencyName = new String(ch, start, length);
            currencyNameFlag = false;
        }
        if (exchangeBuyValueFlag) {
            exchangeBuyValue= new String(ch, start, length);
            exchangeBuyValueFlag = false;
        }
        if (exchangeSellValueFlag) {
            exchangeSellValue= new String(ch, start, length);
            exchangeSellValueFlag = false;
        }
    }

    /**
     * Returns the immutable list of the parsed entities.
     * @return The immutable list of Exchanges.
     */
    public List<Exchange> getExchanges() {
        return Collections.unmodifiableList(exchanges);
    }
}
