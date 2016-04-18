package generate;

import com.graphaware.neo4j.graphgen.GraphgenService;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.procedure.Context;

public class GraphgenProcedure {

    @Context
    public GraphDatabaseService database;

    private static GraphgenService graphgenService;

    protected GraphgenService getGraphgenService() {
        if (null == graphgenService) {
            graphgenService = new GraphgenService(database);
        }

        return graphgenService;
    }

}
