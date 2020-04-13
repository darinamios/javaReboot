package ru.pogo.sbrf.cu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        int arraySize = 40;
        Random random = new Random();
        ArrayList<Integer> iList = new ArrayList<>();
        DIYArrayList<Integer> test = new DIYArrayList<>();
        DIYArrayList<Integer> dest = new DIYArrayList<>();
        for (int i = 0; i < arraySize; i++){
            iList.add(random.nextInt());
        }
        test.addAll(iList);
        dest.addAll(iList);

        System.out.println(test.size());
        for (int i = 0; i < test.size(); i ++){
            System.out.println(test.get(i) + " = " + iList.get(i));
        }
        Collections.sort(test);
        for (int i = 0; i < test.size(); i ++){
            System.out.println(test.get(i));
        }
        Collections.copy(dest, test);
        for (int i = 0; i < dest.size(); i ++){
            System.out.println(dest.get(i));
        }
    }
}
