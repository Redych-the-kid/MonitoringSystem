package ru.redych.util;

import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * Utility class to init the Apache Commons Cli options parser
 */
public class OptionsInitializer {
    static {
        // Logger setup
        String logLevel = System.getProperty("logLevel", "ERROR"); // Default to OFF
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration config = context.getConfiguration();
        Configurator.setRootLevel(org.apache.logging.log4j.Level.toLevel(logLevel));
    }
    private static final Logger log = LogManager.getLogger(OptionsInitializer.class.getName());
    private final Options options;

    /**
        Constructor for options initializer
        @param options Options object from Commons Cli
     */
    public OptionsInitializer(Options options) {
        this.options = options;
    }

    /**
     * Initializes the option object
     */
    public void init(){
        options.addOption("h", "help", false, "Print the help message");
        log.debug("Added option h");
        options.addOption("t1", "table1", true, "Path to table of yesterday sites");
        log.debug("Added option t1");
        options.addOption("t2", "table2", true, "Path to table of today sites");
        log.debug("Added option t2");
        options.addOption("m", "message", true, "Path to message file");
        log.debug("Added option m");
    }
}
