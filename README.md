# Large Scale Data Mining Project

This project is a comprehensive collection of data mining tasks implemented in Java, leveraging the Weka and SPMF libraries. It was developed to demonstrate the practical application of machine learning and pattern mining techniques on real-world datasets.

The project is organized into a series of modular, reusable Java classes, each addressing a specific data analysis challenge.

## Table of Contents

- [Key Features](#key-features)
- [Tasks Implemented](#tasks-implemented)
  - [Task 1: Association Mining](#task-1-association-mining)
  - [Task 2: Predictive Classification](#task-2-predictive-classification)
  - [Task 3: Text Classification](#task-3-text-classification)
- [Technologies & Libraries](#technologies--libraries)
- [Datasets](#datasets)
- [Getting Started](#getting-started)
- [How to Run](#how-to-run)
- [Configuration Files](#configuration-files)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)

## Key Features

This project implements a variety of data mining techniques, from data preprocessing to model evaluation:

*   **Frequent Pattern & Association Rule Mining:**
    *   A comparative performance analysis of the **Apriori** and **FP-Growth** algorithms.
    *   Extraction of frequent patterns and association rules based on custom **support** and **confidence** thresholds.
    *   Identification of **maximum frequent patterns** to generate a concise summary of the most significant itemsets.

*   **Predictive Modeling & Classification:**
    *   Implementation of a suite of classic and advanced classifiers, including **J48 (C4.5)**, **PART**, **Naive Bayes**, **OneR**, **IBk (k-NN)**, **SMO (SVM)**, and **Hoeffding Tree**.
    *   Application of **cost-sensitive learning** with a cost matrix to effectively handle imbalanced datasets and minimize classification errors for critical classes.
    *   Rigorous model evaluation using **10-fold cross-validation** and detailed performance metrics, including accuracy and total cost.

*   **Text Analytics and NLP:**
    *   An end-to-end text classification pipeline for categorizing news articles.
    *   Advanced text preprocessing using Weka's `StringToWordVector` filter, incorporating **TF-IDF transformations**, **stemming**, and **stopword removal** to build a robust feature set.

## Tasks Implemented

The project is divided into three main tasks, each corresponding to a set of Java classes in the `src` directory.

### Task 1: Association Mining

This task focuses on analyzing a bank marketing dataset to understand customer behavior and identify patterns related to term-deposit subscriptions.

*   **`BankPatternMiningComparison.java`**: Compares the runtime and efficiency of the Apriori and FP-Growth algorithms.
*   **`MaximumPatternMiner.java`**: Mines for the top 5 most frequent maximum patterns from the "yes" and "no" subscription classes.
*   **`BankAssociationRulesAnalysis.java`**: Generates the top 10 most frequent association rules for both "subscribed=yes" and "subscribed=no" outcomes.

### Task 2: Predictive Classification

This task involves building and evaluating classifiers to predict COVID-19 infection risk from a medical dataset.

*   **`ClassificationTask.java`**: Implements and evaluates four baseline classification algorithms (J48, PART, NaiveBayes, OneR).
*   **`ClassificationTask2.java`**: Performs cost-sensitive classification using J48 and PART to minimize the cost of misclassifying high-risk individuals.

### Task 3: Text Classification

This task focuses on classifying news documents into different categories using text classification techniques.

*   **`NewsClassification.java`**: Implements and evaluates four classification algorithms (IBk, SMO, J48, HoeffdingTree) for text classification, including the text preprocessing pipeline.

## Technologies & Libraries

*   **Java (JDK 1.8+)**
*   **Weka (3.8)**: A comprehensive suite of machine learning algorithms for data mining tasks.
*   **SPMF (2.4.0)**: An open-source data mining library for discovering patterns in data.

## Datasets

*   `bank.arff`, `bank_no.arff`, `bank_yes.arff`: A bank marketing dataset used to predict whether a client will subscribe to a term deposit.
*   `COVID19.arff`: A dataset related to COVID-19, used for classification tasks to predict infection risk.
*   `News.arff`: A collection of news articles for text classification.

## Getting Started

### Prerequisites

*   **Java JDK** (version 1.8 or above)
*   **Visual Studio Code** with the **Java Extension Pack** (recommended)

### Setup

1.  Clone the repository and open the project folder as the workspace in Visual Studio Code.
2.  Ensure the `spmf-1.jar` and `weka.jar` files are located in the `lib/` directory. The project is pre-configured to use them.
3.  Compile the project from the root directory:
    ```bash
    javac -d bin -cp "lib/*" src/com/davidlangat/datamining/*.java
    ```

## How to Run

1.  **In VS Code**:
    *   Navigate to the **Run and Debug** tab.
    *   Select a launch configuration from the dropdown (e.g., "Run BankPatternMiner") and press the **Start Debugging** (F5) button.

2.  **From the Command Line**:
    *   Use the following command, replacing `ClassName` with the desired class to execute:
    ```bash
    # On Windows
    java -cp "bin;lib/*" com.davidlangat.datamining.ClassName

    # On macOS/Linux
    java -cp "bin:lib/*" com.davidlangat.datamining.ClassName
    ```

## Configuration Files

*   **`.vscode/launch.json`**: Contains the launch configurations for running each Java class directly from VS Code.
*   **`.vscode/settings.json`**: Defines the project's source path, output path, and referenced libraries for the Java extension.

## Troubleshooting

*   **FileNotFoundException**: Ensure that the dataset paths in the Java source files are correct and point to the `datasets/` directory.
*   **Library Issues**: Verify that both `spmf-1.jar` and `weka.jar` are present in the `lib/` folder and that the classpath is set correctly.

## Contributing

Contributions are welcome! If you have ideas to extend or improve the project, please fork the repository and submit a pull request.

## License

This project is licensed under the MIT License. See the LICENSE file for more details.