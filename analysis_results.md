# Data Mining Project: Detailed Analysis Results

This document presents the key findings and performance metrics from the data mining tasks conducted within this project. The analyses cover association rule mining, predictive classification, and text classification, utilizing various algorithms and datasets.

## 1. Association Mining Analysis

### 1.1. Frequent Pattern Mining Algorithm Comparison

A comparative analysis was performed on the Apriori and FP-Growth algorithms for frequent pattern mining using a bank marketing dataset. The algorithms were evaluated based on their execution times and the number of patterns generated across varying minimum support thresholds.

| Minimum Support | Apriori Time (ms) | FP-Growth Time (ms) | #Patterns (Apriori) | #Patterns (FP-Growth) |
|-----------------|-------------------|---------------------|---------------------|-----------------------|
| 0.6             | 78                | 75                  | 18                  | 18                    |
| 0.5             | 55                | 57                  | 37                  | 37                    |
| 0.4             | 59                | 44                  | 81                  | 81                    |
| 0.3             | 92                | 38                  | 154                 | 154                   |
| 0.2             | 270               | 40                  | 401                 | 401                   |
| 0.1             | 969               | 65                  | 1482                | 1482                  |

**Observations:**
As the minimum support threshold decreases, there is an exponential increase in the number of patterns discovered. FP-Growth consistently demonstrated superior efficiency, especially at lower support thresholds, highlighting its scalability for larger pattern volumes.

### 1.2. Top 5 Frequent Size-3 Patterns Analysis

A minimum support of 0.2 was chosen to balance pattern generation and processing efficiency. FP-Growth was selected for this analysis due to its superior performance.

**Top 5 Frequent Size-3 Patterns for Yes-class (Subscribers):**
1.  `{default_credit=no, housing=no, loan=no}` with support 3120
2.  `{default_credit=no, loan=no, past_marketing=unknown}` with support 2988
3.  `{default_credit=no, loan=no, call_duration=100-500s}` with support 2712
4.  `{age=21-30s, default_credit=no, loan=no}` with support 2519
5.  `{marital=married, default_credit=no, loan=no}` with support 2471

**Top 5 Frequent Size-3 Patterns for No-class (Non-subscribers):**
1.  `{default_credit=no, loan=no, past_marketing=unknown}` with support 27371
2.  `{default_credit=no, call_duration=100-500s, past_marketing=unknown}` with support 21352
3.  `{default_credit=no, loan=no, call_duration=100-500s}` with support 21217
4.  `{default_credit=no, past_marketing=unknown, marital=married}` with support 20357
5.  `{default_credit=no, loan=no, marital=married}` with support 19799

**Insights:**
Both subscriber and non-subscriber groups share common characteristics like no credit defaults and no loans. However, call duration (100-500 seconds) is more prevalent among non-subscribers, while younger customers (21-30s) are more common among subscribers.

### 1.3. Top 5 Most Frequent Maximum Patterns Analysis

This analysis identifies the most general frequent itemsets for both subscriber and non-subscriber classes.

**Yes-class Patterns:**
*   Age: 21-30s
*   Job: Admin
*   Marital status: Married
*   Education: Secondary
*   Default credit: No

**No-class Patterns:**
*   Age: 20 or below
*   Job: Student
*   Marital status: Single
*   Education: Primary
*   Default credit: No

**Insights:**
Subscribers are generally older, more financially stable, married, and more educated, while non-subscribers are younger, less financially independent, single, and less educated. These distinctions provide valuable insights for targeted marketing strategies.

### 1.4. Top 10 Association Rules Analysis

Association rules were generated with "subscribed=yes" and "subscribed=no" as consequents, using a minimum confidence of 0.1.

**Top 10 Association Rules for Yes-Class:**
1.  `{Housing=No, Call Duration=100-500s, Past Marketing=Success} ==> {Subscribed=Yes}` (Support: 582, Confidence: 0.715)
    *   *Insight:* Customers without housing loans, with medium call durations, and successful past marketing are more likely to subscribe.
2.  `{Loan=No, Call Duration=100-500s, Past Marketing=Success} ==> {Subscribed=Yes}` (Support: 563, Confidence: 0.714)
    *   *Insight:* Customers without personal loans, medium call durations, and successful past campaigns are also likely to subscribe.
3.  `{Housing=No, Loan=No, Call Duration=100-500s, Past Marketing=Success} ==> {Subscribed=Yes}` (Support: 563, Confidence: 0.714)
    *   *Insight:* Customers with neither housing loans nor personal loans are likely to subscribe if they had a successful past marketing campaign.
4.  `{Call Duration=100-500s, Past Marketing=Success} ==> {Subscribed=Yes}` (Support: 582, Confidence: 0.714)
    *   *Insight:* Medium call durations and successful past marketing predict a higher likelihood of subscription.
