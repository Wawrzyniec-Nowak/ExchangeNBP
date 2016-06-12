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

        ExchangeValidator validator = ExchangeValidator.getInstance();
        if (!validator.validateInputLength(args)) {
            System.err.println(validator.getError());
            System.exit(-1);
        }

        String currencyCode = args[0];
        String publicationDateFrom = args[1];
        String publicationDateTo = args[2];

        if (!validator.validateDateFormat(publicationDateFrom, DATE_FORMAT) ||
            !validator.validateDateFormat(publicationDateTo, DATE_FORMAT) ||
            !validator.validateRangeDate(publicationDateFrom, publicationDateTo)) {
            System.err.println(validator.getError());
            System.exit(-1);
        }

        System.out.println("Validation has passed");

        ExchangeFacade facade = ExchangeFacade.getInstance();

        System.out.println("Computing has been started");
        long start = System.currentTimeMillis();
        facade.compute(currencyCode, publicationDateFrom, publicationDateTo);
        System.out.println("Computing has been finished after: " + (System.currentTimeMillis()-start) + "ms");
        facade.printCalculation();
    }
}
