package com.github.maleksandrowicz93.courseBundle.quoteCalculator;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import static java.util.Comparator.reverseOrder;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

public record CourseBundleRequest(
        Map<String, Integer> topics
) {

    public CourseBundleRequest {
        if (topics.values()
                  .stream()
                  .anyMatch(coverageLevel -> coverageLevel == null || coverageLevel < 1)) {
            throw new IllegalArgumentException("topic coverage levels should be positive");
        }
        if (topics.keySet()
                  .stream()
                  .anyMatch(topic -> topic == null || topic.isBlank())) {
            throw new IllegalArgumentException("topic names should not be blank");
        }
    }

    public Collection<String> topicNames() {
        return topics.keySet();
    }

    public int coverageLevelFor(String topic) {
        return topics.getOrDefault(topic, 0);
    }

    public int coverageLevelFor(Collection<String> topicsContribution) {
        return topics.entrySet()
                     .stream()
                     .filter(e -> topicsContribution.contains(e.getKey()))
                     .mapToInt(Entry::getValue)
                     .sum();
    }

    public int importanceOrder(String topic) {
        var coverageLevel = coverageLevelFor(topic);
        var sortedCoverageLevels = topics.values()
                                         .stream()
                                         .sorted(reverseOrder())
                                         .toList();
        return sortedCoverageLevels.indexOf(coverageLevel) + 1;
    }

    public CourseBundleRequest top3Topics() {
        return topics.entrySet()
                     .stream()
                     .sorted(comparingByValue(reverseOrder()))
                     .limit(3)
                     .collect(collectingAndThen(
                             toMap(Entry::getKey, Entry::getValue),
                             CourseBundleRequest::new
                     ));
    }
}
