package ru.pogo.sbrf.cu.shell;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.pogo.sbrf.cu.models.ATM;
import ru.pogo.sbrf.cu.models.ATMImpl;
import ru.pogo.sbrf.cu.ref.Nominal;
import ru.pogo.sbrf.cu.services.ATMWork;
import ru.pogo.sbrf.cu.services.IOWork;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@ShellComponent
@RequiredArgsConstructor
public class ATMCommands {

    private ATM atm;
    private final IOWork ioWork;
    private final ATMWork atmWork;
    private static Scanner scanner = new Scanner(System.in);
    private static Logger logger = LoggerFactory.getLogger(ATMCommands.class);

    @ShellMethod(value = "Load atm", key = {"load", "l"})
    public void load(@ShellOption(defaultValue = "") String path) {
       logger.info("call load");
       atm = new ATMImpl();
        if(path.isEmpty()){
           atmWork.loadATMbyDefault(atm);
        }
        else {
            atm = ioWork.readFromFile(path);
        }
    }
    @ShellMethod(value = "Print total atm", key = {"total", "t"})
    @ShellMethodAvailability(value = "isCommandAvailable")
    public void printTotal() {
        logger.info("call total");
        System.out.println(atm.getTotalSum());
    }

    @ShellMethod(value = "Get money", key = {"get", "g"})
    @ShellMethodAvailability(value = "isCommandAvailable")
    public void getMoney(@ShellOption(defaultValue = "0") String inSum) {
       var sum = 0;
       logger.info("call get with sum :{} ", inSum);
       try {
           sum = Integer.valueOf(inSum);
       }catch(NumberFormatException ex){
           logger.error("wrong format for input sum");
           System.out.println("Wrong format for input sum");
       }
        System.out.println(atmWork.receiveMoney(sum, atm));
    }

    @ShellMethod(value = "Set money", key = {"set", "s"})
    @ShellMethodAvailability(value = "isCommandAvailable")
    public void setMoney() {
        logger.info("call set");
        var pack = listenConsole();
        System.out.println(atmWork.loadMoney(pack, atm));
    }

    @ShellMethod(value = "Save state atm", key = {"save", "sv"})
    @ShellMethodAvailability(value = "isCommandAvailable")
    public void saveState(@ShellOption(defaultValue = "result.json") String path) {
        logger.info("call save");
        ioWork.writeToFile(atm, path);
    }

    private List<Nominal> listenConsole(){
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
                  logger.error("not valid bill :{}", inBill );
            } else {
                pack.add(nominal);
                logger.info("add nominal to atm:{}", nominal);
            }
        }
        return pack;
    }

    private Availability isCommandAvailable() {
        return atm == null? Availability.unavailable("Загрузите банкомат"): Availability.available();
    }
}
