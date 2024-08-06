package ru.redych.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import ru.redych.Main;
import ru.redych.data.interfaces.IMessageRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Message repository implementation
 */
public class MessageRepository implements IMessageRepository {
    private static final Logger log = LogManager.getLogger(MessageRepository.class.getName());

    static {
        // Logger setup
        String logLevel = System.getProperty("logLevel", "ERROR"); // Default to OFF
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration config = context.getConfiguration();
        Configurator.setRootLevel(org.apache.logging.log4j.Level.toLevel(logLevel));
    }
    private final String messageFile;

    /**
     * Constructor for MessageRepository
     * @param messageFile File path to messageFile. Can be either relative or absolute.
     */
    public MessageRepository(String messageFile) {
        this.messageFile = messageFile;
    }
    @Override
    public String getMessage() {
        log.debug("Reading message from the file");
        log.debug("File name:" + messageFile);
        // Trying to read from relative or absolute path first
        try (BufferedReader reader = new BufferedReader(new FileReader(messageFile))){
            StringBuilder messageBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                messageBuilder.append(line).append("\n");
            }
            return messageBuilder.toString();
        } catch (Exception e) {
            log.error("Error while reading message file: " + e.getMessage());
        }
        // Worst case scenario
        return "No message!";
    }
}
