package com.github.maleksandrowicz93.courseBundle.recommendation;

import lombok.experimental.UtilityClass;

@UtilityClass
class QuoteRecommendationPoliciesFactory {

    QuoteRecommendationPolicies create() {
        return QuoteRecommendationPolicies.from()
                                          .policy(LowestPrice.POLICY)
                                          .compose();
    }
}
