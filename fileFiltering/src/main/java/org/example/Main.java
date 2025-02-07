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
        List<Float> floats = new ArrayList<>();

        for (String pathFile : inputFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(pathFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    line = reader.readLine();
                }

                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

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


}