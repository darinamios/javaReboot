package ru.pogo.sbrf.cu.services;

import ru.pogo.sbrf.cu.models.ATM;
import ru.pogo.sbrf.cu.ref.Nominal;

import java.util.List;

public interface ATMWork {
    void loadATMbyDefault(ATM atm);
    List<Nominal> receiveMoney(Integer sum, ATM atm);
    int loadMoney(List<Nominal> pack, ATM atm);
}
