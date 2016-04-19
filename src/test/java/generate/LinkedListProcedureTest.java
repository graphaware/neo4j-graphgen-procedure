package generate;

import org.junit.Test;
import org.neo4j.graphdb.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class LinkedListProcedureTest extends ProcedureIntegrationTest {

    @Override
    protected List<Class> procedureClass() {
        return Arrays.asList(
                NodeProcedure.class,
                LinkedListProcedure.class
        );
    }

    @Test
    public void testGenerateLinkedList() {
        try (Transaction tx = getDatabase().beginTx()) {
            Result result = getDatabase().execute("CALL generate.nodes('Feed', '', 10) YIELD nodes as feeds " +
                    "CALL generate.linkedList(feeds, 'NEXT') YIELD nodes AS nodes RETURN nodes");

            int i = 0;
            while (result.hasNext()) {
                Map<String, Object> row = result.next();
                ++i;
                int feed = 0;
                List<Node> nodes = (List<Node>) row.get("nodes");
                for (Node node : nodes) {
                    if (feed < nodes.size()-1) {
                        ++feed;
                        assertTrue(node.hasRelationship(RelationshipType.withName("NEXT"), Direction.OUTGOING));
                    }
                }
                assertEquals(9, feed);
            }
            assertEquals(1, i);
            tx.success();
        }
    }
}
