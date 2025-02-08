package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    private static final FileUtility fileUtility = new FileUtility();

    public static void main(String[] args) {
        Set<String> inputFiles = new HashSet<>();
        fileUtility.parseArgs(args, inputFiles);

        List<Integer> integers = new ArrayList<>();
        List<Double> floats = new ArrayList<>();
        List<String> strings = new ArrayList<>();

        fileUtility.filterFiles(inputFiles, integers, floats, strings);

        fileUtility.writerResultOnFiles("integers.txt", integers);
        fileUtility.writerResultOnFiles("floats.txt", floats);
        fileUtility.writerResultOnFiles("strings.txt", strings);

        fileUtility.showStat();
    }
}