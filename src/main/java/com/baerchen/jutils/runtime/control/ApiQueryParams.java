package com.baerchen.jutils.runtime.control;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuperBuilder
@Data
@AllArgsConstructor
public class ApiQueryParams implements Validable<Map<String, String>> {

    private final Map<String, String> params;
    private List<String> requiredKeys;
    private List<String> optionalKeys;

    @Override
    public ValidationReport<Map<String, String>> validate() {
        List<String> missing = requiredKeys == null ? List.of() :
                requiredKeys.stream()
                        .filter(k -> !params.containsKey(k) || params.get(k) == null)
                        .toList();

        boolean valid = missing.isEmpty();

        return ValidationReport.<Map<String, String>>builder()
                .valid(valid)
                .cause(valid ? null : "Missing required query parameters: " + String.join(", ", missing))
                .data(params.toString())
                .build();
    }

    public String toQueryString() {
        return params.entrySet().stream()
                .filter(e -> e.getValue() != null)
                .map(e -> e.getKey() + "=" + UriUtils.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
    }
}