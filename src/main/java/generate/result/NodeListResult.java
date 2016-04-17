package generate.result;

import org.neo4j.graphdb.Node;

import java.util.ArrayList;
import java.util.List;

public class NodeListResult {

    public final List<Node> nodes;

    public NodeListResult(List<Node> nodes) {
        this.nodes = nodes;
    }

    public static NodeListResult fromNode(Node node) {
        List<Node> nodes = new ArrayList<>();
        nodes.add(node);

        return new NodeListResult(nodes);
    }

}
