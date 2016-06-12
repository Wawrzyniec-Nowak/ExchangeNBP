package pl.parser.nbp.xml;

import org.xml.sax.SAXException;
import pl.parser.nbp.model.Exchange;
import pl.parser.nbp.model.Filter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the parser which is responsible for retrieving Exchange entities from files.
 */
public class ExchangeXMLParser implements XMLParser<Exchange> {

    private Filter filter;

    /**
     * Creates parser object and sets the value of the filter which is needed during parsing XML files.
     * @param filter
     */
    public ExchangeXMLParser(Filter filter) {
        this.filter = filter;
    }

    /**
     * Pareses XML file and retrieves Exchange entities which meets the criterion.
     * @param path Path of the XML file.
     * @return List of the Exchange objects which where retrieved from the XML file and
     * which pass the filter.
     */
    @Override
    public List<Exchange> parse(String path) {
        URL url;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("There is no resource under specified path");
        }

        List<Exchange> exchanges = new ArrayList<>();
        try (InputStream stream = url.openStream()){
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            ExchangeHandler handler = new ExchangeHandler();
            handler.filter(filter);
            saxParser.parse(stream, handler);
            exchanges.addAll(handler.getExchanges());
        } catch (IOException | ParserConfigurationException | SAXException e) {
            System.err.println("An exception was thrown during parsing " + e);
        }
        return exchanges;
    }
}
