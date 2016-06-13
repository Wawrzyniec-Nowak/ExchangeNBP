package pl.parser.nbp;

import org.junit.Assert;
import org.junit.Test;
import pl.parser.nbp.service.ExchangeValidator;

/**
 * Holds all tests which verify validator methods.
 */
public class ValidatorTest {

    @Test
    public void shouldValidateInputArgsAndReturnTrue() {
        String[] args = new String[3];
        Assert.assertTrue(ExchangeValidator.validateInputLength(args));
    }

    @Test
    public void shouldValidateTooManyInputArgsAndFail() {
        String[] args = new String[4];
        Assert.assertFalse(ExchangeValidator.validateInputLength(args));
    }

    @Test
    public void shouldValidateNotEnoughInputArgsAndFail() {
        String[] args = new String[2];
        Assert.assertFalse(ExchangeValidator.validateInputLength(args));
    }

    @Test
    public void shouldValidateDateInYYYYMMDDFormatAndReturnTrue() {
        Assert.assertTrue(ExchangeValidator.validateDateFormat("2016-01-01", "yyyy-MM-dd"));
    }

    @Test
    public void shouldValidateDateYYYYMMMDDInYYYYMMDDFormatAndFail() {
        Assert.assertFalse(ExchangeValidator.validateDateFormat("2016-Jan-01", "yyyy-MM-dd"));
    }

    @Test
    public void shouldValidateDateYYYYMMDDInYYYYMMMDDFormatAndFail() {
        Assert.assertFalse(ExchangeValidator.validateDateFormat("2016-01-01", "yyyy-MMM-dd"));
    }

    @Test
    public void shouldCheckDateRangeFrom20160101To20160303() {
        Assert.assertTrue(ExchangeValidator.validateRangeDate("2016-01-01", "2016-03-03"));
    }

    @Test
    public void shouldCheckDateRangeFrom20160101To20160101() {
        Assert.assertTrue(ExchangeValidator.validateRangeDate("2016-01-01", "2016-01-01"));
    }

    @Test
    public void shouldCheckDateRangeFrom20160303To20160101AndFail() {
        Assert.assertFalse(ExchangeValidator.validateRangeDate("2016-03-03", "2016-01-01"));
    }
}
