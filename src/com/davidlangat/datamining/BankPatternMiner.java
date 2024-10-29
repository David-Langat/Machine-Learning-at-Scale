
package com.davidlangat.datamining;

import java.io.*;
import java.util.*;
import ca.pfv.spmf.algorithms.associationrules.agrawal94_association_rules.AlgoAgrawalFaster94;
import ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth.AlgoFPGrowth;
import ca.pfv.spmf.patterns.itemset_array_integers_with_count.Itemsets;
import ca.pfv.spmf.tools.dataset_converter.TransactionDatabaseConverter;

public class BankPatternMiner {
    // Map to store item ID to item name mappings
    private static Map<Integer, String> itemMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        // Define input and output file paths
        String inputFile = "datasets/bank.arff";
        String convertedFile = "output/bank_converted.txt";
        String outputFile = "output/bank_rules_all.txt";
        String outputFileYes = "output/bank_rules_yes.txt";
        String outputFileNo = "output/bank_rules_no.txt";

        // Convert ARFF to SPMF format and get item mappings
        TransactionDatabaseConverter converter = new TransactionDatabaseConverter();
        itemMap = converter.convertARFFandReturnMap(inputFile, convertedFile, Integer.MAX_VALUE);

        // Find the identifiers for "subscribed=yes" and "subscribed=no"
        int subscribedYesId = -1;
        int subscribedNoId = -1;
        for (Map.Entry<Integer, String> entry : itemMap.entrySet()) {
            if (entry.getValue().equals("subscribed=yes")) {
                subscribedYesId = entry.getKey();
            } else if (entry.getValue().equals("subscribed=no")) {
                subscribedNoId = entry.getKey();
            }
        }

        System.out.println("subscribed=yes ID: " + subscribedYesId);
        System.out.println("subscribed=no ID: " + subscribedNoId);

        // Mine frequent itemsets using FP-Growth algorithm
        AlgoFPGrowth fpGrowth = new AlgoFPGrowth();
        double minSupport = 0.01; // 1% minimum support
        Itemsets patterns = fpGrowth.runAlgorithm(convertedFile, null, minSupport);
        
        System.out.println("Number of frequent itemsets found: " + patterns.getItemsetsCount());

        // Generate association rules using Agrawal's algorithm
        double minConfidence = 0.1; // 10% minimum confidence
        AlgoAgrawalFaster94 ruleGen = new AlgoAgrawalFaster94();
        ruleGen.runAlgorithm(patterns, outputFile, fpGrowth.getDatabaseSize(), minConfidence);
        
        // Count and display the total number of rules generated
        int totalRules = countRules(outputFile);
        System.out.println("Total number of rules generated: " + totalRules);

        // Filter and sort rules for "subscribed=yes" and "subscribed=no"
        List<String> rulesYes = filterAndSortRules(outputFile, subscribedYesId, 10);
        List<String> rulesNo = filterAndSortRules(outputFile, subscribedNoId, 10);

        System.out.println("Number of rules with subscribed=yes: " + rulesYes.size());
        System.out.println("Number of rules with subscribed=no: " + rulesNo.size());

        // Write filtered rules to separate output files
        writeRulesToFile(rulesYes, outputFileYes);
        writeRulesToFile(rulesNo, outputFileNo);

        System.out.println("Rules generation complete. Check output files for results.");
    }

    // Method to count the number of rules in a file
    private static int countRules(String fileName) throws IOException {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.readLine() != null) count++;
        }
        return count;
    }

    // Method to filter and sort rules based on a specific consequent
    private static List<String> filterAndSortRules(String inputFile, int consequentId, int topK) throws IOException {
        List<String> filteredRules = new ArrayList<>();
        
        // Read rules from file and filter based on consequent ID
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("==> " + consequentId)) {
                    filteredRules.add(translateRule(line));
                }
            }
        }
        
        // Sort rules by confidence in descending order
        Collections.sort(filteredRules, new Comparator<String>() {
            @Override
            public int compare(String r1, String r2) {
                double conf1 = extractConfidence(r1);
                double conf2 = extractConfidence(r2);
                return Double.compare(conf2, conf1); // Descending order
            }
        });
        
        // Return top K rules
        return filteredRules.subList(0, Math.min(topK, filteredRules.size()));
    }

    // Method to translate item IDs in a rule to their corresponding names
    private static String translateRule(String rule) {
        for (Map.Entry<Integer, String> entry : itemMap.entrySet()) {
            rule = rule.replace(" " + entry.getKey() + " ", " " + entry.getValue() + " ");
        }
        return rule;
    }

    // Method to extract confidence value from a rule string
    private static double extractConfidence(String rule) {
        int start = rule.lastIndexOf("#CONF: ") + 7;
        return Double.parseDouble(rule.substring(start));
    }

    // Method to write a list of rules to a file
    private static void writeRulesToFile(List<String> rules, String outputFile) throws IOException {
        try (PrintWriter writer = new PrintWriter(outputFile)) {
            for (String rule : rules) {
                writer.println(rule);
            }
        }
    }
}