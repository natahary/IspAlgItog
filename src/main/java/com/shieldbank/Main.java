package com.shieldbank;

import com.shieldbank.algorithms.ChangeMaker;
import com.shieldbank.algorithms.ContributionCalculator;
import com.shieldbank.algorithms.GraphAlgorithms;
import com.shieldbank.algorithms.Searcher;
import com.shieldbank.algorithms.Sorter;
import com.shieldbank.algorithms.WindowAnalytics;
import com.shieldbank.algorithms.WindowResult;
import com.shieldbank.model.Account;
import com.shieldbank.model.Operation;
import com.shieldbank.service.BankService;
import com.shieldbank.service.DataGenerator;
import com.shieldbank.service.PathResult;
import com.shieldbank.structures.Graph;
import com.shieldbank.ui.ConsoleMenu;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Точка входа приложения ShieldBank.
 */
public final class Main {

    private static final ConsoleMenu MENU = new ConsoleMenu();
    private static final long NANOSECONDS_PER_MILLISECOND = 1_000_000L;
    private static final int MIN_MONTH = 1;
    private static final int MAX_MONTH = 80;
    private static final int BENCHMARK_MONTH_SMALL = 30;
    private static final int BENCHMARK_MONTH_MEDIUM = 35;
    private static final int BENCHMARK_MONTH_LARGE = 40;
    private static final int LARGE_DATASET_SIZE = 100_000;
    private static final int SMALL_DATASET_SIZE = 50;
    private static final int TINY_DATASET_SIZE = 20;
    private static final int MEDIUM_DATASET_SIZE = 10_000;
    private static final int WINDOW_SIZE = 10;
    private static final int ALMOST_SORTED_SWAPS = 5;
    private static final long DEFAULT_TRANSFER_AMOUNT = 13_560L;
    private static final long INITIAL_BALANCE = 100_000L;

    private Main() {
    }

