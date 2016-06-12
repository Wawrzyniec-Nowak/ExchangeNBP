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
 * Created by wawek on 11.06.16.
 */
public class XMLDownloader {

    private LocalDate publicationDateFrom;
    private LocalDate publicationDateTo;

    public XMLDownloader(LocalDate publicationDateFrom, LocalDate publicationDateTo) {
        this.publicationDateFrom = publicationDateFrom;
        this.publicationDateTo = publicationDateTo;
    }

    public List<String> getXMLFileNamesForPeriod() {
        int yearFrom = publicationDateFrom.getYear();
        int yearTo = publicationDateTo.getYear();

        List<String> xmlTitles = new ArrayList<>();
        for (int year = yearFrom; year <= yearTo; year++) {
            URL url = null;
            try {
                url = new URL(PREFIX_PATH + "dir" + (year != 2016 ? (year + ".txt") : ".txt"));
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("The path of a resource is malformed");
            }

            try (InputStream stream = url.openStream()){
                try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.startsWith(BUY_SELL_TABLE_TYPE)) {
                            xmlTitles.add(line);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Exception was thrown during reading remote files " + e);
            }
        }
        return xmlTitles;
    }

}
