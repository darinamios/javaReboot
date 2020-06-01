package ru.pogo.sbrf.cu;

import ru.pogo.sbrf.cu.atm.ATMImpl;

import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ATMImpl atm = new ATMImpl();
        var moneyToSet = new ArrayList<Integer>();
        moneyToSet.add(100);
        moneyToSet.add(100);
        moneyToSet.add(50);
        moneyToSet.add(50);
        moneyToSet.add(50);

       // atm.loadMoney(moneyToSet);
        atm.getTotalSum();

        try{
            System.out.println("Get your money...");
            for (var bill : atm.receiveMoney(270)){
                System.out.println(bill);
            }
        } catch (Exception e){
            System.out.println("Not enough money");
        }
        //atm.getAtmInfo();
    }
}
