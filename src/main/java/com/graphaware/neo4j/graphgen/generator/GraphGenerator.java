package com.graphaware.neo4j.graphgen.generator;

import com.graphaware.neo4j.graphgen.YamlParser;
import com.graphaware.neo4j.graphgen.configuration.GraphgenConfiguration;
import com.graphaware.neo4j.graphgen.faker.FakerService;
import com.graphaware.neo4j.graphgen.graph.Property;
import com.graphaware.neo4j.graphgen.util.CountSyntaxUtil;
import com.graphaware.neo4j.graphgen.util.ShuffleUtil;
import org.neo4j.graphdb.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GraphGenerator {

    private final GraphDatabaseService database;

    private final YamlParser parser;

    private final FakerService fakerService;

    private final Random random;

    public GraphGenerator(GraphDatabaseService database, FakerService fakerService) {
        this.database = database;
        this.parser = new YamlParser();
        this.fakerService = fakerService;
        this.random = fakerService.getRandom();
    }

    public List<Node> generateNodes(Label[] labels, String propertiesString, long number) {

        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < number; ++i) {
            Node node = database.createNode(labels);
            for (Property property : getProperties(propertiesString)) {
                node.setProperty(property.key(), fakerService.getValue(property));
            }
            nodes.add(node);
        }

        return nodes;
    }


    public List<Relationship> generateRelationships(List<Node> from, List<Node> to, String relationshipType, String properties, String fromCount, String toCount) {
        List<Relationship> list = new ArrayList<>();
        if (from.isEmpty() || to.isEmpty()) {
            return list;
        }

        List<Property> propertyList = getProperties(properties);
        int fromS = CountSyntaxUtil.getCount(fromCount, random);
        int toS = CountSyntaxUtil.getCount(toCount, random);

        List<Integer> fromNodes = ShuffleUtil.shuffle(from, fromS);
        for (int i : fromNodes) {
            List<Integer> toNodes = ShuffleUtil.shuffle(to, toS);
            Node start = from.get(i);
            for (int e : toNodes) {
                Node end = to.get(e);
                Relationship r = start.createRelationshipTo(end, RelationshipType.withName(relationshipType));
                addRelationshipProperties(r, propertyList);
                list.add(r);
            }
        }

        return list;
    }

    private List<Property> getProperties(String definition) {
        return parser.parse(definition);
    }

    private void addRelationshipProperties(Relationship relationship, List<Property> properties) {
        for (Property property : properties) {
            relationship.setProperty(property.key(), fakerService.getValue(property));
        }
    }

}
