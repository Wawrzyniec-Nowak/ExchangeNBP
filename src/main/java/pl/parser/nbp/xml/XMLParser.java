package pl.parser.nbp.xml;

import java.util.List;

/**
 * Stores methods related with parsing XML files.
 * @param <T> The type of the entities which should be retrieved from parsed files.
 */
public interface XMLParser<T> {

    /**
     * Parses XML file and retrieves entities from it.
     * @param path Path of the XML file.
     * @return List of the entities parsed from XML file.
     */
    List<T> parse(String path);
}
