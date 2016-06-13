package pl.parser.nbp.xml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static pl.parser.nbp.config.Constants.NBP.BUY_SELL_TABLE_TYPE;
import static pl.parser.nbp.config.Constants.NBP.PREFIX_PATH;

/**
 * Implementation of the finder which is responsible for looking for files which have only buy and sell tables.
 */
public class XMLBuySellExchangeFileFinder implements XMLExchangeFileFinder {

    private final LocalDate publicationDateFrom;
    private final LocalDate publicationDateTo;

    /**
     * Creates file finder object and sets its fields.
     * @param publicationDateFrom Starting period date.
     * @param publicationDateTo Ending period date.
     */
    public XMLBuySellExchangeFileFinder(LocalDate publicationDateFrom, LocalDate publicationDateTo) {
        this.publicationDateFrom = publicationDateFrom;
        this.publicationDateTo = publicationDateTo;
    }

    /**
     * Looks for XML files' names basing on date range. The retrieved names are the names only for files
     * which stores buy and sell exchange tables.
     * @return List of the files' names which stores only buy and sell exchange tables
     * and which meet dates range criterion.
     */
    @Override
    public List<String> findXMLFiles() {
        int yearFrom = publicationDateFrom.getYear();
        int yearTo = publicationDateTo.getYear();

        List<String> xmlFiles = new ArrayList<>();
        for (int year = yearFrom; year <= yearTo; year++) {
            URL url;
            try {
                url = new URL(PREFIX_PATH + "dir" + (year != 2016 ? (year + ".txt") : ".txt"));
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("The path of a resource is malformed");
            }
            xmlFiles = readTablesFileContent(url);
        }
        return xmlFiles;
    }

    /**
     * Reads lines from external URL and returns list of these lines which starts with the letter "c" (buy and sell exchanges)
     * @param url External resource path
     * @return List of lines which start with letter "c"
     */
    private List<String> readTablesFileContent(URL url) {
        List<String> xmlFiles = new ArrayList<>();
        try (InputStream stream = url.openStream()){
            try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith(BUY_SELL_TABLE_TYPE)) {
                        xmlFiles.add(line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("IOException occurred during reading from stream");
        }
        return xmlFiles;
    }
}
