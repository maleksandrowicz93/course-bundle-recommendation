package com.github.maleksandrowicz93.courseBundle.recommendation;

import com.github.maleksandrowicz93.courseBundle.resourceProvidersSupplier.ResourceProvidersSupplier;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CourseBundleRecommendationFactory {

    ResourceProvidersSupplier resourceProvidersSupplier;

    public CourseBundleRecommendation courseBundleRecommendation() {
        return new CourseBundleRecommendation(
                resourceProvidersSupplier,
                QuoteRecommendationPoliciesFactory.create()
        );
    }
}