5.  `{Loan=No, Past Marketing=Success} ==> {Subscribed=Yes}` (Support: 705, Confidence: 0.704)
    *   *Insight:* Customers with no loans and past marketing success are more likely to subscribe.
6.  `{Housing=No, Loan=No, Past Marketing=Success} ==> {Subscribed=Yes}` (Support: 705, Confidence: 0.704)
    *   *Insight:* Customers without housing loans and personal loans are more likely to subscribe after a successful marketing campaign.
7.  `{Housing=No, Past Marketing=Success} ==> {Subscribed=Yes}` (Support: 729, Confidence: 0.702)
    *   *Insight:* Customers without housing loans are highly likely to subscribe if past marketing efforts succeeded.
8.  `{Past Marketing=Success} ==> {Subscribed=Yes}` (Support: 729, Confidence: 0.701)
    *   *Insight:* Simply having successful past marketing results in a higher chance of subscription.
9.  `{Past Marketing=Success, Balance=Below 1k} ==> {Subscribed=Yes}` (Support: 463, Confidence: 0.682)
    *   *Insight:* Customers with lower balances who were influenced by successful past marketing campaigns are likely to subscribe.
10. `{Loan=No, Past Marketing=Success, Balance=Below 1k} ==> {Subscribed=Yes}` (Support: 463, Confidence: 0.682)
    *   *Insight:* Customers with no loans, lower balances, and past marketing success are likely to subscribe.

**Top 10 Association Rules for No-Class:**
1.  `{Call Duration=100s or below, Job=Blue-collar} ==> {Subscribed=No}` (Support: 950, Confidence: 1.0)
    *   *Insight:* Blue-collar workers with short call durations are certain not to subscribe.
2.  `{Call Duration=100s or below, Age=50s} ==> {Subscribed=No}` (Support: 502, Confidence: 1.0)
    *   *Insight:* Older individuals (50s) with short call durations are very unlikely to subscribe.
3.  `{Marital Status=Married, Job=Services} ==> {Subscribed=No}` (Support: 564, Confidence: 1.0)
    *   *Insight:* Married individuals working in services are guaranteed not to subscribe.
4.  `{Housing=Yes, Balance=Negative} ==> {Subscribed=No}` (Support: 623, Confidence: 1.0)
    *   *Insight:* Customers with housing loans and negative balances have no chance of subscribing.
5.  `{Default Credit=No, Call Duration=100s or below, Job=Blue-collar} ==> {Subscribed=No}` (Support: 938, Confidence: 1.0)
    *   *Insight:* Blue-collar workers without default credit but short call durations are highly unlikely to subscribe.
6.  `{Default Credit=No, Call Duration=100s or below, Age=50s} ==> {Subscribed=No}` (Support: 490, Confidence: 1.0)
    *   *Insight:* Older individuals without default credit and with short call durations are unlikely to subscribe.
7.  `{Loan=No, Call Duration=100s or below, Job=Blue-collar} ==> {Subscribed=No}` (Support: 811, Confidence: 1.0)
    *   *Insight:* Blue-collar workers without loans and short call durations are guaranteed not to subscribe.
8.  `{Call Duration=100s or below, Past Marketing=Unknown, Job=Blue-collar} ==> {Subscribed=No}` (Support: 820, Confidence: 1.0)
    *   *Insight:* Customers with unknown past marketing results and short call durations who work blue-collar jobs will not subscribe.
9.  `{Call Duration=100s or below, Marital Status=Married, Housing=Yes} ==> {Subscribed=No}` (Support: 734, Confidence: 1.0)
    *   *Insight:* Married customers with housing loans and short call durations are certain not to subscribe.
10. `{Loan=No, Call Duration=100s or below, Past Marketing=Unknown} ==> {Subscribed=No}` (Support: 811, Confidence: 1.0)
    *   *Insight:* Customers without loans and unknown past marketing results are highly unlikely to subscribe.

## 2. Predictive Classification Analysis

This section details the classification analysis performed on a COVID-19 infection risk dataset.

### 2.1. Algorithm Performance Comparison

Six classification algorithms were evaluated using 10-fold cross-validation.

| Algorithm   | Accuracy (%) | Kappa  | Precision (High) | Recall (High) | F-Measure (High) | Precision (Low) | Recall (Low) | F-Measure (Low) |
|-------------|--------------|--------|------------------|---------------|------------------|-----------------|--------------|-----------------|
| J48         | 95.86        | 0.8974 | 0.987            | 0.872         | 0.926            | 0.949           | 0.995        | 0.971           |
| NaiveBayes  | 94.23        | 0.8592 | 0.933            | 0.869         | 0.900            | 0.946           | 0.973        | 0.960           |
| PART        | 95.64        | 0.8922 | 0.981            | 0.870         | 0.922            | 0.948           | 0.993        | 0.970           |
| OneR        | 95.29        | 0.8824 | 0.994            | 0.847         | 0.915            | 0.939           | 0.998        | 0.968           |
| ZeroR       | 70.27        | 0      | 0.000            | -             | 0.703           | 1.000        | 0.825        | -               |
| IBk         | 93.67        | 0.8465 | 0.912            | 0.872         | 0.891            | 0.947           | 0.964        | 0.955           |

