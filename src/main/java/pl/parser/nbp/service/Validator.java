package pl.parser.nbp.service;

/**
 * Stores methods needed to validate user input.
 */
public interface Validator {

    /**
     * Validates the array length.
     * @param args Array of the input parameters.
     * @return True if the length of the array is the same as specified in requirements.
     */
    boolean validateInputLength(String[] args);

    /**
     * Validates the dates range.
     * @param from Date which starts range.
     * @param to Date which ends range.
     * @return True if passed dates can create valid date range.
     */
    boolean validateRangeDate(String from, String to);

    /**
     * Validates the format of the date.
     * @param date Date object passed as String.
     * @param format Format of the checked date.
     * @return True if format of the date is the same as the @format value.
     */
    boolean validateDateFormat(String date, String format);
}
