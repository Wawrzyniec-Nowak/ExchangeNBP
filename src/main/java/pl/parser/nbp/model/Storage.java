package pl.parser.nbp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents place in the memory which stores project data.
 */
public final class Storage {

    private final List<Exchange> exchanges;

    /**
     * Initializes storage and creates new list of stored exchanges.
     */
    public Storage() {
        this.exchanges = new ArrayList<>();
    }

    public List<Exchange> getExchanges() {
        return Collections.unmodifiableList(exchanges);
    }

    /**
     * Adds new exchange to the stored entities.
     * @param exchange New exchange which should be saved.
     */
    public void save(Exchange exchange) {
        exchanges.add(exchange);
    }
}
