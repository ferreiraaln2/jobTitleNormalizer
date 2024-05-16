package org.normalizer.com.utils;

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.StringMetrics;

public class StringSimilarityUtils {
    private static final StringMetric metric = StringMetrics.levenshtein();

    public static double calculate(String s1, String s2) {
        return metric.compare(s1, s2);
    }
}