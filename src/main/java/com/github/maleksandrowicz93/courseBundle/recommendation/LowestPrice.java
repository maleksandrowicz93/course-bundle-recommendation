package com.github.maleksandrowicz93.courseBundle.recommendation;

import com.github.maleksandrowicz93.courseBundle.quoteCalculator.ProviderQuotes;
import com.github.maleksandrowicz93.courseBundle.quoteCalculator.QuotesCombinations;

import java.util.function.Function;

import static java.util.Comparator.comparing;

enum LowestPrice implements QuoteRecommendationPolicy {

    POLICY {
        @Override
        public QuoteRecommendation apply(QuotesCombinations quotesCombinations) {
            return quotesCombinations.all()
                                     .stream()
                                     .min(comparing(ProviderQuotes::sumQuotes))
                                     .map(Recommendation.FACTORY)
                                     .orElseThrow(() -> new IllegalArgumentException("No quote to recommend"));
        }
    };

    private static final String REASON = "the lowest price";

    private enum Recommendation implements Function<ProviderQuotes, QuoteRecommendation> {

        FACTORY {
            @Override
            public QuoteRecommendation apply(ProviderQuotes providerQuotes) {
                return new QuoteRecommendation(providerQuotes, REASON);
            }
        }
    }
}
