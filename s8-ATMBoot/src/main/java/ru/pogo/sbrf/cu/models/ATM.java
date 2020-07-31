package ru.pogo.sbrf.cu.models;
import java.util.List;

public interface ATM {
    Integer getTotalSum();
    List<Cassette> getCassettes();
    void setTotalSum(Integer totalSum);
}
