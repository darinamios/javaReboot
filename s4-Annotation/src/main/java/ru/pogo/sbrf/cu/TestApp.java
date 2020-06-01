package ru.pogo.sbrf.cu;

import ru.pogo.sbrf.cu.annotations.After;
import ru.pogo.sbrf.cu.annotations.Before;
import ru.pogo.sbrf.cu.annotations.Test;

public class TestApp {

    public TestApp(){
        System.out.println("constructor");
    }

    @Before
    public void prepare(){
        System.out.println("Run prepare");
    }

    @Before
    public void prepare1(){
        System.out.println("Run prepare1");
    }

    @Before
    public void prepare2(){
        System.out.println("Run prepare2");
    }

    @Test
    public void test1(){
        System.out.println("Run test1");
    }

    @Test
    public void test2(){
        System.out.println("Run test2");
    }

    @Test
    public void test3(){
        System.out.println("Run test3");
        throw new RuntimeException();
    }

    @Test
    public void test4(){
        System.out.println("Run test4");
    }

    @Test
    public void test5(){
        System.out.println("Run test5");
        throw new RuntimeException();
    }

    @Test
    public void test6(){
        System.out.println("Run test6");
        throw new RuntimeException();
    }

    @Test
    public void test7(){
        System.out.println("Run test7");
    }

    @Test
    public void test8(){
        System.out.println("Run test8");
    }

    @Test
    public void test9(){
        System.out.println("Run test9");
        throw new RuntimeException();
    }

    @Test
    public void test10(){
        System.out.println("Run test10");
    }

    @After
    public void completion(){
        System.out.println("Run completion");
    }

    @After
    public void completion1(){
        System.out.println("Run completion1");
    }
}
