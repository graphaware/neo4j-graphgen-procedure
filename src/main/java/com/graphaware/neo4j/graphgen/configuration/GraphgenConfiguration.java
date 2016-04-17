package com.graphaware.neo4j.graphgen.configuration;

import org.neo4j.graphdb.GraphDatabaseService;

public class GraphgenConfiguration {

    private static final String NAMESPACE = "graphaware.graphgen.";
    private static final String SEED_KEY = "seed";
    private static final String NODE_TEMPLATE_KEY = "template.node.";
    private static final String RELATIONSHIP_TEMPLATE_KEY = "relationship.template.";

    private final GraphDatabaseService database;

    public GraphgenConfiguration(GraphDatabaseService database) {
        this.database = database;
    }

    public long getSeedValue() {
        return System.currentTimeMillis();
    }

}
