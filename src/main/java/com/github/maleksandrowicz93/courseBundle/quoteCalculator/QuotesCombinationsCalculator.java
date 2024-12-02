package com.github.maleksandrowicz93.courseBundle.quoteCalculator;

import com.github.maleksandrowicz93.courseBundle.commonLanguage.ResourceProvider;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toSet;

@UtilityClass
public class QuotesCombinationsCalculator {

    public static QuotesCombinations calculateFor(CourseBundleContext context) {
        var top3Topics = context.courseBundleRequest().top3Topics();
        var result = new HashSet<ProviderQuotes>();
        backtrack(
                top3Topics,
                new HashSet<>(),
                context.resourceProviders().all(),
                result,
                new HashMap<>()
        );
        return new QuotesCombinations(Set.copyOf(result));
    }

    private static void backtrack(
            CourseBundleRequest request,
            Collection<String> currentTopicsCoverage,
            Collection<ResourceProvider> providers,
            Collection<ProviderQuotes> result,
            Map<String, ProviderQuote> currentProviders
    ) {
        var requestedTopics = request.topicNames();
        if (currentTopicsCoverage.containsAll(requestedTopics)) {
            result.add(new ProviderQuotes(Map.copyOf(currentProviders)));
            return;
        }

        for (var provider : providers) {
            var providerName = provider.providerName();
            if (currentProviders.containsKey(providerName)) {
                continue;
            }

            var topicsContribution = provider.topics()
                                             .stream()
                                             .filter(requestedTopics::contains)
                                             .filter(not(currentTopicsCoverage::contains))
                                             .collect(toSet());

            if (topicsContribution.isEmpty()) {
                continue;
            }

            currentTopicsCoverage.addAll(topicsContribution);
            var quoteValue = QuoteValueCalculator.calculateFor(request, topicsContribution);
            var providerQuote = new ProviderQuote(providerName, topicsContribution, quoteValue);
            currentProviders.put(providerName, providerQuote);

            backtrack(request, currentTopicsCoverage, providers, result, currentProviders);

            currentTopicsCoverage.removeAll(topicsContribution);
            currentProviders.remove(providerName);
        }
    }
}
