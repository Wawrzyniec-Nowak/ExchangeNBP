package pl.parser.nbp.service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Computes arithmetic operations.
 */
public interface Calculator<T> {

    /**
     * Calculates an average value.
     * @param elements list of elements based on which the average value should be calculated
     * @return The average value.
     */
    BigDecimal calculateAverage(List<T> elements);

    /**
     * Calculates a standard deviation value.
     * @param elements list of elements based on which the standard deviation value should be calculated
     * @return The standard deviation value.
     */
    BigDecimal calculateStandardDeviation(List<T> elements);
}
