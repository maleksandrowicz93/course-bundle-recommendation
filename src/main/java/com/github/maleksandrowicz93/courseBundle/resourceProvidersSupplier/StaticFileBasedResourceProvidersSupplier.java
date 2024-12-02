package com.github.maleksandrowicz93.courseBundle.resourceProvidersSupplier;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.maleksandrowicz93.courseBundle.commonLanguage.ResourceProvider;
import com.github.maleksandrowicz93.courseBundle.commonLanguage.ResourceProviders;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
class StaticFileBasedResourceProvidersSupplier implements ResourceProvidersSupplier {

    String configFIleName;

    @Override
    public ResourceProviders get() {
        var providerTopics = Map.<String, String>of();
        try (var inputStream = StaticFileBasedResourceProvidersSupplier.class.getClassLoader()
                                                                             .getResourceAsStream(configFIleName)) {
            var objectMapper = new ObjectMapper();
            providerTopics = objectMapper.readValue(
                                                 inputStream,
                                                 new TypeReference<Map<String, Map<String, String>>>() {
                                                 }
                                         )
                                         .get("provider_topics");
        } catch (IOException e) {
            return ResourceProviders.EMPTY;
        }
        return providerTopics.entrySet()
                             .stream()
                             .map(e -> new ResourceProvider(
                                     e.getKey(),
                                     Set.of(e.getValue().split("\\+"))
                             ))
                             .collect(collectingAndThen(
                                     toSet(),
                                     ResourceProviders::new
                             ));
    }
}
