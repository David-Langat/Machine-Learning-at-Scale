package com.davidlangat.datamining;


import java.io.*;
import java.util.*;
import ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth.AlgoFPGrowth;
import ca.pfv.spmf.patterns.itemset_array_integers_with_count.Itemsets;
import ca.pfv.spmf.tools.dataset_converter.TransactionDatabaseConverter;

public class MaximumPatternMiner {

    public static void main(String[] args) throws IOException {
        // Specify input files
        String input_yes = "datasets/bank_yes.arff";
        String input_no = "datasets/bank_no.arff";

        // Specify converted input files
        String converted_yes = "output/bank_yes_converted.txt";
        String converted_no = "output/bank_no_converted.txt";

        // Specify minimum support (20% of transactions)
        double minsup = 0.2;

        // Convert ARFF to SPMF format
        TransactionDatabaseConverter converter = new TransactionDatabaseConverter();
        converter.convertARFFandReturnMap(input_yes, converted_yes, Integer.MAX_VALUE);
        converter.convertARFFandReturnMap(input_no, converted_no, Integer.MAX_VALUE);

        // Create objects of pattern mining algorithm
        AlgoFPGrowth algo_yes = new AlgoFPGrowth();
        AlgoFPGrowth algo_no = new AlgoFPGrowth();

        // Run algorithm for yes-class
        Itemsets patterns_yes = algo_yes.runAlgorithm(converted_yes, null, minsup);
        algo_yes.printStats();

        // Run algorithm for no-class
        Itemsets patterns_no = algo_no.runAlgorithm(converted_no, null, minsup);
        algo_no.printStats();

        // Get and print top 5 maximum patterns for each class
        System.out.println("Top 5 maximum patterns for YES class:");
        printTopMaximumPatterns(patterns_yes, converted_yes, 5);

        System.out.println("\nTop 5 maximum patterns for NO class:");
        printTopMaximumPatterns(patterns_no, converted_no, 5);
    }

    private static void printTopMaximumPatterns(Itemsets patterns, String convertedFile, int n) throws IOException {
        // Find the maximum size
        int maxSize = patterns.getLevels().size() - 1;
        
        // Get itemsets of maximum size
        List<ca.pfv.spmf.patterns.itemset_array_integers_with_count.Itemset> maxPatterns = 
            patterns.getLevels().get(maxSize);

        // Sort by support
        maxPatterns.sort((a, b) -> Double.compare(b.getAbsoluteSupport(), a.getAbsoluteSupport()));

        // Convert item IDs to names
        Map<Integer, String> itemNames = loadItemNames(convertedFile);

        // Print top n patterns
        for (int i = 0; i < Math.min(n, maxPatterns.size()); i++) {
            ca.pfv.spmf.patterns.itemset_array_integers_with_count.Itemset itemset = maxPatterns.get(i);
            System.out.print("Pattern: ");
            for (Integer item : itemset.getItems()) {
                System.out.print(itemNames.get(item) + " ");
            }
            System.out.println("#SUP: " + itemset.getAbsoluteSupport());
        }
    }

    private static Map<Integer, String> loadItemNames(String filename) throws IOException {
        Map<Integer, String> itemNames = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("@ITEM=")) {
                String[] parts = line.substring(6).split("=");
                itemNames.put(Integer.parseInt(parts[0]), parts[1]);
            }
        }
        reader.close();
        return itemNames;
    }
}