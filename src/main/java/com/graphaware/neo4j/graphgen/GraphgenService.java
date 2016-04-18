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
