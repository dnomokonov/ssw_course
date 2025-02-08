package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    private static final FileUtility utility = new FileUtility();

    public static void main(String[] args) {
        Set<String> inputFiles = new HashSet<>();
        utility.parseArgs(args, inputFiles);

        List<Integer> integers = new ArrayList<>();
        List<Double> floats = new ArrayList<>();
        List<String> strings = new ArrayList<>();

        utility.filterFiles(inputFiles, integers, floats, strings);

        utility.writerResultOnFiles("integers.txt", integers);
        utility.writerResultOnFiles("floats.txt", floats);
        utility.writerResultOnFiles("strings.txt", strings);

        utility.showStat();
    }
}