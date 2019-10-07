package com.geekbrains.ru.lesson8;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.assertEquals;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void addition_isCorrect() {


        assertEquals(4, 2 + 2);
    }
}