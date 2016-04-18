package generate;

import org.junit.Test;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class NodeProcedureTest extends ProcedureIntegrationTest {

    @Override
    protected List<Class> procedureClass() {
        return Arrays.asList(
                NodeProcedure.class
        );
    }

    @Test
    public void testGenerateNodeWithOneLabel() {
        executeCypher("CALL generate.nodes('Cool', '{}', 1)");
        assertNodesWithLabelCount(1, "Cool");
    }

    @Test
    public void testGenerateNodeWithMultipleLabels() {
        executeCypher("CALL generate.nodes(['Cool','Test','Awesome'], '', 1)");
        assertNodesWithLabelCount(1, "Cool,Test,Awesome".split(","));
    }

    @Test
    public void testGenerateNodeWithProperty() {
        try (Transaction tx = getDatabase().beginTx()) {
            Result result = getDatabase().execute("CALL generate.nodes('Person', '{name: firstName}', 10) YIELD nodes RETURN nodes");
            while (result.hasNext()) {
                Map<String, Object> row = result.next();
                List<Node> nodes = (List<Node>) row.get("nodes");
                assertEquals(10, nodes.size());
                for (Node node : nodes) {
                    assertTrue(node.hasLabel(Label.label("Person")));
                    assertTrue(node.hasProperty("name"));
                    assertNotNull(node.getProperty("name"));
                }
            }
            tx.success();
        }

    }

    private void assertNodesWithLabelCount(int c, String... labels) {
        int i = 0;
        try (Transaction tx = getDatabase().beginTx()) {
            for (org.neo4j.graphdb.Node node : getDatabase().getAllNodes()) {
                int z = 0;
                for (String label : labels) {
                    if (node.hasLabel(Label.label(label))) {
                        ++z;
                    }
                }
                if (z == labels.length) { ++i; }
            }

            tx.success();
        }

        assertEquals(c, i);
    }
}
