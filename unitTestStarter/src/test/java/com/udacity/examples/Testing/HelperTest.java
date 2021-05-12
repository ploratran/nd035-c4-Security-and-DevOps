package com.udacity.examples.Testing;

import org.junit.Test;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class HelperTest {
	@Test
    public void testGetCount() {
        List<String> empNames = Arrays.asList("sareeta","john");
        final long actual = Helper.getCount(empNames);
        assertEquals(2, actual);
    }

    @Test
    public void testSummaryStatistics() {
        List<Integer> yrsOfExperience = Arrays.asList(13,4,15,6,17,8,19,1,2,3);
        List<Integer> expectedList = Arrays.asList(13,4,15,6,17,8,19,1,2,3);
        IntSummaryStatistics stats = Helper.getStats(yrsOfExperience);
        assertEquals(19, stats.getMax());
        assertEquals(expectedList, yrsOfExperience);
    }

    @Test
    public void compareArrays() {
	    int[] years = {10, 14, 2};
	    int[] expectedYrs = {10, 14, 2};
	    assertArrayEquals(expectedYrs, years);
    }

    @Test
    public void testGetStringsOfLength3() {
        List<String> empNames = Arrays.asList("sareeta", "", "john","", "abc");
        final long expected = Helper.getStringsOfLength3(empNames);
        assertEquals(1, expected);
    }

    @Test
    public void testGetSquareList() {
        List<Integer> empLevel = Arrays.asList(3,3,3,5,7,2,2,5,7,5);
        List<Integer> expected = Helper.getSquareList(empLevel);
        assertEquals(Arrays.asList(9,25,49,4), expected);
    }

    @Test
    public void testGetMergedList() {
        List<String> empNames = Arrays.asList("sareeta", "", "john","");
        String expected = Helper.getMergedList(empNames);
        assertEquals("sareeta, john", expected);
    }

    @Test
    public void testGetFilteredList() {
        List<String> empNames = Arrays.asList("sareeta", "", "john","");
        List<String> expected = Helper.getFilteredList(empNames);
        assertEquals(Arrays.asList("sareeta", "john"), expected);
    }
}
