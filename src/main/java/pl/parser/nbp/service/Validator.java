package pl.parser.nbp.service;

/**
 * Created by wawek on 11.06.16.
 */
public interface Validator {

    boolean validateInputLength(String[] args);

    boolean validateRangeDate(String from, String to);

    boolean validateDateFormat(String date);
}
