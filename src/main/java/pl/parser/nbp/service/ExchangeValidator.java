package pl.parser.nbp.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static pl.parser.nbp.config.Constants.NBP.DATE_FORMAT;

/**
 * Implementation of the validator needed to check the user input in the program.
 */
public class ExchangeValidator {


    /**
     * Validates the length of the input arguments passed by user.
     * @param args Array of the input arguments passed by user.
     * @return True if there are three elements in the array.
     */
    public static boolean validateInputLength(String[] args) {
        return args.length == 3;
    }

    /**
     * Validates the dates range passed by user. Firstly check if the dates are valid and then compares
     * first one with the second one.
     * @param from Date which starts the range.
     * @param to Date which ends the range.
     * @return True if @from date is before @to date or if they are equal.
     */
    public static boolean validateRangeDate(String from, String to) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        if (validateDateFormat(from, DATE_FORMAT) && validateDateFormat(to, DATE_FORMAT)) {
            if (LocalDate.parse(from, formatter).isBefore(LocalDate.parse(to, formatter)) ||
                    LocalDate.parse(from, formatter).isEqual(LocalDate.parse(to, formatter))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates the format of the passed date.
     * @param date Date which needs to be verified.
     * @param format The format of the date which is valid.
     * @return True if the passed String value can be formatted to LocalDate object basing on the @format.
     */
    public static boolean validateDateFormat(String date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
