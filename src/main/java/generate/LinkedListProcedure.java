package generate;

import generate.result.GraphResult;
import org.neo4j.graphdb.Node;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.PerformsWrites;
import org.neo4j.procedure.Procedure;

import java.util.List;
import java.util.stream.Stream;

public class LinkedListProcedure extends GraphgenProcedure {

    @Procedure
    @PerformsWrites
    public Stream<GraphResult> linkedList(@Name("nodes") List<Node> nodes, @Name("relationshipType") String relationshipType) {
        return Stream.of(getGraphgenService().graphGenerator().generateLinkedList(nodes, relationshipType));
    }

}
