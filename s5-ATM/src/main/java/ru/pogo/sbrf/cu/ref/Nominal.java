package ru.pogo.sbrf.cu.ref;

import java.util.Arrays;
import java.util.Collections;

public enum Nominal {
    ONE_NUND(100)
    ,TWO_HUND(200)
    ,FIVE_HUND(500)
    ,ONE_THOUS(1000)
    ,TWO_THOUS(2000)
    ,FIVE_THOUS(5000)
    ;

    private int value;

    Nominal(int nominal){
        this.value = nominal;
    }

    public int getValue(){
        return value;
    }

    public static Nominal getByNominal(int value){
        for(Nominal nominal: values()){
            if(nominal.value == value) return nominal;
        }
        return null;
    }
    public static Nominal[] getSortedValues(){
        var result = values();
        Arrays.sort(result, Collections.reverseOrder());
        return result;
    }
}
