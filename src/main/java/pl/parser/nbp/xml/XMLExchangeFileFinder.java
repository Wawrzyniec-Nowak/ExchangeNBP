package pl.parser.nbp.xml;

import java.util.List;

/**
 * Stores methods related with finding XML files for NBP exchanges.
 */
public interface XMLExchangeFileFinder {

    /**
     * Looks for XML files for NBP exchanges.
     * @return List of the files' names.
     */
    List<String> findXMLFiles();
}
