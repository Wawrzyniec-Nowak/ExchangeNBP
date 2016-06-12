package pl.parser.nbp.service;

import java.math.BigDecimal;

/**
 * Computes arithmetic operations.
 */
public interface Calculator {

    /**
     * Calculates an average value.
     * @return The average value.
     */
    BigDecimal calculateAverage();

    /**
     * Calculates a standard deviation value.
     * @return The standard deviation value.
     */
    BigDecimal calculateStandardDeviation();
}
