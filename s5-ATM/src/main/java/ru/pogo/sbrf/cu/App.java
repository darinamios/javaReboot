package ru.pogo.sbrf.cu;

import ru.pogo.sbrf.cu.atm.ATMImpl;
import ru.pogo.sbrf.cu.ref.Nominal;
import ru.pogo.sbrf.cu.utils.IOWorking;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    private static IOWorking util = new IOWorking();
    private static Scanner scanner = new Scanner(System.in);
    public static void main( String[] args )  {
        // read args (load form file?)
        String path = "";
        if (args.length > 0)
            path = args[0];
        var atm = loadATM(path);
        var inMenu = true;
        while(inMenu) {
            System.out.println("Hello!\n 1 - make money\n 2 - get money\n 3 - get balance\n 4 - exit");
            var menu = scanner.nextInt();
            switch (menu){
                case 1:{
                    depositeMoney(atm);
                    break;
                }
                case 2:{
                    receiveMoney(atm);
                    break;
                }
                case 3:{
                    System.out.println("Balance of atm is :" + atm.getTotalSum());
                    break;
                }
                case 4:{
                    scanner.close();
                    inMenu = false;
                    break;
                }
            }
        }
        util.writeToFile(atm, "result.json");
     }

    private static void depositeMoney(ATMImpl atm){
        var pack = new ArrayList<Nominal>();
        System.out.println("Make bills...");
        var inStr = scanner.next();
        inStr += scanner.nextLine();
        var strScanner = new Scanner(inStr);
        while(strScanner.hasNextInt()){
            var inBill = strScanner.nextInt();
            var nominal = Nominal.getByNominal(inBill);
            if (nominal == null){
                System.out.println("Your bill does not fit :" + inBill);
            } else {
                pack.add(nominal);
             //   System.out.println("add" + inBill);
            }
        }
        atm.loadMoney(pack);
    }

    private static void receiveMoney(ATMImpl atm){
        try{
            System.out.println("Enter amount...");
            var sum = scanner.nextInt();
            for (var bill : atm.receiveMoney(sum)){
                System.out.print(bill.getValue() + " ");
            }
        } catch (Exception e){
            System.out.println("Not enough money");
        }
    }

    private static ATMImpl loadATM(String path){
        System.out.println("path = " + path);
        var atm = new ATMImpl();
        if(path != null){
            atm = util.readFromFile(path);
        }
        else {
            loadATMbyDefault(atm);
        }
         return atm;
    }

    private static void loadATMbyDefault(Depositing atm){
        var pack = new ArrayList<Nominal>();
        pack.add(Nominal.FIVE_THOUS);
        pack.add(Nominal.TWO_THOUS);
        pack.add(Nominal.ONE_HUND);
        pack.add(Nominal.ONE_HUND);
        pack.add(Nominal.TWO_HUND);
        pack.add(Nominal.TWO_HUND);
        atm.loadMoney(pack);
    }
}
