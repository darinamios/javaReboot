package ru.pogo.sbrf.cu.atm;

import ru.pogo.sbrf.cu.Collector;
import ru.pogo.sbrf.cu.Depositing;
import ru.pogo.sbrf.cu.Receiving;
import ru.pogo.sbrf.cu.cassette.Cassette;
import ru.pogo.sbrf.cu.cassette.CassetteImpl;
import ru.pogo.sbrf.cu.dto.CashPair;
import ru.pogo.sbrf.cu.exceptions.IncorrectValue;
import ru.pogo.sbrf.cu.exceptions.NoSuchNominal;
import ru.pogo.sbrf.cu.exceptions.NotAvailableRequestCount;
import ru.pogo.sbrf.cu.ref.Nominal;

import java.util.*;
import java.util.stream.Collectors;

public class ATMImpl implements Collector, Depositing, Receiving {
    private List<Cassette> cassettes;
    private Integer totalSum;

    public ATMImpl(){
        cassettes = new ArrayList<>();
       for (var nominal : Nominal.values())
           cassettes.add(new CassetteImpl(nominal));
        totalSum = 0;
    }

    @Override
    public Integer getTotalSum() {
        return totalSum;
    }

    @Override
    public List<CashPair> getBalance(){
        return  cassettes.stream()
                .map(cassette -> new CashPair(cassette.getNominal(), cassette.count()))
                .collect(Collectors.toList());
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
        return result;
    }

  /*  private void summ(){
        Optional<Integer> summ = cassettes.stream()
                .map(cassette -> cassette.getNominal().getValue()*cassette.count())
                .reduce((x1,x2)->x1+x2);
    }*/
}
