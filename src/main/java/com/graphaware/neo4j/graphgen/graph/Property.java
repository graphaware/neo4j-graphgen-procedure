package com.graphaware.neo4j.graphgen.graph;

import java.util.ArrayList;
import java.util.List;

public class Property {

    private String key;

    private String generatorName;

    private List<Object> parameters = new ArrayList<>();

    public Property(String key, String generatorName) {
        this.key = key;
        this.generatorName = generatorName;
    }

    public Property(String key, String generatorName, List<Object> parameters) {
        this.key = key;
        this.generatorName = generatorName;
        this.parameters = parameters;
    }

    public String key() {
        return key;
    }

    public String generatorName() {
        return generatorName;
    }

    public List<Object> parameters() {
        return parameters;
    }
}
