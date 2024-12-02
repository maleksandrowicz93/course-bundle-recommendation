package com.github.maleksandrowicz93.courseBundle.commonLanguage;

import java.util.Collection;
import java.util.Set;

public record ResourceProviders(
        Collection<ResourceProvider> all
) {

    public static final ResourceProviders EMPTY = new ResourceProviders(Set.of());
}
