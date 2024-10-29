package com.davidlangat.datamining;


import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.classifiers.rules.PART;
import weka.classifiers.CostMatrix;
import weka.classifiers.meta.CostSensitiveClassifier;

import java.util.Random;

public class ClassificationTask2 {

    public static void main(String[] args) {
        try {
            // Load the dataset
            String filePath = "datasets/COVID19.arff";
            DataSource source = new DataSource(filePath);
            Instances data = source.getDataSet();
            
            // Create a Remove filter to remove the specified attributes
            String[] removeOptions = new String[]{"-R", "1-3,5,10,12-16,18"};
            Remove remove = new Remove();
            remove.setOptions(removeOptions);
            remove.setInputFormat(data);
            Instances filteredData = Filter.useFilter(data, remove);
            
            // Set the class index to the last attribute (infection_risk) in the filtered dataset
            filteredData.setClassIndex(filteredData.numAttributes() - 1);
            
            // Create classifiers
            J48 j48 = new J48();
            j48.setConfidenceFactor(0.25f);
            j48.setMinNumObj(2);
            
            PART part = new PART();
            part.setConfidenceFactor(0.25f);
            part.setMinNumObj(2);
            
            // Create cost matrix
            CostMatrix costMatrix = new CostMatrix(2);
            costMatrix.setCell(0, 0, 0.0);
            costMatrix.setCell(0, 1, 5.0);
            costMatrix.setCell(1, 0, 1.0);
            costMatrix.setCell(1, 1, 0.0);
            
            // Array of classifiers
            weka.classifiers.Classifier[] classifiers = {j48, part};
            String[] classifierNames = {"J48", "PART"};
            
            // Perform cost-sensitive classification for each classifier
            for (int i = 0; i < classifiers.length; i++) {
                CostSensitiveClassifier costSensitiveClassifier = new CostSensitiveClassifier();
                costSensitiveClassifier.setClassifier(classifiers[i]);
                costSensitiveClassifier.setCostMatrix(costMatrix);
                costSensitiveClassifier.setMinimizeExpectedCost(true);
                
                Evaluation eval = new Evaluation(filteredData, costMatrix);
                eval.crossValidateModel(costSensitiveClassifier, filteredData, 10, new Random(1));
                
                // Print results
                System.out.println("Results for Cost-Sensitive " + classifierNames[i] + ":");
                System.out.println("Classification Accuracy: " + String.format("%.4f%%", eval.pctCorrect()));
                System.out.println("Total Cost: " + eval.totalCost());
                System.out.println();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
