package ru.pogo.sbrf.cu.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.pogo.sbrf.cu.exceptions.IncorrectValue;
import ru.pogo.sbrf.cu.exceptions.NoSuchNominal;
import ru.pogo.sbrf.cu.exceptions.NotAvailableRequestCount;
import ru.pogo.sbrf.cu.models.ATM;
import ru.pogo.sbrf.cu.ref.Nominal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ATMWorkImpl implements ATMWork {
    private static Logger logger = LoggerFactory.getLogger(ATMWorkImpl.class);

    @Override
    public void loadATMbyDefault(ATM atm){
        logger.info("load by default");
        var pack = Arrays.asList(Nominal.FIVE_THOUS, Nominal.TWO_THOUS, Nominal.ONE_HUND,
                Nominal.ONE_HUND, Nominal.TWO_HUND, Nominal.TWO_HUND);
        loadMoney(pack, atm);
    }

    @Override
    public List<Nominal> receiveMoney(Integer sum, ATM atm){
        logger.info("receive money with sum:{} ", sum);
        if(sum < 0) throw new IncorrectValue();
        if(sum > atm.getTotalSum()) throw new NotAvailableRequestCount();
        var result = new ArrayList<Nominal>();
        var curSum = accumulateSum(result, atm, sum);
        atm.setTotalSum(atm.getTotalSum() - curSum);
        if (curSum != sum){
            logger.error("rollback sum: no such nominal in atm");
            loadMoney(result, atm);
            throw new NoSuchNominal();
        }
        logger.info("receive money sum:{}", sum);
        return result;
    }

    @Override
    public int loadMoney(List<Nominal> pack, ATM atm){
        var sum = 0;
        for (var nominal : pack){
            for (var cassette : atm.getCassettes()){
                if (nominal == cassette.getNominal()){
                    cassette.add(1);
                    sum += cassette.getNominal().getValue();
                    break;
                }
            }
        }
        atm.setTotalSum(atm.getTotalSum() + sum);
        logger.info("load money sum:{}", sum);
        return sum;
    }

    private int accumulateSum(List<Nominal> result, ATM atm, int sum){
        int curSum = 0;
        for(var nominal : Nominal.getSortedValues()){
            if (nominal.getValue() > sum) continue;
            for(var cassette : atm.getCassettes()) {
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
        return curSum;
    }
}
