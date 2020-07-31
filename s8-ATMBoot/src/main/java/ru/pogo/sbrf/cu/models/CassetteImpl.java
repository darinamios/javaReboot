package ru.pogo.sbrf.cu.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pogo.sbrf.cu.exceptions.IncorrectValue;
import ru.pogo.sbrf.cu.exceptions.NotAvailableRequestCount;
import ru.pogo.sbrf.cu.ref.Nominal;

public class CassetteImpl implements Cassette {
    private static Logger logger = LoggerFactory.getLogger(ATMImpl.class);
    private final Nominal nominal;
    private Integer count;

    @JsonCreator
    public CassetteImpl(@JsonProperty("nominal") Nominal nominal, @JsonProperty("count") Integer count) {
        this.nominal = nominal;
        this.count = count;
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

    @Override
    public Nominal getNominal() {
        return this.nominal;
    }

    @Override
    public String toString() {
        return "Cassete{" +
             /*   "id=" + id +
                ", atmId=" + atm +*/
                "count='" + count + '\'' +
                ", nominal" + nominal.getValue() +
                '}';
    }
}
