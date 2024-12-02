package com.github.maleksandrowicz93.courseBundle.recommendation;

import com.github.maleksandrowicz93.courseBundle.quoteCalculator.ProviderQuotes;

public record QuoteRecommendation(
        ProviderQuotes providerQuotes,
        String reason
) {
}
