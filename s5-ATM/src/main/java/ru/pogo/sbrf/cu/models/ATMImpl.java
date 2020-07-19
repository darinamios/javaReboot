package ru.pogo.sbrf.cu.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pogo.sbrf.cu.exceptions.IncorrectValue;
import ru.pogo.sbrf.cu.exceptions.NoSuchNominal;
import ru.pogo.sbrf.cu.exceptions.NotAvailableRequestCount;
import ru.pogo.sbrf.cu.ref.Nominal;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "atm")
public class ATMImpl implements ATM {
    private static Logger logger = LoggerFactory.getLogger(ATMImpl.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @OneToMany(targetEntity = CassetteImpl.class, mappedBy = "atm", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@JoinColumn(name="atm_id")
    private List<Cassette> cassettes;

    @Column(name = "total")
    private Integer totalSum;

   @JsonCreator
   public ATMImpl(@JsonProperty("balance")  List<Cassette> balance, @JsonProperty("totalSum") Integer totalSum) {
       System.out.println("JsonCreator makes object...");
       cassettes = new ArrayList<>();
       for (var nominal : balance)
           cassettes.add(new CassetteImpl(nominal.getNominal(), nominal.count()));
       this.totalSum = totalSum;
   }
    public ATMImpl(){
       setCassettes(getDefaultCassettes());
       totalSum = 0;
    }

    @Override
    public Integer getTotalSum() {
        return totalSum;
    }

    @Override
    public int loadMoney(List<Nominal> pack){
        var sum = 0;
        for (var nominal : pack){
            for (var cassette : cassettes){
                if (nominal == cassette.getNominal()){
                    cassette.add(1);
                    sum += cassette.getNominal().getValue();
                    break;
                }
            }
        }
        totalSum += sum;
        logger.info("load money sum:{}", sum);
        return sum;
    }

    @Override
    public List<Nominal> receiveMoney(Integer sum){
        if(sum < 0) throw new IncorrectValue();
        if(sum > totalSum) throw new NotAvailableRequestCount();
        var result = new ArrayList<Nominal>();
        int curSum = 0;
        for(var nominal : Nominal.getSortedValues()){
           if (nominal.getValue() > sum) continue;
            for(var cassette : cassettes) {
                if (cassette.getNominal() == nominal && cassette.count() > 0) {
                    int cnt = cassette.count();
                    for (var i = 0; i < cnt; i++) {
                        if ((curSum + nominal.getValue()) > sum) break;
                        curSum += nominal.getValue();
                        cassette.extract(1);
                        result.add(nominal);
                    }
                    break;
                }
            }
        }
        totalSum -= curSum;
        if (curSum != sum){
            loadMoney(result);
            throw new NoSuchNominal();
        }
        logger.info("receive money sum:{}", sum);
        return result;
    }
    public Long getId() {
        return id;
    }

    @Override
    public List<Cassette> getCassettes() {
        return cassettes;
    }

    @Override
    public void setCassettes(List<Cassette> cassettes) {
        this.cassettes = cassettes;
    }

    @Override
    public void setTotalSum(Integer totalSum) {
        this.totalSum = totalSum;
    }

    public List<Cassette> getDefaultCassettes(){
        var res  = new ArrayList<Cassette>();
        for (var nominal : Nominal.values())
            res.add(new CassetteImpl(nominal, 0, this));
        return res;
    }

    @Override
    public String toString() {
        return "ATM{" +
                "id=" + id +
                ", total='" + totalSum + '\'' +
                ", cassettes=" + cassettes.size() +
                '}';
    }
}
