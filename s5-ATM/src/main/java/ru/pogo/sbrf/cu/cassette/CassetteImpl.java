package ru.pogo.sbrf.cu.cassette;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pogo.sbrf.cu.atm.ATMImpl;
import ru.pogo.sbrf.cu.exceptions.IncorrectValue;
import ru.pogo.sbrf.cu.exceptions.NotAvailableRequestCount;
import ru.pogo.sbrf.cu.ref.Nominal;

import java.util.List;

@Getter
public class CassetteImpl implements Cassette {
    private static Logger logger = LoggerFactory.getLogger(ATMImpl.class);
    private final Nominal nominal;
    private Integer count;

    @JsonCreator
    public CassetteImpl(@JsonProperty("nominal") Nominal nominal, @JsonProperty("count") Integer count) {
        this.nominal = nominal;
        this.count = count;
    }

    public CassetteImpl(Nominal nominal){
       this.nominal = nominal;
       this.count = 0;
   }
    @Override
    public void add(int count) {
       if (count < 0) throw new IncorrectValue();
       this.count += count;
       logger.info("add nominal:{}", this.nominal);
    }

    @Override
    public void extract(int count) {
        if (count < 0 ) throw new IncorrectValue();
        if (this.count - count < 0) throw new NotAvailableRequestCount();
        this.count -= count;
        logger.info("exctract nominal:{}", this.nominal);
    }

    @Override
    public int count() {
        return this.count;
    }
}
