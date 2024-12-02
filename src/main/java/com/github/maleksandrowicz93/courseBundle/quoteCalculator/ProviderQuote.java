package com.github.maleksandrowicz93.courseBundle.quoteCalculator;

import java.math.BigDecimal;
import java.util.Collection;

public record ProviderQuote(
        String providerName,
        Collection<String> coveredTopics,
        BigDecimal value
) {

    public ProviderQuote {
        if (providerName == null || providerName.isBlank()) {
            throw new IllegalArgumentException("provider name cannot be blank");
        }
        if (coveredTopics == null) {
            throw new IllegalArgumentException("covered topic cannot be null");
        }
        if (value.intValue() < 0) {
            throw new IllegalArgumentException("value cannot be negative");
        }
    }
}
