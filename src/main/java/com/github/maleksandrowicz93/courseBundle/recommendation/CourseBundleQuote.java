package com.github.maleksandrowicz93.courseBundle.recommendation;

import com.github.maleksandrowicz93.courseBundle.quoteCalculator.QuotesCombinations;

public record CourseBundleQuote(
        QuotesCombinations quotesCombinations,
        QuoteRecommendations recommendations
) {
}
