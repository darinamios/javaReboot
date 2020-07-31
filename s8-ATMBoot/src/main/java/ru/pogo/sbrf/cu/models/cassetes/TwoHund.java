package ru.pogo.sbrf.cu.models.cassetes;

import org.springframework.stereotype.Component;
import ru.pogo.sbrf.cu.models.CassetteImpl;
import ru.pogo.sbrf.cu.ref.Nominal;

@Component
public class TwoHund extends CassetteImpl {

    public TwoHund() {
        super(Nominal.TWO_HUND, 0);
    }
}
