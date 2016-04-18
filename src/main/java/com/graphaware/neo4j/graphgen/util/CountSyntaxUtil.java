package com.graphaware.neo4j.graphgen.util;


import java.util.Random;

public class CountSyntaxUtil {

    public static int getCount(String syntax, Random random) {
        try {
            int i =  Integer.parseInt(syntax);
            checkZero(i, syntax);

            return i;
        } catch (NumberFormatException e) {
            //
        }

        String[] parts = syntax.split("-");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Unable to parse count variable " + syntax);
        }

        try {
            int i1 = Integer.parseInt(parts[0]);
            int i2 = Integer.parseInt(parts[1]);
            checkZero(i1, syntax);
            checkZero(i2, syntax);

            if (i1 >= i2) {
                throw new IllegalArgumentException("first value should be greater than second value in " + syntax);
            }

            return random.nextInt(i2 - i1) + i1;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Unable to parse count variable " + syntax);
        }

    }

    private static void checkZero(int i, String syntax) {
        if (0 == i) {
            throw new IllegalArgumentException("Value cannot be zero in " + syntax);
        }
    }

}
