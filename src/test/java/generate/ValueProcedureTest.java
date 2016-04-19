package generate;

import org.junit.Test;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ValueProcedureTest extends ProcedureIntegrationTest {

    @Override
    protected List<Class> procedureClass() {
        return Arrays.asList(
                ValueProcedure.class
        );
    }

    @Test
    public void testGenerateValueWithoutParameters() {
        try (Transaction tx = getDatabase().beginTx()) {
            Result result = getDatabase().execute("CALL generate.values('firstName', null, 10) YIELD values RETURN values");
            int i = 0;
            while (result.hasNext()) {
                Map<String, Object> row = result.next();
                List<Object> values = (List<Object>) row.get("values");
                assertEquals(10, values.size());
                ++i;
            }
            assertEquals(1, i);
            tx.success();
        }
    }

    @Test
    public void testGenerateValueWithParameters() {
        try (Transaction tx = getDatabase().beginTx()) {
            Result result = getDatabase().execute("CALL generate.values('numberBetween', [0, 10], 100) YIELD values UNWIND values AS value RETURN value");
            int i = 0;
            while (result.hasNext()) {
                Map<String, Object> row = result.next();
                Integer value = (Integer) row.get("value");
                assertTrue(value >= 0 && value <= 100);
                ++i;
            }
            assertEquals(100, i);
            tx.success();
        }
    }
}
