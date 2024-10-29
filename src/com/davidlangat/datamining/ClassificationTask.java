package com.davidlangat.datamining;


import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.classifiers.rules.PART;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.rules.OneR;

import java.util.Random;

public class ClassificationTask {

    public static void main(String[] args) {
        try {
            // Load the dataset
            String filePath = "datasets/COVID19.arff";
            DataSource source = new DataSource(filePath);
            Instances data = source.getDataSet();
            
            // Set the class index to the last attribute
            if (data.classIndex() == -1) {
                data.setClassIndex(data.numAttributes() - 1);
            }
            
            // Create classifiers with the same parameters as in the GUI
            J48 j48 = new J48();
            j48.setConfidenceFactor(0.25f);
            j48.setMinNumObj(2);
            
            PART part = new PART();
            part.setConfidenceFactor(0.25f);
            part.setMinNumObj(2);
            
            NaiveBayes naiveBayes = new NaiveBayes();
            
            OneR oneR = new OneR();
            oneR.setMinBucketSize(2);
            
            // Array of classifiers
            weka.classifiers.Classifier[] classifiers = {j48, part, naiveBayes, oneR};
            String[] classifierNames = {"J48", "PART", "NaiveBayes", "OneR"};
            
            // Perform 10-fold cross-validation for each classifier
            for (int i = 0; i < classifiers.length; i++) {
                Evaluation eval = new Evaluation(data);
                eval.crossValidateModel(classifiers[i], data, 10, new Random(1));
                
                // Print results
                System.out.println("Results for " + classifierNames[i] + ":");
                System.out.println("Correctly Classified Instances: " + eval.correct());
                System.out.println("Accuracy: " + String.format("%.4f%%", eval.pctCorrect()));
                System.out.println();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}