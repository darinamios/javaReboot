package ru.pogo.sbrf.cu.atm;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.pogo.sbrf.cu.Depositing;
import ru.pogo.sbrf.cu.Receiving;
import ru.pogo.sbrf.cu.dto.CashPair;
import ru.pogo.sbrf.cu.exceptions.IncorrectValue;
import ru.pogo.sbrf.cu.exceptions.NoSuchNominal;
import ru.pogo.sbrf.cu.exceptions.NotAvailableRequestCount;
import ru.pogo.sbrf.cu.ref.Nominal;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName(value="Банкомат должен...")
class ATMImplTest {
    ATMImpl atmReceive;
    ATMImpl atmDeposite;
    List<Nominal> testLoadPack;

    @BeforeEach
    void prepareATM(){
        atmReceive = new ATMImpl();
        atmDeposite = new ATMImpl();
        testLoadPack = new ArrayList<>();
        testLoadPack.add(Nominal.ONE_HUND);
        testLoadPack.add(Nominal.ONE_HUND);
        testLoadPack.add(Nominal.TWO_THOUS);
        var testReceivePack = new ArrayList<Nominal>();
        testReceivePack.add(Nominal.FIVE_HUND);
        testReceivePack.add(Nominal.FIVE_HUND);
        testReceivePack.add(Nominal.ONE_THOUS);
        testReceivePack.add(Nominal.FIVE_THOUS);
        atmReceive.loadMoney(testReceivePack);
    }

    @DisplayName(value=" принимать пустую пачку денег, не меняя ячейки")
    @Test
    void loadMoneyZero() {
       atmDeposite.loadMoney(new ArrayList<>());
       assertEquals(0, atmDeposite.getTotalSum());
    }

    @DisplayName(value=" принимать 2*100 и 1*2000, вернуть сумму 2200")
    @Test
    void loadMoney2200() {
        atmDeposite.loadMoney(testLoadPack);
        assertEquals(2200, atmDeposite.getTotalSum());
    }

    @DisplayName(value=" принимать 2*100 и 1*2000, вернуть соответствующие пары")
    @Test
    void loadMoney2200Pairs() {
        atmDeposite.loadMoney(testLoadPack);
        var res = atmDeposite.getBalance();
        for (var cell : res){
            if (cell.getNominal() == Nominal.ONE_HUND){
                assertEquals(2, cell.getCount());
            }else if(cell.getNominal() == Nominal.TWO_THOUS){
                assertEquals(1, cell.getCount());
            }else{
                assertEquals(0, cell.getCount());
            }
        }
    }
    @DisplayName(value=" не выдавать деньги, если сумма отрицательна")
    @Test
    void receiveMoneySumMinus() {
        assertThrows(IncorrectValue.class, () -> {
            atmReceive.receiveMoney( -1);
        });
    }

    @DisplayName(value=" не выдавать деньги, если сумма больше имеющейся в банкомате")
    @Test
    void receiveMoneyNotEnough() {
        assertThrows(NotAvailableRequestCount.class, () -> {
            atmReceive.receiveMoney( 100000);
        });
    }

    @DisplayName(value=" не выдавать деньги, если сумма некорректная")
    @Test
    void receiveMoneyIncorrectSum() {
        assertThrows(NoSuchNominal.class, () -> {
            atmReceive.receiveMoney(50);
        });
    }

    @DisplayName(value=" не выдавать деньги, если нет подходящего номинала")
    @Test
    void receiveMoneyHaveNotNominal() {
        assertThrows(NoSuchNominal.class, () -> {
            atmReceive.receiveMoney(300);
        });
    }

    @DisplayName(value=" выдавать деньги, если есть подходящий номинал, уменьшая баланс")
    @Test
    void receiveMoney() {
        var result = atmReceive.receiveMoney(1500);
        assertEquals(true, result.contains(Nominal.ONE_THOUS));
        assertEquals(true, result.contains(Nominal.FIVE_HUND));
        assertEquals(5500, atmReceive.getTotalSum());
    }

    @DisplayName(value=" выдавать все деньги, если есть подходящий номинал, обнулив баланс")
    @Test
    void receiveAllMoney() {
        var result = atmReceive.receiveMoney(7000);
        assertEquals(true, result.contains(Nominal.ONE_THOUS));
        assertEquals(true, result.contains(Nominal.FIVE_HUND));
        assertEquals(true, result.contains(Nominal.FIVE_THOUS));
        assertEquals(0, atmReceive.getTotalSum());
    }

    @DisplayName(value=" выдавать деньги, минимальным количеством купюр, меняя баланс")
    @Test
    void receiveMinMoney() {
        var result = atmReceive.receiveMoney(1000);
        assertEquals(true, result.contains(Nominal.ONE_THOUS));
        assertEquals(6000, atmReceive.getTotalSum());
    }




}