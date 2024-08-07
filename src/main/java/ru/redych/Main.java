package ru.redych;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import ru.redych.data.MessageRepository;
import ru.redych.data.PropertiesRepository;
import ru.redych.data.URLRepository;
import ru.redych.data.interfaces.IURLRepository;
import ru.redych.usecases.GetChangedUsecase;
import ru.redych.usecases.GetDisappearedUsecase;
import ru.redych.usecases.GetNewUsecase;
import ru.redych.util.OptionsInitializer;

public class Main {
    static {
        // Logger setup
        String logLevel = System.getProperty("logLevel", "ERROR"); // Default to OFF
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration config = context.getConfiguration();
        Configurator.setRootLevel(org.apache.logging.log4j.Level.toLevel(logLevel));
    }
    private static final Logger log = LogManager.getLogger(Main.class.getName());
    /**
     * Prints the help window
     * @param options Options object from Apache Commons Cli to print the options available
     */
    private static void printHelp(Options options){
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java [logLevel(optional)] -jar MonitoringSystem.jar [OPTIONS] [FILENAMES]", options);
        System.out.println("""
                Hashtable file format example:
                    example.com,200
                    ya.ru,404
                    ...
                    url,code
                """);
        System.out.println("""
                Message must contain these placeholder lines:
                    $name(name of a person to insert)
                    $disappeared(list of disappeared URLs)
                    $new(new URLs)
                    $changed(changed URLs)
                """);
    }

    public static void main(String[] args) {
        log.debug("Parsing the command line options");
        // Parse the command line arguments
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        OptionsInitializer initializer = new OptionsInitializer(options);
        initializer.init();
        CommandLine line;
        try{
            line = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
            printHelp(options);
            return;
        }
        if(line.hasOption("h")){
            printHelp(options);
            return;
        }

        log.debug("Initializing the file paths");
        // Initialize the file paths with either default values or with parsed from command line ones
        String yesterdayPath;
        String todayPath;
        String messagePath;
        if (line.hasOption("t1")) {
            yesterdayPath = line.getOptionValue("t1");
        } else{
            System.out.println("No table 1 provided! Type \"-help\" for options");
            return;
        }
        if (line.hasOption("t2")) {
            todayPath = line.getOptionValue("t2");
        } else{
            System.out.println("No table 2 provided! Type \"-help\" for options");
            return;
        }
        if (line.hasOption("m")) {
            messagePath = line.getOptionValue("m");
        } else{
            System.out.println("No message provided! Type \"-help\" for options");
            return;
        }

        log.debug("Initializing the repos");
        // Initialize the repositories
        IURLRepository yesterday = new URLRepository(yesterdayPath);
        IURLRepository today = new URLRepository(todayPath);
        PropertiesRepository propertiesRepository = new PropertiesRepository();
        MessageRepository messageRepository = new MessageRepository(messagePath);

        log.debug("Setting up the message");
        // Setting up the usecases and getting the message string
        String message = messageRepository.getMessage();
        GetChangedUsecase changedUsecase = new GetChangedUsecase(yesterday, today);
        GetDisappearedUsecase getDisappearedUsecase = new GetDisappearedUsecase(yesterday, today);
        GetNewUsecase getNewUsecase = new GetNewUsecase(yesterday, today);
        String name = propertiesRepository.getPropertyByName("name");
        if (name == null) {
            name = "имя, фамилия";
        }
        log.debug("Getting the result");
        // Assembling the result
        String result = message.replace("$changed", changedUsecase.getChanged())
                .replace("$name", name)
                .replace("$new", getNewUsecase.getNew())
                .replace("$disappeared", getDisappearedUsecase.getDisappeared());

        // Printing the result
        System.out.println(result);
    }
}