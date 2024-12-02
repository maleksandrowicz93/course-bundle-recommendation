package com.github.maleksandrowicz93.courseBundle.recommendation;

import com.github.maleksandrowicz93.courseBundle.quoteCalculator.QuotesCombinations;
import lombok.Builder;
import lombok.Singular;

import java.util.Collection;
import java.util.function.Function;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

@Builder(
        builderClassName = "QuoteRecommendationPoliciesComposer",
        builderMethodName = "from",
        buildMethodName = "compose"
)
record QuoteRecommendationPolicies(
        @Singular(value = "policy", ignoreNullCollections = true) Collection<QuoteRecommendationPolicy> all
) implements Function<QuotesCombinations, QuoteRecommendations> {

    @Override
    public QuoteRecommendations apply(QuotesCombinations quotesCombinations) {
        return all.stream()
                  .map(policy -> policy.apply(quotesCombinations))
                  .collect(collectingAndThen(
                          toSet(),
                          QuoteRecommendations::new
                  ));
    }
}
