package ru.pogo.sbrf.cu.cassette;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.pogo.sbrf.cu.exceptions.IncorrectValue;
import ru.pogo.sbrf.cu.exceptions.NotAvailableRequestCount;
import ru.pogo.sbrf.cu.ref.Nominal;
@Getter
@AllArgsConstructor
public class CassetteImpl implements Cassette {

    private final Nominal nominal;
    private Integer count;

   public CassetteImpl(Nominal nominal){
       this.nominal = nominal;
       this.count = 0;
   }
    @Override
    public void add(int count) {
       if (count < 0) throw new IncorrectValue();
       this.count += count;
    }

    @Override
    public void extract(int count) {
        if (count < 0 ) throw new IncorrectValue();
        if (this.count - count < 0) throw new NotAvailableRequestCount();
        this.count -= count;
    }

    @Override
    public int count() {
        return this.count;
    }
}
