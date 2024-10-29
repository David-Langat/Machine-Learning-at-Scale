
package com.davidlangat.datamining;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.Map;

import ca.pfv.spmf.algorithms.frequentpatterns.apriori.AlgoApriori;
import ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth.AlgoFPGrowth;
import ca.pfv.spmf.tools.dataset_converter.TransactionDatabaseConverter;

public class BankPatternMiningComparison {

    public static void main(String[] args) throws IOException {
        // Define file paths
        String inputFile = Paths.get("datasets", "bank.arff").toString();
        String convertedFile = Paths.get("output", "bank_converted.txt").toString();
        String outputFile = Paths.get("output", "pattern_mining_results.txt").toString();
        String tempOutputFileApriori = Paths.get("output", "temp_patterns_apriori.txt").toString();
        String tempOutputFileFPGrowth = Paths.get("output", "temp_patterns_fpgrowth.txt").toString();

        System.out.println("Starting bank pattern mining comparison...");
        System.out.println("Input file: " + inputFile);

        // Convert ARFF file to transaction database format
        System.out.println("Converting ARFF to transaction database format...");
        convertARFFToTransactionDB(inputFile, convertedFile);
        System.out.println("Conversion complete. Converted file: " + convertedFile);

        // Define minimum support thresholds to test
        double[] minSupports = {0.6, 0.5, 0.4, 0.3, 0.2, 0.1};

        System.out.println("Beginning pattern mining process...");
        try (PrintWriter out = new PrintWriter(new FileWriter(outputFile))) {
            // Write header for results table
            out.printf("%-10s %-15s %-15s %-20s %-20s%n", 
                       "Minsup", "Apriori Time", "FP-Growth Time", "#Patterns (Apriori)", "#Patterns (FP-Growth)");
            out.printf("%-10s %-15s %-15s %-20s %-20s%n",
                       "", "(ms)", "(ms)", "", "");

            // Iterate through different minimum support thresholds
            for (double minSup : minSupports) {
                System.out.println("\nProcessing minimum support: " + minSup);
                
                // Run Apriori algorithm
                System.out.println("Running Apriori algorithm...");
                AlgoApriori apriori = new AlgoApriori();
                long startTimeApriori = System.currentTimeMillis();
                apriori.runAlgorithm(minSup, convertedFile, tempOutputFileApriori);
                long endTimeApriori = System.currentTimeMillis();
                long durationApriori = endTimeApriori - startTimeApriori;
                apriori.printStats();
                int patternCountApriori = countPatterns(tempOutputFileApriori);
                System.out.println("Apriori completed in " + durationApriori + " ms");
                System.out.println("Number of patterns found (Apriori): " + patternCountApriori);

                // Run FP-Growth algorithm
                System.out.println("Running FP-Growth algorithm...");
                AlgoFPGrowth fpGrowth = new AlgoFPGrowth();
                long startTimeFPGrowth = System.currentTimeMillis();
                fpGrowth.runAlgorithm(convertedFile, tempOutputFileFPGrowth, minSup);
                long endTimeFPGrowth = System.currentTimeMillis();
                long durationFPGrowth = endTimeFPGrowth - startTimeFPGrowth;
                fpGrowth.printStats();
                int patternCountFPGrowth = countPatterns(tempOutputFileFPGrowth);
                System.out.println("FP-Growth completed in " + durationFPGrowth + " ms");
                System.out.println("Number of patterns found (FP-Growth): " + patternCountFPGrowth);

                // Write results to output file
                out.printf("%-10.1f %-15d %-15d %-20d %-20d%n",
                           minSup, durationApriori, durationFPGrowth, patternCountApriori, patternCountFPGrowth);
            }
        }

        System.out.println("\nPattern mining process completed.");
        System.out.println("Results have been written to: " + outputFile);
    }

    /**
     * Converts an ARFF file to a transaction database format.
     * 
     * @param inputFile The path to the input ARFF file.
     * @param convertedFile The path where the converted file will be saved.
     * @throws IOException If an I/O error occurs.
     */
    private static void convertARFFToTransactionDB(String inputFile, String convertedFile) throws IOException {
        TransactionDatabaseConverter converter = new TransactionDatabaseConverter();
        Map<Integer, String> mapping = converter.convertARFFandReturnMap(inputFile, convertedFile, Integer.MAX_VALUE);
        System.out.println("Conversion completed. Number of items: " + mapping.size());
    }

    /**
     * Counts the number of patterns in a file.
     * 
     * @param fileName The path to the file containing patterns.
     * @return The number of patterns (lines) in the file.
     * @throws IOException If an I/O error occurs.
     */
    private static int countPatterns(String fileName) throws IOException {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.readLine() != null) count++;
        }
        return count;
    }
}