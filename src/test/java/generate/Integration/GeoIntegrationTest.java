package generate.Integration;

import generate.NodeProcedure;
import generate.ProcedureIntegrationTest;
import org.junit.Test;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class GeoIntegrationTest extends ProcedureIntegrationTest {

    @Override
    protected List<Class> procedureClass() {
        return Arrays.asList(
                NodeProcedure.class
        );
    }

    @Test
    public void testGeneratePositionNodes() {
        String q = "CALL generate.nodes('Position', '{latitude: latitude, longitude: longitude}', 150)";
        executeCypher(q);
        long i = 0;
        try (Transaction tx = getDatabase().beginTx()) {
            Iterator<Node> it = getDatabase().findNodes(Label.label("Position"));
            while (it.hasNext()) {
                Node position = it.next();
                ++i;
                assertTrue(position.hasProperty("latitude"));
                assertTrue(position.hasProperty("longitude"));
            }
            tx.success();
        }

        assertEquals(150, i);
    }
}
