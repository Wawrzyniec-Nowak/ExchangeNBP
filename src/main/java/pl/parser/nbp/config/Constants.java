package pl.parser.nbp.config;

/**
 * Holds all constant fields used in project
 */
public class Constants {

    private Constants() {

    }

    /**
     * Inner class related with NBP exchange constants
     */
    public static class NBP {

        public static final String PREFIX_PATH = "http://www.nbp.pl/kursy/xml/";
        public static final String BUY_SELL_TABLE_TYPE = "c";

        public static final String DATE_FORMAT = "yyyy-MM-dd";

        public static final String PUBLICATION_DATE = "data_publikacji";
        public static final String CURRENCY_CODE = "kod_waluty";
        public static final String BUY_EXCHANGE = "kurs_kupna";
        public static final String SELL_EXCHANGE = "kurs_sprzedazy";
        public static final String POSITION = "pozycja";

    }
}
