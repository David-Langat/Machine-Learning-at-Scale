package com.davidlangat.datamining;


import weka.core.*;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.*;
import weka.classifiers.trees.*;
import weka.classifiers.lazy.*;
import weka.classifiers.functions.*;
import weka.filters.*;
import weka.filters.unsupervised.attribute.*;
import java.util.Random;

public class NewsClassification {

    public static void main(String[] args) {
        try {
            // Load data
            DataSource source = new DataSource("datasets/News.arff");
            Instances data = source.getDataSet();
            
            // Set class attribute
            data.setClassIndex(data.numAttributes() - 1);
            
            // Configure StringToWordVector filter
            StringToWordVector filter = new StringToWordVector();
            filter.setIDFTransform(true);
            filter.setTFTransform(true);
            filter.setAttributeIndices("first-last");
            filter.setDoNotOperateOnPerClassBasis(true);
            filter.setLowerCaseTokens(false);
            filter.setMinTermFreq(1);
            filter.setNormalizeDocLength(new SelectedTag(StringToWordVector.FILTER_NONE, StringToWordVector.TAGS_FILTER));
            filter.setOutputWordCounts(false);
            filter.setPeriodicPruning(-1.0);
            filter.setStemmer(new weka.core.stemmers.LovinsStemmer());
            filter.setStopwordsHandler(new weka.core.stopwords.Rainbow());
            filter.setTokenizer(new weka.core.tokenizers.WordTokenizer());
            filter.setWordsToKeep(100);
            
            // Apply filter
            filter.setInputFormat(data);
            Instances filteredData = Filter.useFilter(data, filter);
            
            // Prepare classifiers
            Classifier[] classifiers = {
                new IBk(),
                new SMO(),
                new J48(),
                new HoeffdingTree()
            };
            
            String[] classifierNames = {"IBk", "SMO", "J48", "HoeffdingTree"};
            
            // Perform classification and measure time for each classifier
            for (int i = 0; i < classifiers.length; i++) {
                long startTime = System.nanoTime();
                
                Evaluation eval = new Evaluation(filteredData);
                eval.crossValidateModel(classifiers[i], filteredData, 10, new Random(1));
                
                long endTime = System.nanoTime();
                double timeInSeconds = (endTime - startTime) / 1e9;
                
                System.out.println("Classifier: " + classifierNames[i]);
                System.out.println("Correctly Classified Instances: " + eval.correct());
                System.out.println("Accuracy: " + eval.pctCorrect() + "%");
                System.out.println("Time taken: " + timeInSeconds + " seconds");
                System.out.println();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}