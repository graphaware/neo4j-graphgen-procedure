package generate;

import generate.result.ValueListResult;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.Procedure;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ValueProcedure extends GraphgenProcedure {

    @Procedure
    public Stream<ValueListResult> values(@Name("name") String name, @Name("parameters") List<Object> parameters, @Name("amount") Long amount) {
        List<Object> params = null != parameters ? parameters : new ArrayList<>();
        return Stream.of(new ValueListResult(getGraphgenService().graphGenerator().generateValues(name, params, amount)));
    }

}
