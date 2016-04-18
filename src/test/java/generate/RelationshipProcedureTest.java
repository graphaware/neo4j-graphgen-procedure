package generate;

import org.junit.Test;
import org.neo4j.graphdb.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class RelationshipProcedureTest extends ProcedureIntegrationTest {

    @Override
    protected List<Class> procedureClass() {
        return Arrays.asList(
                RelationshipProcedure.class,
                NodeProcedure.class
        );
    }

    @Test
    public void testRelationshipCreation() {
        try (Transaction tx = getDatabase().beginTx()) {
            Result result = getDatabase().execute(
                    "CALL generate.nodes(\"Person\", '{}', 10) YIELD nodes as persons " +
                    "CALL generate.nodes(\"Company\", '{}', 5) YIELD nodes as companies " +
                    "CALL generate.relationships(persons, companies, \"WORKS_AT\", '{}', 10, 1) " +
                    "YIELD relationships RETURN *"
            );
            while (result.hasNext()) {
                Map<String, Object> row = result.next();
            }
            tx.success();
        }

        try (Transaction tx = getDatabase().beginTx()) {
            ResourceIterator<Node> it = getDatabase().findNodes(Label.label("Person"));
            int i = 0;
            while (it.hasNext()) {
                Node node = it.next();
                ++i;
                assertEquals(1, node.getDegree(RelationshipType.withName("WORKS_AT"), Direction.OUTGOING));
            }

            assertEquals(10, i);
            tx.success();
        }
    }
}
