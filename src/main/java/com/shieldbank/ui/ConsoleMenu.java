package com.shieldbank.ui;

import java.util.Scanner;

/**
 * Консольный ввод и вывод меню.
 */
public class ConsoleMenu {

    private static final int MAX_ATTEMPTS = 5;

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Читает целое число с ограничением числа попыток.
     *
     * @param prompt приглашение
     * @return введённое число
     * @throws IllegalStateException при исчерпании попыток
     */
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

    /**
     * Читает длинное целое число.
     *
     * @param prompt приглашение
     * @return введённое число
     * @throws IllegalStateException при исчерпании попыток
     */
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