**Observations:**
J48, NaiveBayes, PART, and OneR demonstrated the highest accuracy. J48 provided a balanced performance across both risk classes. ZeroR and IBk were less suitable due to lower accuracy or higher computational cost.

### 2.2. Attribute Selection Based on Accuracy

GainRatioAttributeEval was used to select optimal attributes for J48, Naive Bayes, PART, and OneR.

**J48 (Decision Tree):**
Optimal Number of Attributes: 10 (Accuracy: 95.95%)
Selected Attributes: `public_transport_count`, `race`, `covid19_symptoms`, `covid19_contact`, `hiv_positive`, `nursing_home`, `kidney_disease`, `health_worker`, `working`, `other_chronic`.

**Naive Bayes:**
Optimal Number of Attributes: 3 (Accuracy: 94.84%)
Selected Attributes: `public_transport_count`, `race`, `covid19_symptoms`.
*Adding more attributes did not improve accuracy for Naive Bayes.*

**PART:**
Optimal Number of Attributes: 10 (Accuracy: 95.90%)
Selected Attributes: `public_transport_count`, `race`, `covid19_symptoms`, `covid19_contact`, `hiv_positive`, `nursing_home`, `kidney_disease`, `health_worker`, `working`, `other_chronic`.

**OneR:**
Optimal Number of Attributes: 3 (Accuracy: 95.29%)
Selected Attributes: `public_transport_count`, `race`, `covid19_symptoms`.
*This simple rule-based algorithm performs best with fewer attributes.*

### 2.3. Cost-Sensitive Analysis

A cost-sensitive analysis was performed on J48 and PART to minimize misclassification of high-risk infection cases.

**Cost Matrix:**
| Actual \ Predicted | High | Low |
|--------------------|------|-----|
| High               | 0    | 5   |
| Low                | 1    | 0   |

**Results:**
*   **Cost-Sensitive J48:** Accuracy: 95.9454%, Total Cost: 879.0
*   **Cost-Sensitive PART:** Accuracy: 95.7936%, Total Cost: 882.0

**Conclusion:**
Cost-sensitive J48 demonstrated slightly higher accuracy and lower total cost compared to PART, making it a more reliable choice for minimizing classification errors in high-risk scenarios.

## 3. Text Classification Analysis

This section details the text classification analysis performed on a news dataset.

### 3.1. Parameter Tuning for StringToWordVector Filter

The `StringToWordVector` filter was tuned to optimize text preprocessing for classification.

| IDFTransform | TFTransform | Stemmer          | Tokenizer     | StopwordsHandler | Accuracy (%) |
|--------------|-------------|------------------|---------------|------------------|--------------|
| True         | True        | LovinsStemmer    | WordTokenizer | Rainbow          | 56.2776      |

**Optimal Parameters:**
*   IDFTransform: True
*   TFTransform: True
*   Stemmer: LovinsStemmer
*   Tokenizer: WordTokenizer
*   StopwordsHandler: Rainbow

### 3.2. Classification Results for News Articles

Four classification algorithms were evaluated for news article categorization.

| Classifier    | Correctly Classified Instances | Accuracy (%) | Time Taken (seconds) |
|---------------|--------------------------------|--------------|----------------------|
| IBk           | 8527.0                         | 60.83        | 25.07                |
| SMO           | 9060.0                         | 64.63        | 1202.99              |
| J48           | 7889.0                         | 56.28        | 51.16                |
| HoeffdingTree | 6823.0                         | 48.67        | 6.40                 |

**Observations:**
SMO achieved the highest accuracy (64.63%) but was the most time-consuming (1202.99 seconds). HoeffdingTree was the fastest (6.40 seconds) but had the lowest accuracy (48.67%). IBk offered a good balance of accuracy and speed.

### 3.3. Time Efficiency Analysis

*   **HoeffdingTree:** Most time-efficient (6.40 seconds), suitable for data streams and online learning.
*   **IBk:** Good efficiency (25.07 seconds), typical for lazy learners.
*   **J48:** Moderate execution time (51.16 seconds), reflecting its model complexity.
*   **SMO:** Most time-consuming (1202.99 seconds), characteristic of SVMs with large datasets.

The choice of classifier depends on the specific application requirements, balancing accuracy against time constraints.
