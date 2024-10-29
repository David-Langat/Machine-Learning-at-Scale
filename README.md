
---

# DataMining Project

This project is a collection of data mining tasks implemented in Java, leveraging machine learning and pattern mining techniques. It includes a series of applications, each focused on a specific data mining task, including association rule mining, classification, and frequent pattern analysis.

## Project Structure

```
DataMining/
├── .vscode/                 # VS Code configurations (launch and settings)
├── bin/                     # Compiled Java class files
├── datasets/                # Datasets used for data mining tasks
│   ├── bank.arff
│   ├── bank_no.arff
│   ├── bank_yes.arff
│   ├── COVID19.arff
│   └── News.arff
├── lib/                     # External libraries
│   ├── spmf-1.jar           # SPMF library for frequent pattern mining
│   └── weka.jar             # Weka library for machine learning tasks
├── output/                  # Output files (e.g., pattern mining results)
└── src/                     # Java source files
    └── com/davidlangat/datamining/
        ├── BankAssociationRulesAnalysis.java
        ├── BankPatternMiner.java
        ├── BankPatternMiningComparison.java
        ├── ClassificationTask.java
        ├── ClassificationTask2.java
        ├── MaximumPatternMiner.java
        └── NewsClassification.java
```

## Getting Started

### Prerequisites

- **Java JDK** (version 1.8 or above)
- **Visual Studio Code** with the **Java Extension Pack** (recommended for running the project in VS Code)

### Libraries

This project uses the following external libraries:
- **SPMF** for frequent pattern mining
- **Weka** for machine learning algorithms

Ensure that `spmf-1.jar` and `weka.jar` are placed in the `lib` directory as per the `settings.json` configuration.

### Setup

1. Clone the repository and open the `DataMining` folder as the workspace in Visual Studio Code.
2. Open the terminal and navigate to the project’s root directory.
3. Run the following command to compile the project:
   ```bash
   javac -d bin -cp "lib/*" src/com/davidlangat/datamining/*.java
   ```
4. You can then run each main class individually using the VS Code `launch.json` configurations.

## Usage

Each Java file in the `src/com/davidlangat/datamining` directory corresponds to a specific data mining task. You can run these files individually using the configurations in `.vscode/launch.json`.

## Data Mining Tasks

### 1. BankAssociationRulesAnalysis.java

**Description**: This class performs association rule analysis on a banking dataset using the SPMF library. It aims to extract valuable insights regarding banking customer behaviors.

- **Input**: `datasets/bank.arff`
- **Output**: Association rules based on defined confidence and support thresholds.

### 2. BankPatternMiner.java

**Description**: BankPatternMiner finds frequent patterns within the banking dataset, allowing analysts to understand common transactions and trends.

- **Input**: `datasets/bank.arff`
- **Library**: Uses SPMF library for pattern mining.

### 3. BankPatternMiningComparison.java

**Description**: This class compares the performance of Apriori and FP-Growth algorithms on different support thresholds, enabling an evaluation of efficiency between the two methods.

- **Input**: `datasets/bank.arff`
- **Output**: Outputs a comparison of runtime and pattern count for each algorithm.

### 4. ClassificationTask.java

**Description**: ClassificationTask performs multi-class classification on a dataset, using algorithms like J48 (decision trees), PART, Naive Bayes, and OneR.

- **Input**: `datasets/COVID19.arff`
- **Library**: Weka
- **Output**: Accuracy and evaluation metrics for each classifier.

### 5. ClassificationTask2.java

**Description**: A variant of ClassificationTask that includes cost-sensitive classification. It incorporates a cost matrix to handle imbalanced data and improve model performance on critical classes.

- **Input**: `datasets/COVID19.arff`
- **Library**: Weka
- **Output**: Cost-sensitive evaluation metrics.

### 6. MaximumPatternMiner.java

**Description**: This class mines maximum patterns in the banking dataset, which are the largest frequent itemsets, providing a summary of common transaction sets.

- **Input**: `datasets/bank_yes.arff` and `datasets/bank_no.arff`
- **Output**: Top patterns for both "yes" and "no" classes.

### 7. NewsClassification.java

**Description**: NewsClassification is a text classification task that uses various classifiers like IBk (k-nearest neighbors), SMO (SVM), J48, and Hoeffding Tree to categorize news articles.

- **Input**: `datasets/News.arff`
- **Library**: Weka
- **Output**: Classification accuracy and evaluation results for each classifier.

## Configuration Files

### launch.json

The `launch.json` file in `.vscode` is set up to allow easy execution of each main class. Each configuration points to one of the data mining tasks in the project.

```json
{
  "version": "0.2.0",
  "configurations": [
    // Configurations for each main class
  ]
}
```

### settings.json

The `settings.json` file defines the source path (`src`), output path (`bin`), and external libraries (`lib/weka.jar` and `lib/spmf-1.jar`), which are required for compilation and execution.

```json
{
  "java.project.sourcePaths": ["src"],
  "java.project.outputPath": "bin",
  "java.project.referencedLibraries": [
    "lib/weka.jar",
    "lib/spmf-1.jar"
  ]
}
```

## Running the Project

1. **In VS Code**:
   - Open the **Run and Debug** tab.
   - Select a configuration (e.g., "Run BankPatternMiner") and press **Start Debugging**.

2. **From the Command Line**:
   - Use `java -cp "bin;lib/*" com.davidlangat.datamining.ClassName` to run any specific class, replacing `ClassName` with the desired Java file.

## Troubleshooting

- **FileNotFoundException**: Ensure that dataset paths in each Java file match the relative paths (e.g., `datasets/bank.arff`).
- **Library Issues**: Verify that both `spmf-1.jar` and `weka.jar` are in the `lib` folder.

## Contributing

Contributions are welcome! If you have ideas to extend or improve the project, please fork the repository and submit a pull request.

## License

This project is licensed under the MIT License. See the LICENSE file for more details.

---
