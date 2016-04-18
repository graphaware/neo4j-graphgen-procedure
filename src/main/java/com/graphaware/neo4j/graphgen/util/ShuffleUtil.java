package com.graphaware.neo4j.graphgen.util;

import com.google.common.collect.Range;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShuffleUtil {

    public static List<Integer> shuffle(List<?> list, int count) {
        List<Integer> listClone = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            listClone.add(i);
        }
        if (list.size() == count) {
            return listClone;
        }
        Collections.shuffle(listClone);

        return listClone.subList(0, count);
    }

}
