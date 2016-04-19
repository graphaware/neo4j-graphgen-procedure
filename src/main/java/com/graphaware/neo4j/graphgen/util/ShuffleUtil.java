/*
 * Copyright (c) 2013-2016 GraphAware
 *
 * This file is part of the GraphAware Framework.
 *
 * GraphAware Framework is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received a copy of
 * the GNU General Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.graphaware.neo4j.graphgen.util;

import com.google.common.collect.Range;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShuffleUtil {

    public static List<Integer> shuffle(List<?> list, int count) {
        List<Integer> listClone = new ArrayList<>();
        for (int i = 0; i < list.size(); ++i) {
            listClone.add(i);
        }
        if (list.size() == count) {
            return listClone;
        }
        Collections.shuffle(listClone);

        return listClone.subList(0, count);
    }

}
