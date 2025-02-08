package org.example;

public class Stats {
    private int countIntegers;
    private int countFloats;
    private int countString;

    private int minIntegers = Integer.MAX_VALUE;
    private int maxIntegers = Integer.MIN_VALUE;
    private long sumIntegers;
    private double averageIntegers;

    private double minFloats = Double.MAX_VALUE;
    private double maxFloats = Double.MIN_VALUE;
    private double sumFloats;
    private double averageFloats;

    private int minStringLength = Integer.MAX_VALUE;
    private int maxStringLength = Integer.MIN_VALUE;

    public void UpdateStatsInteger(int value) {
        this.countIntegers++;
        this.minIntegers = Math.min(this.minIntegers, value);
        this.maxIntegers = Math.max(this.maxIntegers, value);
        this.sumIntegers += value;
        this.averageIntegers = (double) sumIntegers / countIntegers;
    }

    public void UpdateStatsFloat(double value) {
        this.countFloats++;
        this.minFloats = Math.min(this.minFloats, value);
        this.maxFloats = Math.max(this.maxFloats, value);
        this.sumFloats += value;
        this.averageFloats = sumFloats / countFloats;
    }

    public void UpdateStatsString(String value) {
        this.countString++;
        this.minStringLength = Math.min(this.minStringLength, value.length());
        this.maxStringLength = Math.max(this.maxStringLength, value.length());
    }

    public void printShortStats(){
        System.out.println("Short statistics");

        if (this.countIntegers > 0) {
            System.out.println("Count Integers: " + this.countIntegers);
        }

        if (this.countFloats > 0) {
            System.out.println("Count Floats: " + this.countFloats);
        }

        if (this.countString > 0) {
            System.out.println("Count Strings: " + this.countString);
        }

        if (this.countIntegers == 0 && this.countFloats == 0 && this.countString == 0) {
            System.out.println("All files are empty!");
        }

    }

    public void printFullStats() {
        System.out.println("Full statistics");

        if (this.countIntegers > 0) {
            System.out.println("Count Integers: " + this.countIntegers);
            System.out.println("Min: " + this.minIntegers);
            System.out.println("Max: " + this.maxIntegers);
            System.out.println("Avg: " + this.averageIntegers);

        }

        if (this.countFloats > 0) {
            System.out.println("Count Floats: " + this.countFloats);
            System.out.println("Min: " + this.minFloats);
            System.out.println("Max: " + this.maxFloats);
            System.out.println("Avg: " + this.averageFloats);
        }

        if (this.countString > 0) {
            System.out.println("Count Strings: " + this.countString);
            System.out.println("Min length: " + this.minStringLength);
            System.out.println("Max length: " + this.maxStringLength);
        }

        if (this.countIntegers == 0 && this.countFloats == 0 && this.countString == 0) {
            System.out.println("All files are empty!");
        }

    }

}
