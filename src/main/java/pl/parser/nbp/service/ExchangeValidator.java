package pl.parser.nbp.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static pl.parser.nbp.config.Constants.NBP.DATE_FORMAT;

/**
 * Created by wawek on 11.06.16.
 */
public class ExchangeValidator implements Validator {

    private StringBuilder message;
    private static ExchangeValidator instance;

    public static ExchangeValidator getInstance() {
        if (instance == null) {
            instance = new ExchangeValidator();
        }
        return instance;
    }

    private ExchangeValidator() {
        message = new StringBuilder();
    }

    @Override
    public boolean validateInputLength(String[] args) {
        boolean result = args.length == 3;
        if (!result) {
            message.append("In order to run program specify the currency and the range of dates.");
        }
        return result;
    }

    @Override
    public boolean validateRangeDate(String from, String to) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        if (validateDateFormat(from) && validateDateFormat(to)) {
            if (LocalDate.parse(from, formatter).isBefore(LocalDate.parse(to, formatter)) ||
                    LocalDate.parse(from, formatter).isEqual(LocalDate.parse(to, formatter))) {
                return true;
            }
        }
        message.append("Specified date range is invalid.");
        return false;
    }

    @Override
    public boolean validateDateFormat(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        try {
            LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            message.append("The date has bad format.");
            return false;
        }
        return true;
    }

    public String getError() {
        return message.toString();
    }
}
