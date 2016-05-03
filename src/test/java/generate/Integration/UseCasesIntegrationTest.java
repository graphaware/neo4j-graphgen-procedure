package generate.Integration;

import generate.*;
import org.junit.Test;
import org.neo4j.graphdb.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UseCasesIntegrationTest extends ProcedureIntegrationTest {

    @Override
    protected List<Class> procedureClass() {
        return Arrays.asList(
                NodeProcedure.class,
                RelationshipProcedure.class,
                LinkedListProcedure.class,
                ValueProcedure.class
        );
    }

    @Test
    public void testUseCase1() {
        String query = "CALL generate.nodes('Person', '{firstName: firstName, lastName: lastName, email: email, accountBalance: { numberBetween: [1,10000]}}', 10)";
        executeCypher(query);
        long i = 0;
        try (Transaction tx = getDatabase().beginTx()) {
            ResourceIterator<Node> it = getDatabase().findNodes(Label.label("Person"));
            i = it.stream().count();
            tx.success();
        }
        assertEquals(10, i);
    }

    @Test
    public void testUseCase2() {
        String query = "CALL generate.nodes('Person', '{firstName: firstName, lastName: lastName, email: email, accountBalance: randomNumber}', 10)";
        executeCypher(query);
        long i = 0;
        try (Transaction tx = getDatabase().beginTx()) {
            ResourceIterator<Node> it = getDatabase().findNodes(Label.label("Person"));
            i = it.stream().count();
            tx.success();
        }
        assertEquals(10, i);
    }

    @Test
    public void testUseCase3() {
        String query = "CALL generate.nodes('Person', '{firstName: firstName, lastName: lastName, email: email, accountBalance: { randomNumber: [5, true]}}', 10)";
        executeCypher(query);
        long i = 0;
        try (Transaction tx = getDatabase().beginTx()) {
            ResourceIterator<Node> it = getDatabase().findNodes(Label.label("Person"));
            while (it.hasNext()) {
                Node node = it.next();
                ++i;
                assertEquals(5, node.getProperty("accountBalance").toString().length());
            }
            tx.success();
        }
        assertEquals(10, i);
    }
}
