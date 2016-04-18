package generate;

import generate.result.RelationshipListResult;
import org.neo4j.graphdb.Node;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.PerformsWrites;
import org.neo4j.procedure.Procedure;

import java.util.List;
import java.util.stream.Stream;

public class RelationshipProcedure extends GraphgenProcedure {

    @Procedure
    @PerformsWrites
    public Stream<RelationshipListResult> relationships(@Name("from") List<Node> from, @Name("to") List<Node> to, @Name("relationshipType") String relationshipType, @Name("properties") String properties, @Name("fromCount") Object fromCount, @Name("toCount") Object toCount) {
        return Stream.of(new RelationshipListResult(getGraphgenService().graphGenerator().generateRelationships(from, to, relationshipType, properties, fromCount.toString(), toCount.toString())));
    }

}
