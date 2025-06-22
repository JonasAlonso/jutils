package com.baerchen.jutils.runtime.control;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class WebClientProviderWithApiKey implements Validable {

    private final Map<String, String> credentials;
    private List<String> keys;

    public WebClientProviderWithApiKey(Map<String, String> credentials, List<String> keys) {
        this.credentials = credentials;
        this.keys = keys;
    }

    @Override
    public ValidationReport<Map<String, String>> validate() {
        String input = this.credentials.entrySet()
                .stream()
                .map(k -> String.format("[key,value]=[%s,%s]", k.getKey(), k.getValue()))
                .collect(Collectors.joining(", "));
        String missingKeys = missingKeys(keys).stream().map( str -> str.toString()).collect(Collectors.joining(", "));

                 return ValidationReport.<Map<String, String>>builder()
                        .valid(containsAllKeys(keys))
                        .data(input)
                        .cause(String.format("Missing key(s): [%s]", missingKeys))
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
