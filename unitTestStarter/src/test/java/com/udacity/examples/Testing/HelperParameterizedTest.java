package com.udacity.examples.Testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertNotEquals;

/**
 * Parameterized Tests allow you to run the SAME test again
 * using different values
 * */
@RunWith(Parameterized.class) // define class to be Parameterized test class
public class HelperParameterizedTest {

    private String input;
    private String output;

    public HelperParameterizedTest(String input, String output) {
        super();
        this.input = input;
        this.output = output;
    }

    // serve as a data provider:
    @Parameterized.Parameters
    public static Collection initData() {
        String empNames[][] = {{"plora", "plora"}, {"plora", "Jeff"}};
        // return as a Collection:
        return Arrays.asList(empNames);
    }

    // without parameters:
    @Test
    public void verifyInputOutputName() {
        assertNotEquals(input, output);
    }


}
