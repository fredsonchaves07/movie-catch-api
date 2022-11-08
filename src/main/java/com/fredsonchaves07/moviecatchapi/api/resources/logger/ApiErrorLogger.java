package com.fredsonchaves07.moviecatchapi.api.resources.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiErrorLogger {

    public static void generateLog(Exception error) {
        final Logger logger = LoggerFactory.getLogger(error.getClass());
        logger.info(error.getMessage());
        logger.error("Traceback: ", error);
    }
}
