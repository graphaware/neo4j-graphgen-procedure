package com.graphaware.neo4j.graphgen.faker;

import com.graphaware.neo4j.graphgen.configuration.GraphgenConfiguration;
import com.graphaware.neo4j.graphgen.graph.Property;
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

    @Test
    public void testLatitude() {
        for (int i = 0; i < 1000; ++i) {
            double lat = fakerService.latitude(-90, 90);
            assertTrue(lat >= -90 && lat <= 90);
        }
    }

    @Test
    public void testLongitude() {
        for (int i = 0; i < 1000; ++i) {
            double lon = fakerService.longitude(-180, 180);
            assertTrue(lon >= -180 && lon <= 180);
        }
    }

    @Test
    public void testLatLonWithPropertyObject() {
        for (int i = 0; i < 1000; ++i) {
            Property latProp = new Property("lat", "latitude", Arrays.asList());
            Property longProp = new Property("long", "longitude", Arrays.asList());
            double lat = fakerService.latitude(latProp);
            double lon = fakerService.longitude(longProp);
            assertTrue(lat >= -90 && lat <= 90);
            assertTrue(lon >= -180 && lon <= 180);

            Property latProp2 = new Property("lat", "latitude", Arrays.asList(-30, 30));
            Property lonProp2 = new Property("lon", "longitude", Arrays.asList(-120, 120));
            double lat2 = fakerService.latitude(latProp2);
            double lon2 = fakerService.longitude(lonProp2);
            assertTrue(lat2 >= -30 && lat2 <= 30);
            assertTrue(lon2 >= -120 && lon2 <= 120);
        }
    }

    @Test
    public void testRandomNumber() {
        for (int i = 0; i < 1000; ++i) {
            Property property = new Property("accountBalance", "randomNumber", Arrays.asList());
            Long n = fakerService.randomLong(property);
        }

        for (int i = 0; i < 1000; ++i) {
            Property property = new Property("accountBalance", "randomNumber", Arrays.asList(5, true));
            Long n = fakerService.randomLong(property);
            String s = n.toString();
            assertEquals(5, s.length());
        }
    }

    @After
    public void tearDown() {
        database.shutdown();
    }

}
