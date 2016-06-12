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
import java.util.Collections;
import java.util.List;

/**
 * Created by wawek on 11.06.16.
 */
public class ExchangeParser implements Parser {

    private List<Exchange> exchanges;
    private Filter filter;

    public ExchangeParser(Filter filter, List<Exchange> exchanges) {
        this.filter = filter;
        this.exchanges = exchanges;
    }

    public void parse(String path) {
        URL url = null;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("There is no resource under specified path");
        }

        try (InputStream stream = url.openStream()){
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            ExchangeHandler handler = new ExchangeHandler();
            handler.filter(filter);
            saxParser.parse(stream, handler);
            this.exchanges.addAll(handler.getExchanges());
        } catch (IOException | ParserConfigurationException | SAXException e) {
            System.err.println("An exception was thrown during parsing " + e);
        }
    }

    public List<Exchange> getExchanges() {
        return Collections.unmodifiableList(exchanges);
    }
}
