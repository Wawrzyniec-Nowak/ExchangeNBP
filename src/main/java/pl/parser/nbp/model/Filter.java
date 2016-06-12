package pl.parser.nbp.model;

import java.time.LocalDate;

/**
 * Created by wawek on 11.06.16.
 */
public class Filter {

    private String currencyName;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public Filter(String currencyName, LocalDate dateFrom, LocalDate dateTo) {
        this.currencyName = currencyName;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }
}
