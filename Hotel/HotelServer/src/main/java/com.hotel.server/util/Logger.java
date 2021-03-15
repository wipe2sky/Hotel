package com.hotel.server.util;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger implements Serializable {
    private String name;
    private static final String PATTERN_DATE_AND_TIME = "HH:mm / dd-MM-yy";
    private String currentDate;

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
            currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern(PATTERN_DATE_AND_TIME));
            bufferedWriter.write(currentDate + " - "
                    + name + " - " + level.name() + " - " + message + System.lineSeparator());
            if (level.equals(Level.FATAL)
                    || level.equals(Level.ERROR)
                    || level.equals(Level.WARNING)) {
                System.err.println(currentDate + " - "
                        + name + " - " + level.name() + " - " + message);
            } else System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void log(Level level, String message, Exception exc) {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("log.txt", true)))
        ) {
            currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern(PATTERN_DATE_AND_TIME));

            printWriter.write(currentDate + " - "
                    + name + " - " + level.name() + " - " + message + System.lineSeparator());
            exc.printStackTrace(printWriter);

            if (level.equals(Level.FATAL)
                    || level.equals(Level.ERROR)
                    || level.equals(Level.WARNING)) {
                System.err.println(currentDate + " - "
                        + name + " - " + level.name() + " - " + message);
            } else System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
