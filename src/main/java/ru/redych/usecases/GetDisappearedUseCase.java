package ru.redych.usecases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import ru.redych.data.interfaces.IURLRepository;

/**
 * Use case for getting a string of disappeared URLs
 */
public class GetDisappearedUseCase {
    static {
        // Logger setup
        String logLevel = System.getProperty("logLevel", "ERROR"); // Default to OFF
        Configurator.setRootLevel(org.apache.logging.log4j.Level.toLevel(logLevel));
    }

    private static final Logger log = LogManager.getLogger(GetDisappearedUseCase.class.getName());
    private final IURLRepository yesterday;
    private final IURLRepository today;

    /**
     * Constructor for GetDisappearedUseCase
     *
     * @param yesterday Repository for yesterday's URLs
     * @param today     Repository for today's URLs
     */
    public GetDisappearedUseCase(IURLRepository yesterday, IURLRepository today) {
        this.yesterday = yesterday;
        this.today = today;
    }

    public String getDisappeared() {
        log.debug("GetDisappearedUseCase started");
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        for (String key : yesterday.getSet()) {
            if (today.getCode(key) == null) {
                builder.append("- ").append(key).append("\n");
            }
        }
        // We don't need the last new line symbol
        int lastNewLine = builder.lastIndexOf("\n");
        if (lastNewLine != -1) {
            builder.delete(lastNewLine, lastNewLine + 2);
        }
        log.debug("GetDisappearedUseCase started");
        return builder.toString();
    }
}
