package com.baerchen.jutils.runtime.entity;

import com.baerchen.jutils.runtime.control.Validable;
import com.baerchen.jutils.runtime.control.ValidationReport;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public abstract class ApiCredentials implements Validable<Map<String, String>> {

    private final Map<String, String> credentials;
    private List<String> keys;

    protected ApiCredentials(Map<String, String> credentials, List<String> keys) {
        this.credentials = credentials;
        this.keys = keys;
    }

    @Override
    public abstract ValidationReport<Map<String, String>> validate();
    public boolean containsAllKeys(List<String> requiredKeys) {
        return requiredKeys.stream()
                .allMatch(k -> credentials.containsKey(k) && credentials.get(k) != null);
    }

    public boolean containsAnyKey(List<String> optionalKeys) {
        return optionalKeys.stream()
                .anyMatch(k -> credentials.containsKey(k) && credentials.get(k) != null);
    }

    public List<String> missingKeys(List<String> requiredKeys) {
        return requiredKeys.stream()
                .filter(k -> !credentials.containsKey(k) || credentials.get(k) == null)
                .toList();
    }

}
