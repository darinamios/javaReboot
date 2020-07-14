package ru.pogo.sbrf.cu.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.pogo.sbrf.cu.ref.Nominal;
import lombok.Data;
@Data
public class CashPair {
    private Nominal nominal;
    private Integer count;

    @JsonCreator
    public CashPair(@JsonProperty("nominal") Nominal nominal, @JsonProperty("count") Integer count){
        this.nominal = nominal;
        this.count = count;
    }
}
