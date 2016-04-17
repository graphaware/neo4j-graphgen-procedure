package com.graphaware.neo4j.graphgen.faker;

import com.github.javafaker.Faker;
import com.graphaware.neo4j.graphgen.configuration.GraphgenConfiguration;
import com.graphaware.neo4j.graphgen.graph.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FakerService {

    // Names
    private static final String FIRSTNAME  = "firstName";
    private static final String LASTNAME   = "lastName";

    // Address
    private static final String COUNTRY = "country";
    private static final String CITY    = "city";

    private final Faker faker;

    public FakerService(GraphgenConfiguration configuration) {
        faker = new Faker(new Random(configuration.getSeedValue()));
    }

    public List<Object> getValues(Property property, int number) {
        List<Object> values = new ArrayList<>();
        for (int i = 0; i < number; ++i) {
            values.add(getValue(property));
        }

        return values;
    }

    public Object getValue(Property property) {

        switch (property.generatorName()) {
            // Names
            case FIRSTNAME:
                return faker.name().firstName();
            case LASTNAME:
                return faker.name().lastName();

            // Address
            case COUNTRY:
                return faker.address().country();
            case CITY:
                return faker.address().city();
            default:
                throw new IllegalArgumentException(String.format("Undefined value generator name '%s'", property.generatorName()));
        }
    }

}
