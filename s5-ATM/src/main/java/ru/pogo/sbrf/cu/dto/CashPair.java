package ru.pogo.sbrf.cu.dto;

import ru.pogo.sbrf.cu.ref.Nominal;
import lombok.Data;
@Data
public class CashPair {
    private Nominal nominal;
    private Integer count;
    public CashPair(Nominal nominal, Integer count){
        this.nominal = nominal;
        this.count = count;
    }
}
