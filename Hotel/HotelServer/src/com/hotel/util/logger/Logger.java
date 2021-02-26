package com.hotel.util.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private String name;
    private static final String PATTERN_DATE_AND_TIME = "HH:mm / dd-MM-yy";

    public Logger(String name) {
        this.name = name;
    }

    public enum Level {
        INFO,
        WARNING,
        ERROR,
        FATAL
    }

    public void log(Level level, String message) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("log.txt", true))
        ) {
            bufferedWriter.write(LocalDateTime.now().format(DateTimeFormatter.ofPattern(PATTERN_DATE_AND_TIME)) + " - "
                    + name + " - " + level.name() + " - " + message + System.lineSeparator());
            if (level.equals(Level.FATAL)
                    || level.equals(Level.ERROR)
                    || level.equals(Level.WARNING)) {
                System.err.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern(PATTERN_DATE_AND_TIME)) + " - "
                        + name + " - " + level.name() + " - " + message);
            } else System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void log(Level level, String message, Exception exc) {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("log.txt", true)))
        ) {
            printWriter.write(LocalDateTime.now().format(DateTimeFormatter.ofPattern(PATTERN_DATE_AND_TIME)) + " - "
                    + name + " - " + level.name() + " - " + message + System.lineSeparator());
            exc.printStackTrace(printWriter);

            if (level.equals(Level.FATAL)
                    || level.equals(Level.ERROR)
                    || level.equals(Level.WARNING)) {
                System.err.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern(PATTERN_DATE_AND_TIME)) + " - "
                        + name + " - " + level.name() + " - " + message);
            } else System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
