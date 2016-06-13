package pl.parser.nbp.xml;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Implementation of the parser which is responsible for retrieving Exchange entities from files.
 */
public class ExchangeXMLParser implements XMLParser{


    /**
     * Pareses XML file and retrieves Exchange entities which meets the criterion.
     * @param path Path of the XML file.
     * @param handler SAX handler which is responsible for passing through the XML
     */
    @Override
    public void parse(String path, DefaultHandler handler) {
        URL url;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("There is no resource under specified path");
        }

        try (InputStream stream = url.openStream()){
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(stream, handler);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            System.err.println("An exception was thrown during parsing " + e);
        }
    }
}
