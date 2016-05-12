package generate;

import org.junit.Test;
import org.neo4j.graphdb.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class RelationshipDistributionTest extends ProcedureIntegrationTest {

    @Override
    protected List<Class> procedureClass() {
        return Arrays.asList(
                NodeProcedure.class,
                RelationshipProcedure.class
        );
    }

    @Test
    public void testRelationshipDistributionRight() {
        int personsCount = 0;
        executeCypher("CALL generate.nodes('Person', '{name: fullName}', 10) YIELD nodes as persons " +
                "CALL generate.nodes('Skill', '{name: word}', 20) YIELD nodes as skills " +
                "CALL generate.relationships(persons, skills, 'HAS_SKILL', '{since: unixTime}', 10, '3-7') " +
                "YIELD relationships as relationships RETURN *");


        try (Transaction tx = getDatabase().beginTx()) {
            Iterator<Node> persons = getDatabase().findNodes(Label.label("Person"));
            while (persons.hasNext()) {
                Node person = persons.next();
                ++personsCount;
                int skillsDegree = person.getDegree(RelationshipType.withName("HAS_SKILL"), Direction.OUTGOING);
                assertTrue(skillsDegree <= 7 && skillsDegree >= 3);
            }

            tx.success();
        }

        assertEquals(10, personsCount);
    }

    @Test
    public void testRelationshipDistributionLeft() {
        int personsCount = 0;
        executeCypher("CALL generate.nodes('Person', '{name: fullName}', 10) YIELD nodes as persons " +
                "CALL generate.nodes('Skill', '{name: word}', 20) YIELD nodes as skills " +
                "CALL generate.relationships(persons, skills, 'HAS_SKILL', '{since: unixTime}', '3-7', 10) " +
                "YIELD relationships as relationships RETURN *");

        try (Transaction tx = getDatabase().beginTx()) {
            ResourceIterator<Node> it = getDatabase().findNodes(Label.label("Person"));
        }
    }
}
