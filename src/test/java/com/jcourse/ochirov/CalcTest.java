package com.jcourse.ochirov;

import com.jcourse.ochirov.seminar3.Calc;
import com.jcourse.ochirov.seminar3.Commands.Command;
import com.jcourse.ochirov.seminar3.Parser.ConsoleParser;
import com.jcourse.ochirov.seminar3.Parser.FileParser;
import com.jcourse.ochirov.seminar3.Parser.Parser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static org.junit.Assert.*;

public class CalcTest {

    private Calc calc;

/*    @Before
    public void setUp() throws Exception{
        calc = new Calc();
    }

 */

    @After
    public void tearDown() throws Exception{
        System.out.println("Тест завершен");
    }

    @Test
    public void testCalculator() {
        Class<? extends CalcTest> aClass = getClass();
        InputStream stream = aClass
                .getClassLoader()
                .getResourceAsStream("calc-test.txt");
        Stack<Double> stack = new Stack<Double>();
        Map<String, Double> variables = new HashMap<>();
//        Parser parser = new ConsoleParser();
        Parser parser = new FileParser(stream);
        Calc calc = new Calc(parser);
        calc.execute();
        Assert.assertEquals(-3.0, stack.peek(), 0.01);

    }

/*    static void staticMethod() {
        Class<CalcTest> calcTestClass = CalcTest.class;
    }*/
}
/*interface Parser{
    List<Command> getCommand(InputStream);
}*/