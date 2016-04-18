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
