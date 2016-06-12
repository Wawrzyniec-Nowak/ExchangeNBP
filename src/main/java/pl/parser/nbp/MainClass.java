package pl.parser.nbp;

import pl.parser.nbp.service.ExchangeFacade;
import pl.parser.nbp.service.ExchangeValidator;

public class MainClass {

    public static void main(String[] args) {

        ExchangeValidator validator = ExchangeValidator.getInstance();
        if (!validator.validateInputLength(args)) {
            System.err.println(validator.getError());
            System.exit(-1);
        }

        String currencyName = args[0];
        String publicationDateFrom = args[1];
        String publicationDateTo = args[2];

        if (!validator.validateDateFormat(publicationDateFrom) ||
            !validator.validateDateFormat(publicationDateTo) ||
            !validator.validateRangeDate(publicationDateFrom, publicationDateTo)) {
            System.err.println(validator.getError());
            System.exit(-1);
        }

        System.out.println("Validation has passed");

        ExchangeFacade facade = ExchangeFacade.getInstance();

        System.out.println("Computing has been started");
        long start = System.currentTimeMillis();
        facade.startComputing(currencyName, publicationDateFrom, publicationDateTo);
        System.out.println("Computing has been finished after: " + (System.currentTimeMillis()-start) + "ms");
        facade.printCalculation();
    }
}