    /**
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = MENU.readInt("Выбор: ");
            try {
                switch (choice) {
                    case 1:
                        demoActionStack();
                        break;
                    case 2:
                        demoRegistry();
                        break;
                    case 3:
                        demoSorting();
                        break;
                    case 4:
                        demoGraph();
                        break;
                    case 5:
                        demoChangeMaker();
                        break;
                    case 6:
                        demoWindowAnalytics();
                        break;
                    case 7:
                        demoContribution();
                        break;
                    case 8:
                        demoEndToEnd();
                        break;
                    case 0:
                        running = false;
                        System.out.println("Выход.");
                        break;
                    default:
                        System.out.println("Нет такого пункта.");
                        break;
                }
            } catch (RuntimeException exception) {
                System.out.println("Ошибка: " + exception.getMessage());
            }
        }
    }

    private static void printMainMenu() {
        System.out.println();
        System.out.println("ShieldBank: итоговый проект");
        System.out.println("1. BR-1: журнал операций с откатом");
        System.out.println("2. BR-2: реестр счетов");
        System.out.println("3. BR-3: сортировки и бинарный поиск");
        System.out.println("4. BR-4: граф переводов");
        System.out.println("5. BR-5: банкомат");
        System.out.println("6. BR-6: аналитика потока");
        System.out.println("7. BR-7: накопительная программа");
        System.out.println("8. Сквозной сценарий");
        System.out.println("0. Выход");
    }

    private static void demoActionStack() {
        BankService bank = new BankService();
        bank.register(new Account(1001L, INITIAL_BALANCE));
        bank.register(new Account(1002L, INITIAL_BALANCE));

        System.out.println("До операций:");
        printBalance(bank, 1001L);
        printBalance(bank, 1002L);

        long transferAmount = MENU.readLong("Сумма перевода: ");
        bank.transfer(1001L, 1002L, transferAmount);
        System.out.println("После перевода:");
        printBalance(bank, 1001L);
        printBalance(bank, 1002L);

        bank.undoLast();
        System.out.println("После отката:");
        printBalance(bank, 1001L);
        printBalance(bank, 1002L);

        try {
            bank.undoLast();
        } catch (IllegalStateException exception) {
            System.out.println("Ожидаемо: " + exception.getMessage());
        }
    }

    private static void printBalance(BankService bank, long accountNumber) {
        Account account = bank.getRegistry().find(accountNumber);
        System.out.printf("  Счёт %d: баланс %d%n",
                accountNumber, account.getBalance());
    }

    private static void demoRegistry() {
        BankService bank = new BankService();
        bank.register(new Account(500L, INITIAL_BALANCE));
        bank.register(new Account(100L, INITIAL_BALANCE));
        bank.register(new Account(900L, INITIAL_BALANCE));
        bank.register(new Account(300L, INITIAL_BALANCE));
        bank.register(new Account(700L, INITIAL_BALANCE));

        long searchNumber = MENU.readLong("Номер счёта для поиска: ");
        Account found = bank.getRegistry().find(searchNumber);
        System.out.printf("Найден счёт: %s%n", found);

        System.out.println("Счета по возрастанию номера:");
        List<Account> accounts = bank.getRegistry().collectInOrder();
        for (Account account : accounts) {
            System.out.printf("  %s%n", account);
        }

        try {
            bank.register(new Account(500L, INITIAL_BALANCE));
        } catch (IllegalArgumentException exception) {
            System.out.println("Ожидаемо: " + exception.getMessage());
        }
    }

    private static void demoSorting() {
        Comparator<Operation> byTimestamp =
                Comparator.comparingLong(Operation::getTimestamp);
        Comparator<Operation> byAmount =
                Comparator.comparingLong(Operation::getAmount);

        System.out.printf("%-30s %-12s %-15s %-15s%n",
                "Сценарий", "Размер", "Bubble (ms)", "Merge (ms)");

        runBenchmark("По дате, маленький",
                DataGenerator.generateOperations(TINY_DATASET_SIZE), byTimestamp);
        runBenchmark("По дате, средний",
                DataGenerator.generateOperations(MEDIUM_DATASET_SIZE), byTimestamp);
        runBenchmark("По дате, большой",
                DataGenerator.generateOperations(LARGE_DATASET_SIZE), byTimestamp);
        runBenchmark("По сумме, средний",
                DataGenerator.generateOperations(MEDIUM_DATASET_SIZE), byAmount);

        List<Operation> almostSorted = DataGenerator.makeAlmostSorted(
                DataGenerator.generateSortedOperations(MEDIUM_DATASET_SIZE),
                ALMOST_SORTED_SWAPS);
        runBenchmark("Почти отсортированный", almostSorted, byTimestamp);

        List<Operation> small = DataGenerator.generateOperations(SMALL_DATASET_SIZE);
        Sorter.mergeSort(small, byTimestamp);
        long from = small.get(10).getTimestamp();
        long to = small.get(40).getTimestamp();
        List<Operation> between = Searcher.findBetween(small, from, to);
        System.out.printf("Операции в интервале [%d, %d]: %d%n",
                from, to, between.size());
    }

    private static void runBenchmark(String label, List<Operation> source,
                                     Comparator<Operation> comparator) {
        List<Operation> bubbleData = new java.util.ArrayList<>(source);
        long bubbleStart = System.nanoTime();
        Sorter.bubbleSort(bubbleData, comparator);
        long bubbleMilliseconds =
                (System.nanoTime() - bubbleStart) / NANOSECONDS_PER_MILLISECOND;

        List<Operation> mergeData = new java.util.ArrayList<>(source);
        long mergeStart = System.nanoTime();
        Sorter.mergeSort(mergeData, comparator);
        long mergeMilliseconds =
                (System.nanoTime() - mergeStart) / NANOSECONDS_PER_MILLISECOND;

        System.out.printf("%-30s %-12d %-15d %-15d%n",
                label, source.size(), bubbleMilliseconds, mergeMilliseconds);
    }

    private static void demoGraph() {
        Graph graph = new Graph();
        graph.addEdge(1L, 2L, 10);
        graph.addEdge(2L, 3L, 20);
        graph.addEdge(3L, 1L, 5);
        graph.addEdge(3L, 4L, 30);
        graph.addVertex(99L);

        long start = MENU.readLong("Стартовый счёт: ");
        System.out.printf("BFS от %d: %s%n", start,
                GraphAlgorithms.breadthFirstSearch(graph, start));

        List<Long> cycle = GraphAlgorithms.findCycle(graph);
        if (cycle.isEmpty()) {
            System.out.println("Циклов нет.");
        } else {
            System.out.printf("Цикл: %s%n", cycle);
        }

        System.out.printf("Компонент: %d%n",
                GraphAlgorithms.countComponents(graph));

        Map<Long, PathResult> paths = GraphAlgorithms.findShortestPaths(graph, start);
        System.out.println("Кратчайшие пути:");
        for (Map.Entry<Long, PathResult> entry : paths.entrySet()) {
            PathResult result = entry.getValue();
            if (result.isReachable()) {
                System.out.printf("  Счёт %d, комиссия %d, путь %s%n",
                        entry.getKey(), result.getTotalWeight(), result.getPath());
            } else {
                System.out.printf("  Счёт %d недостижим%n", entry.getKey());
            }
        }
    }

    private static void demoChangeMaker() {
        int amount = MENU.readInt("Сумма для выдачи: ");
        int[] standardDenominations = {1, 2, 5, 10, 50, 100, 200, 500,
                1000, 2000, 5000};
        System.out.println("Стандартные номиналы:");
        System.out.printf("  Жадный: %s%n",
                ChangeMaker.makeChangeGreedy(standardDenominations, amount));
        System.out.printf("  Оптимальный: %s%n",
                ChangeMaker.makeChangeOptimal(standardDenominations, amount));

        int[] nonStandardDenominations = {1, 3, 4};
        int nonStandardAmount = 6;
        System.out.printf("Номиналы {1, 3, 4}, сумма %d:%n", nonStandardAmount);
        System.out.printf("  Жадный: %s%n",
                ChangeMaker.makeChangeGreedy(nonStandardDenominations,
                        nonStandardAmount));
        System.out.printf("  Оптимальный: %s%n",
                ChangeMaker.makeChangeOptimal(nonStandardDenominations,
                        nonStandardAmount));
    }

    private static void demoWindowAnalytics() {
        long[] values = DataGenerator.generateValues(1000);
        int windowSize = MENU.readInt("Размер окна: ");
        long maximum = WindowAnalytics.maxWindowSum(values, windowSize);
        System.out.printf("Максимум по окну %d: %d%n", windowSize, maximum);

        long limit = MENU.readLong("Лимит для подсветки окон: ");
        List<WindowResult> windows =
                WindowAnalytics.findWindowsAboveLimit(values, windowSize, limit);
        System.out.printf("Окон выше лимита: %d%n", windows.size());
        int previewLimit = Math.min(windows.size(), 10);
        for (int index = 0; index < previewLimit; index++) {
            System.out.printf("  %s%n", windows.get(index));
        }

        long[] sortedValues = Sorter.mergeSort(values);
        long target = MENU.readLong("Целевая сумма пары: ");
        long[] pair = WindowAnalytics.findPair(sortedValues, target);
        long[] brutePair = WindowAnalytics.findPairBruteForce(sortedValues, target);
        System.out.printf("Two pointers: %s%n", formatPair(pair));
        System.out.printf("Перебор:     %s%n", formatPair(brutePair));
    }

    private static String formatPair(long[] pair) {
        if (pair == null) {
            return "пара не найдена";
        }
        return String.format("%d и %d", pair[0], pair[1]);
    }

    private static void demoContribution() {
        int month = MENU.readInt(
                String.format("Номер месяца (%d-%d): ", MIN_MONTH, MAX_MONTH));
        System.out.printf("Наивно: %d%n",
                ContributionCalculator.calculateContributionNaive(month));
        System.out.printf("С мемоизацией: %d%n",
                ContributionCalculator.calculateContributionMemo(month));
        System.out.printf("Накопленная сумма за %d месяцев: %d%n",
                month, ContributionCalculator.calculateTotalContribution(month));

        System.out.println("Замеры:");
        System.out.printf("%-10s %-20s %-20s%n", "Месяц", "Naive (ms)", "Memo (ms)");
        int[] benchmarks = {BENCHMARK_MONTH_SMALL, BENCHMARK_MONTH_MEDIUM,
                BENCHMARK_MONTH_LARGE};
        for (int value : benchmarks) {
            long naiveStart = System.nanoTime();
            ContributionCalculator.calculateContributionNaive(value);
            long naiveMilliseconds =
                    (System.nanoTime() - naiveStart) / NANOSECONDS_PER_MILLISECOND;

            long memoStart = System.nanoTime();
            ContributionCalculator.calculateContributionMemo(value);
            long memoMilliseconds =
                    (System.nanoTime() - memoStart) / NANOSECONDS_PER_MILLISECOND;

            System.out.printf("%-10d %-20d %-20d%n",
                    value, naiveMilliseconds, memoMilliseconds);
        }
    }

    private static void demoEndToEnd() {
        System.out.println("Сквозной сценарий:");
        BankService bank = new BankService();
        for (long number = 1001L; number <= 1020L; number++) {
            bank.register(new Account(number, INITIAL_BALANCE));
        }

        bank.transfer(1001L, 1002L, 5_000L);
        bank.transfer(1002L, 1003L, 3_000L);
        bank.transfer(1003L, 1004L, 2_000L);

        System.out.printf("Операций в журнале: %d%n", bank.getJournal().size());

        List<Operation> statement = bank.getJournal().toList();
        Comparator<Operation> byTimestamp =
                Comparator.comparingLong(Operation::getTimestamp);
        Sorter.mergeSort(statement, byTimestamp);
        System.out.printf("Выписка отсортирована, размер: %d%n", statement.size());

        long[] amounts = new long[statement.size()];
        for (int index = 0; index < statement.size(); index++) {
            amounts[index] = statement.get(index).getAmount();
        }
        long peak = WindowAnalytics.maxWindowSum(amounts, 2);
        System.out.printf("Пиковая сумма двух операций: %d%n", peak);

        bank.undoLast();
        System.out.printf("После отката операций: %d%n", bank.getJournal().size());
    }
}
