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
 * Reference: https://github.com/junit-team/junit4/wiki/Parameterized-tests
 * Reference: https://www.tutorialspoint.com/junit/junit_parameterized_test.htm
 * */
@RunWith(Parameterized.class) // define class to be Parameterized test class
public class HelperParameterizedTest {

    private String input;
    private String output;

    // initialize Constructor of the class:
    public HelperParameterizedTest(String input, String output) {
        super();
        this.input = input;
        this.output = output;
    }

    // serve as a data provider:
    @Parameterized.Parameters
    public static Collection<String[]> initData() {
        // Return as a Collection of String[]:
        return Arrays.asList(new String[][]{{"plora", "plora"}, {"plora", "Jeff"}});
    }

    // without parameters:
    @Test
    public void verifyInputOutputName() {
        assertNotEquals(input, output);
    }


}
