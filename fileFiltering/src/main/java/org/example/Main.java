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
    private static Boolean appendMode = false;
    private static Boolean statistMode = null;

    private static final Stats stats = new Stats();

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
//                System.out.println(e);
                throw new RuntimeException(e);
            }
        }

        writerResultOnFiles("integers.txt", integers);
        writerResultOnFiles("floats.txt", floats);
        writerResultOnFiles("strings.txt", strings);

        if (statistMode != null) {
            if (statistMode) {
                stats.printFullStats();
            } else {
                stats.printShortStats();
            }
        }
    }

    private static void parseArgs(String[] args, Set<String> inputFiles) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    if (i + 1 < args.length && isValidOption(args[i + 1])) {
                        Main.outputPath = args[++i];
                    }
                    break;
                case "-p":
                    if (i + 1 < args.length && isValidOption(args[i + 1])) {
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
                    if (args[i].startsWith("-")) {
                        System.out.println("Error: Unknown option" + args[i]);
                        return;
                    }

                    inputFiles.add(args[i]);
            }
        }
    }

    private  static boolean isValidOption(String option) {
        return !option.startsWith("-") && !option.trim().isEmpty();
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
                    stats.UpdateStatsInteger(Integer.parseInt(cleanedWord));
                } else if (cleanedWord.matches("-?\\d+\\.\\d+")) {
                    floats.add(Double.parseDouble(cleanedWord));
                    stats.UpdateStatsFloat(Double.parseDouble(cleanedWord));
                } else {

//                  Работает некорректно!
                    if (word.contains(".")) {
                        word = word.replace('.', ' ');
                    } else if (word.contains(",")) {
                        word = word.replace(',', ' ');
                    }

                    strings.add(word);
                    stats.UpdateStatsString(word);
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

//        System.out.println(resultPath.toString());

        try {
            Files.createDirectories(resultPath.getParent());
        } catch (IOException e) {
            System.out.println("Error when creating a folder: " + e.getMessage());
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