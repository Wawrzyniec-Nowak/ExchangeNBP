package pl.parser.nbp.model;

import java.time.LocalDate;

/**
 * Filter to compute values only for specified input parameters: currency, dates range.
 */
public final class Filter {

    private final String currencyCode;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;

    /**
     * Creates filter instance and sets its fields.
     * @param currencyCode Code of the currency.
     * @param dateFrom Starting date.
     * @param dateTo Ending date.
     */
    public Filter(String currencyCode, LocalDate dateFrom, LocalDate dateTo) {
        this.currencyCode = currencyCode;
        if (dateFrom.isAfter(dateTo)) {
            throw new IllegalStateException("Starting date is after ending date");
        }
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }
}
