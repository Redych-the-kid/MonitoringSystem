package ru.redych.usecases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import ru.redych.data.interfaces.IURLRepository;

/**
 * Use case for getting a string of new URLs
 */
public class GetNewUseCase {
    static {
        // Logger setup
        String logLevel = System.getProperty("logLevel", "ERROR"); // Default to OFF
        Configurator.setRootLevel(org.apache.logging.log4j.Level.toLevel(logLevel));
    }

    private static final Logger log = LogManager.getLogger(GetNewUseCase.class.getName());
    private final IURLRepository yesterday;
    private final IURLRepository today;

    /**
     * Constructor for GetNewUseCase
     *
     * @param yesterday Repository for yesterday's URLs
     * @param today     Repository for today's URLs
     */
    public GetNewUseCase(IURLRepository yesterday, IURLRepository today) {
        this.yesterday = yesterday;
        this.today = today;
    }

    public String getNew() {
        log.debug("GetNewUseCase started");
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        for (String key : today.getSet()) {
            if (yesterday.getCode(key) == null) {
                builder.append("- ").append(key).append("\n");
            }
        }
        // We don't need the last new line symbol
        int lastNewLine = builder.lastIndexOf("\n");
        if (lastNewLine != -1) {
            builder.delete(lastNewLine, lastNewLine + 2);
        }
        log.debug("GetNewUseCase finished");
        return builder.toString();
    }
}
