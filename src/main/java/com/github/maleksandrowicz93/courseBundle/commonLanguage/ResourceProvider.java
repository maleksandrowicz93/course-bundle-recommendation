package com.github.maleksandrowicz93.courseBundle.commonLanguage;

import java.util.Collection;

public record ResourceProvider(
        String providerName,
        Collection<String> topics
) {

    public ResourceProvider {
        if (providerName == null || providerName.isBlank()) {
            throw new IllegalArgumentException("provider name cannot be blank");
        }
        if (topics == null) {
            throw new IllegalArgumentException("covered topic cannot be null");
        }
    }
}
