package ru.iflex.burov.loggers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.File;

public class MessageManagerLogger {
    private static final String fileName = System.getProperty("user.dir").
            concat(File.separator).
            concat("appConfig").
            concat(File.separator).
            concat("LoggingConfig.xml");

    static {
        Configurator.initialize(null, fileName);
    }

    public static Logger getLogger(String className) {
        return LogManager.getLogger(className);
    }
}
