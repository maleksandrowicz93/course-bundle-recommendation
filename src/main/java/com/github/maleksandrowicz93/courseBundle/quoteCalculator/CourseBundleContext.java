package com.github.maleksandrowicz93.courseBundle.quoteCalculator;

import com.github.maleksandrowicz93.courseBundle.commonLanguage.ResourceProviders;

public record CourseBundleContext(
        CourseBundleRequest courseBundleRequest,
        ResourceProviders resourceProviders
) {
}
