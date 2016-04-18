package generate;

import com.graphaware.neo4j.graphgen.util.LabelsUtil;
import generate.result.GraphgenProcedure;
import generate.result.NodeListResult;
import org.neo4j.logging.Log;
import org.neo4j.procedure.Context;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.PerformsWrites;
import org.neo4j.procedure.Procedure;

import java.util.stream.Stream;

public class NodeProcedure extends GraphgenProcedure {

    @Context
    public Log log;

    @Procedure
    @PerformsWrites
    public Stream<NodeListResult> nodes(@Name("labels") Object labels, @Name("properties") String propertyString, @Name("number") Long number) {
        log.warn(number.toString());
        return Stream.of(new NodeListResult(getGraphgenService().graphGenerator().generateNodes(LabelsUtil.fromInput(labels), propertyString, number)));
    }
}
