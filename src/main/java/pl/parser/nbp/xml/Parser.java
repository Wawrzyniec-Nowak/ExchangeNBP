package pl.parser.nbp.xml;

import org.xml.sax.SAXException;
import pl.parser.nbp.model.Exchange;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

/**
 * Created by wawek on 11.06.16.
 */
public interface Parser {

    void parse(String path) throws IOException, ParserConfigurationException, SAXException;
}
