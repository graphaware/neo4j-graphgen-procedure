package com.graphaware.neo4j.graphgen;

import com.graphaware.neo4j.graphgen.configuration.GraphgenConfiguration;
import com.graphaware.neo4j.graphgen.faker.FakerService;
import com.graphaware.neo4j.graphgen.generator.GraphGenerator;
import org.neo4j.graphdb.GraphDatabaseService;

public class GraphgenService {

    private final GraphDatabaseService database;

    private final FakerService fakerService;

    private final GraphGenerator graphGenerator;

    public GraphgenService(GraphDatabaseService database) {
        this.database = database;
        fakerService = new FakerService(new GraphgenConfiguration(database));
        graphGenerator = new GraphGenerator(database, fakerService);
    }

    public FakerService fakerService() {
        return fakerService;
    }

    public GraphGenerator graphGenerator() {
        return graphGenerator;
    }

}
