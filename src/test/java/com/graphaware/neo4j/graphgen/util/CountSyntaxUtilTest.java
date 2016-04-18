package com.graphaware.neo4j.graphgen.util;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class CountSyntaxUtilTest {

    private Random random;

    @Before
    public void setUp() {
        random = new Random();
    }

    @Test
    public void testSimpleIntegerStringsAreParsed() {
        assertEquals(1, CountSyntaxUtil.getCount("1", random));
        assertEquals(10, CountSyntaxUtil.getCount("10", random));
        assertEquals(125, CountSyntaxUtil.getCount("125", random));
    }

    @Test
    public void testRangeStringsAreParsed() {
        for (int i = 0; i < 10000; ++i) {
            int v = CountSyntaxUtil.getCount("1-100", random);
            assertTrue(v <= 100 && v >= 0);
        }
    }

    @Test
    public void testExceptionIsThrownWhenSingleValueCannotBeParsed() {
        try {
            int i = CountSyntaxUtil.getCount("b", random);
            assertEquals(1, 2); // bug if here
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Unable to parse count"));
        }
    }

    @Test
    public void testExceptionIsThrownWhenSeparatorIsNotValid() {
        try {
            int i = CountSyntaxUtil.getCount("1:10", random);
            assertEquals(1, 2); // bug if here
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Unable to parse count"));
        }
    }

    @Test
    public void testExceptionIsThrownWhenValuesWithSepAreInvalid() {
        try {
            int i = CountSyntaxUtil.getCount("1-b", random);
            assertEquals(1, 2);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Unable to parse count"));
        }

        try {
            int i = CountSyntaxUtil.getCount("b-1", random);
            assertEquals(1, 2);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Unable to parse count"));
        }
    }

    @Test
    public void testExceptionIsThrownWhenFirstIsGreaterThanSecond() {
        try {
            int i = CountSyntaxUtil.getCount("10-5", random);
            assertEquals(1, 2);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("first value should be greater than second value"));
        }
    }

    @Test
    public void testExceptionIsThrownWhenIfValueIsZero() {
        try {
            int i = CountSyntaxUtil.getCount("0", random);
            assertEquals(1, 2);
        } catch (IllegalArgumentException e) {
            assertEquals(1, 1);
        }

        try {
            int i = CountSyntaxUtil.getCount("0-10", random);
            assertEquals(1, 2);
        } catch (IllegalArgumentException e) {
            assertEquals(1, 1);
        }

        try {
            int i = CountSyntaxUtil.getCount("10-0", random);
            assertEquals(1, 2);
        } catch (IllegalArgumentException e) {
            assertEquals(1, 1);
        }
    }

}
