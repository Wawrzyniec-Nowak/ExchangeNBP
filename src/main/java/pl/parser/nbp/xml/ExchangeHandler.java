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
 * Created by wawek on 11.06.16.
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

    public ExchangeHandler() {
        this.exchanges = new ArrayList<>();
    }

    public void filter(Filter filter) {
        this.filter = filter;
    }

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
                            new BigDecimal(exchangeSellValue.replaceAll(",", ".")),
                            currencyName
                    );
                    exchanges.add(exchange);
                }
            }
        }
    }

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

    public List<Exchange> getExchanges() {
        return Collections.unmodifiableList(exchanges);
    }
}
