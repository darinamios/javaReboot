package ru.pogo.sbrf.cu;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pogo.sbrf.cu.dao.ATMDaoImpl;
import ru.pogo.sbrf.cu.models.ATM;
import ru.pogo.sbrf.cu.models.ATMImpl;
import ru.pogo.sbrf.cu.models.Cassette;
import ru.pogo.sbrf.cu.models.CassetteImpl;
import ru.pogo.sbrf.cu.ref.Nominal;
import ru.pogo.sbrf.cu.service.DBServiceATMImpl;
import ru.pogo.sbrf.cu.session.SessionManagerImpl;
import ru.pogo.sbrf.cu.utils.HibernateUtils;
import ru.pogo.sbrf.cu.utils.IOWorking;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App
{
    private static final IOWorking util = new IOWorking();
    private static Scanner scanner = new Scanner(System.in);
    private static Logger logger = LoggerFactory.getLogger( App.class );
    private static DBServiceATMImpl dbService;

    public static void main( String[] args )  {
        var sessionFactory = new HibernateUtils().getSessionFactory();
        var sessionManager = new SessionManagerImpl(sessionFactory);
        dbService = new DBServiceATMImpl(new ATMDaoImpl(sessionManager));
        String path = "";
        if (args.length > 0)
            path = args[0];
        var id = loadATM(path);
        show(sessionFactory);
        showMenu(id);
        var atm = dbService.getATM(id);
        if (atm.get() != null) {
            util.writeToFile((ATMImpl) atm.get(), "result.json");
        }
        System.out.println("in exit");
    }
    private static void show(SessionFactory sessionFactory){
        var entityManager = sessionFactory.createEntityManager();
        logger.info( "select atm list:" );
        List<ATMImpl> atms = entityManager.createQuery(
                "select p from ATMImpl p", ATMImpl.class )
                .getResultList();
        logger.info( "atms:{}", atms );
        logger.info( "select atm list:" );
        List<CassetteImpl> selecteCassetes = entityManager.createQuery(
                "select p from CassetteImpl p", CassetteImpl.class )
                .getResultList();
        logger.info( "selecteCassettes:{}", selecteCassetes );
    }
    private static void showMenu(Long atmId){
        var inMenu = true;
        while (inMenu) {
            System.out.println("Hello!\n 1 - make money\n 2 - get money\n 3 - get balance\n 4 - exit");
            var menu = scanner.nextInt();
            switch (menu) {
                case 1: {
                    logger.info("call deposite for atm id:{}", atmId);
                    var atm = dbService.getATM(atmId);
                    if (atm != null) {
                        depositeMoney(atm.get());
                        dbService.saveATM(atm.get());
                    }
                    break;
                }
                case 2: {
                    logger.info("call receive for atm id:{}", atmId);
                    var atm = dbService.getATM(atmId);
                    if (atm != null) {
                        receiveMoney(atm.get());
                        dbService.saveATM(atm.get());
                    }
                    break;
                }
                case 3: {
                    logger.info("call balance for atm id:{}", atmId);
                    var atm = dbService.getATM(atmId);
                    if (atm != null) {
                        System.out.println("Balance of atm is :" + atm.get().getTotalSum());
                    }
                    break;
                }
                case 4: {
                    logger.info("call exit");
                    scanner.close();
                    inMenu = false;
                    break;
                }
            }
        }
    }
    private static void depositeMoney(ATM atm){
        logger.info("start deposite");
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
                logger.info("not valid bill :{}", inBill );
            } else {
                pack.add(nominal);
                logger.info("add nominal to atm:{}", nominal);
            }
        }
        atm.loadMoney(pack);
    }

    private static void receiveMoney(ATM atm){
        logger.info("start receive");
        try{
            System.out.println("Enter amount...");
            var sum = scanner.nextInt();
            logger.info("sum for receive:{}", sum);
            for (var bill : atm.receiveMoney(sum)){
                System.out.print(bill.getValue() + " ");
                logger.info("issued bill:{}", bill.getValue());
            }
        } catch (Exception e){
            System.out.println("Not enough money");
            logger.error("Not enough money");
        }
    }

    private static Long loadATM(String path){
        logger.info("receive path:{}", path);
        var atm = new ATMImpl();
        var temp = new ATMImpl();
        if (!path.isEmpty()) {
            temp = util.readFromFile(path);
        } else {
            loadATMbyDefault(temp);
        }
        atm.setTotalSum(temp.getTotalSum());
        atm.setCassettes(temp.getCassettes());
        for(var cassette : atm.getCassettes()){
            cassette.setATM(atm);
        }
        return dbService.saveATM(atm);
    }

    private static void loadATMbyDefault(ATM atm){
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

