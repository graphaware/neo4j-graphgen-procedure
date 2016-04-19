/*
 * Copyright (c) 2013-2016 GraphAware
 *
 * This file is part of the GraphAware Framework.
 *
 * GraphAware Framework is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received a copy of
 * the GNU General Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.graphaware.neo4j.graphgen.faker;

import com.github.javafaker.Faker;
import com.graphaware.neo4j.graphgen.configuration.GraphgenConfiguration;
import com.graphaware.neo4j.graphgen.graph.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.toIntExact;

public class FakerService {

    // Names
    private static final String FIRSTNAME  = "firstName";
    private static final String LASTNAME   = "lastName";
    private static final String FULLNAME = "fullName";

    // Address
    private static final String COUNTRY = "country";
    private static final String CITY    = "city";
    private static final String STATE = "state";
    private static final String STREET_ADDRESS = "streetAddress";
    private static final String STREET_NAME = "streetName";
    private static final String ZIP_CODE = "zipCode";

    // Business
    private static final String CREDIT_CARD_NUMBER = "creditCardNumber";
    private static final String CREDIT_CARD_TYPE = "creditCardType";

    // Company
    private static final String COMPANY_NAME = "companyName";

    // Internet
    private static final String AVATAR_URL = "avatarUrl";
    private static final String EMAIL_ADDRESS = "email";
    private static final String URL = "url";

    // Lorem
    private static final String PARAGRAPH = "paragraph";
    private static final String SENTENCE = "sentence";
    private static final String WORD = "word";

    // Phone
    private static final String PHONE_NUMBER = "phoneNumber";

    // Time
    private static final String UNIX_TIME = "unixTime";

    // Numbers
    private static final String NUMBER_BETWEEN = "numberBetween";

    private final Faker faker;
    private final Random random;

    public FakerService(GraphgenConfiguration configuration) {
        this.random = new Random(configuration.getSeedValue());
        faker = new Faker(random);
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
            case FULLNAME:
                return faker.name().fullName();

            // Address
            case COUNTRY:
                return faker.address().country();
            case CITY:
                return faker.address().city();
            case STATE:
                return faker.address().state();
            case STREET_ADDRESS:
                return faker.address().streetAddress();
            case STREET_NAME:
                return faker.address().streetName();
            case ZIP_CODE:
                return faker.address().zipCode();

            // Business
            case CREDIT_CARD_NUMBER:
                return faker.business().creditCardNumber();
            case CREDIT_CARD_TYPE:
                return faker.business().creditCardType();

            // Company
            case COMPANY_NAME:
                return faker.company().name();

            // Internet
            case AVATAR_URL:
                return faker.internet().avatar();
            case EMAIL_ADDRESS:
                return faker.internet().emailAddress();
            case URL:
                return faker.internet().url();

            // Lorem
            case PARAGRAPH:
                return faker.lorem().paragraph();
            case SENTENCE:
                return faker.lorem().sentence();
            case WORD:
                return faker.lorem().word();

            // Phone
            case PHONE_NUMBER:
                return faker.phoneNumber().phoneNumber();

            // Time
            case UNIX_TIME:
                return unixTime();

            // Numbers
            case NUMBER_BETWEEN:
                return numberBetween(property);
            default:
                throw new IllegalArgumentException(String.format("Undefined value generator name '%s'", property.generatorName()));
        }
    }

    private long unixTime() {
        long now = System.currentTimeMillis();
        long diff = ThreadLocalRandom.current().nextLong(now);

        return ThreadLocalRandom.current().nextLong((now - diff), now);
    }

    private int numberBetween(Property property) {
        if (property.parameters().size() != 2) {
            throw new IllegalArgumentException(String.format("Expected exactly %d arguments, %d received", 2, property.parameters().size()));
        }

        Integer i1 = toIntExact((long) property.parameters().get(0));
        Integer i2 = toIntExact((long) property.parameters().get(1));

        if (i1 >= i2) {
            throw new IllegalArgumentException("First parameter should not be greater or equal than second parameter");
        }

        return ThreadLocalRandom.current().nextInt(i1, i2);
    }

    public Random getRandom() {
        return random;
    }

}
