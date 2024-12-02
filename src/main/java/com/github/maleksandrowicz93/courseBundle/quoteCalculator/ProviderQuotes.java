package com.github.maleksandrowicz93.courseBundle.quoteCalculator;

import java.math.BigDecimal;
import java.util.Map;

import static java.math.BigDecimal.ZERO;

public record ProviderQuotes(
        Map<String, ProviderQuote> providers
) {

    public BigDecimal sumQuotes() {
        return providers.values()
                        .stream()
                        .map(ProviderQuote::value)
                        .reduce(BigDecimal::add)
                        .orElse(ZERO);
    }
}
