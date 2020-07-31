package ru.pogo.sbrf.cu.services;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.pogo.sbrf.cu.exceptions.IncorrectValue;
import ru.pogo.sbrf.cu.exceptions.NoSuchNominal;
import ru.pogo.sbrf.cu.exceptions.NotAvailableRequestCount;
import ru.pogo.sbrf.cu.models.ATM;
import ru.pogo.sbrf.cu.models.ATMImpl;
import ru.pogo.sbrf.cu.ref.Nominal;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName(value="Банкомат должен...")
class ATMImplTest {

    ATM atm, atmReceive;
    List<Nominal> testLoadPack;
    ATMWork service;

    @BeforeEach
    void prepareATM(){
        atmReceive = new ATMImpl();
        atm = new ATMImpl();
        service = new ATMWorkImpl();
        testLoadPack = new ArrayList<>();
        testLoadPack.add(Nominal.ONE_HUND);
        testLoadPack.add(Nominal.ONE_HUND);
        testLoadPack.add(Nominal.TWO_THOUS);
        var testReceivePack = new ArrayList<Nominal>();
        testReceivePack.add(Nominal.FIVE_HUND);
        testReceivePack.add(Nominal.FIVE_HUND);
        testReceivePack.add(Nominal.ONE_THOUS);
        testReceivePack.add(Nominal.FIVE_THOUS);
        service.loadMoney(testReceivePack, atmReceive);
    }

    @DisplayName(value=" принимать пустую пачку денег, не меняя ячейки")
    @Test
    void loadMoneyZero() {
        service.loadMoney(new ArrayList<>(), atm);
        assertEquals(0, atm.getTotalSum());
    }

    @DisplayName(value=" принимать 2*100 и 1*2000, вернуть сумму 2200")
    @Test
    void loadMoney2200() {
        service.loadMoney(testLoadPack, atm);
        assertEquals(2200, atm.getTotalSum());
    }

    @DisplayName(value=" не выдавать деньги, если сумма отрицательна")
    @Test
    void receiveMoneySumMinus() {
        assertThrows(IncorrectValue.class, () -> {
            service.receiveMoney( -1, atmReceive);
        });
    }

    @DisplayName(value=" не выдавать деньги, если сумма больше имеющейся в банкомате")
    @Test
    void receiveMoneyNotEnough() {
        assertThrows(NotAvailableRequestCount.class, () -> {
            service.receiveMoney( 100000, atmReceive);
        });
    }

    @DisplayName(value=" не выдавать деньги, если сумма некорректная")
    @Test
    void receiveMoneyIncorrectSum() {
        assertThrows(NoSuchNominal.class, () -> {
            service.receiveMoney(50, atmReceive);
        });
    }

    @DisplayName(value=" не выдавать деньги, если нет подходящего номинала")
    @Test
    void receiveMoneyHaveNotNominal() {
        assertThrows(NoSuchNominal.class, () -> {
            service.receiveMoney(300, atmReceive);
        });
    }

    @SneakyThrows
    @DisplayName(value=" выдавать деньги, если есть подходящий номинал, уменьшая баланс")
    @Test
    void receiveMoney() {
        var result = service.receiveMoney(1500, atmReceive);
        assertEquals(true, result.contains(Nominal.ONE_THOUS));
        assertEquals(true, result.contains(Nominal.FIVE_HUND));
        assertEquals(5500, atmReceive.getTotalSum());
    }

    @SneakyThrows
    @DisplayName(value=" выдавать все деньги, если есть подходящий номинал, обнулив баланс")
    @Test
    void receiveAllMoney() {
        var result = service.receiveMoney(7000, atmReceive);
        assertEquals(true, result.contains(Nominal.ONE_THOUS));
        assertEquals(true, result.contains(Nominal.FIVE_HUND));
        assertEquals(true, result.contains(Nominal.FIVE_THOUS));
        assertEquals(0, atmReceive.getTotalSum());
    }

    @SneakyThrows
    @DisplayName(value=" выдавать деньги, минимальным количеством купюр, меняя баланс")
    @Test
    void receiveMinMoney() {
        var result = service.receiveMoney(1000, atmReceive);
        assertEquals(true, result.contains(Nominal.ONE_THOUS));
        assertEquals(6000, atmReceive.getTotalSum());
    }
}