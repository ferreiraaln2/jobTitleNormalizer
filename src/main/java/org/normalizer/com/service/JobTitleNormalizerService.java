package org.normalizer.com.service;

import org.normalizer.com.models.JobTitleModel;
import org.normalizer.com.utils.StringSimilarityUtils;

import java.util.List;
import java.util.Objects;

public class JobTitleNormalizerService {
    private final List<JobTitleModel> normalizedJobTitleModels;
    private static final double MAX_QUALITY_MATCH = 1.0;
    private static final double MIN_QUALITY_MATCH = 0.0;

    public JobTitleNormalizerService(List<JobTitleModel> normalizedJobTitleModels) {
        this.normalizedJobTitleModels = Objects.requireNonNull(
                normalizedJobTitleModels,
                "Normalized job titles list cannot be null"
        );
    }

    public String normalize(String inputTitle) {
        this.validateInput(inputTitle);

        String bestMatch = null;
        double highestScore = MIN_QUALITY_MATCH;

        for (JobTitleModel jobTitleModel : normalizedJobTitleModels) {
            double score = calculateSimilarity(inputTitle.toLowerCase(), jobTitleModel.normalizedTitle().toLowerCase());
            if (isBetterMatch(score, highestScore)) {
                highestScore = score;
                bestMatch = jobTitleModel.normalizedTitle();
            }
        }

        return bestMatch;
    }

    private void validateInput(String inputTitle) {
        if (inputTitle == null || inputTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Input title cannot be null or empty");
        }
    }

    private double calculateSimilarity(String input1, String input2) {
        return StringSimilarityUtils.calculate(input1, input2);
    }

    private boolean isBetterMatch(double score, double highestScore) {
        return highestScore < score && MAX_QUALITY_MATCH >= score;
    }
}