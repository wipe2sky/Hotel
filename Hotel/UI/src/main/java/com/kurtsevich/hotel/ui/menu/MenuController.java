package com.kurtsevich.hotel.ui.menu;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
@Log4j2
@RequiredArgsConstructor
public class MenuController {
    private final Builder builder;
    private final Navigator navigator;
    private int index = -1;

    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.err.println();
            builder.buildMenu();
            navigator.setCurrentMenu(builder.getRootMenu());
            do {
                navigate(reader);
            } while (index != 0);
        } catch (IOException e) {
            log.error("Menu Controller error", e);
        }
    }

    private void navigate(BufferedReader reader) throws IOException {
        try {
            navigator.printMenu();
            index = Integer.parseInt(reader.readLine());
            navigator.navigate(index);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            log.warn("Menu Controller error", e);
        }
    }

}
