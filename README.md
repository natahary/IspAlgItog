# IspAlgItog — ShieldBank

Итоговый проект: антифрод-сервис банка на Java.

## Запуск

Сборка (macOS / Linux):

    javac -d out $(find src -name "*.java")

Запуск:

    java -cp out com.shieldbank.Main

Сборка (Windows PowerShell):

    Get-ChildItem -Recurse -Filter *.java -Path src | ForEach-Object { $_.FullName } > sources.txt
    javac -d out @sources.txt

Запуск:

    java -cp out com.shieldbank.Main

## Меню приложения

    1. BR-1: журнал операций с откатом
    2. BR-2: реестр счетов
    3. BR-3: сортировки и бинарный поиск
    4. BR-4: граф переводов
    5. BR-5: банкомат
    6. BR-6: аналитика потока
    7. BR-7: накопительная программа
    8. Сквозной сценарий
    0. Выход

## Таблица: тема — класс — сложность

| Тема                          | Класс                              | Ключевой метод           | Сложность        |
|-------------------------------|------------------------------------|--------------------------|------------------|
| Связный список                | structures.SinglyLinkedList        | addFirst, removeFirst    | O(1)             |
| Стек                          | structures.ActionStack             | push, pop                | O(1)             |
| Бинарное дерево поиска        | structures.BinarySearchTree        | insert, find             | O(log n) средн.  |
| Обход in-order                | structures.BinarySearchTree        | collectInOrder           | O(n)             |
| Сортировка пузырьком          | algorithms.Sorter                  | bubbleSort               | O(n^2) / O(n)    |
| Сортировка слиянием           | algorithms.Sorter                  | mergeSort                | O(n log n)       |
| Бинарный поиск                | algorithms.Searcher                | lowerBound, upperBound   | O(log n)         |
| Граф                          | structures.Graph                   | addEdge, getNeighbors    | O(1)             |
| BFS                           | algorithms.GraphAlgorithms         | breadthFirstSearch       | O(V + E)         |
| DFS, поиск цикла              | algorithms.GraphAlgorithms         | findCycle                | O(V + E)         |
| Компоненты связности          | algorithms.GraphAlgorithms         | countComponents          | O(V + E)         |
| Дейкстра                      | algorithms.GraphAlgorithms         | findShortestPaths        | O(E log V)       |
| Жадный алгоритм               | algorithms.ChangeMaker             | makeChangeGreedy         | O(n log n)       |
| Динамическое программирование | algorithms.ChangeMaker             | makeChangeOptimal        | O(amount * n)    |
| Скользящее окно               | algorithms.WindowAnalytics         | maxWindowSum             | O(n)             |
| Два указателя                 | algorithms.WindowAnalytics         | findPair                 | O(n)             |
| Мемоизация                    | algorithms.ContributionCalculator  | calculateContributionMemo| O(n)             |
| Наивная рекурсия              | algorithms.ContributionCalculator  | calculateContributionNaive| O(2^n)          |

## Соответствие BR и модулей

BR-1: ActionStack на собственном связном списке, откат в LIFO, BankService выполняет реальные операции и откатывает их.

BR-2: BinarySearchTree поддерживает insert, find, in-order вывод. Дубликаты отклоняются.

BR-3: Sorter (bubble, merge) и Searcher (lowerBound, upperBound, findBetween). Замеры на объёмах от 20 до 100000.

BR-4: Graph на списке смежности. GraphAlgorithms: BFS, findCycle (возвращает цепочку), countComponents, Дейкстра (возвращает путь).

BR-5: ChangeMaker: жадный и ДП. Расхождение на {1, 3, 4} сумма 6.

BR-6: WindowAnalytics: maxWindowSum, findWindowsAboveLimit, findPair.

BR-7: ContributionCalculator: наивная рекурсия и мемоизация.

Сквозной сценарий: BankService → журнал → выписка → аналитика.

## Замеры производительности

Заполняются после запуска приложения. См. раздел Скриншоты.

## Структура проекта

    IspAlgItog/
    ├── README.md
    ├── .gitignore
    └── src/main/java/com/shieldbank/
        ├── Main.java
        ├── model/
        │   ├── Account.java
        │   ├── Operation.java
        │   └── Transfer.java
        ├── structures/
        │   ├── SinglyLinkedList.java
        │   ├── ActionStack.java
        │   ├── BinarySearchTree.java
        │   └── Graph.java
        ├── algorithms/
        │   ├── Sorter.java
        │   ├── Searcher.java
        │   ├── GraphAlgorithms.java
        │   ├── ChangeMaker.java
        │   ├── WindowAnalytics.java
        │   └── ContributionCalculator.java
        ├── service/
        │   ├── BankService.java
        │   ├── DataGenerator.java
        │   └── PathResult.java
        └── ui/
            └── ConsoleMenu.java

## Запрещённые структуры

Запрещено: LinkedList, Stack, Deque, TreeMap, TreeSet, Arrays.sort, Collections.sort, List.sort, Stream.sorted, Arrays.binarySearch, Collections.binarySearch, графовые библиотеки.

Разрешено: ArrayList, HashMap, HashSet, ArrayDeque, PriorityQueue, Random, Scanner.

## Автор

Наталья Дегтярёва
