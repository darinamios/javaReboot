package ru.pogo.sbrf.cu;

import ru.pogo.sbrf.cu.annotations.After;
import ru.pogo.sbrf.cu.annotations.Before;
import ru.pogo.sbrf.cu.annotations.Test;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        String className = TestApp.class.getName();
        runTestsForClass(className);
    }

    public static void runTestsForClass(String className) throws Exception {
        final String SUCCESS = "SUCCESS";
        final String FAILED = "FAILED";

        Class<?> curClass = Class.forName(className);
        System.out.println("Run tests for class: " + curClass.getSimpleName());

        var tests = fillMethods(curClass);
        var results = new HashMap<String, Integer>();
        results.put(SUCCESS, 0);
        results.put(FAILED, 0);
        for(var test: tests.entrySet()){
            System.out.println("--------------------------");
            var instance = getInstance(curClass);
            for (var method : test.getValue())
                if(!method.getName().equals(test.getKey())) {
                    runMethod(method, instance);
                }
                else{
                    try{
                        runMethod(method, instance);
                        results.put(SUCCESS, results.get(SUCCESS) + 1);
                    } catch(Exception e){
                        results.put(FAILED, results.get(FAILED) + 1);
                    }
                }
        }
        printResults(results);
    }

    public static HashMap<String, ArrayList<Method>> fillMethods(Class<?> type){
        var allMethods = new HashMap<String, ArrayList<Method>>();
        var beforeTests = new ArrayList<Method>();
        var afterTests = new ArrayList<Method>();
        Method[] methods = type.getMethods();

        for(var i = 0; i < methods.length; i++){
            if (methods[i].isAnnotationPresent(Test.class)){
                allMethods.put(methods[i].getName(), new ArrayList<>(List.of(methods[i])));
            } else if (methods[i].isAnnotationPresent(Before.class)){
                beforeTests.add(methods[i]);
            } else if(methods[i].isAnnotationPresent(After.class)){
                afterTests.add(methods[i]);
            }
        }
        for (var test : allMethods.entrySet()) {
            var tmpArray = new ArrayList<Method>(beforeTests);
            tmpArray.addAll(test.getValue());
            tmpArray.addAll(afterTests);
            test.setValue(tmpArray);
        }
        return allMethods;
    }

    public static void runMethod(Method method, Object object) throws Exception {
        method.invoke(object);
    }

    public static <T> T getInstance(Class<T> type) throws Exception {
        return type.getDeclaredConstructor().newInstance();
    }
    public static  void printResults(HashMap<String,Integer> results){
        System.out.println("RESULTS...");
        int total = 0;
        for(var res : results.entrySet()){
            System.out.println(res.getKey() + " : " + res.getValue());
            total += res.getValue();
        }
        System.out.println("TOTAL" + " : " + total);
    }
}
