package com.graphaware.neo4j.graphgen;

import com.fasterxml.jackson.dataformat.yaml.snakeyaml.Yaml;
import com.graphaware.neo4j.graphgen.graph.Property;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class YamlParser {

    private final Yaml yaml;

    public YamlParser() {
        yaml = new Yaml();
    }

    public List<Property> parse(String input) {
        @SuppressWarnings("unchecked")
        Map<String, Object> values = (Map<String, Object>) yaml.load(input);
        List<Property> properties = new ArrayList<>();

        for (String k : values.keySet()) {
            if (values.get(k) instanceof String) {
                properties.add(new Property(k, String.valueOf(values.get(k))));
            } else if (values.get(k) instanceof LinkedHashMap){
                properties.add(fromMap(k, (LinkedHashMap) values.get(k)));
            }
        }

        return properties;
    }

    public Property fromMap(String propertyKey, LinkedHashMap map) {
        Map<String, Object> prop = (Map<String, Object>) map;
        String k = prop.keySet().iterator().next();

        if (prop.get(k) instanceof List) {
            return new Property(propertyKey, k, (List) prop.get(k));
        }

        throw new IllegalArgumentException("Invalid grapghen format");
    }
}
