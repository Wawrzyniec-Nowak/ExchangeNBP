package pl.parser.nbp.xml;

import org.xml.sax.helpers.DefaultHandler;

/**
 * Stores methods related with parsing XML files.
 */
public interface XMLParser {

    /**
     * Parses XML file.
     * @param path Path of the XML file.
     * @param handler SAX handler.
     */
    void parse(String path, DefaultHandler handler);
}
