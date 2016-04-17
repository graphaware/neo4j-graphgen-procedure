package com.graphaware.neo4j.graphgen.util;

import org.neo4j.graphdb.Label;

import java.util.List;

public class LabelsUtil {

    public static Label[] fromInput(Object o) {
        if (o instanceof List) {
            List names = (List) o;
            Label[] labels = new Label[names.size()];
            int i = -1;
            for (Object n : names) {
                labels[++i] = Label.label(n.toString());
            }
            return labels;
        }

        return new Label[]{Label.label(o.toString())};
    }
}
