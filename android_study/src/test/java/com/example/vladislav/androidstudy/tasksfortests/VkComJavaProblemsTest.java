package com.example.vladislav.androidstudy.tasksfortests;

import android.util.Log;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by vladislav on 10.03.17.
 */
public class VkComJavaProblemsTest {

    private VkComJavaProblems problems;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
//        Log.i(VkComJavaProblemsTest.class.getSimpleName(), " - @BeforeClass has been executed.");
        System.out.println(VkComJavaProblemsTest.class.getSimpleName() + " - @AfterClass has been executed.");
    }

    @Before
    public void setUpBefore() throws Exception {
        problems = new VkComJavaProblems();
    }

    @Test
    public void isBase2NumberTest1() throws Exception {
        assertEquals(problems.isBase2Number(16),true);
    }

    @Test
    public void isBase2NumberTest2() throws Exception {
        assertNotEquals(problems.isBase2Number(2),false);
    }

    @Test
    public void isBase2NumberTest3() throws Exception {
        assertFalse(problems.isBase2Number(5));
    }

    @Test
    public void isBase2NumberTest4() throws Exception {
        assertTrue(problems.isBase2Number(8));
    }

    @Test(expected = AssertionError.class)
    public void isBase2NumberTest5() throws Exception {
        assertEquals(problems.isBase2Number(999999999),true);
    }

    @Test
    public void isPerfectNumberTest1() throws Exception {
        assertArrayEquals(problems.isPerfectNumber(0,3), new int[]{0});
    }

    @Test(expected=NullPointerException.class)
    public void removeWordTest1() throws Exception {
        String[] strings = null;
        assertEquals(problems.removeWord(strings, ""),"");
    }

    @Test
    public void numberToDigitsArrayTest1() throws Exception {
        String[] strings = null;
        assertArrayEquals(problems.numberToDigitsArray(123),new int[]{1,2,3});
    }

    @After
    public void tearDownAfter() throws Exception {
        problems = new VkComJavaProblems();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        System.out.println(VkComJavaProblemsTest.class.getSimpleName() + " - @AfterClass has been executed.");
    }

}