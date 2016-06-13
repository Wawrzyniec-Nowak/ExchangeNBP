package pl.parser.nbp;

import pl.parser.nbp.service.ExchangeFacade;
import pl.parser.nbp.service.ExchangeValidator;

import static pl.parser.nbp.config.Constants.NBP.DATE_FORMAT;

/**
 * The main class of the program which starts algorithm.
 */
public class MainClass {

    /**
     * Starts program. Required parameters are:
     * - currency code
     * - starting date
     * - ending date
     * @param args User input
     */
    public static void main(String[] args) {

        if (!ExchangeValidator.validateInputLength(args)) {
            System.err.println("You must specify currency code and dates range.");
            System.exit(-1);
        }

        String currencyCode = args[0];
        String publicationDateFrom = args[1];
        String publicationDateTo = args[2];

        if (!ExchangeValidator.validateDateFormat(publicationDateFrom, DATE_FORMAT) ||
            !ExchangeValidator.validateDateFormat(publicationDateTo, DATE_FORMAT) ||
            !ExchangeValidator.validateRangeDate(publicationDateFrom, publicationDateTo)) {
            System.err.println("Check your dates.");
            System.exit(-1);
        }

        ExchangeFacade facade = new ExchangeFacade();

        System.out.println("Computing has been started");
        long start = System.currentTimeMillis();
        facade.compute(currencyCode, publicationDateFrom, publicationDateTo);
        System.out.println("Computing has been finished after: " + (System.currentTimeMillis()-start) + "ms");
        facade.printCalculation();
    }
}
