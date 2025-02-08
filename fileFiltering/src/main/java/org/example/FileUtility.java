package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

// Путь проекта: ... /ssw_course/fileFiltering

public class FileUtility {
    private String outputPath = "";
    private String prefix = "";
    private Boolean appendMode = false;
    private Boolean statistMode = null;

    private static final Stats stats = new Stats();

    public void parseArgs(String[] args, Set<String> inputFiles) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    if (i + 1 < args.length && isValidOption(args[i + 1])) {
                        this.outputPath = args[++i];
                    }
                    break;
                case "-p":
                    if (i + 1 < args.length && isValidOption(args[i + 1])) {
                        this.prefix = args[++i];
                    }
                    break;
                case "-a":
                    this.appendMode = true;
                    break;
                case "-s":
                    this.statistMode = false;
                    break;
                case "-f":
                    this.statistMode = true;
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

    private static boolean isValidOption(String option) {
        return !option.startsWith("-") && !option.trim().isEmpty();
    }

    public void filterFiles(Set<String> inputFiles, List<Integer> integers, List<Double> floats, List<String> strings) {
        for (String pathFile : inputFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(pathFile))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    filteringData(line.trim(), integers, floats, strings);
                }

                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
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

    public static <T> void showItemList(List<T> data) {
        for (int i = 0; i < data.size(); i++) {
            System.out.println(data.get(i));
        }
    }

    public <T> void writerResultOnFiles(String nameFile, List<T> data) {
        if (data.isEmpty()) return;

        String basePath = System.getProperty("user.dir");

        // Абсолютный или относительный путь?
        Path resultPath = (!this.outputPath.isEmpty())
                ? Paths.get(this.outputPath, this.prefix + nameFile)
                : Paths.get(basePath, this.prefix + nameFile);

//        System.out.println(resultPath.toString());

        try {
            Files.createDirectories(resultPath.getParent());
        } catch (IOException e) {
            System.out.println("Error when creating a folder: " + e.getMessage());
            return;
        }

        try (FileWriter writer = new FileWriter(resultPath.toString(), this.appendMode)) {
            for (T item : data) {
                writer.write(item.toString() + System.lineSeparator());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void showStat() {
        if (this.statistMode != null) {
            if (this.statistMode) {
                stats.printFullStats();
            } else {
                stats.printShortStats();
            }
        }
    }
}
