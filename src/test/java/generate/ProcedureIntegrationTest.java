package generate;

import org.junit.After;
import org.junit.Before;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.api.exceptions.KernelException;
import org.neo4j.kernel.impl.proc.Procedures;
import org.neo4j.kernel.internal.GraphDatabaseAPI;
import org.neo4j.test.TestGraphDatabaseFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class ProcedureIntegrationTest {

    private GraphDatabaseService db;

    @Before
    public void setUp() throws Exception {
        db = new TestGraphDatabaseFactory().newImpermanentDatabase();
        registerProcedure();
    }

    protected abstract List<Class> procedureClass();

    private void registerProcedure() throws KernelException {
        for (Class clazz : procedureClass()) {
            ((GraphDatabaseAPI) db).getDependencyResolver().resolveDependency(Procedures.class).register(clazz);
        }
    }

    protected GraphDatabaseService getDatabase() {
        return db;
    }

    protected List<Map<String, Object>> executeCypher(String query) {
        List<Map<String, Object>> rows = new ArrayList<>();

        try (Transaction tx = getDatabase().beginTx()) {
            Result result = getDatabase().execute(query);
            while (result.hasNext()) {
                rows.add(result.next());
            }

            tx.success();
        }

        return rows;
    }

    @After
    public void tearDown() {
        db.shutdown();
    }

}
