package ru.redych.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import ru.redych.data.interfaces.IPropertiesRepository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * PropertiesRepository implementation
 */
public class PropertiesRepository implements IPropertiesRepository {
    private static final Logger log = LogManager.getLogger(PropertiesRepository.class.getName());

    static {
        // Logger setup
        String logLevel = System.getProperty("logLevel", "ERROR"); // Default to OFF
        Configurator.setRootLevel(org.apache.logging.log4j.Level.toLevel(logLevel));
    }

    private final Properties properties = new Properties();

    /**
     * Constructor for PropertiesRepository
     */
    public PropertiesRepository() {
        log.debug("Reading the properties file");
        // Trying to read properties file from resources
        try (InputStream inputStream = PropertiesRepository.class.getClassLoader().getResourceAsStream("application.properties");
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            properties.load(bufferedReader);
        } catch (Exception e) {
            log.error("Error while reading the properties file! It may not exist or be corrupted!");
        }
    }

    @Override
    public String getPropertyByName(String name) {
        return properties.getProperty(name);
    }
}
