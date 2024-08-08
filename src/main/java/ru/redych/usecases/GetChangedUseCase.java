package ru.redych.usecases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import ru.redych.data.interfaces.IURLRepository;

import java.util.Objects;

/**
 * Use case for getting a string of changed URLs
 */
public class GetChangedUseCase {
    static {
        // Logger setup
        String logLevel = System.getProperty("logLevel", "ERROR"); // Default to OFF
        Configurator.setRootLevel(org.apache.logging.log4j.Level.toLevel(logLevel));
    }

    private static final Logger log = LogManager.getLogger(GetChangedUseCase.class.getName());
    private final IURLRepository yesterday;
    private final IURLRepository today;

    /**
     * Constructor for GetChangedUseCase
     *
     * @param yesterday Repository for yesterday's URLs
     * @param today     Repository for today's URLs
     */
    public GetChangedUseCase(IURLRepository yesterday, IURLRepository today) {
        this.yesterday = yesterday;
        this.today = today;
    }

    /**
     * Gets all changed URLs
     *
     * @return String of all changed URLs
     */
    public String getChanged() {
        log.debug("GetChangedUseCase started");
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        for (String key : today.getSet()) {
            if (null != yesterday.getCode(key)) {
                if (!Objects.equals(yesterday.getCode(key), today.getCode(key))) {
                    builder.append("- ").append(key).append("\n");
                }
            }
        }
        // We don't need the last new line symbol
        int lastNewLine = builder.lastIndexOf("\n");
        if (lastNewLine != -1) {
            builder.delete(lastNewLine, lastNewLine + 2);
        }
        log.debug("GetChangedUseCase finished");
        return builder.toString();
    }
}
