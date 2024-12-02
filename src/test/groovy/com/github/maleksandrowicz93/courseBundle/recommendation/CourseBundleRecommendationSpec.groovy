package com.github.maleksandrowicz93.courseBundle.recommendation

import com.github.maleksandrowicz93.courseBundle.commonLanguage.ResourceProvider
import com.github.maleksandrowicz93.courseBundle.quoteCalculator.CourseBundleRequest
import com.github.maleksandrowicz93.courseBundle.quoteCalculator.ProviderQuote
import com.github.maleksandrowicz93.courseBundle.quoteCalculator.ProviderQuotes
import com.github.maleksandrowicz93.courseBundle.resourceProvidersSupplier.ResourceProvidersSupplier
import spock.lang.Specification
import spock.lang.Subject

class CourseBundleRecommendationSpec extends Specification {

    def factory = new CourseBundleRecommendationFactory(
            Stub(ResourceProvidersSupplier) {
                get() >> Set.of(
                        new ResourceProvider("provider_a", Set.of("math", "science")),
                        new ResourceProvider("provider_b", Set.of("reading", "science")),
                        new ResourceProvider("provider_c", Set.of("history", "math"))
                )
            }
    )

    @Subject
    def recommendation = factory.courseBundleRecommendation()

    def "recommendations are suggested correctly"() {
        given:
            def request = new CourseBundleRequest(Map.of(
                    "reading", 20,
                    "math", 50,
                    "science", 30,
                    "history", 15,
                    "art", 10
            ))

        when:
            def courseBundleQuote = recommendation.recommendQuoteFor(request)

        then:
            with(courseBundleQuote.recommendations().all()) {
                size() == 1
                with(first()) {
                    reason() == "the lowest price"
                    with(providerQuotes()) {
                        sumQuotes() == BigDecimal.valueOf(14)
                        with(providers().keySet()) {
                            containsAll("provider_a", "provider_b")
                        }
                        with(providers().values()) {
                            containsAll(
                                    new ProviderQuote(
                                            "provider_a",
                                            Set.of("math", "science"),
                                            BigDecimal.valueOf(8).setScale(2)
                                    ),
                                    new ProviderQuote(
                                            "provider_b",
                                            Set.of("reading"),
                                            BigDecimal.valueOf(6).setScale(2)
                                    )
                            )
                        }
                    }
                }
            }
        and:
            with(courseBundleQuote.quotesCombinations().all()) {
                size() == 4
                contains(new ProviderQuotes(Map.of(
                        "provider_a",
                        new ProviderQuote(
                                "provider_a",
                                Set.of("math", "science"),
                                BigDecimal.valueOf(8).setScale(2)
                        ),
                        "provider_b",
                        new ProviderQuote(
                                "provider_b",
                                Set.of("reading"),
                                BigDecimal.valueOf(6).setScale(2)
                        )
                )))
                contains(new ProviderQuotes(Map.of(
                        "provider_b",
                        new ProviderQuote(
                                "provider_b",
                                Set.of("science", "reading"),
                                BigDecimal.valueOf(5).setScale(2)
                        ),
                        "provider_a",
                        new ProviderQuote(
                                "provider_a",
                                Set.of("math"),
                                BigDecimal.valueOf(10).setScale(2)
                        )
                )))
                contains(new ProviderQuotes(Map.of(
                        "provider_b",
                        new ProviderQuote(
                                "provider_b",
                                Set.of("science", "reading"),
                                BigDecimal.valueOf(5).setScale(2)
                        ),
                        "provider_c",
                        new ProviderQuote(
                                "provider_c",
                                Set.of("math"),
                                BigDecimal.valueOf(10).setScale(2)
                        )
                )))
                contains(new ProviderQuotes(Map.of(
                        "provider_a",
                        new ProviderQuote(
                                "provider_a",
                                Set.of("science"),
                                BigDecimal.valueOf(7.5).setScale(2)
                        ),
                        "provider_b",
                        new ProviderQuote(
                                "provider_b",
                                Set.of("reading"),
                                BigDecimal.valueOf(6).setScale(2)
                        ),
                        "provider_c",
                        new ProviderQuote(
                                "provider_c",
                                Set.of("math"),
                                BigDecimal.valueOf(10).setScale(2)
                        )
                )))
            }
    }
}
