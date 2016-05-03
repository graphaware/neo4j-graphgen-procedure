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
import com.graphaware.neo4j.graphgen.util.ShuffleUtil;
import org.apache.commons.lang.math.RandomUtils;

import java.math.BigDecimal;
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
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";

    // Business
    private static final String CREDIT_CARD_NUMBER = "creditCardNumber";
    private static final String CREDIT_CARD_TYPE = "creditCardType";

    // Company
    private static final String COMPANY_NAME = "companyName";

    // Internet
    private static final String AVATAR_URL = "avatarUrl";
    private static final String EMAIL_ADDRESS = "email";
    private static final String URL = "url";
    private static final String IPV4 = "ipv4";

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
    private static final String RANDOM_NUMBER = "randomNumber";

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
            case LATITUDE:
                return latitude(property);
            case LONGITUDE:
                return longitude(property);

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
            case IPV4:
                return ipV4();

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
            case RANDOM_NUMBER:
                return randomLong(property);
            default:
                throw new IllegalArgumentException(String.format("Undefined value generator name '%s'", property.generatorName()));
        }
    }

    public String ipV4() {
        return String.format(
                "%d.%d.%d.%d",
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256)
        );
    }

    public long unixTime() {
        long now = System.currentTimeMillis();
        long diff = ThreadLocalRandom.current().nextLong(now);

        return ThreadLocalRandom.current().nextLong((now - diff), now);
    }

    public int numberBetween(Property property) {
        if (property.parameters().size() != 2) {
            throw new IllegalArgumentException(String.format("Expected exactly %d arguments, %d received", 2, property.parameters().size()));
        }

        Integer i1 = parseInt(property.parameters().get(0));
        Integer i2 = parseInt(property.parameters().get(1));

        if (i1 >= i2) {
            throw new IllegalArgumentException("First parameter should not be greater or equal than second parameter");
        }

        return ThreadLocalRandom.current().nextInt(i1, i2);
    }

    public Object randomElement(List<?> objects) {
        return objects.get(ShuffleUtil.shuffle(objects, 1).get(0));
    }

    public double longitude(Property property) {
        if (property.parameters().size() != 2 && property.parameters().size() != 0) {
            throw new IllegalArgumentException("Number of parameters for the latitude generate should be exactly 2 or none");
        }

        int min = property.parameters().size() > 0 ? (Integer) property.parameters().get(0) : -180;
        int max = property.parameters().size() > 0 ? (Integer) property.parameters().get(1) : 180;

        return latitude(min, max);
    }

    public double longitude(int min, int max) {
        return randomDouble(6, min, max);
    }

    public double latitude(Property property) {
        if (property.parameters().size() != 2 && property.parameters().size() != 0) {
            throw new IllegalArgumentException("Number of parameters for the latitude generate should be exactly 2 or none");
        }

        int min = property.parameters().size() > 0 ? (Integer) property.parameters().get(0) : -90;
        int max = property.parameters().size() > 0 ? (Integer) property.parameters().get(1) : 90;

        return latitude(min, max);
    }

    public double latitude(int min, int max) {
        return randomDouble(6, min, max);
    }

    /**
     * Returns a random double
     *
     * @param maxNumberOfDecimals maximum number of places
     * @param min minimum value
     * @param max maximum value
     */

    public double randomDouble(int maxNumberOfDecimals, int min, int max) {
        double value = min + (max - min) * random.nextDouble();

        return new BigDecimal(value).setScale(maxNumberOfDecimals, BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }

    public Random getRandom() {
        return random;
    }

    /**
     * Returns a ranbom number
     */

    public long randomLong(Property property) {
        int numberOfDigits = property.parameters().size() > 0 ? (int) property.parameters().get(0) : random.nextInt(8) + 1;
        failZero(numberOfDigits);
        boolean strict = property.parameters().size() > 1 && (boolean) property.parameters().get(1);

        return randomLong(numberOfDigits, strict);
    }

    /**
     *
     * @param numberOfDigits the number of digits the generated value should have
     * @param strict whether or not the generated value should have exactly <code>numberOfDigits</code>
     */
    public long randomLong(int numberOfDigits, boolean strict) {
        long max = (long) Math.pow(10, numberOfDigits);
        if (strict) {
            long min = (long) Math.pow(10, numberOfDigits-1);
            return min + ((long)(random.nextDouble()*(max - min)));
        }

        return (long) (random.nextDouble()*max);
    }

    private int parseInt(Object o) {
        if (o instanceof String) {
            return Integer.parseInt(o.toString());
        }

        if (o instanceof Long) {
            return toIntExact((long) o);
        }

        return (int) o;
    }

    private void failZero(int number) {
        if (0 == number) {
            throw new IllegalArgumentException("given number cannot be 0");
        }
    }

}
