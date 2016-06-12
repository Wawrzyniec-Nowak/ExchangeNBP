package pl.parser.nbp.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static pl.parser.nbp.config.Constants.NBP.DATE_FORMAT;

/**
 * Implementation of the validator needed to check the user input in the program.
 */
public class ExchangeValidator implements Validator {

    private StringBuilder message;
    private static ExchangeValidator instance;

    /**
     * Creates validator instance. This program limits number of ExchangeValidator objects to one.
     * @return
     */
    public static ExchangeValidator getInstance() {
        if (instance == null) {
            instance = new ExchangeValidator();
        }
        return instance;
    }

    /**
     * Creates validator object and initialize the error message object.
     */
    private ExchangeValidator() {
        message = new StringBuilder();
    }

    /**
     * Validates the length of the input arguments passed by user.
     * @param args Array of the input arguments passed by user.
     * @return True if there are three elements in the array.
     */
    @Override
    public boolean validateInputLength(String[] args) {
        boolean result = args.length == 3;
        if (!result) {
            message.append("In order to run program specify the currency and the range of dates.");
        }
        return result;
    }

    /**
     * Validates the dates range passed by user. Firstly check if the dates are valid and then compares
     * first one with the second one.
     * @param from Date which starts the range.
     * @param to Date which ends the range.
     * @return True if @from date is before @to date or if they are equal.
     */
    @Override
    public boolean validateRangeDate(String from, String to) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        if (validateDateFormat(from, DATE_FORMAT) && validateDateFormat(to, DATE_FORMAT)) {
            if (LocalDate.parse(from, formatter).isBefore(LocalDate.parse(to, formatter)) ||
                    LocalDate.parse(from, formatter).isEqual(LocalDate.parse(to, formatter))) {
                return true;
            }
        }
        message.append("Specified date range is invalid.");
        return false;
    }

    /**
     * Validates the format of the passed date.
     * @param date Date which needs to be verified.
     * @param format The format of the date which is valid.
     * @return True if the passed String value can be formatted to LocalDate object basing on the @format.
     */
    @Override
    public boolean validateDateFormat(String date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
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
