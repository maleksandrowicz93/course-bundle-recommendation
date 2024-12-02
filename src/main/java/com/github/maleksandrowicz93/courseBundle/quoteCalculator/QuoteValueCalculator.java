package com.github.maleksandrowicz93.courseBundle.quoteCalculator;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;

@UtilityClass
class QuoteValueCalculator {

    BigDecimal calculateFor(CourseBundleRequest request, Collection<String> topicsContribution) {
        if (!request.topicNames().containsAll(topicsContribution)) {
            throw new IllegalArgumentException("topics contribution should be subset of requested topics");
        }
        if (topicsContribution.isEmpty()) {
            return ZERO;
        }
        if (topicsContribution.size() > 1) {
            int totalCoverageLevel = request.coverageLevelFor(topicsContribution);
            return BigDecimal.valueOf(totalCoverageLevel)
                             .divide(TEN, 2, HALF_UP);
        }
        var oneContributed = List.copyOf(topicsContribution).getFirst();
        var coverageLevel = request.coverageLevelFor(oneContributed);
        var importanceOrder = request.importanceOrder(oneContributed);
        var factor = switch (importanceOrder) {
            case 1 -> ONE.divide(BigDecimal.valueOf(5), 2, HALF_UP);
            case 2 -> ONE.divide(BigDecimal.valueOf(4), 2, HALF_UP);
            case 3 -> BigDecimal.valueOf(3).divide(TEN, 2, HALF_UP);
            default -> ZERO;
        };
        return BigDecimal.valueOf(coverageLevel).multiply(factor);
    }
}
