package com.baerchen.jutils.runtime.control;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class WebClientProviderWithApiKey implements Validable<Map<String, String>> {

    private final Map<String, String> credentials;
    private final List<String> keys;

    public WebClientProviderWithApiKey(Map<String, String> credentials, List<String> keys) {
        this.credentials = credentials;
        this.keys = keys;
    }

    @Override
    public ValidationReport<Map<String, String>> validate() {
        String input = this.credentials.entrySet()
                .stream()
                .map(e -> String.format("[key,value]=[%s,%s]", e.getKey(), e.getValue()))
                .collect(Collectors.joining(", "));
        List<String> missing = missingKeys(keys);
        boolean valid = missing.isEmpty();

        return ValidationReport.<Map<String, String>>builder()
                .valid(valid)
                .data(input)
                .cause(valid ? null : String.format("Missing key(s): [%s]", String.join(", ", missing)))
                .build();
    }
    private boolean containsAllKeys(List<String> requiredKeys) {
        return requiredKeys.stream()
        .allMatch(k -> credentials.containsKey(k) && credentials.get(k) != null);
    }

    private List<String> missingKeys(List<String> requiredKeys) {
        return requiredKeys.stream()
        .filter(k -> !credentials.containsKey(k) || credentials.get(k) == null)
        .toList();
    }
}
