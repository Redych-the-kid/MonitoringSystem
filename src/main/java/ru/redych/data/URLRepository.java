package ru.redych.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import ru.redych.data.interfaces.IURLRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * URLRepository implementation
 */
public class URLRepository implements IURLRepository {
    private static final Logger log = LogManager.getLogger(URLRepository.class.getName());

    static {
        // Logger setup
        String logLevel = System.getProperty("logLevel", "ERROR"); // Default to OFF
        Configurator.setRootLevel(org.apache.logging.log4j.Level.toLevel(logLevel));
    }

    private final HashMap<String, Integer> hashMap;

    /**
     * Constructor for URLRepository
     *
     * @param fileName File path to URL table file. Can be either relative or absolute.
     */
    public URLRepository(String fileName) {
        hashMap = new HashMap<>();
        log.debug("Reading hashtable from the external file");
        log.debug("File name:" + fileName);
        // Trying to read from relative or absolute path first
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                log.debug(line);
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String key = parts[0];
                    Integer value = Integer.valueOf(parts[1]);
                    log.debug(key);
                    log.debug(value.toString());
                    hashMap.put(key, value);
                }
            }
            log.debug(hashMap.toString());
        } catch (IOException e) {
            log.error("Error while reading the URL table from file " + e.getMessage());
        } catch (NumberFormatException e) {
            log.error("File may be in wrong format or corrupted. Please read the -help message for correct file format");
            log.error(e.getMessage());
        }
    }

    @Override
    public Integer getCode(String url) {
        return hashMap.get(url);
    }

    @Override
    public Set<String> getSet() {
        return hashMap.keySet();
    }
}
