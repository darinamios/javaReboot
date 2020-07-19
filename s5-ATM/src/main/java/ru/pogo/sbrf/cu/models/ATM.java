package ru.pogo.sbrf.cu.models;

import ru.pogo.sbrf.cu.ref.Nominal;

import java.util.List;

public interface ATM {
    Integer getTotalSum();
    int loadMoney(List<Nominal> pack);
    List<Nominal> receiveMoney(Integer sum) throws Exception;
    Long getId();
    List<Cassette> getCassettes();
    void setCassettes(List<Cassette> cassetes);
    void setTotalSum(Integer totalSum);
}
