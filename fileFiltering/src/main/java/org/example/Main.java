package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Путь проекта: ... /ssw_course/fileFiltering

public class Main {
    private static String outputPath = "";
    private static String prefix = "";
    private static boolean appendMode = false;
    private static boolean statistMode = false;

    public static void main(String[] args) {
        Set<String> inputFiles = new HashSet<>();
        parseArgs(args, inputFiles);

        List<Integer> integers = new ArrayList<>();
        List<String> strings = new ArrayList<>();
        List<Double> floats = new ArrayList<>();

        for (String pathFile : inputFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(pathFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
//                    System.out.println(line);
//                    line = reader.readLine();
                    filteringData(line.trim(), integers, floats, strings);
                }

                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        writerResultOnFiles("integers.txt", integers);
        writerResultOnFiles("floats.txt", floats);
        writerResultOnFiles("strings.txt", strings);

// Вывод отфильтрованных данных
//        System.out.println("integers list:");
//        showItemList(integers);
//
//        System.out.println("string list:");
//        showItemList(strings);
//
//        System.out.println("floats list:");
//        showItemList(floats);
    }

    private static void parseArgs(String[] args, Set<String> inputFiles) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    if (i + 1 < args.length) {
                        Main.outputPath = args[++i];
                    }
                    break;
                case "-p":
                    if (i + 1 < args.length) {
                        Main.prefix = args[++i];
                    }
                    break;
                case "-a":
                    Main.appendMode = true;
                    break;
                case "-s":
                    Main.statistMode = false;
                    break;
                case "-f":
                    Main.statistMode = true;
                    break;
                default:
                    inputFiles.add(args[i]);
            }
        }
    }

    private static void filteringData(String line, List<Integer> integers, List<Double> floats, List<String> strings) {
        if (line.isEmpty()) return;

        String[] words = line.split(" "); // либо regex: [\\s,]+

        for (String word : words) {
            if (word.isEmpty()) continue;

            String cleanedWord = word.replace(",", "."); // Если цисло не целочисленное

            try {
                if (cleanedWord.matches("-?\\d+")) {
                    integers.add(Integer.parseInt(cleanedWord));
                } else if (cleanedWord.matches("-?\\d+\\.\\d+")) {
                    floats.add(Double.parseDouble(cleanedWord));
                } else {

                    if (word.contains(".")) {
                        word = word.replace('.', ' ');
                    } else if (word.contains(",")) {
                        word = word.replace(',', ' ');
                    }

                    strings.add(word);
                }
            } catch (NumberFormatException e) {
                strings.add(word);
            }
        }
    }

    private static <T> void showItemList(List<T> data) {
        for (int i = 0; i < data.size(); i++) {
            System.out.println(data.get(i));
        }
    }

    private static <T> void writerResultOnFiles(String nameFile, List<T> data) {
        if (data.isEmpty()) return;

        String basePath = System.getProperty("user.dir");

        // Абсолютный или относительный путь?
        Path resultPath = (!Main.outputPath.isEmpty())
                ? Paths.get(Main.outputPath, Main.prefix + nameFile)
                : Paths.get(basePath, Main.prefix + nameFile);

        System.out.println(resultPath.toString());

        try {
            Files.createDirectories(resultPath.getParent());
        } catch (IOException e) {
            System.out.println("Ошибка при создании каталога: " + e.getMessage());
            return;
        }

        try (FileWriter writer = new FileWriter(resultPath.toString(), Main.appendMode)) {
            for (T item : data) {
                writer.write(item.toString() + System.lineSeparator());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}