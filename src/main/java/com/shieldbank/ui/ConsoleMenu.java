package com.shieldbank.ui;

import java.util.Scanner;

public class ConsoleMenu {
    private static final int MAX_ATTEMPTS = 5;
    private final Scanner scanner = new Scanner(System.in);

    public int readInt(String prompt) {
        int attempts = 0;
        
        while (attempts < MAX_ATTEMPTS) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException exception) {
                attempts++;
                System.out.println("Ошибка: нужно целое число.");
            }
        }
        throw new IllegalStateException(
                "Превышено число попыток ввода");
    }

    public long readLong(String prompt) {
        int attempts = 0;
        
        while (attempts < MAX_ATTEMPTS) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Long.parseLong(line);
            } catch (NumberFormatException exception) {
                attempts++;
                System.out.println("Ошибка: нужно целое число.");
            }
        }
        throw new IllegalStateException(
                "Превышено число попыток ввода");
    }
}
