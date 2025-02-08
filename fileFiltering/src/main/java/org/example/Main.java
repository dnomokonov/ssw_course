package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Путь проекта: ... /ssw_course/fileFiltering

public class Main {
    private static String outputPath = "";
    private static String prefix = "";
    private static boolean appendMode = false;
    private static boolean statistMode = false;

    public static void main(String[] args) {
        List<String> inputFiles = new ArrayList<>();
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

    private static void parseArgs(String[] args, List<String> inputFiles) {
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

}