package com.github.maleksandrowicz93.courseBundle.recommendation;

import com.github.maleksandrowicz93.courseBundle.quoteCalculator.CourseBundleContext;
import com.github.maleksandrowicz93.courseBundle.quoteCalculator.CourseBundleRequest;
import com.github.maleksandrowicz93.courseBundle.quoteCalculator.QuotesCombinationsCalculator;
import com.github.maleksandrowicz93.courseBundle.resourceProvidersSupplier.ResourceProvidersSupplier;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CourseBundleRecommendation {

    ResourceProvidersSupplier resourceProvidersSupplier;
    QuoteRecommendationPolicies quoteRecommendationPolicies;

    public CourseBundleQuote recommendQuoteFor(CourseBundleRequest request) {
        var resourceProviders = resourceProvidersSupplier.get();
        var context = new CourseBundleContext(request, resourceProviders);
        var quoteCombinations = QuotesCombinationsCalculator.calculateFor(context);
        var quoteRecommendations = quoteRecommendationPolicies.apply(quoteCombinations);
        return new CourseBundleQuote(quoteCombinations, quoteRecommendations);
    }
}
