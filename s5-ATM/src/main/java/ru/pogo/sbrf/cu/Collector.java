package ru.pogo.sbrf.cu;


import ru.pogo.sbrf.cu.dto.CashPair;

import java.util.List;

public interface Collector {
    List<CashPair> getBalance();
    Integer getTotalSum();
}
