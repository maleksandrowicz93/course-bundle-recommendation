package com.github.maleksandrowicz93.courseBundle.recommendation;

import com.github.maleksandrowicz93.courseBundle.quoteCalculator.QuotesCombinations;

import java.util.function.Function;

interface QuoteRecommendationPolicy extends Function<QuotesCombinations, QuoteRecommendation> {
}
