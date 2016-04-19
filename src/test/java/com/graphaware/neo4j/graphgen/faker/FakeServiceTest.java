package com.graphaware.neo4j.graphgen.faker;

import com.graphaware.neo4j.graphgen.configuration.GraphgenConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.test.TestGraphDatabaseFactory;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FakeServiceTest {

    private GraphDatabaseService database;

    private FakerService fakerService;

    @Before
    public void setUp() {
        database = new TestGraphDatabaseFactory().newImpermanentDatabase();
        fakerService = new FakerService(new GraphgenConfiguration(database));
    }

    @Test
    public void testRandomElement() {
        List<Object> elements = Arrays.asList(
                "One", "Two", "Tree"
        );

        for (int i = 0; i < 1000; ++i) {
            Object value = fakerService.randomElement(elements);
            assertTrue(elements.contains(value.toString()));
        }
    }

    @Test
    public void testIpV4() {
        for (int i = 0; i < 1000; ++i) {
            String value = fakerService.ipV4();
            String[] parts = value.split("\\.");
            assertEquals(4, parts.length);
            for (String p : parts) {
                int s = Integer.valueOf(p);
                assertTrue(s >= 0 && s <= 255);
            }
        }
    }

    @After
    public void tearDown() {
        database.shutdown();
    }

}
