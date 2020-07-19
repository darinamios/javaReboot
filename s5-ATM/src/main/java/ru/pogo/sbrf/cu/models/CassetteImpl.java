package ru.pogo.sbrf.cu.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pogo.sbrf.cu.exceptions.IncorrectValue;
import ru.pogo.sbrf.cu.exceptions.NotAvailableRequestCount;
import ru.pogo.sbrf.cu.ref.Nominal;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "cassette")
public class CassetteImpl implements Cassette {
    private static Logger logger = LoggerFactory.getLogger(ATMImpl.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "nominal")
    private Nominal nominal;

    @Column(name = "count")
    private Integer count;

    @ManyToOne(targetEntity = ATMImpl.class)
    @JoinColumn(name="atm_id")
    private ATM atm;

    @JsonCreator
    public CassetteImpl(@JsonProperty("nominal") Nominal nominal, @JsonProperty("count") Integer count) {
        this.nominal = nominal;
        this.count = count;
    }

    public CassetteImpl(){

    }

    public CassetteImpl(Nominal nominal){
       this.nominal = nominal;
       this.count = 0;
   }

    public CassetteImpl(Nominal nominal, Integer count, ATM atm){
        this.nominal = nominal;
        this.count = count;
        this.atm = atm;
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
    public void setATM(ATM atm) {
        this.atm = atm;
    }

    @Override
    public String toString() {
        return "Cassete{" +
                "id=" + id +
                ", atmId=" + atm +
                ", count='" + count + '\'' +
                ", nominal" + nominal.getValue() +
                '}';
    }
}
